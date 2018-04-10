import java.io.*;
import java.util.*;

public class GraphGenerator {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Error: Must specify number of Vertices, Edges and also file to write to");
            System.err.println("Usage: GraphGenerator [vertices] [edges] [file out]");
            System.exit(1);
        }
        int v = Integer.parseInt(args[0]);
        int e = Integer.parseInt(args[1]);
        if (e > v*(v-1)/2) {
            System.err.println("Error: Too many edges.");
        }
        String fileOut = args[2];
        System.out.println("Generating graph with " + v + "," + e + "...");
        //TODO generate graph
        FriendshipGraph<Integer> graph = new AdjMatrix<Integer>();
        Set<Integer> vertices = new TreeSet<Integer>();
        Set<TreeSet<Integer>> edges = new HashSet<TreeSet<Integer>>();
        System.out.println("Generating vertices..");
        for (int vert = 0; vert < v; vert++) {
            vertices.add(vert);
        }
        System.out.println("Generating edges..");
        Random rand = new Random();
        while (edges.size() < e) {
            int v1 = rand.nextInt(v);
            int v2 = rand.nextInt(v);
            if (v1 == v2) continue;
            TreeSet<Integer> newEdge = new TreeSet<Integer>();
            newEdge.add(v1);
            newEdge.add(v2);
            edges.add(newEdge);
            System.out.println(edges.toString());
        }
        for (Integer vert : vertices) {
            graph.addVertex(vert);
        }
        for (TreeSet<Integer> edge : edges) {
            List<Integer> connectedVertices = new ArrayList<Integer>(edge);
            int v1 = connectedVertices.get(0);
            int v2 = connectedVertices.get(1);
            graph.addEdge(v1, v2);
        }
        graph.printEdges(new PrintWriter(System.out));
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            //print edges
            writer.println("test");
            writer.close();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}
