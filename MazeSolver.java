import java.util.LinkedList;
import java.util.List;
import java.util.Set;

final public class MazeSolver {
    private MazeSolver() {}

    /**
     * Returns a list of coordinates on the shortest path from {@code src} to {@code tgt}
     * by executing a breadth-first search on the graph represented by a 2D-array of size m x n.
     * Please note, you MUST use your ResizingDeque implementation as the BFS queue for this method.
     * <p>
     * Input {@code maze} guaranteed to be a non-empty and valid matrix.
     * Input {@code src} and {@code tgt} are guaranteed to be valid, in-bounds, and not blocked.
     * <p>
     * Do NOT modify this method header.
     *
     * @param maze the input maze, which is a 2D-array of size m x n
     * @param src The starting Coordinate of the path on the matrix
     * @param tgt The target Coordinate of the path on the matrix
     * @return an empty list if there is no path from {@param src} to {@param tgt}, otherwise an
     * ordered list of vertices in the shortest path from {@param src} to {@param tgt},
     * with the first element being {@param src} and the last element being {@param tgt}.
     * @implSpec This method should run in worst-case O(m x n) time.
     */
    public static List<Coordinate> getShortestPath(int[][] maze, Coordinate src, Coordinate tgt) {
        
        Graph mazeGraph = graphify(maze);
        
        int[] parentArray = bfs(mazeGraph, src, maze.length);

        int n = maze[0].length;
        
        int targetGraphIndex = (n * tgt.getY()) + tgt.getX();
        
        int startGraphIndex = (n * src.getY()) + src.getX();
        
        LinkedList<Coordinate> shortestPath = new LinkedList<Coordinate>();
        
        // Initialize the shortest path to just contain the end point
        shortestPath.addLast(tgt);
        
        // Travel up the parent chain to get from the target to the source
        for (int node = targetGraphIndex; node != startGraphIndex; node = parentArray[node]) {
            
            int nodeParent = parentArray[node];
            
            // If the parent didn't exist, the target was unreachable, return an empty list
            if (nodeParent == -1) {
                
                return new LinkedList<Coordinate>();
            }
            
            // The y coord is the index in the graph array list divided by the num of cols (floored)
            int yCoord = nodeParent / maze[0].length;
            
            // The x coord is the index minus the y coord * num of cols
            int xCoord = nodeParent - (yCoord * n);
            
            shortestPath.addFirst(new Coordinate(xCoord, yCoord));
        }
        
        return shortestPath;
    }
    
    /**
     * This function takes the 2D maze input and creates a graph out of it. (Each coordinate
     * corresponds to an index in the graph's array list (a node essentially)... The order of the
     * nodes goes from top left to bottom right (going left to right on a row before moving down)).
     * 
     *  a 2x2 array would have the nodes arranged in the following order: top left, top right, bot
     *  left, bot right in the graph array list
     * 
     * @param maze the 2D int array representing the maze to be turned into a graph (graphified)
     * @return an instance of the graph class in which the graph represents the movements through
     * the 2D maze (an edge exists between two nodes if you could travel between those two points
     * in the maze (they were adjacent and were both 0s).
     */
    public static Graph graphify(int[][]maze) {
        
        int n = maze[0].length;
        int m = maze.length;
        
        Graph newGraph = new Graph(m * n);
        
        // Iterate through the maze, adding edges as necessary
        for (int row = 0; row < m; row++) {
            
            for (int col = 0; col < n; col++) {
                
                int graphIndex = (n * row) + col;
                
                // If we're not in the first row, check if the element above is open
                if (row != 0 && maze[row - 1][col] == 0) {
                    
                    newGraph.addEdge(graphIndex, (n * (row - 1)) + col, 0);
                }
                
                // If we're not in the final row, check if the element below is open
                if (row != m - 1 && maze[row + 1][col] == 0) {
                    
                    newGraph.addEdge(graphIndex, (n * (row + 1)) + col, 0);
                }
                
                // If we're not in the first column, check if the element to the left is open
                if (col != 0 && maze[row][col - 1] == 0) {
                    
                    newGraph.addEdge(graphIndex, (n * row) + col - 1, 0);
                }
                
                // If we're not in the last column, check if the element to the right is open
                if (col != n - 1 && maze[row][col + 1] == 0) {
                    
                    newGraph.addEdge(graphIndex, (n * row) + col + 1, 0);
                }
            }
        }
        
        return newGraph;
    }
    
    /**
     * This function actually performs a Breadth First Search on a graph given a start coordinate
     * (and the number of columns in the 2D array version of the graph (the maze). This version of
     * BFS only creates one tree (the tree containing the start node). Thus, if a node isn't
     * findable, it isn't reachable.
     * 
     * @param theGraph the instance of the Graph class that corresponds to the graph we're running
     * BFS on
     * @param start the starting coordinate from the maze that corresponds to the start node for BFS
     * @param n the number of columns in the 2D array version of the graph (the maze)
     * @return it returns an int array representing the parent array (parent[0] corresponds to the
     * node that is the parent of node 0 in the BFS tree)
     */
    public static int[] bfs(Graph theGraph, Coordinate start, int n) {
        
        int rowStart = start.getY();
        int colStart = start.getX();
        
        int graphIndexStart = (n * rowStart) + colStart;
        
        int numCoords = theGraph.getSize();
        
        boolean[] discovered = new boolean[numCoords];
        
        discovered[graphIndexStart] = true;
        
        int[] parents = new int[numCoords];
        
        // Initialize all parents to -1 (so no parent = unreachable is easy to see)
        for (int i = 0; i < parents.length; i++) {
            
            parents[i] = -1;
        }
        
        parents[graphIndexStart] = graphIndexStart;
        
        ResizingDequeImpl<Integer> theQueue = new ResizingDequeImpl<Integer>();
        
        theQueue.addLast(graphIndexStart);
        
        // As long as the queue isn't empty, keep going
        while (theQueue.size() > 0) {
            
            // Get the next node in the queue
            int currNode = (int) theQueue.pollFirst();
            
            Set<Integer> neighbors = theGraph.outNeighbors(currNode);
            
            // Go through all the node's neighbors
            for (int currNeighbor : neighbors) {
                
                // If there's an undiscovered neighbor, discover it, add to queue, and update parent
                if (!discovered[currNeighbor]) {
                    
                    theQueue.addLast(currNeighbor);
                    parents[currNeighbor] = currNode;
                    discovered[currNeighbor] = true;
                }
            }
        }
        
        return parents;
    }  
}