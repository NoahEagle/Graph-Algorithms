import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class WidestPathTest {
    
    @Test
    public void noPathExists() {
        
        Graph testGraph = new Graph(7);
        
        testGraph.addEdge(0, 1, 5);
        testGraph.addEdge(0, 2, 4);
        testGraph.addEdge(0, 4, 2);
        testGraph.addEdge(0, 5, 5);
        testGraph.addEdge(1, 3, 6);
        testGraph.addEdge(1, 5, 4);
        testGraph.addEdge(2, 3, 2);
        testGraph.addEdge(2, 4, 4);
        testGraph.addEdge(2, 5, 10);
        testGraph.addEdge(3, 5, 2);
        testGraph.addEdge(4, 5, 9);
        
        testGraph.addEdge(1, 0, 5);
        testGraph.addEdge(2, 0, 4);
        testGraph.addEdge(4, 0, 2);
        testGraph.addEdge(5, 0, 5);
        testGraph.addEdge(3, 1, 6);
        testGraph.addEdge(5, 1, 4);
        testGraph.addEdge(3, 2, 2);
        testGraph.addEdge(4, 2, 4);
        testGraph.addEdge(5, 2, 10);
        testGraph.addEdge(5, 3, 2);
        testGraph.addEdge(5, 4, 9);
        
        List<Integer> widestPath = WidestPath.getWidestPath(testGraph, 0, 6);
        
        assertTrue(widestPath.isEmpty());
    }
    
    @Test
    public void oneNodePath() {
        
        Graph testGraph = new Graph(7);
        
        testGraph.addEdge(0, 1, 5);
        testGraph.addEdge(0, 2, 4);
        testGraph.addEdge(0, 4, 2);
        testGraph.addEdge(0, 5, 5);
        testGraph.addEdge(1, 3, 6);
        testGraph.addEdge(1, 5, 4);
        testGraph.addEdge(2, 3, 2);
        testGraph.addEdge(2, 4, 4);
        testGraph.addEdge(2, 5, 10);
        testGraph.addEdge(3, 5, 2);
        testGraph.addEdge(4, 5, 9);
        
        testGraph.addEdge(1, 0, 5);
        testGraph.addEdge(2, 0, 4);
        testGraph.addEdge(4, 0, 2);
        testGraph.addEdge(5, 0, 5);
        testGraph.addEdge(3, 1, 6);
        testGraph.addEdge(5, 1, 4);
        testGraph.addEdge(3, 2, 2);
        testGraph.addEdge(4, 2, 4);
        testGraph.addEdge(5, 2, 10);
        testGraph.addEdge(5, 3, 2);
        testGraph.addEdge(5, 4, 9);
        
        List<Integer> widestPath = WidestPath.getWidestPath(testGraph, 0, 0);
        
        assertEquals(widestPath.size(), 1);
        assertEquals((int) widestPath.get(0), 0);
    }
    
    @Test
    public void normalWidePath() {
        
        Graph testGraph = new Graph(7);
        
        testGraph.addEdge(0, 1, 5);
        testGraph.addEdge(0, 2, 4);
        testGraph.addEdge(0, 4, 2);
        testGraph.addEdge(0, 5, 3);
        testGraph.addEdge(1, 3, 6);
        testGraph.addEdge(1, 5, 3);
        testGraph.addEdge(2, 3, 2);
        testGraph.addEdge(2, 4, 4);
        testGraph.addEdge(2, 5, 10);
        testGraph.addEdge(3, 5, 2);
        testGraph.addEdge(4, 5, 9);
        
        testGraph.addEdge(1, 0, 5);
        testGraph.addEdge(2, 0, 4);
        testGraph.addEdge(4, 0, 2);
        testGraph.addEdge(5, 0, 3);
        testGraph.addEdge(3, 1, 6);
        testGraph.addEdge(5, 1, 3);
        testGraph.addEdge(3, 2, 2);
        testGraph.addEdge(4, 2, 4);
        testGraph.addEdge(5, 2, 10);
        testGraph.addEdge(5, 3, 2);
        testGraph.addEdge(5, 4, 9);
        
        List<Integer> widestPath = WidestPath.getWidestPath(testGraph, 0, 5);
        
        assertEquals(widestPath.size(), 3);
        assertEquals((int) widestPath.get(0), 0);
        assertEquals((int) widestPath.get(1), 2);
        assertEquals((int) widestPath.get(2), 5);
    }
    
    @Test
    public void normalWidePath2() {
        
        Graph testGraph = new Graph(5);
        
        testGraph.addEdge(0, 1, 3);
        testGraph.addEdge(0, 3, 1);
        testGraph.addEdge(0, 4, 4);
        testGraph.addEdge(1, 2, 4);
        testGraph.addEdge(2, 3, 3);
        testGraph.addEdge(2, 4, 5);
        
        testGraph.addEdge(1, 0, 3);
        testGraph.addEdge(3, 0, 1);
        testGraph.addEdge(4, 0, 4);
        testGraph.addEdge(2, 1, 4);
        testGraph.addEdge(3, 2, 3);
        testGraph.addEdge(4, 2, 5);
        
        List<Integer> widestPath = WidestPath.getWidestPath(testGraph, 0, 2);
        
        assertEquals(widestPath.size(), 3);
        assertEquals((int) widestPath.get(0), 0);
        assertEquals((int) widestPath.get(1), 4);
        assertEquals((int) widestPath.get(2), 2);
    }
}