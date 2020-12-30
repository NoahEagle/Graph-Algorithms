import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Returns a widest path between two vertices in an undirected graph. A widest path between two
 * vertices maximizes the weight of the minimum-weight edge in the path.
 * <p/>
 * There are multiple ways to solve this problem. The following algorithms may be helpful:
 * - Kruskal's algorithm using Union Find, or
 * - Prim's algorithm using Binary Min Heap (Priority Queue)
 * Feel free to use any previous algorithms that you have already implemented.
 */
public final class WidestPath {
    private WidestPath() {}

    /**
     * Computes a widest path from {@param src} to {@param tgt} for an undirected graph. If there 
     * are multiple widest paths, this method may return any one of them.
     * Input {@param g} guaranteed to be undirected.
     * Input {@param src} and {@param tgt} are guaranteed to be valid and in-bounds.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param g   the graph
     * @param src the vertex from which to start the search
     * @param tgt the vertex to find via {@code src}
     * @return an ordered list of vertices on a widest path from {@code src} to {@code tgt}, or an
     * empty list if there is no such path. The first element is {@code src} and the last
     * element is {@code tgt}. If {@code src == tgt}, a list containing just that element is
     * returned.
     * @implSpec This method should run in worst-case O(n + m log n) time.
     */
    public static List<Integer> getWidestPath(Graph g, int src, int tgt) {
        
        // If the src and tgt are the same node, return a 1 node path (with that singular node)
        if (src == tgt) {
            
            LinkedList<Integer> trivialWidestPath = new LinkedList<Integer>();
            
            trivialWidestPath.addFirst(tgt);
            
            return trivialWidestPath;
        }
        
        BinaryMinHeapImpl<Integer, Integer> prioQ = new BinaryMinHeapImpl<Integer, Integer>();
        
        Graph negatedGraph = negateGraph(g);
        
        int numNodes = negatedGraph.numVertices;
        
        // Initialize every node's key to infinity (will all change upon first contact)
        for (int i = 0; i < numNodes; i++) {
            
            prioQ.add(Integer.MAX_VALUE, i);
        }
        
        int[] parentArray = new int[numNodes];
        
        // Initialize every node's parent to be -1 (to signify unreachable)
        for (int i = 0; i < numNodes; i++) {
            
            parentArray[i] = -1;
        }
        
        boolean[] inHeapArray = new boolean[numNodes];
        
        // Initialize every node to be in the heap still
        for (int i = 0; i < numNodes; i++) {
            
            inHeapArray[i] = true;
        }
        
        int[] smallestWeight = new int[numNodes];
        
        for (int i = 0; i < numNodes; i++) {
            
            smallestWeight[i] = Integer.MAX_VALUE;
        }
        
        // Decrease the src node's key to 0 (so that we start with it)
        prioQ.decreaseKey(src, 0);
        
        // Make the src node its own parent
        parentArray[src] = src;
        
        // Make the smallest edge weight on MST path to src a 0
        smallestWeight[src] = 0;
        
        // As long as the prio q isn't empty, keep going
        while (!prioQ.isEmpty()) {
            
            BinaryMinHeapImpl.Entry<Integer, Integer> extractedMin = prioQ.extractMin();
            
            int minNode = extractedMin.value;
            
            inHeapArray[minNode] = false;
            
            Set<Integer> minNodeNeighbors = negatedGraph.outNeighbors(minNode);
            
            // Go through all the min node's neighbors
            for (int neighbor : minNodeNeighbors) {
                
                int neighborEdgeWeight = negatedGraph.getWeight(minNode, neighbor);
                
                int currMinWeightToNeighbor = smallestWeight[neighbor];
                
                /*
                 *  If the edge weight from extracted to neighbor is less than the min weight along
                 *  the path we currently have from src to neighbor, make this edge weight the new
                 *  min edge weight along the path from src to neighbor & make extracted the new
                 *  parent
                 */
                if (currMinWeightToNeighbor > neighborEdgeWeight && inHeapArray[neighbor]) {
                    
                    smallestWeight[neighbor] = neighborEdgeWeight;
                    parentArray[neighbor] = minNode;
                    prioQ.decreaseKey(neighbor, neighborEdgeWeight);
                }
            }
        }

        // If after exhausting the prio Q we still haven't reached tgt, there's no path to it
        if (parentArray[tgt] == -1) {
            
            return new LinkedList<Integer>();
        }
        
        LinkedList<Integer> widestPath = new LinkedList<Integer>();
        
        // Add the tgt node to the end of the widest path list
        widestPath.addLast(tgt);
        
        // Iterate up from the tgt's parent to the src node, adding each one to the front
        for (int node = tgt; node != src; node = parentArray[node]) {
            
            widestPath.addFirst(parentArray[node]);
        }
        
        return widestPath;
    }
    
    /**
     * This is a helper function that creates a new graph which has the same edges and nodes as the
     * input graph except that all edge weights have been multiplied by -1
     * 
     * @param ogGraph the graph for which we want to negate all edges
     * @return a graph which is structurally the same as the input but with negated edge weights
     */
    public static Graph negateGraph(Graph ogGraph) {
        
        Graph reversedGraph = new Graph(ogGraph.getSize());
        
        for (int index = 0; index < ogGraph.getSize(); index++) {
            
            Set<Integer> currNeighbors = ogGraph.outNeighbors(index);
            
            for (int node : currNeighbors) {
                
                int weight = ogGraph.getWeight(index, node);
                
                reversedGraph.addEdge(index, node, weight * -1);
            }
        }
        
        return reversedGraph;
    }
}
