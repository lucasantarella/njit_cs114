package edu.njit.cs114;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/28/20
 */
public class AdjListGraph extends AbstractGraph {

    private List<Edge>[] adjLists;

    public AdjListGraph(int n, boolean directed) {
        super(n, directed);
        init(n);
    }

    @Override
    public void init(int n) {
        adjLists = (List<Edge>[]) Array.newInstance(List.class, n);
        for (int i = 0; i < n; i++) {
            adjLists[i] = new LinkedList<>();
        }
    }


    @Override
    public void addGraphEdge(int u, int v, int weight) {
        if (v < 0 || v >= adjLists.length) {
            throw new IllegalArgumentException("Invalid vertex " + v);
        }
        // throw exception if it already exists
        /**
         * Complete code here
         */
        if (getEdge(u, v) == null)
            adjLists[u].add(new Edge(u, v, weight));
        else throw new IllegalArgumentException("edge already exists");
    }

    @Override
    public Iterator<Edge> getOutgoingEdges(int v) {
        if (v < 0 || v >= adjLists.length) {
            throw new IllegalArgumentException("Invalid vertex " + v);
        }
        return adjLists[v].iterator();
    }

    @Override
    public Edge delGraphEdge(int u, int v) {
        Edge edge = this.getEdge(u, v);
        if (edge != null)
            this.adjLists[u].remove(edge);

        return edge;
    }

    /**
     * Get edge from u to v if it exists else null
     *
     * @param u
     * @param v
     * @return
     */
    public Edge getEdge(int u, int v) {
        for (Edge edge : this.adjLists[u])
            if (edge.from == u && edge.to == v)
                return edge;
        return null;
    }

    @Override
    public boolean isEdge(int u, int v) {
        return (getEdge(u, v) != null);
    }

    @Override
    public int weight(int u, int v) {
        Edge edge = getEdge(u, v);
        if (edge == null) {
            throw new IllegalArgumentException("No edge from " + u + " to " + v + " exists");
        }
        return edge.weight;
    }

}
