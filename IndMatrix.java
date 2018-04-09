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
	private static final int INITIAL_SIZE = 1000;
    private static final int SIZE_INCREMENT = 1000;

	private int numVertices;
	private int numEdges;
	private int vertexSize;
	private int edgeSize;
	private Map<T, Integer> vIndexes;
	private Map<Integer, T> vertices;
	private Map<Edge, Integer> eIndexes;
	private Map<Integer, Edge> edges;
	private boolean[][] incidenceMatrix;
	/**
	 * Contructs empty graph.
	 */
    public IndMatrix() {
    	numVertices = 0;
    	numEdges = 0;
    	vertexSize = INITIAL_SIZE;
    	edgeSize = INITIAL_SIZE;
    	vIndexes = new HashMap<T, Integer>();
        vertices = new HashMap<Integer, T>();
        eIndexes = new HashMap<Edge, Integer>();
        edges = new HashMap<Integer, Edge>();
    	incidenceMatrix = new boolean[vertexSize][edgeSize];
    	
    } // end of IndMatrix()
    
    
    public void addVertex(T vertLabel) {
    	
    	//dont add vertices that are already present
        if (vIndexes.containsKey(vertLabel)) 
        	return;
        
        int insert = getNextFreeSpot();
        
        //No free spot
        if(insert == -1)
        {
            vertexSize += SIZE_INCREMENT;
        	boolean[][] newMatrix = new boolean[vertexSize][edgeSize];
            for (int i = 0; i < numVertices; i++) 
            {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, numEdges);
            }
            incidenceMatrix = newMatrix;
            vIndexes.put(vertLabel, numVertices);
            vertices.put(numVertices, vertLabel);
            numVertices++;
        }
        //Free spot
        else
        {
        	vIndexes.put(vertLabel, insert);
            vertices.put(insert, vertLabel);
            numVertices++;
        }
        
        
        
        /*
        
        boolean[][] newMatrix = new boolean[numVertices+1][numEdges];
    	
        if(numEdges > 0)
        {
        	for (int i = 0; i < numVertices; i++) 
        	{
        		System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, numEdges);
        	}
        }
        
        incidenceMatrix = newMatrix;
        
    	vIndexes.put(vertLabel, numVertices);
        vertices.put(numVertices, vertLabel);
    	
    	numVertices++;
    	*/
    } // end of addVertex()
	
    
    public void addEdge(T srcLabel, T tarLabel) {  	
    	
    	Edge<T> e = new Edge<T>(srcLabel,tarLabel);
    	int srcPos = vIndexes.get(srcLabel);
        int tarPos = vIndexes.get(tarLabel);
        
        int insert = getNextFreeSpotE();
    	

        //No free spot
        if(insert == -1)
        {
            edgeSize += SIZE_INCREMENT;
        	boolean[][] newMatrix = new boolean[vertexSize][edgeSize];
            for (int i = 0; i < numVertices; i++) 
            {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, numEdges);
            }
            incidenceMatrix = newMatrix;
            
            eIndexes.put(e, numEdges);
            edges.put(numEdges, e);
                 	
        	incidenceMatrix[srcPos][numEdges] = true;
        	incidenceMatrix[tarPos][numEdges] = true;
        	
            numEdges++;
        }
        //Free spot
        else
        {
        	eIndexes.put(e, insert);
            edges.put(insert, e);
            incidenceMatrix[srcPos][insert] = true;
        	incidenceMatrix[tarPos][insert] = true;
            numEdges++;
        }
    	
    	/*
    	boolean[][] newMatrix = new boolean[numVertices][numEdges+1];
    	
    	if(numEdges > 0)
        {
        	for (int i = 0; i < numVertices; i++) 
        	{
        		System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, numEdges);
        	}
        }
        
        incidenceMatrix = newMatrix;
    	int srcPos = vIndexes.get(srcLabel);
        int tarPos = vIndexes.get(tarLabel);
    	
    	incidenceMatrix[srcPos][numEdges] = true;
    	incidenceMatrix[tarPos][numEdges] = true;
    	
    	numEdges++;
    	*/
    	
    } // end of addEdge()
	

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();
        ArrayList<Integer> neighbourPos = new ArrayList<Integer>();
        int vertPos = vIndexes.get(vertLabel);
        
        //Fills the arraylist with the indexes of the edges associated with the vertex
        for (int i = 0; i < numEdges; i++)
        {
        	if (incidenceMatrix[vertPos][i])
        	{
        		neighbourPos.add(i);
        	}
        }
        
        //Looks at each edge (column) associated with the vertex. Once it finds another vertex
        //associated with that edge, it adds that vertex to the array and moves to the next relevant edge
        for (int pos : neighbourPos)
		{
        	for (int i = 0; i < numVertices; i++)
        	{
       			if(i != vertPos && incidenceMatrix[i][pos])
       			{
        			neighbours.add(vertices.get(i));
        			break;
       			}
       		}
        }
        
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
    	
        if (!vIndexes.containsKey(vertLabel)) 
        	return;

    	
    	int removalIndex = vIndexes.get(vertLabel);
    	int numEdgesRemoved = 0;
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
    		newMatrix = new boolean[numVertices][numEdges-numEdgesRemoved];
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
    	
    	if (!vIndexes.containsKey(srcLabel) || !vIndexes.containsKey(tarLabel))
            return;
            	
    	int srcPos = vIndexes.get(srcLabel);
        int tarPos = vIndexes.get(tarLabel);
    	/*
    	int srcPos = vIndexes.get(srcLabel);
        int tarPos = vIndexes.get(tarLabel);
        int removalIndex = -1;
    	boolean[][] newMatrix = new boolean[numVertices][numEdges-1];
        
        for(int i = 0; i < numEdges; i++)
        {
        	if (incidenceMatrix[srcPos][i] && incidenceMatrix[tarPos][i])
        	{
        		removalIndex = i;
        		break;
        	}
        		
        }
        if(removalIndex != -1)
        {
        	for (int i = 0; i < numVertices; i++) 
        	{
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, removalIndex);
                System.arraycopy(incidenceMatrix[i], removalIndex+1, newMatrix[i], removalIndex, numEdges-removalIndex-1);
            }
        	incidenceMatrix = newMatrix;
        	
        	numEdges--;
        	
        }
        */
    	
    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
    	for (Object v : vIndexes.keySet()) {
            os.print(v.toString() + " ");
        }
        os.println();
        os.flush();
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {

    	for (Edge e : eIndexes.keySet()) {
            os.print(e.getSrcLabel() + " " + e.getTarLabel());
            os.println();
            os.print(e.getTarLabel() + " " + e.getSrcLabel());
    	}
        os.println();
        os.flush();
    	
    	
    	/*
    	int i;
    	T srcLabel = null;
    	T tarLabel = null;
    	
    	for (int j = 0; j < numEdges; j++)
    	{
    		i = 0;
    		while (i < numVertices)
    		{
    			if(incidenceMatrix[i][j])
    			{
        			srcLabel = vertices.get(i);
        			i++;
        			break;
    			}
    			i++;
    		}
    		while (i < numVertices)
    		{
    			if(incidenceMatrix[i][j])
    			{
        			tarLabel = vertices.get(i);
        			break;
    			}
    			i++;
    		}
            os.print(srcLabel.toString() + " " + tarLabel.toString());
            os.println();
            os.print(tarLabel.toString() + " " + srcLabel.toString());
            os.println();
            os.flush();
    	}
    	*/
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	// Implement me!
    	
    	//Depth or Breadth
    	
        // if we reach this point, source and target are disconnected
        return disconnectedDist;    	
    } // end of shortestPathDistance()
    
    
    //helper function
    //returns the first array slot that has no vertex associated with it
    private int getNextFreeSpot() {
    	if(numVertices == 0)
    		return 0;
        for (int i = 0; i < vertexSize; i++) {
            if (!vertices.containsKey(i)) 
            	return i;
        }
        return -1;
    }
    
    //helper function
    //returns the first array slot that has no edge associated with it
    private int getNextFreeSpotE() {
    	if(numEdges == 0)
    		return 0;
        for (int i = 0; i < edgeSize; i++) {
            if (!edges.containsKey(i)) 
            	return i;
        }
        return -1;
    }
    
} // end of class IndMatrix

class Edge<T extends Object> {
	
	private T srcLabel;
	private T tarLabel;
	public Edge(T srcLabel, T tarLabel)
	{
		this.srcLabel = srcLabel;
		this.tarLabel = tarLabel;
	}
	public T getSrcLabel() {
		return srcLabel;
	}
	
	public T getTarLabel() {
		return tarLabel;
	}
} // end of class Edge
