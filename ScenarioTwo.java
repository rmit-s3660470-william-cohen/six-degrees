import java.io.*;
import java.util.*;

public class ScenarioTwo {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println();
        }
        String fileName = args[0];
        FriendshipGraph<String> adjgraph = new AdjMatrix<String>();
        FriendshipGraph<String> indgraph = new AdjMatrix<String>();
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

                adjgraph.addVertex(srcLabel);
                adjgraph.addVertex(tarLabel);
                adjgraph.addEdge(srcLabel, tarLabel);

                indgraph.addVertex(srcLabel);
                indgraph.addVertex(tarLabel);
                indgraph.addEdge(srcLabel, tarLabel);

            }
        } catch (FileNotFoundException ef) {
            System.err.println("Error: File not present.");
        } catch (IOException ei) {
            System.err.println("Error: Something went wrong.");
        }

        StringWriter vertexData = new StringWriter();
        adjgraph.printVertices(new PrintWriter(vertexData));
        String[] vertices = vertexData.toString().split(" ");
        //for (String v : vertices) {
          //  System.out.println(v);
        //}
        System.out.println("Total vertices: " + vertices.length);
        
        //TreeSet
        //int edgeIndex = new Random().nextInt(edges.size());

    }
}
