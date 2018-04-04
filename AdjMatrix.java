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
    private static final int INITIAL_SIZE = 1000;
    private static final int SIZE_INCREMENT = 1000;

    //Keep the total number of vertices (quicker than list length getters)
    private int numVertices;
    //Two maps because Java has no bi-directional map
    private Map<T, Integer> indexes; //Maps Vertices to their Indexes
    private Map<Integer, T> vertices; //Maps Indexes to their Vertices
    private boolean[][] matrix; //The actual Adjacency matrix
	/**
	 * Contructs empty graph.
	 */
    public AdjMatrix() {
        numVertices = 0;
        indexes = new HashMap<T, Integer>();
        vertices = new HashMap<Integer, T>();
        matrix = new boolean[INITIAL_SIZE][INITIAL_SIZE];
    } // end of AdjMatrix()


    public void addVertex(T vertLabel) {
        //dont add vertices that are already present
        if (indexes.containsKey(vertLabel)) return;
        int insert = getNextFreeSpot();
        //no free spot
        if (insert == -1) {
            boolean[][] newMatrix = new boolean[numVertices+SIZE_INCREMENT][numVertices+SIZE_INCREMENT];
            for (int i = 0; i < numVertices; i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, numVertices);
            }
            matrix = newMatrix;
            indexes.put(vertLabel, numVertices);
            vertices.put(numVertices, vertLabel);
            numVertices += SIZE_INCREMENT;
        } else {
            //free spot
            indexes.put(vertLabel, insert);
            vertices.put(insert, vertLabel);
        }
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
        //TODO validate vertex before removing
        int removalIndex = indexes.get(vertLabel);

        //
        for (T neighbour : neighbours(vertLabel)) {
            removeEdge(neighbour, vertLabel);
        }

        //
        indexes.remove(vertLabel);
        vertices.remove(removalIndex);
    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
        //TODO validate edge before removing
        matrix[indexes.get(srcLabel)][indexes.get(tarLabel)] = false;
        matrix[indexes.get(tarLabel)][indexes.get(srcLabel)] = false;
    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
        for (Object v : indexes.keySet()) {
            os.print(v.toString() + " ");
        }
        os.println();
        os.flush();
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {
        //TODO take advantage of matrix symmetry to make this heaps quicker
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrix[i][j]) {
                    T v1 = vertices.get(i);
                    T v2 = vertices.get(j);
                    os.println(v1.toString() + " " + v2.toString());
                }
            }
        }
        os.flush();
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
        Queue<T> toVisit = new LinkedList<T>();
        Queue<Integer> depths = new LinkedList<Integer>();
        boolean[] marked = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) marked[i] = false;
        toVisit.add(vertLabel1);
        depths.add(0);
        while (!toVisit.isEmpty()) {
            T v = toVisit.remove();
            int d = depths.remove();
            if (v.equals(vertLabel2)) return d;
            if (marked[indexes.get(v)]) continue;
            marked[indexes.get(v)] = true;
            for (T w : neighbours(v)) {
                //if (!marked[indexes.get(w)]) {
                    toVisit.add(w);
                    depths.add(d+1);
                //}
            }
        }
        return disconnectedDist;
    } // end of shortestPathDistance()
    

    //helper function
    //TODO optimise this
    private int getNextFreeSpot() {
        for (int i = 0; i < numVertices; i++) {
            if (!vertices.containsKey(i)) return i;
        }
        return -1;
    }
} // end of class AdjMatri
