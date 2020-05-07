package edu.njit.cs114.tests;

import edu.njit.cs114.AdjListGraph;
import edu.njit.cs114.Graph;

import edu.njit.cs114.GraphSearch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Author: Ravi Varadarajan
 * Date created: 5/3/20
 */
public class GraphTests extends UnitTests {

    @Test
    public void addEdgeDirectedGraphTest() {
        try {
            Graph g = new AdjListGraph(8, true);
            g.addEdge(0, 1);
            g.addEdge(0, 2);
            g.addEdge(1, 2);
            g.addEdge(1, 3);
            g.addEdge(2, 0);
            g.addEdge(3, 2);
            g.addEdge(4, 3);
            g.addEdge(4, 6);
            assertEquals(8,g.numEdges());
            assertEquals(true, g.isEdge(0,1));
            assertEquals(true, g.isEdge(0,2));
            assertEquals(true, g.isEdge(1,2));
            assertEquals(true, g.isEdge(1,3));
            assertEquals(true, g.isEdge(2,0));
            assertEquals(true, g.isEdge(3,2));
            assertEquals(true, g.isEdge(4,3));
            assertEquals(true, g.isEdge(4,6));
            totalScore += 6;
            assertEquals(false, g.isEdge(3,4));
            assertEquals(false, g.isEdge(0,4));
            totalScore += 2;
            try {
                g.addEdge(3,2);
                assertFalse(true);
            } catch (Exception e) {
                assertTrue(true);
                totalScore += 2;
            }
            success("addEdgeDirectedGraphTest");
        } catch(Exception e) {
            failure("addEdgeDirectedGraphTest", e);
        }
    }

    @Test
    public void addEdgeUnDirectedGraphTest() {
        try {
            Graph g = new AdjListGraph(8, false);
            g.addEdge(0, 1);
            g.addEdge(0, 2);
            g.addEdge(1, 2);
            g.addEdge(1, 3);
            g.addEdge(3, 2);
            g.addEdge(4, 3);
            g.addEdge(4, 6);
            assertEquals(7, g.numEdges());
            assertEquals(true, g.isEdge(0, 1));
            assertEquals(true, g.isEdge(0, 2));
            assertEquals(true, g.isEdge(1, 2));
            assertEquals(true, g.isEdge(1, 3));
            assertEquals(true, g.isEdge(3, 2));
            assertEquals(true, g.isEdge(4, 3));
            assertEquals(true, g.isEdge(4, 6));
            assertEquals(true, g.isEdge(1, 0));
            assertEquals(true, g.isEdge(2, 0));
            assertEquals(true, g.isEdge(2, 1));
            assertEquals(true, g.isEdge(3, 1));
            assertEquals(true, g.isEdge(2, 3));
            assertEquals(true, g.isEdge(3, 4));
            assertEquals(true, g.isEdge(6, 4));
            totalScore += 6;
            try {
                g.addEdge(3,4);
                assertFalse(true);
            } catch (Exception e) {
                assertTrue(true);
                totalScore += 2;
            }
            assertEquals(false, g.isEdge(0, 4));
            totalScore += 2;
            success("addEdgeUnDirectedGraphTest");
        } catch (Exception e) {
            failure("addEdgeUnDirectedGraphTest", e);
        }
    }

    @Test
    public void delEdgeDirectedGraphTest() {
        try {
            Graph g = new AdjListGraph(7, true);
            g.addEdge(0, 1);
            g.addEdge(2, 0);
            g.addEdge(3, 5);
            g.addEdge(4, 6);
            g.addEdge(5, 3);
            g.addEdge(1, 6);
            g.addEdge(2, 6);
            assertEquals(7,g.numEdges());
            g.delEdge(3,5);
            assertEquals(6,g.numEdges());
            totalScore += 2;
            assertEquals(false, g.isEdge(3,5));
            totalScore += 5;
            assertEquals(true, g.isEdge(5,3));
            totalScore += 3;
            success("delEdgeDirectedGraphTest");
        } catch(Exception e) {
            failure("delEdgeDirectedGraphTest", e);
        }
    }

    @Test
    public void delEdgeUnDirectedGraphTest() {
        try {
            Graph g = new AdjListGraph(7, true);
            g.addEdge(0, 1);
            g.addEdge(2, 0);
            g.addEdge(3, 5);
            g.addEdge(4, 6);
            g.addEdge(5, 4);
            g.addEdge(1, 6);
            g.addEdge(2, 6);
            assertEquals(7,g.numEdges());
            g.delEdge(3,5);
            assertEquals(6,g.numEdges());
            totalScore += 2;
            assertEquals(false, g.isEdge(3,5));
            totalScore += 3;
            assertEquals(false, g.isEdge(5,3));
            totalScore += 3;
            success("delEdgeUnDirectedGraphTest");
        } catch(Exception e) {
            failure("delEdgeUnDirectedGraphTest", e);
        }
    }

