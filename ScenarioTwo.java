import java.io.*;
import java.util.*;

public class ScenarioTwo {
    public static void main(String[] args) {
        String fileName = args[0];
        FriendshipGraph<String> graph = new AdjMatrix<String>();
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
        StringWriter vertexData = new StringWriter();
        graph.printVertices(new PrintWriter(vertexData));
        String[] vertices = vertexData.toString().split(" ");


    }
}
