import java.util.*;

public class SetsGraph<T extends Object> {
    private Set<T> V;
    private Set<TreeSet<T>> E;
    public SetsGraph() {
        this.V = new TreeSet<T>();
        this.E = new HashSet<TreeSet<T>>();
    }

    public void addVertex(T v1) {
        V.add(v1);
    }
    public void addEdge(T v1, T v2) {
        if (!V.contains(v1) || !V.contains(v2)) return;
        TreeSet<T> edge = new TreeSet<T>();
        edge.add(v1);
        edge.add(v2);
        E.add(edge);
    }
    public Set<T> getV() {
        return V;
    }
    public Set<TreeSet<T>> getE() {
        return E;
    }
    public double getDensity() {
        double v = (float) V.size();
        double e = (float) E.size();
        return (2*e)/(v*(v-1));
    }

}