    @Test
    public void getEdgeDirectedGraphTest() {
        try {
            AdjListGraph g = new AdjListGraph(7, true);
            g.addEdge(0,1,2);
            g.addEdge(2,0,3);
            g.addEdge(3,5,4);
            g.addEdge(4,6,5);
            g.addEdge(5,4,6);
            g.addEdge(1,6,7);
            g.addEdge(2,6,8);
            Graph.Edge edge = g.getEdge(3,5);
            assertEquals(3, edge.from);
            assertEquals(5, edge.to);
            assertEquals(4, edge.weight);
            totalScore += 5;
            assertEquals(null, g.getEdge(4,1));
            totalScore += 2;
            success("getEdgeDirectedGraphTest");
        } catch(Exception e) {
            failure("getEdgeDirectedGraphTest", e);
        }
    }

    @Test
    public void getEdgeUnDirectedGraphTest() {
        try {
            AdjListGraph g = new AdjListGraph(7, false);
            g.addEdge(0,1,2);
            g.addEdge(2,0,3);
            g.addEdge(3,5,4);
            g.addEdge(4,6,5);
            g.addEdge(5,4,6);
            g.addEdge(1,6,7);
            g.addEdge(2,6,8);
            Graph.Edge edge = g.getEdge(4,5);
            assertEquals(4, edge.from);
            assertEquals(5, edge.to);
            assertEquals(6, edge.weight);
            totalScore += 5;
            assertEquals(null, g.getEdge(4,1));
            totalScore += 2;
            success("getEdgeUnDirectedGraphTest");
        } catch(Exception e) {
            failure("getEdgeUnDirectedGraphTest", e);
        }
    }

    @Test
    public void cycleTest() {
        try {
            Graph g = new AdjListGraph(8, false);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(1,3);
            g.addEdge(1,4);
            g.addEdge(3,2);
            g.addEdge(3,4);
            g.addEdge(4,5);
            g.addEdge(6,5);
            g.addEdge(5,7);
            g.addEdge(7,6);
            assertEquals(true, GraphSearch.cycleExists(g));
            totalScore += 10;
            g = new AdjListGraph(6, false);
            g.addEdge(0,1);
            g.addEdge(1,2);
            g.addEdge(2,3);
            g.addEdge(3,4);
            g.addEdge(3,5);
            assertEquals(false, GraphSearch.cycleExists(g));
            totalScore += 8;
            success("cycleTest");
        } catch(Exception e) {
            failure("cycleTest", e);
        }
    }

    @Test
    public void oddCycleTest() {
        try {
            Graph g = new AdjListGraph(8, false);
            g.addEdge(0,1);
            g.addEdge(1,2);
            g.addEdge(2,3);
            g.addEdge(3,1);
            g.addEdge(3,4);
            g.addEdge(4,5);
            g.addEdge(5,6);
            g.addEdge(6,3);
            g.addEdge(0,6);
            assertEquals(true, GraphSearch.oddCycleExists(g));
            totalScore += 12;
            g = new AdjListGraph(6, false);
            g.addEdge(0,1);
            g.addEdge(1,2);
            g.addEdge(2,3);
            g.addEdge(3,4);
            g.addEdge(4,5);
            assertEquals(false, GraphSearch.oddCycleExists(g));
            totalScore += 8;
            success("oddCycleTest");
        } catch(Exception e) {
            failure("oddCycleTest", e);
        }
    }

    @Test
    public void cycleExistsDirectTest() {
        try {
            Graph g = new AdjListGraph(8,true);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(0,3);
            g.addEdge(1,2);
            g.addEdge(1,3);
            g.addEdge(2,0);
            g.addEdge(3,2);
            g.addEdge(4,3);
            g.addEdge(4,6);
            g.addEdge(5,7);
            g.addEdge(6,5);
            g.addEdge(7,5);
            g.addEdge(7,6);
            assertEquals(true, GraphSearch.cycleExistsDirect(g));
            totalScore += 8;
            g = new AdjListGraph(6,true);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(2,1);
            g.addEdge(1,3);
            g.addEdge(2,3);
            g.addEdge(2,4);
            g.addEdge(3,4);
            g.addEdge(3,5);
            g.addEdge(4,5);
            assertEquals(false, GraphSearch.cycleExistsDirect(g));
            totalScore += 7;
            success("cycleExistsDirectTest");
        } catch(Exception e) {
            failure("cycleExistsDirectTest", e);
        }
    }


}
