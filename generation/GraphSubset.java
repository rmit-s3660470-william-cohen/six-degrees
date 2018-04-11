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

        int start = (int) (Math.random()*graph.getNumVertices());
        Queue<String> vertsToAdd = new LinkedList<String>();
        String v = graph.getVertices().get(start);
        while(subgraph.getNumVertices() < 1000) {
            if ((String v = vertsToAdd.poll()) != null) {

            }
        }
    }
}
