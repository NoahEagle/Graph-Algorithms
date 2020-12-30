import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class IslandBridgeTest {
    
    @Test
    public void reverseGraph() {
    
        Graph ogGraph = new Graph(10);
        
        ogGraph.addEdge(0, 1, 0);
        ogGraph.addEdge(2, 8, 0);
        ogGraph.addEdge(5, 2, 0);
        ogGraph.addEdge(6, 3, 0);
        ogGraph.addEdge(0, 4, 0);
        ogGraph.addEdge(3, 1, 0);
        ogGraph.addEdge(1, 7, 0);
        ogGraph.addEdge(1, 5, 0);
        ogGraph.addEdge(6, 5, 0);
        ogGraph.addEdge(8, 5, 0);
        
        Graph reversed = IslandBridge.reverseGraph(ogGraph);
        
        assertTrue(reversed.hasEdge(1, 0));
        assertTrue(reversed.hasEdge(8, 2));
        assertTrue(reversed.hasEdge(2, 5));
        assertTrue(reversed.hasEdge(3, 6));
        assertTrue(reversed.hasEdge(4, 0));
        assertTrue(reversed.hasEdge(1, 3));
        assertTrue(reversed.hasEdge(7, 1));
        assertTrue(reversed.hasEdge(5, 1));
        assertTrue(reversed.hasEdge(5, 6));
        assertTrue(reversed.hasEdge(5, 8));
    
    }
    
    @Test
    public void reachableNodes() {
        
        Graph ogGraph = new Graph(10);
        
        ogGraph.addEdge(0, 1, 0);
        ogGraph.addEdge(2, 8, 0);
        ogGraph.addEdge(5, 2, 0);
        ogGraph.addEdge(6, 3, 0);
        ogGraph.addEdge(0, 4, 0);
        ogGraph.addEdge(3, 1, 0);
        ogGraph.addEdge(1, 7, 0);
        ogGraph.addEdge(1, 5, 0);
        ogGraph.addEdge(6, 5, 0);
        ogGraph.addEdge(8, 5, 0);
        
        Set<Integer> reachableNodes = IslandBridge.bfs(ogGraph, 1);
        
        assertTrue(reachableNodes.contains(1));
        assertTrue(reachableNodes.contains(7));
        assertTrue(reachableNodes.contains(5));
        assertTrue(reachableNodes.contains(2));
        assertTrue(reachableNodes.contains(8));
        
        assertFalse(reachableNodes.contains(0));
        assertFalse(reachableNodes.contains(3));
        assertFalse(reachableNodes.contains(4));
        assertFalse(reachableNodes.contains(6));
        assertFalse(reachableNodes.contains(9));
    }
    
    @Test
    public void allNavegableFalse() {
        
        Graph ogGraph = new Graph(5);
        
        ogGraph.addEdge(0, 1, 0);
        ogGraph.addEdge(0, 2, 0);
        ogGraph.addEdge(0, 3, 0);
        ogGraph.addEdge(1, 0, 0);
        ogGraph.addEdge(3, 0, 0);
        ogGraph.addEdge(4, 1, 0);
        
        Graph revGraph = IslandBridge.reverseGraph(ogGraph);
        
        Set<Integer> ogReachable = IslandBridge.bfs(ogGraph, 0);
        Set<Integer> revReachable = IslandBridge.bfs(revGraph, 0);
        
        assertFalse(IslandBridge.allNavigable(ogGraph, 0));
        
        assertTrue(ogReachable.contains(0));
        assertTrue(ogReachable.contains(1));
        assertTrue(ogReachable.contains(2));
        assertTrue(ogReachable.contains(3));
        
        assertTrue(revReachable.contains(0));
        assertTrue(revReachable.contains(1));
        assertTrue(revReachable.contains(3));
        assertTrue(revReachable.contains(4));
    }
    
    @Test
    public void allNavegableTrue() {
        
        Graph ogGraph = new Graph(5);
        
        ogGraph.addEdge(2, 1, 0);
        ogGraph.addEdge(1, 4, 0);
        ogGraph.addEdge(4, 3, 0);
        ogGraph.addEdge(3, 1, 0);
        ogGraph.addEdge(1, 2, 0);
        
        Graph revGraph = IslandBridge.reverseGraph(ogGraph);
        
        Set<Integer> ogReachable = IslandBridge.bfs(ogGraph, 2);
        Set<Integer> revReachable = IslandBridge.bfs(revGraph, 2);
        
        assertTrue(IslandBridge.allNavigable(ogGraph, 2));
        
        assertTrue(ogReachable.contains(2));
        assertTrue(ogReachable.contains(1));
        assertTrue(ogReachable.contains(4));
        assertTrue(ogReachable.contains(3));
        
        assertTrue(revReachable.contains(2));
        assertTrue(revReachable.contains(1));
        assertTrue(revReachable.contains(4));
        assertTrue(revReachable.contains(3));
    }
}