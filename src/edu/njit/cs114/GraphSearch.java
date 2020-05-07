package edu.njit.cs114;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/26/20
 */
public class GraphSearch {

    public static final int VISITED = 1;
    public static final int UNVISITED = 0;


    public static void preVisit(Graph g, int v) {
        System.out.print(v + ",");
    }

    public static void postVisit(Graph g, int v) {
    }

    public static void graphTraverseBFS(Graph g) {
        System.out.println("breadth-first search of graph..");
        for (int v = 0; v < g.numVertices(); v++) {
            g.setMark(v, UNVISITED);
        }
        for (int v = 0; v < g.numVertices(); v++) {
            if (g.getMark(v) == UNVISITED) {
                System.out.println("Start vertex : " + v);
                bfs(g, v);
                System.out.println("");
            }
        }
        System.out.println("");
    }

    public static void graphTraverseDFS(Graph g) {
        System.out.println("Depth-first search of graph..");
        for (int v = 0; v < g.numVertices(); v++) {
            g.setMark(v, UNVISITED);
        }
        for (int v = 0; v < g.numVertices(); v++) {
            if (g.getMark(v) == UNVISITED) {
                System.out.println("Start vertex : " + v);
                dfs(g, v);
                System.out.println("");
            }
        }
        System.out.println("");
    }

    public static void dfs(Graph g, int v) {
        preVisit(g, v);
        g.setMark(v, VISITED);
        Iterator<Graph.Edge> outEdgeIter = g.getOutgoingEdges(v);
        while (outEdgeIter.hasNext()) {
            Graph.Edge edge = outEdgeIter.next();
            int w = edge.to;
            if (g.getMark(w) == UNVISITED) {
                dfs(g, w);
            }
        }
        postVisit(g, v);
    }

    public static void bfs(Graph g, int start) {
        Queue<Integer> vertexQueue = new LinkedList<Integer>();
        vertexQueue.add(start);
        g.setMark(start, 1);
        while (!vertexQueue.isEmpty()) {
            int v = vertexQueue.remove(); // remove from head of the queue
            preVisit(g, v);
            Iterator<Graph.Edge> outEdgeIter = g.getOutgoingEdges(v);
            while (outEdgeIter.hasNext()) {
                Graph.Edge edge = outEdgeIter.next();
                int w = edge.to;
                if (g.getMark(w) == 0) {
                    g.setMark(w, g.getMark(v) + 1);
                    vertexQueue.add(w);
                }
            }
            postVisit(g, v);
        }
    }

    /**
     * Returns true if a cycle exists in undirected graph
     *
     * @param g undirected graph
     * @return
     */
    public static boolean cycleExists(Graph g) {
        boolean[] visited = new boolean[g.numEdges() + 1];
        for (int i = 0; i < g.numEdges(); i++)
            if (!visited[i])
                if (cycleEval(g, i, visited, -1))
                    return true;

        return false;
    }

    private static boolean cycleEval(Graph g, int i, boolean[] visited, int p) {
        visited[i] = true;
        for (Iterator<Graph.Edge> it = g.getOutgoingEdges(i); it.hasNext(); ) {
            Graph.Edge edge = it.next();
            if (!visited[edge.to]) {
                if (cycleEval(g, edge.to, visited, i))
                    return true;
            } else if (edge.to != p)
                return true;
        }
        return false;
    }

    /**
     * Returns true if a simple cycle with odd number of edges exists in undirected graph
     *
     * @param g undirected graph
     * @return
     */
    public static boolean oddCycleExists(Graph g) {
        return cycleExists(g) && g.numEdges() % 2 == 1;
    }

    /**
     * Does the directed graph have a cycle of directed edges ?
     *
     * @param g
     * @return
     */
    public static boolean cycleExistsDirect(Graph g) {
        boolean[] visited = new boolean[g.numEdges()];
        boolean[] stack = new boolean[g.numEdges()];
        for (int i = 0; i < g.numEdges(); i++)
            if (cycleEvalDirect(g, i, visited, stack))
                return true;

        return false;
    }

    private static boolean cycleEvalDirect(Graph g, int i, boolean[] visited, boolean[] stack) {
        if (stack[i])
            return true;
        if (visited[i])
            return false;
        visited[i] = true;
        stack[i] = true;
        for (Iterator<Graph.Edge> it = g.getOutgoingEdges(i); it.hasNext(); ) {
            Graph.Edge edge = it.next();
            if (cycleEvalDirect(g, edge.to, visited, stack))
                return true;
        }

        stack[i] = false;
        return false;
    }


    public static void main(String[] args) {
        Graph g = new AdjListGraph(8, true);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 0);
        g.addEdge(3, 2);
        g.addEdge(4, 3);
        g.addEdge(4, 6);
        g.addEdge(5, 7);
        g.addEdge(6, 5);
        g.addEdge(7, 5);
        g.addEdge(7, 6);
        System.out.println(g);
        graphTraverseBFS(g);
        graphTraverseDFS(g);
        //System.out.println("Directed cycle exists in g ? " + cycleExistsDirect(g));
        g = new AdjListGraph(8, false);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(3, 2);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(6, 5);
        g.addEdge(5, 7);
        g.addEdge(7, 6);
        System.out.println(g);
        graphTraverseBFS(g);
        graphTraverseDFS(g);
        System.out.println("Cycle exists in g ? " + cycleExists(g));
        System.out.println("Odd cycle exists in g ? " + oddCycleExists(g));
        g = new AdjListGraph(7, false);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 6);
        System.out.println(g);
        System.out.println("Cycle exists in g ? " + cycleExists(g));
        g = new AdjListGraph(7, false);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 0);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 3);
        System.out.println(g);
        System.out.println("Cycle exists in g ? " + cycleExists(g));
        System.out.println("Odd cycle exists in g ? " + oddCycleExists(g));
    }


}
