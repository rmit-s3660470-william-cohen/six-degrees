import java.io.*;
import java.util.*;

public class GraphToJSON {
    public static void main(String[] args) throws Exception {
        String inputFilename = "facebook_combined.txt";

        FriendshipGraph<String> graph = new AdjMatrix<String>();

        // if file specified, then load file
        if (inputFilename != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(inputFilename));

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
            }
            catch (FileNotFoundException ex) {
                System.err.println("File " + args[1] + " not found.");
            }
            catch(IOException ex) {
                System.err.println("Cannot open file " + args[1]);
            }
        }
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("graph.js"));
            writer.println("{");
            writer.println("\t\"nodes\": [");

            StringWriter vertices = new StringWriter();
            graph.printVertices(new PrintWriter(vertices));
            //System.out.println(vertices.toString());
            for (String v : vertices.toString().split(" ")) {
                if (v.equals("\n")) continue;
                writer.println("\t\t{\"data\": {\"id\": \"" + v +"\"}},");
            }
            writer.println("\t],");

            writer.println("\t\"edges\": [");

            StringWriter edges = new StringWriter();
            graph.printEdges(new PrintWriter(edges));
            //System.out.println(edges.toString());
            for (String e : edges.toString().split("\n")) {
                String[] ends = e.split(" ");
                String from = ends[1];
                String to = ends[0];
                writer.println("\t\t{\"data\": {\"source\": \"" + from +"\", \"target\": \"" + to + "\"}},");
            }
            writer.println("\t]");
            writer.println("}");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
