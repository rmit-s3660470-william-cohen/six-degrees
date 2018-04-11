import java.io.*;
import java.util.*;

public class GraphSubset {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: No graph file specified.");
            System.err.println("Usage: GraphSubset [graph file]");
            return;
        }
        String fileName = args[0];
        Graph<String> graph = new Graph<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line;
            String delimiter = " ";
            String[] tokens;
            String srcLabel, tarLabel;

            while ((line = reader.readLine()) != null) {
                tokens = line.split(delimiter);
                srcLabel = tokens[0];
                tarLabel = tokens[1];
                graph.addVertex(srcLabel);
                graph.addVertex(tarLabel);
                graph.addEdge(srcLabel, tarLabel);
            }
        } catch (FileNotFoundException ef) {
            System.err.println("Error: File not present.");
        } catch (IOException ei) {
            System.err.println("Error: Something went wrong.");
        }
        System.out.println("--- Full graph ---");
        System.out.println("Vertices: " + graph.getNumVertices());
        System.out.println("Edges: " + graph.getNumEdges());
        System.out.println("Density: " + graph.getDensity());

        Graph<String> subgraph = new Graph<String>();
        int start = (int) (Math.random()*graph.getNumVertices());
        Queue<String> vertsToVisit = new LinkedList<String>();
        List<String> visited = new LinkedList<String>();
        vertsToVisit.add(graph.getVertices().get(start));
        String v;
        while(subgraph.getNumVertices() < 1000) {
            if ((v = vertsToVisit.poll()) != null) {
                if (visited.contains(v)) continue;
                visited.add(v);
                subgraph.addVertex(v);
                for (String w : graph.neighbours(v)) {
                    if (subgraph.getNumVertices() >= 1000) break;
                    vertsToVisit.add(w);
                    subgraph.addVertex(w);
                    subgraph.addEdge(v,w);
                }
            } else {
                start = (int) (Math.random()*graph.getNumVertices());
                vertsToVisit.add(graph.getVertices().get(start));
            }
        }

        System.out.println("--- Sub graph ---");
        System.out.println("Vertices: " + subgraph.getNumVertices());
        System.out.println("Edges: " + subgraph.getNumEdges());
        System.out.println("Density: " + subgraph.getDensity());
        System.out.println("Writing to file 'test.txt'...");
        try {
            PrintWriter writer = new PrintWriter(new File("test.txt"));
            subgraph.printEdges(writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
