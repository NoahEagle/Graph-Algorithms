import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Provides access to Dijkstra's algorithm for a weighted graph.
 */
final public class Dijkstra {
    private Dijkstra() {}

    /**
     * Computes the shortest path between two nodes in a weighted graph.
     * Input graph is guaranteed to be valid and have no negative-weighted edges.
     *
     * @param g   the weighted graph to compute the shortest path on
     * @param src the source node
     * @param tgt the target node
     * @return an empty list if there is no path from {@param src} to {@param tgt}, otherwise an
     * ordered list of vertices in the shortest path from {@param src} to {@param tgt},
     * with the first element being {@param src} and the last element being {@param tgt}.
     */
    public static List<Integer> getShortestPath(Graph g, int src, int tgt) {
        
        BinaryMinHeapImpl<Integer, Integer> prioQ = new BinaryMinHeapImpl<Integer, Integer>();
        
        int vertexCount = g.numVertices;
        
        // Add all the nodes into the prio Q with shortest path distances of infinity initially
        for (int i = 0; i < vertexCount; i++) {
            
            prioQ.add(Integer.MAX_VALUE, i);
        }
        
        int[] parentArray = new int[vertexCount];
        
        // Initialize every node's parent to -1 (so that -1 signifies no parent / unreachable)
        for (int i = 0; i < parentArray.length; i++) {
            
            parentArray[i] = -1;
        }
        
        int[] distanceArray = new int[vertexCount];
        
        // Initialize every node's distance from start to be infinity
        for (int i = 0; i < distanceArray.length; i++) {
            
            distanceArray[i] = Integer.MAX_VALUE;
        }
        
        boolean[] inHeapArray = new boolean[vertexCount];
        
        // Initialize every node to be considered in the heap still
        for (int i = 0; i < inHeapArray.length; i++) {
            
            inHeapArray[i] = true;
        }
        
        // Change the start node's distance to 0 in prio Q
        prioQ.decreaseKey(src, 0);
        
        // Make the start node's parent itself
        parentArray[src] = src;
        
        // Make the start node's distance to 0 in array
        distanceArray[src] = 0;
        
        // As long as the prio Q isn't empty, keep going
        while (!prioQ.isEmpty()) {
            
            BinaryMinHeapImpl.Entry<Integer, Integer> minNodeEntry = prioQ.extractMin();
            
            int minNode = minNodeEntry.value;
            
            inHeapArray[minNode] = false;
            
            Set<Integer> minNodeNeighbors = g.outNeighbors(minNodeEntry.value);
            
            // Go through all the min node's neighbors
            for (int neighbor : minNodeNeighbors) {
                
                int neighborWeight = g.getWeight(minNode, neighbor);
                
                int currDist = distanceArray[neighbor];
                
                int potDist = distanceArray[minNode] + neighborWeight;
                
                /*
                 *  If the distance to the neighbor is greater than the dist to min node + weight
                 *  to min and then through edge from min to neighbor (as well as still being in the
                 *  prio Q), update the distances and parent accordingly
                 */
                if (currDist > potDist && inHeapArray[neighbor]) {
                    
                    prioQ.decreaseKey(neighbor, potDist);
                    distanceArray[neighbor] = potDist;
                    parentArray[neighbor] = minNode;
                }
            }
        }
        
        // If after the prio Q is exhausted, we still haven't reached tgt, there's no path to it
        if (parentArray[tgt] == -1) {
            
            return new LinkedList<Integer>();
        }
        
        LinkedList<Integer> shortestPathList = new LinkedList<Integer>();
        
        // Add the tgt node to the end of the shortest path list
        shortestPathList.addLast(tgt);
        
        // Iterate up from the tgt's parent all the way until we get to the src, adding to front
        for (int node = tgt; node != src; node = parentArray[node]) {
            
            // If the parent is -1 (it wasn't reached from anywhere)
            if (parentArray[tgt] == -1) {
                
                return new LinkedList<Integer>();
            }
            
            shortestPathList.addFirst(parentArray[node]);
        }
        
        return shortestPathList;
    }
}
