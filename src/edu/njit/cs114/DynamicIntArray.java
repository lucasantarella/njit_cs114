package edu.njit.cs114;

import java.util.Arrays;

/**
 * Author: Ravi Varadarajan
 * Date created: 2/17/20
 */
public class DynamicIntArray {

    private static final int DEFAULT_INITIAL_CAPACITY = 1;

    private Integer[] arr;
    private int size;
    private int nCopies;

    public DynamicIntArray(int initialCapacity) {
        arr = new Integer[initialCapacity];
    }

    public DynamicIntArray() {
        this(DEFAULT_INITIAL_CAPACITY);
    }


    /**
     * Add element at specified index position shifting to right elements at positions higher than
     * or equal to index
     *
     * @param index
     * @param elem
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    public void add(int index, int elem) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        this.insert(index, elem);
    }

    public void insert(int index, int elem) {
        this.size++;
        if (this.size > this.arr.length)
            this.arr = grow();

        Integer[] afterElements = Arrays.copyOfRange(this.arr, index, this.arr.length);
        System.arraycopy(afterElements, 0, this.arr, index + 1, afterElements.length - 1);
        nCopies += this.arr.length - (index + 1);
        this.arr[index] = elem;
    }


    /**
     * Append element to the end of the array
     *
     * @param elem
     */
    public void add(int elem) {
        this.insert(this.size, elem);
    }

    /**
     * @return The new buffer size.
     */
    private int calculateNewGrowthCapacity() {
        int oldCapacity = this.arr.length;
        return oldCapacity + oldCapacity;
    }

    private Integer[] grow() {
        int newCapacity = calculateNewGrowthCapacity();
        this.nCopies += this.arr.length;
        return Arrays.copyOf(this.arr, newCapacity);
    }

    /**
     * @return The new buffer size.
     */
    private int calculateNewShrunkCapacity() {
        int oldCapacity = this.arr.length;
        return oldCapacity / 2;
    }

    private Integer[] shrink() {
        int newCapacity = calculateNewShrunkCapacity();
        this.nCopies += this.size;
        return Arrays.copyOf(this.arr, newCapacity);
    }


    /**
     * Set the element at specified index position replacing any previous value
     *
     * @param index
     * @param elem
     * @return previous value in the index position
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    public int set(int index, int elem) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        int oldValue = this.arr[index];
        this.arr[index] = elem;
        return oldValue;
    }

    /**
     * Get the element at the specified index position
     *
     * @param index
     * @return
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return arr[index];
    }

    /**
     * Remove and return the element at the specified index position. The elements with positions
     * higher than index are shifted to left
     *
     * @param index
     * @return
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        this.size--;
        if (this.size <= (this.arr.length / 4))
            this.arr = shrink();
        int result = this.arr[index];
        Integer[] afterElements = Arrays.copyOfRange(this.arr, index + 1, this.arr.length);
        System.arraycopy(afterElements, 0, this.arr, index, afterElements.length);
        nCopies += this.size;
        return result;
    }

    /**
     * Remove and return the element at the end of the array
     *
     * @return
     * @throws Exception if size() == 0
     */
    public int remove() throws Exception {
        if (size == 0) {
            throw new Exception("Array is empty");
        }
        return this.remove(this.size - 1);
    }

    /**
     * Returns the number of elements in the array
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Returns the total number of copy operations done due to expansion of array
     *
     * @return
     */
    public int nCopies() {
        return nCopies;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(" + arr.length + ")");
        builder.append("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append(arr[i] == null ? "" : arr[i]);
        }
        builder.append("]");
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        DynamicIntArray arr = new DynamicIntArray();
        arr.add(10);
        arr.add(5);
        arr.add(-5);
        System.out.println("array of size " + arr.size() + " : " + arr);
        arr.add(1, 4);
        arr.add(3, 19);
        System.out.println("array of size " + arr.size() + " : " + arr);
        System.out.println("old value at index 3 after replacing it with 25 = " + arr.set(3, 25));
        System.out.println("Element at position 2 = " + arr.get(2));
        System.out.println("array of size " + arr.size() + " : " + arr);
        System.out.println("Removed element at position 0 = " + arr.remove(0));
        System.out.println("array of size " + arr.size() + " : " + arr);
        System.out.println("Removed element at position 2 = " + arr.remove(2));
        System.out.println("array of size " + arr.size() + " : " + arr);
        System.out.println("Removed element at position 2 = " + arr.remove(2));
        System.out.println("array of size " + arr.size() + " : " + arr);
        System.out.println("Removed element at end = " + arr.remove());
        System.out.println("array of size " + arr.size() + " : " + arr);
        System.out.println("Removed element at end = " + arr.remove());
        System.out.println("array of size " + arr.size() + " : " + arr);
        arr.add(67);
        arr.add(-14);
        arr.add(15);
        System.out.println("array of size " + arr.size() + " : " + arr);

        int[] nItemsArr = new int[]{0, 100000, 200000, 400000, 800000, 1600000, 3200000};
        DynamicIntArray arr1 = new DynamicIntArray();
        long totalTime = 0;
        for (int k = 1; k < nItemsArr.length; k++) {
            for (int i = 0; i < nItemsArr[k] - nItemsArr[k - 1]; i++) {
                long startTime = System.currentTimeMillis();
                arr1.add(i + 1);
                long stopTime = System.currentTimeMillis();
                totalTime += (stopTime - startTime);
            }
            System.out.println("copy cost for inserting = " + nItemsArr[k] + " items = " +
                    +arr1.nCopies());
            System.out.println("total time(ms) for inserting = " + nItemsArr[k] + " items = " +
                    +totalTime);
        }

        totalTime = 0;
        for (int k = 1; k < nItemsArr.length; k++) {
            for (int i = 0; i < nItemsArr[k] - nItemsArr[k - 1]; i++) {
                long startTime = System.currentTimeMillis();
                arr1.remove();
                long stopTime = System.currentTimeMillis();
                totalTime += (stopTime - startTime);
            }
            System.out.println("total time(ms) for deleting = " + nItemsArr[k] + " items = " +
                    +totalTime);
        }

    }

}
