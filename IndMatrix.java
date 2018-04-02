import java.io.*;
import java.util.*;

import model.interfaces.Player;


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
	private Map<T, Integer> vIndexes;
	private Map<Integer, T> vertices;
	private boolean[][] incidenceMatrix;
	/**
	 * Contructs empty graph.
	 */
    public IndMatrix() {
    	// Implement me!
    	numVertices = 0;
    	numEdges = 0;
    	vIndexes = new HashMap<T, Integer>();
        vertices = new HashMap<Integer, T>();
    	incidenceMatrix = new boolean[0][0];
    	
    } // end of IndMatrix()
    
    
    public void addVertex(T vertLabel) {
        // Implement me!
    	
    	//dont add vertices that are already present
        if (vIndexes.containsKey(vertLabel)) 
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
        
    	vIndexes.put(vertLabel, numVertices);
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
    	int srcPos = vIndexes.get(srcLabel);
        int tarPos = vIndexes.get(tarLabel);
    	
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
    	
    	int removalIndex = vIndexes.get(vertLabel);
    	int numEdgesRemoved = 0;
    	ArrayList<int> eRemoveIndexes = new ArrayList<int>();
    	Map<Integer,Integer> eRemoveIndexes = new HashMap<Integer, Integer>();
    	boolean[][] newMatrix = new boolean[numVertices-1][numEdges];
    	
    	//Gets a list of indexes for edges to remove
    	for (int i = 0; i < numEdges; i++)
    	{
    		if (incidenceMatrix[removalIndex][i])
    		{
    			eRemoveIndexes.put(i,i);
    			numEdgesRemoved++;
    		}
    	}
    	
    	for (int i = 0; i < removalIndex; i++) 
    	{
            System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, numEdges);
        }
        for (int i = removalIndex+1; i < numVertices; i++) 
        {
            System.arraycopy(incidenceMatrix[i], 0, newMatrix[i-1], 0, numEdges);
        }
    	
        //Matrix no longer has the removed vertex, but still contains edges associated with said vertex
    	incidenceMatrix = newMatrix;
    		
        vIndexes.remove(vertLabel);
        vertices.remove(removalIndex);
        
        //Shifts vertices down when a lower vertex is removed.
        //E.g. Remove B in A B C. C will get moved down to position 1 from 2
        for (int i = removalIndex+1; i < numVertices; i++) {
            T v = vertices.get(i);
            vertices.put(i-1, v);
            vertices.remove(i);
            vIndexes.remove(v);
            vIndexes.put(v, i-1);
        }
        
    	numVertices--;
    	
    	//Remove edges associated with removed vertex (Don't bother if there's none)
    	if(numEdgesRemoved > 0)
    	{
    		int skips = 0;
    		newMatrix = new boolean[numVertices][numEdges-numEdgesRemoved]
    		for (int i = 0; i < numVertices; i++)
    		{
    			skips = 0;
    			for (int j = 0; j < numEdges; j++)
    			{
    		        if (!eRemoveIndexes.containsKey(j)) 
    		        {
    		        	newMatrix[i][j-skips] = incidenceMatrix[i][j];
    		        }
    		        else
    		        {
    		        	skips++;
    		        }
    			}
    		
    		}
    		
    		//Matrix has now removed the vertex and all associated edges
        	incidenceMatrix = newMatrix;
    		numEdges -= numEdgesRemoved;
    	}
    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!
    	
    	
    	numEdges--;
    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
    	for (Object v : vIndexes.keySet()) {
            os.print(v.toString() + " ");
        }
        os.println();
        os.flush();
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {
        // Implement me!
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	// Implement me!
    	
    	//Depth or Breadth
    	
        // if we reach this point, source and target are disconnected
        return disconnectedDist;    	
    } // end of shortestPathDistance()
    
    
} // end of class IndMatrix
