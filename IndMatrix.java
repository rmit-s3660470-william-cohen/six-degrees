import java.io.*;
import java.util.*;


/**
 * Incidence matrix implementation for the FriendshipGraph interface.
 * 
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class IndMatrix <T extends Object> implements FriendshipGraph<T>
{

	private int numVertices;
	private int numEdges;
	private Map<T, Integer> indexes;
	private Map<Integer, T> vertices;
	private boolean[][] incidenceMatrix;
	/**
	 * Contructs empty graph.
	 */
    public IndMatrix() {
    	// Implement me!
    	numVertices = 0;
    	numEdges = 0;
    	indexes = new HashMap<T, Integer>();
        vertices = new HashMap<Integer, T>();
    	incidenceMatrix = new boolean[0][0];
    	
    } // end of IndMatrix()
    
    
    public void addVertex(T vertLabel) {
        // Implement me!
    	
    	//dont add vertices that are already present
        if (indexes.containsKey(vertLabel)) 
        	return;
        
        boolean[][] newMatrix = new boolean[numVertices+1][numEdges];
    	
        if(numEdges > 0)
        {
        	for (int i = 0; i < numVertices; i++) 
        	{
        		System.arraycopy(matrix[i], 0, newMatrix[i], 0, numEdges);
        	}
        }
        
        incidenceMatrix = newMatrix;
        
    	indexes.put(vertLabel, numVertices);
        vertices.put(numVertices, vertLabel);
    	
    	numVertices++;
    } // end of addVertex()
	
    
    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!
    	
    	
    	boolean[][] newMatrix = new boolean[numVertices][numEdges+1];
    	
    	if(numEdges > 0)
        {
        	for (int i = 0; i < numVertices; i++) 
        	{
        		System.arraycopy(matrix[i], 0, newMatrix[i], 0, numEdges);
        	}
        }
        
        incidenceMatrix = newMatrix;
    	int srcPos = indexes.get(srcLabel);
        int tarPos = indexes.get(tarLabel);
    	
    	incidenceMatrix[srcPos][numEdges] = true;
    	incidenceMatrix[tarPos][numEdges] = true;
    	
    	numEdges++;
    	
    } // end of addEdge()
	

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();
        
        // Implement me!
        
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
        // Implement me!
    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!
    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
        // Implement me!
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {
        // Implement me!
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	// Implement me!
    	
        // if we reach this point, source and target are disconnected
        return disconnectedDist;    	
    } // end of shortestPathDistance()
    
} // end of class IndMatrix
