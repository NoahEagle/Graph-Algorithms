import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class DijkstraTest {
    
    @Test
    public void noPathExists() {
        
        Graph testGraph = new Graph(6);
        
        testGraph.addEdge(0, 1, 1);
        testGraph.addEdge(0, 2, 2);
        testGraph.addEdge(1, 3, 3);
        testGraph.addEdge(2, 3, 1);
        testGraph.addEdge(3, 5, 2);
        testGraph.addEdge(4, 2, 4);
        testGraph.addEdge(4, 5, 2);
        
        List<Integer> shortestPath = Dijkstra.getShortestPath(testGraph, 0, 4);
        
        assertTrue(shortestPath.isEmpty());
    }
    
    @Test
    public void pathExists() {
        
        Graph testGraph = new Graph(6);
        
        testGraph.addEdge(0, 1, 1);
        testGraph.addEdge(0, 2, 4);
        testGraph.addEdge(1, 2, 2);
        testGraph.addEdge(2, 4, 1);
        testGraph.addEdge(2, 3, 4);
        testGraph.addEdge(3, 5, 1);
        testGraph.addEdge(2, 5, 4);
        testGraph.addEdge(4, 5, 2); 
        
        List<Integer> shortestPath = Dijkstra.getShortestPath(testGraph, 0, 5);
        
        assertEquals((int) shortestPath.get(0), 0);
        assertEquals((int) shortestPath.get(1), 1);
        assertEquals((int) shortestPath.get(2), 2);
        assertEquals((int) shortestPath.get(3), 4);
        assertEquals((int) shortestPath.get(4), 5);
    }
}