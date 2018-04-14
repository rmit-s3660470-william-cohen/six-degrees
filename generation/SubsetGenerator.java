import java.io.*;
import java.util.*;

public class SubsetGenerator {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Error: No graph file specified.");
            System.err.println("Usage: GraphSubset [graph file] [#vertices to sample] [target density]");
            System.err.println("Example: Subset graph.txt 100 0.25");
            return;
        }
        String fileName = args[0];
        int verticesInSubset = Integer.parseInt(args[1]);
        double targetDensity = Double.parseDouble(args[2]);
        //DEBUG
        System.out.println("Params: " + fileName + ", " + verticesInSubset + ", " + targetDensity);
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
        System.out.println();
        System.out.println("Generating subset of " + fileName + "...");
        System.out.println();
        //create new graph
        Graph<String> subgraph = new Graph<String>();
        //breadth first traverse the full graph
        int randomIndex = (int) (Math.random()*graph.getNumVertices());
        Queue<String> vertsToVisit = new LinkedList<String>();
        List<String> visited = new LinkedList<String>();
        vertsToVisit.add(graph.getVertices().get(randomIndex));
        String v;
        while(subgraph.getNumVertices() < verticesInSubset) {
            if ((v = vertsToVisit.poll()) != null) {
                if (visited.contains(v)) continue;
                visited.add(v);
                subgraph.addVertex(v);
                for (String w : graph.neighbours(v)) {
                    if (subgraph.getNumVertices() >= verticesInSubset) break;
                    vertsToVisit.add(w);
                    subgraph.addVertex(w);
                    subgraph.addEdge(v,w);
                }
            } else {
                randomIndex = (int) (Math.random()*graph.getNumVertices());
                vertsToVisit.add(graph.getVertices().get(randomIndex));
            }
        }
        System.out.print("Altering edges to meet target density...");
        if (targetDensity > subgraph.getDensity()) {
            //add edges
            while (subgraph.getDensity() < targetDensity) {
                //TODO fix this shit
                randomIndex = (int) (Math.random()*subgraph.getNumVertices());
                String v1 = subgraph.getVertices().get(randomIndex);
                randomIndex = (int) (Math.random()*subgraph.getNumVertices());
                String v2 = subgraph.getVertices().get(randomIndex);
                subgraph.addEdge(v1,v2);
            }
        } else if (targetDensity < subgraph.getDensity()) {
            //remove edges
            randomIndex = (int) (Math.random()*subgraph.getNumVertices());
            String v1 = subgraph.getVertices().get(randomIndex);
            randomIndex = (int) (Math.random()*subgraph.getNumVertices());
            String v2 = subgraph.getVertices().get(randomIndex);
            subgraph.removeEdge(v1,v2);

        } else {
            System.out.println("No edges to alter.");
        }
        System.out.println("Done.");
        System.out.println();
        System.out.println("--- (Generated) Sub graph ---");
        System.out.println("Vertices: " + subgraph.getNumVertices());
        System.out.println("Edges: " + subgraph.getNumEdges());
        System.out.println("Density: " + subgraph.getDensity());
        String subgraphFile = targetDensity + "_" + verticesInSubset + "_" + fileName;
        try {
            System.out.println("Writing to file '" + subgraphFile + "'...");
            PrintWriter writer = new PrintWriter(new File(subgraphFile));
            subgraph.printEdgesNoDup(writer);
            writer.close();
        } catch (Exception e) {
            System.err.println("Error: Couldnt write to file.");
        }
    }
}
