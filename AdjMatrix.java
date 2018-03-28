import java.io.*;
import java.util.*;


/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 * 
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjMatrix <T extends Object> implements FriendshipGraph<T>
{
    private int numVertices;
    private Map<T, Integer> indexes;
    private Map<Integer, T> vertices;
    private boolean[][] matrix;
	/**
	 * Contructs empty graph.
	 */
    public AdjMatrix() {
        numVertices = 0;
        indexes = new HashMap<T, Integer>();
        vertices = new HashMap<Integer, T>();
        matrix = new boolean[0][0];
    } // end of AdjMatrix()


    public void addVertex(T vertLabel) {
        boolean[][] newMatrix = new boolean[numVertices+1][numVertices+1];
        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, numVertices);
        }
        matrix = newMatrix;
        indexes.put(vertLabel, numVertices);
        vertices.put(numVertices, vertLabel);
        numVertices++;
    } // end of addVertex()


    public void addEdge(T srcLabel, T tarLabel) {
        //TODO validate the edges before chucking them into the structure
        int srcPos = indexes.get(srcLabel);
        int tarPos = indexes.get(tarLabel);
        matrix[srcPos][tarPos] = true;
        matrix[tarPos][srcPos] = true;
    } // end of addEdge()
	

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();
        int vertIndex = indexes.get(vertLabel);
        for (int i = 0; i < numVertices; i++) {
            if (matrix[vertIndex][i]) {
                neighbours.add(vertices.get(i));
            }
        }
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
        //TODO implement to also remove edges
        //matrix.remove(vertLabel);
    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
        //TODO validate edge before removing
    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {
        // Implement me!
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
        return disconnectedDist;
    } // end of shortestPathDistance()
    
} // end of class AdjMatri
