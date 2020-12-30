import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;

public class GraphTest {
    
    @Test (expected = IllegalArgumentException.class)
    public void graphNegNodes() {
        
        new Graph(-5);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void graphZeroNodes() {
        
        new Graph(0);
    }
    
    @Test
    public void graphPosNodes() {
        
        Graph testGraph = new Graph(10);
        
        assertEquals(testGraph.getSize(), 10);  
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void hasEdgeVertexDNE() {
        
        Graph testGraph = new Graph(15);
        
        testGraph.hasEdge(13, 20);
    }
    
    @Test
    public void hasEdgeFakeEdge() {
        
        Graph testGraph = new Graph(20);
        
        testGraph.addEdge(10, 11, 5);
        
        assertFalse(testGraph.hasEdge(11, 10));
    }
    
    @Test
    public void hasEdgeTrueEdge() {
        
        Graph testGraph = new Graph(5);
        
        testGraph.addEdge(2, 3, 10);
        
        assertTrue(testGraph.hasEdge(2, 3));
    }
    
    @Test (expected = NoSuchElementException.class)
    public void getWeightEdgeDNE() {
        
        Graph testGraph = new Graph(7);
        
        testGraph.getWeight(2, 5);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void getWeightVertexDNE() {
        
        Graph testGraph = new Graph(9);
        
        testGraph.getWeight(4, 9);
    }
    
    @Test
    public void getWeightEdgeExists() {
        
        Graph testGraph = new Graph(11);
        
        testGraph.addEdge(2, 1, 13);
        
        assertEquals(testGraph.getWeight(2, 1), 13);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void outNeighborsVertexDNE() {
        
        Graph testGraph = new Graph(8);
        
        testGraph.outNeighbors(10);
    }
    
    @Test
    public void outNeighborsValidVertex() {
        
        Graph testGraph = new Graph(7);
        
        testGraph.addEdge(1, 2, 10);
        testGraph.addEdge(1, 6, 10);
        testGraph.addEdge(1, 0, 10);
        testGraph.addEdge(1, 4, 10);
        
        Set<Integer> expectedSet = new HashSet<Integer>();
        
        expectedSet.add(2);
        expectedSet.add(6);
        expectedSet.add(0);
        expectedSet.add(4);
        
        assertEquals(expectedSet, testGraph.outNeighbors(1));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void addEdgeVertexDNE() {
        
        Graph testGraph = new Graph(3);
        
        testGraph.addEdge(2, 3, 5);
    }
    
    @Test
    public void addEdgeExistingEdge() {
        
        Graph testGraph = new Graph(15);
        
        testGraph.addEdge(3, 10, 97);
        
        assertFalse(testGraph.addEdge(3, 10, 56));
    }
    
    @Test
    public void addEdgeNewEdge() {
        
        Graph testGraph = new Graph(42);
        
        assertFalse(testGraph.hasEdge(15, 32));
        
        assertTrue(testGraph.addEdge(15, 32, 12));
        assertTrue(testGraph.hasEdge(15, 32));
        assertEquals(testGraph.getWeight(15, 32), 12);
    }
}