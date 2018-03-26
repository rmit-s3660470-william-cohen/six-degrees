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

    private Map<T, Set<T> > matrix;
	/**
	 * Contructs empty graph.
	 */
    public AdjMatrix() {
        //XXX TreeMap over TreeSet???
        matrix = new HashMap<T, Set<T> >();
    } // end of AdjMatrix()


    public void addVertex(T vertLabel) {
        matrix.put(vertLabel, new TreeSet<T>());
    } // end of addVertex()


    public void addEdge(T srcLabel, T tarLabel) {
        //TODO validate the edges before chucking them into the structure
        
        matrix.get(srcLabel).add(tarLabel);
        matrix.get(tarLabel).add(srcLabel);
    } // end of addEdge()
	

    public ArrayList<T> neighbours(T vertLabel) {
        //FIXME return empty if no neighbours
        return new ArrayList<T>(matrix.get(vertLabel));
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
        
        //matrix.remove(vertLabel);
    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
        //TODO validate edge before removing
        matrix.get(srcLabel).remove(tarLabel);
        matrix.get(tarLabel).remove(srcLabel);
    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
        for (Object v : matrix.keySet()) {
            os.print(v.toString() + " ");
            //System.out.print(v.toString() + " ");
        }
        os.println();
        os.flush();
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {
        // Implement me!
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
        //FIXME doesnt correctly return -1 with disconnected v's
        List<T> visited = new LinkedList<T>();
        Queue<T> vertices = new LinkedList<T>();
        Queue<Integer> depths = new LinkedList<Integer>();
        vertices.add(vertLabel1);
        depths.add(0);
        while (!vertices.isEmpty()) {
            T v = vertices.remove();
            int d = depths.remove();
            if (v.equals(vertLabel2)) return d;
            if (visited.contains(v)) continue;
            for (T w : neighbours(v)) {
                vertices.add(w);
                depths.add(d+1);
            }
        }
        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()
    
} // end of class AdjMatrix
