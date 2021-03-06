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
    private static final int SIZE_MULTIPLIER = 2;

	private int numEdges;
	private int vertexSize;
	private int edgeSize;
	private Map<T, Integer> vIndexes;
	private Map<Integer, T> vertices;
	private Map<TreeSet<T>,Integer> eIndexes;
	private Map<Integer,TreeSet<T>> edges;
	private boolean[][] incidenceMatrix;
	/**
	 * Contructs empty graph.
	 */
    public IndMatrix() {
    	numEdges = 0;
    	vertexSize = INITIAL_SIZE;
    	edgeSize = INITIAL_SIZE;
    	vIndexes = new HashMap<T, Integer>();
        vertices = new HashMap<Integer, T>();
        eIndexes = new HashMap<TreeSet<T>,Integer>();
        edges = new HashMap<Integer,TreeSet<T>>();
        incidenceMatrix = new boolean[vertexSize][edgeSize];
    	
    } // end of IndMatrix()
    //TODO
    //Fix all array copies
    
    public void addVertex(T vertLabel) {
    	
    	//dont add vertices that are already present
        if (vIndexes.containsKey(vertLabel)) 
        	return;
        
        int insert = getNextFreeSpot();
        
        //No free spot
        if(insert == -1)
        {
        	boolean[][] newMatrix = new boolean[vertexSize * SIZE_MULTIPLIER][edgeSize];
            for (int i = 0; i < vertexSize; i++) 
            {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, edgeSize);
            }
            incidenceMatrix = newMatrix;
            vIndexes.put(vertLabel, vertexSize);
            vertices.put(vertexSize, vertLabel);
			vertexSize *= SIZE_MULTIPLIER;
        }
        //Free spot
        else
        {
        	vIndexes.put(vertLabel, insert);
            vertices.put(insert, vertLabel);
        }
    } // end of addVertex()
	
    
    public void addEdge(T srcLabel, T tarLabel) {  	
    	
    	if (!vIndexes.containsKey(srcLabel) || !vIndexes.containsKey(tarLabel))
            return;
    	
    	int srcPos = vIndexes.get(srcLabel);
        int tarPos = vIndexes.get(tarLabel);
        
        int insert = getNextFreeSpotE();
    	
        TreeSet<T> edge = new TreeSet<T>();
    	edge.add(srcLabel);
    	edge.add(tarLabel);
        
        //No free spot
        if(insert == -1)
        {
        	boolean[][] newMatrix = new boolean[vertexSize][edgeSize * SIZE_MULTIPLIER];
            for (int i = 0; i < vertexSize; i++) 
            {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, edgeSize);
            }
            incidenceMatrix = newMatrix;
                       	
        	incidenceMatrix[srcPos][edgeSize] = true;
        	incidenceMatrix[tarPos][edgeSize] = true;
        	
        	eIndexes.put(edge,edgeSize);
        	edges.put(edgeSize,edge);
        	
            edgeSize *= SIZE_MULTIPLIER;
        }
        //Free spot
        else
        {
            incidenceMatrix[srcPos][insert] = true;
        	incidenceMatrix[tarPos][insert] = true;
        	
        	eIndexes.put(edge,insert);
        	edges.put(insert,edge);
        }
    } // end of addEdge()
	

    public ArrayList<T> neighbours(T vertLabel) {
    	
        ArrayList<T> neighbours = new ArrayList<T>();
        ArrayList<Integer> neighbourPos = new ArrayList<Integer>();
        int vertPos = vIndexes.get(vertLabel);
        
        for (int i = 0; i < edgeSize; i++)
        {
        	if (incidenceMatrix[vertPos][i])
        	{
        		for (int j = 0; j < vertexSize; j++)
        		{
        			if(incidenceMatrix[j][i] && j!=vertPos)
        			{
        				neighbours.add(vertices.get(j));
            			break;
        			}
        		}
        	}
        }
        /*
        for (int pos : neighbourPos)
		{
        	for (int i = 0; i < vertexSize; i++)
        	{
       			if(i != vertPos && incidenceMatrix[i][pos])
       			{
        			neighbours.add(vertices.get(i));
        			break;
       			}
       		}
        }
        
        */
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
    	
    	 if (!vIndexes.containsKey(vertLabel)) 
         	return;
    	 
     	Map<Integer,Integer> eRemoveIndexes = new HashMap<Integer, Integer>();

    	
     	int vertPos = vIndexes.get(vertLabel);

    	for (TreeSet<T> edge : eIndexes.keySet()) {
        	if(edge.first().equals(vertLabel) || edge.last().equals(vertLabel))
        	{
        		//int vertPos = vIndexes.get(edge.last());
        		int ePos = eIndexes.get(edge);

        		incidenceMatrix[vertPos][ePos] = false;
        	    incidenceMatrix[vertPos][ePos] = false;
        		
    			eRemoveIndexes.put(ePos,ePos);

        	}
        }
    	
    	for(int ePos : eRemoveIndexes.keySet())
    	{
    		eIndexes.remove(edges.get(ePos));
            edges.remove(ePos);
    	}
    	
    	vIndexes.remove(vertLabel);
    	vertices.remove(vertPos);

    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
    	
    	
    	if (!vIndexes.containsKey(srcLabel) || !vIndexes.containsKey(tarLabel))
            return;
            	     
        for (TreeSet<T> edge : eIndexes.keySet()) {
        	if((edge.first().equals(srcLabel) && edge.last().equals(tarLabel)) ||
        			(edge.last().equals(srcLabel) && edge.first().equals(tarLabel)))
        	{
        		int ePos = eIndexes.get(edge);
        		int srcPos = vIndexes.get(srcLabel);
                int tarPos = vIndexes.get(tarLabel);
        	
        		incidenceMatrix[srcPos][ePos] = false;
        	    incidenceMatrix[tarPos][ePos] = false;
        	    
        	    eIndexes.remove(edge);
                edges.remove(ePos);
                
                return;
        	}
        }
    	
    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
    	for (Object v : vIndexes.keySet()) {
            os.print(v + " ");
        }
        os.println();
        os.flush();
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {

    	for (TreeSet<T> edge : eIndexes.keySet()) {
    		os.print(edge.first() + " " + edge.last());
    		os.println();
    		os.print(edge.last() + " " + edge.first());
    		os.println();
    	}
        os.println();
        os.flush();
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	// Implement me!
    	
    	Queue<T> toVisit = new LinkedList<T>();
        Queue<Integer> depths = new LinkedList<Integer>();
        boolean[] marked = new boolean[vertexSize];
       
        toVisit.add(vertLabel1);
        depths.add(0);
        
        while(!toVisit.isEmpty())
        {
        	//Retrieves and removes the head (first element) of this list.
            T v = toVisit.remove();
            int d = depths.remove();
            
            //Found shortest path
            if (v.equals(vertLabel2)) 
            	return d;
            //If already visited, go to next vertex
            if (marked[vIndexes.get(v)]) 
            	continue;
            //Marks index as visited
            marked[vIndexes.get(v)] = true;
            for (T w : neighbours(v)) {
                toVisit.add(w);
                depths.add(d+1);
            }
        }
        
    	
    	
        // if we reach this point, source and target are disconnected
        return disconnectedDist;    	
    } // end of shortestPathDistance()
    
    
    //helper function
    //returns the first array slot that has no vertex associated with it
    private int getNextFreeSpot() {
        for (int i = 0; i < vertexSize; i++) {
            if (!vertices.containsKey(i)) 
            	return i;
        }
        return -1;
    }
    
    //helper function
    //returns the first array slot that has no edge associated with it
    private int getNextFreeSpotE() {
        for (int i = 0; i < edgeSize; i++) {
            if (!edges.containsKey(i)) 
            	return i;
        }
        return -1;
    }
    
} // end of class IndMatrix
