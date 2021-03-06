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
        for (T v : neighbours(vertLabel)) {
            removeEdge(vertLabel, v);
        }
        matrix.remove(vertLabel);
    } // end of removeVertex()


    public void removeEdge(T srcLabel, T tarLabel) {
        if (!matrix.containsKey(srcLabel) || !matrix.containsKey(tarLabel)) return;
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
    public void printEdges(PrintWriter os) {
        for (T v1 : getVertices()) {
            for (T v2 : matrix.get(v1)) {
                os.println(v1.toString() + " " + v2.toString());
            }
        }
        os.flush();
    }
    public void printEdgesNoDup(PrintWriter os) {
        Set<TreeSet<T>> edges = new HashSet<TreeSet<T>>();

        for (T v1 : getVertices()) {
            for (T v2 : matrix.get(v1)) {
                TreeSet<T> edge = new TreeSet<T>();
                edge.add(v1);
                edge.add(v2);
                edges.add(edge);
            }
        }

        for (TreeSet<T> edge : edges) {
            os.print(edge.first() + " " + edge.last());
            os.println();
        }
        os.flush();

    }
    public TreeSet<T> getRandomEdge() {
        HashSet<TreeSet<T>> edges = new HashSet<TreeSet<T>>();

        for (T v1 : getVertices()) {
            for (T v2 : matrix.get(v1)) {
                TreeSet<T> edge = new TreeSet<T>();
                edge.add(v1);
                edge.add(v2);
                edges.add(edge);
            }
        }

        int edgeIndex = new Random().nextInt(edges.size());
        int i = 0;
        for (TreeSet<T> e : edges) {
            if (i == edgeIndex) return e;
            i++;
        }
        return null;
    }


}
