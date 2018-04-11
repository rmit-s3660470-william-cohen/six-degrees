import java.io.*;
import java.util.*;

public class Graph <T extends Object> {

    private Map<T, Set<T> > matrix;
    int vertices, edges;

    public Graph() {
        matrix = new HashMap<T, Set<T> >();
        vertices = 0;
        edges = 0;
    }


    public void addVertex(T vertLabel) {
        if (matrix.containsKey(vertLabel)) return;
        matrix.put(vertLabel, new TreeSet<T>());
        vertices++;
    }


    public void addEdge(T srcLabel, T tarLabel) {
        //TODO validate the edges before chucking them into the structure
        if (!matrix.containsKey(srcLabel) || !matrix.containsKey(tarLabel)) return;
        if (matrix.get(srcLabel).contains(tarLabel)) return;
        if (matrix.get(tarLabel).contains(srcLabel)) return;
        matrix.get(srcLabel).add(tarLabel);
        matrix.get(tarLabel).add(srcLabel);
        edges++;
    }


    public ArrayList<T> neighbours(T vertLabel) {
        return new ArrayList<T>(matrix.get(vertLabel));
    }


    public void removeVertex(T vertLabel) {
        //TODO implement to also remove edges
        matrix.remove(vertLabel);
    } // end of removeVertex()


    public void removeEdge(T srcLabel, T tarLabel) {
        //TODO validate edge before removing
        matrix.get(srcLabel).remove(tarLabel);
        matrix.get(tarLabel).remove(srcLabel);
    } // end of removeEdges()
    public int getNumVertices() {
        return vertices;
    }
    public int getNumEdges() {
        return edges;
    }
    public double getDensity() {
        double v = (float) vertices;
        double e = (float) edges;
        return (2*e)/(v*(v-1));
    }
    public String toString() {
        return matrix.toString();
    }
    public ArrayList<T> getVertices() {
        return new ArrayList<T>(matrix.keySet());
    }

}
