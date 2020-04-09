package edu.njit.cs114;

import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 3/31/20
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

    private BSTNode<K, V> root;
    private int size;

    public static class BSTNode<K extends Comparable<K>, V> implements BinTreeNode<K, V> {

        private K key;
        private V value;
        private int height;
        // number of nodes (including this node) in the subtree rooted at this node
        private int size;
        private BSTNode<K, V> left, right;

        public BSTNode(K key, V value, BSTNode<K, V> left, BSTNode<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;

            if (left == null && right == null)
                this.height = 1;
            else
                this.height = 1 + (Math.max((left == null ? 0 : left.height), (right == null ? 0 : right.height)));

            if (left == null && right == null)
                this.size = 1;
            else
                this.size = 1 + (left == null ? 0 : left.size) + (right == null ? 0 : right.size);
        }

        public BSTNode(K key, V value) {
            this(key, value, null, null);
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public BinTreeNode<K, V> leftChild() {
            return left;
        }

        @Override
        public BinTreeNode<K, V> rightChild() {
            return right;
        }

        @Override
        public boolean isLeaf() {
            return (left == null && right == null);
        }

        private void setLeftChild(BinTreeNode<K, V> node) {
            this.left = (BSTNode<K, V>) node;
        }

        private void setRightChild(BinTreeNode<K, V> node) {
            this.right = (BSTNode<K, V>) node;
        }

        /**
         * Returns height of left subtree - height of right subtree
         *
         * @return
         */
        @Override
        public int balanceFactor() {
            return (this.left == null ? 0 : this.left.height) - (this.right == null ? 0 : this.right.height);
        }


        private void copy(BSTNode<K, V> node) {
            this.key = node.key;
            this.value = node.value;
        }
    }


    public BSTNode<K, V> getRoot() {
        return root;
    }

    public BSTNode<K, V> insertAux(BSTNode<K, V> localRoot, K key, V value) {
        if (localRoot == null) {
            size++;
            return new BSTNode<K, V>(key, value);
        }
        int result = key.compareTo(localRoot.key);
        if (result < 0) {
            localRoot.setLeftChild(insertAux(localRoot.left, key, value));
        } else {
            localRoot.setRightChild(insertAux(localRoot.right, key, value));
        }

        if (localRoot.left == null && localRoot.right == null)
            localRoot.height = 1;
        else
            localRoot.height = 1 + (Math.max((localRoot.left == null ? 0 : localRoot.left.height), (localRoot.right == null ? 0 : localRoot.right.height)));

        if (localRoot.left == null && localRoot.right == null)
            localRoot.size = 1;
        else
            localRoot.size = 1 + (localRoot.left == null ? 0 : localRoot.left.size) + (localRoot.right == null ? 0 : localRoot.right.size);

        return localRoot;
    }

    public void insert(K key, V value) {
        this.root = insertAux(root, key, value);
    }

    public int height() {
        return (root == null ? 0 : root.height);
    }

    public int size() {
        return size;
    }

    private boolean isBalanced(BSTNode<K, V> localRoot) {
        if (localRoot == null)
            return true; // base case

        if (Math.abs((localRoot.left == null ? 0 : localRoot.left.height) - (localRoot.right == null ? 0 : localRoot.right.height)) <= 1
                && isBalanced(localRoot.left)
                && isBalanced(localRoot.right))
            return true;

        return false;
    }

    /**
     * Is the tree balanced ?
     * For every node, height of left and right subtrees differ by at most 1
     *
     * @return
     */
    public boolean isBalanced() {
        return isBalanced(root);
    }

    /**
     * Get level ordering of nodes
     *
     * @return a map which associates a level with list of nodes at that level
     */
    public Map<Integer, List<BSTNode<K, V>>> getNodeLevels() {
        return getNodeLevels(new HashMap<>(), 0, this.root); // recursive def
    }

    /**
     * @param map
     * @param level
     * @param node
     * @return
     */
    public Map<Integer, List<BSTNode<K, V>>> getNodeLevels(Map<Integer, List<BSTNode<K, V>>> nodeLevels, int level, BSTNode<K, V> node) {
        if (node == null)
            return nodeLevels; // nothing to do

        // check for list existence already
        if (nodeLevels.containsKey(level)) {
            nodeLevels.get(level).add(node);
        } else {
            List<BSTNode<K, V>> nodes = new ArrayList<>();
            nodes.add(node);
            nodeLevels.put(level, nodes);
        }

        nodeLevels.putAll(getNodeLevels(nodeLevels, level + 1, node.left)); // add all the keys from the left
        nodeLevels.putAll(getNodeLevels(nodeLevels, level + 1, node.right)); // add all the keys from the right
        return nodeLevels; // return the combo
    }

    /**
     * Return list of nodes whose keys are greater than or equal to key1
     * and smaller than or equal to key2
     * @param key1
     * @param key2
     * @return
     */
    public List<BSTNode<K, V>> getRange(K key1, K key2) {
        /**
         * Complete code for homework (define a recursive aux function to be calledfrom here)
         */
        return null;
    }

    /**
     * Find number of keys smaller than or equal to the specified key
     * @param key
     * @return
     */
    public int rank(K key) {
        /**
         * Complete code for homework (define a recursive aux function to be calledfrom here)
         */
        return 0;
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        bst.insert(25, 25);
        bst.insert(10, 10);
        bst.insert(30, 30);
        bst.insert(5, 5);
        bst.insert(28, 28);
        bst.insert(35, 35);
        bst.insert(40, 40);
        bst.insert(8, 8);
        bst.insert(50, 50);
        new BinTreeInOrderNavigator<Integer, Integer>().visit(bst.root);
        System.out.println("size of bst=" + bst.size());
        System.out.println("height of bst=" + bst.height());
        System.out.println("Is bst an AVL tree ? " + bst.isBalanced());
        Map<Integer, List<BSTNode<Integer, Integer>>> nodeLevels = bst.getNodeLevels();
        for (int level : nodeLevels.keySet()) {
            System.out.print("Keys at level " + level + " :");
            for (BSTNode<Integer, Integer> node : nodeLevels.get(level)) {
                System.out.print(" " + node.getKey());
            }
            System.out.println("");
        }
        BinarySearchTree<Integer, Integer> bst1 = new BinarySearchTree<>();
        bst1.insert(44, 1);
        bst1.insert(17, 1);
        bst1.insert(78, 1);
        bst1.insert(50, 1);
        bst1.insert(62, 1);
        bst1.insert(88, 1);
        bst1.insert(48, 1);
        bst1.insert(32, 1);
        new BinTreeInOrderNavigator<Integer, Integer>().visit(bst1.root);
        System.out.println("size of bst1=" + bst1.size());
        System.out.println("height of bst1=" + bst1.height());
        System.out.println("Is bst1 an AVL tree ? " + bst1.isBalanced());
        nodeLevels = bst1.getNodeLevels();
        for (int level : nodeLevels.keySet()) {
            System.out.print("Keys at level " + level + " :");
            for (BSTNode<Integer, Integer> node : nodeLevels.get(level)) {
                System.out.print(" " + node.getKey());
            }
            System.out.println("");
        }
    }

}
