import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class MazeSolverTest {
    
    @Test
    public void constructGraph() {
        
        int[][] testMaze = new int[4][5];
        
        testMaze[0][0] = 1;
        testMaze[0][1] = 0;
        testMaze[0][2] = 0;
        testMaze[0][3] = 1;
        testMaze[0][4] = 0;
        
        testMaze[1][0] = 1;
        testMaze[1][1] = 0;
        testMaze[1][2] = 0;
        testMaze[1][3] = 0;
        testMaze[1][4] = 1;
        
        testMaze[2][0] = 1;
        testMaze[2][1] = 0;
        testMaze[2][2] = 1;
        testMaze[2][3] = 0;
        testMaze[2][4] = 0;
        
        testMaze[3][0] = 1;
        testMaze[3][1] = 0;
        testMaze[3][2] = 1;
        testMaze[3][3] = 0;
        testMaze[3][4] = 1;
        
        Graph testGraph = MazeSolver.graphify(testMaze);
        
        assertTrue(testGraph.hasEdge(1, 2));
        assertTrue(testGraph.hasEdge(1, 6));
        assertTrue(testGraph.hasEdge(2, 1));
        assertTrue(testGraph.hasEdge(2, 7));
        assertTrue(testGraph.hasEdge(6, 1));
        assertTrue(testGraph.hasEdge(6, 7));
        assertTrue(testGraph.hasEdge(6, 11));
        assertTrue(testGraph.hasEdge(7, 2));
        assertTrue(testGraph.hasEdge(7, 6));
        assertTrue(testGraph.hasEdge(7, 8));
        assertTrue(testGraph.hasEdge(8, 7));
        assertTrue(testGraph.hasEdge(8, 13));
        assertTrue(testGraph.hasEdge(11, 6));
        assertTrue(testGraph.hasEdge(11, 16));
        assertTrue(testGraph.hasEdge(13, 8));
        assertTrue(testGraph.hasEdge(13, 14));
        assertTrue(testGraph.hasEdge(13, 18));
        assertTrue(testGraph.hasEdge(14, 13));
        assertTrue(testGraph.hasEdge(16, 11));
        assertTrue(testGraph.hasEdge(18, 13));
    }
    
    @Test
    public void bfsNoPath() {
        
        int[][] testMaze = new int[4][5];
        
        testMaze[0][0] = 1;
        testMaze[0][1] = 0;
        testMaze[0][2] = 0;
        testMaze[0][3] = 1;
        testMaze[0][4] = 0;
        
        testMaze[1][0] = 1;
        testMaze[1][1] = 0;
        testMaze[1][2] = 0;
        testMaze[1][3] = 0;
        testMaze[1][4] = 1;
        
        testMaze[2][0] = 1;
        testMaze[2][1] = 0;
        testMaze[2][2] = 1;
        testMaze[2][3] = 0;
        testMaze[2][4] = 0;
        
        testMaze[3][0] = 1;
        testMaze[3][1] = 0;
        testMaze[3][2] = 1;
        testMaze[3][3] = 0;
        testMaze[3][4] = 1;
        
        Coordinate src = new Coordinate(1, 0);
        Coordinate tgt = new Coordinate(4, 0);
        
        List<Coordinate> shortestPath = MazeSolver.getShortestPath(testMaze, src, tgt);
        
        assertTrue(shortestPath.isEmpty());  
    }
    
    @Test
    public void bfsPathExists() {
        
        int[][] testMaze = new int[4][5];
        
        testMaze[0][0] = 0;
        testMaze[0][1] = 0;
        testMaze[0][2] = 0;
        testMaze[0][3] = 1;
        testMaze[0][4] = 0;
        
        testMaze[1][0] = 1;
        testMaze[1][1] = 0;
        testMaze[1][2] = 0;
        testMaze[1][3] = 0;
        testMaze[1][4] = 1;
        
        testMaze[2][0] = 1;
        testMaze[2][1] = 0;
        testMaze[2][2] = 1;
        testMaze[2][3] = 0;
        testMaze[2][4] = 0;
        
        testMaze[3][0] = 1;
        testMaze[3][1] = 0;
        testMaze[3][2] = 1;
        testMaze[3][3] = 0;
        testMaze[3][4] = 1;
        
        Coordinate src = new Coordinate(1, 0);
        Coordinate tgt = new Coordinate(3, 3);
        
        List<Coordinate> shortestPath = MazeSolver.getShortestPath(testMaze, src, tgt);
        
        assertEquals(shortestPath.get(0), new Coordinate(1, 0)); 
        assertEquals(shortestPath.get(1), new Coordinate(2, 0));
        assertEquals(shortestPath.get(2), new Coordinate(2, 1));
        assertEquals(shortestPath.get(3), new Coordinate(3, 1));
        assertEquals(shortestPath.get(4), new Coordinate(3, 2));
        assertEquals(shortestPath.get(5), new Coordinate(3, 3));
    }
    
    @Test
    public void bfsPathExists2() {
        
        int[][] testMaze = new int[4][5];
        
        testMaze[0][0] = 1;
        testMaze[0][1] = 0;
        testMaze[0][2] = 0;
        testMaze[0][3] = 1;
        testMaze[0][4] = 0;
        
        testMaze[1][0] = 1;
        testMaze[1][1] = 0;
        testMaze[1][2] = 0;
        testMaze[1][3] = 0;
        testMaze[1][4] = 1;
        
        testMaze[2][0] = 1;
        testMaze[2][1] = 0;
        testMaze[2][2] = 1;
        testMaze[2][3] = 0;
        testMaze[2][4] = 0;
        
        testMaze[3][0] = 1;
        testMaze[3][1] = 0;
        testMaze[3][2] = 1;
        testMaze[3][3] = 0;
        testMaze[3][4] = 1;
        
        Coordinate src = new Coordinate(1, 0);
        Coordinate tgt = new Coordinate(1, 3);
        
        List<Coordinate> shortestPath = MazeSolver.getShortestPath(testMaze, src, tgt);
        
        assertEquals(shortestPath.get(0), new Coordinate(1, 0)); 
        assertEquals(shortestPath.get(1), new Coordinate(1, 1));
        assertEquals(shortestPath.get(2), new Coordinate(1, 2));
        assertEquals(shortestPath.get(3), new Coordinate(1, 3));
    }
}