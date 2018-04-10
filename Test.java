import java.util.*;
import java.io.*;

public class Test {
    public static void main(String[] args) {
        Set<TreeSet<Integer>> edges = new HashSet<TreeSet<Integer>>();
        int v1 = 5;
        int v2 = 3;
        TreeSet<Integer> newEdge = new TreeSet<Integer>();
        newEdge.add(v1);
        newEdge.add(v2);
        edges.add(newEdge);
        
        int v12 = 4;
        int v22 = 3;
        TreeSet<Integer> newEdge2 = new TreeSet<Integer>();
        newEdge2.add(v12);
        newEdge2.add(v22);
        edges.add(newEdge2);

        int v13 = 3;
        int v23 = 4;
        TreeSet<Integer> newEdge3 = new TreeSet<Integer>();
        newEdge3.add(v13);
        newEdge3.add(v23);
        edges.add(newEdge3);
        
        for (TreeSet<Integer> edge : edges) {
            System.out.println(edge.toString());
        }
        System.out.println("Edges: " + edges.size());
    }
}

