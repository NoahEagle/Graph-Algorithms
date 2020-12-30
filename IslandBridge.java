import java.util.HashSet;
import java.util.Set;

final public class IslandBridge {
    private IslandBridge() {}

    /**
     * See question details in the write-up. Input is guaranteed to be valid.
     *
     * @param g the input graph representing the islands as vertices and bridges as directed edges
     * @param x the starting node
     * @return true, if no matter how you navigate through the one-way bridges from node x,
     * there is always a way to drive back to node x, and false otherwise.
     * @implSpec This method should run in worst-case O(n + m) time.
     */
    public static boolean allNavigable(Graph g, int x) {
        
        Set<Integer> ogReachable = bfs(g, x);
        
        Graph reversedGraph = reverseGraph(g);
        
        Set<Integer> reverseReachable = bfs(reversedGraph, x);
        
        return ogReachable.equals(reverseReachable);
    }    
    
    /**
     * This function reverses all the edges in the input graph and outputs the reversed graph
     * 
     * @param ogGraph the graph to be reversed
     * @return a new graph with all the reversed edges and not the original edges
     */
    public static Graph reverseGraph(Graph ogGraph) {
        
        Graph reversedGraph = new Graph(ogGraph.getSize());
        
        for (int index = 0; index < ogGraph.getSize(); index++) {
            
            Set<Integer> currNeighbors = ogGraph.outNeighbors(index);
            
            for (int node : currNeighbors) {
                
                reversedGraph.addEdge(node, index, 0);
            }
        }
        return reversedGraph;
    }
    
    /**
     * This function does a Breadth First Search on the input graph and returns a set of all nodes
     * reachable from the start vertex
     * 
     * @param graphy The graph we're performing BFS on
     * @param startNode The node from which we start BFS
     * @return A set of nodes (as integers) that are reachable from the start node
     */
    public static Set<Integer> bfs(Graph graphy, int startNode) {
        
        Set<Integer> reachableNodes = new HashSet<Integer>();
        
        reachableNodes.add(startNode);
        
        boolean[] discovered = new boolean[graphy.getSize()];
        
        discovered[startNode] = true;
        
        ResizingDequeImpl<Integer> theQueue = new ResizingDequeImpl<Integer>();
        
        theQueue.addLast(startNode);
        
        // As long as the queue isn't empty, keep going
        while (theQueue.size() > 0) {
            
            int currNode = (int) theQueue.pollFirst();
            
            Set<Integer> currNeighbors = graphy.outNeighbors(currNode);
            
            for (int neighbor : currNeighbors) {
                
                // If not discovered, add it to the queue, discover it, and add to reachable nodes
                if (!discovered[neighbor]) {
                    
                    theQueue.addLast(neighbor);
                    discovered[neighbor] = true;
                    reachableNodes.add(neighbor);
                }
            }
        }
        
        return reachableNodes;
    }   
}
