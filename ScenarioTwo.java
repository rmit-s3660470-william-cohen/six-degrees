import java.io.*;
import java.util.*;

public class ScenarioTwo {
    static final int NUMBER_OF_PAIRS = 500;
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println();
        }
        String fileName = args[0];
        FriendshipGraph<String> adjgraph = new AdjMatrix<String>();
        FriendshipGraph<String> indgraph = new IndMatrix<String>();
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
        HashSet<TreeSet<String>> pairs = new HashSet<TreeSet<String>>();
        int sampleSize = vertices.length-1;
        while (pairs.size() < NUMBER_OF_PAIRS) {
            String v1 = vertices[new Random().nextInt(sampleSize)];
            String v2 = vertices[new Random().nextInt(sampleSize)];
            TreeSet<String> pair = new TreeSet<String>();
    	    pair.add(v1);
    	    pair.add(v2);
            pairs.add(pair);
        }
        long startTime, timeTaken;
        double avgDist;

        System.out.println("Timing AdjMatrix...");
        startTime = System.nanoTime();
        avgDist = 0;
        for (TreeSet<String> p : pairs) {
            String v1 = p.first();
            String v2 = p.last();
            int dist = adjgraph.shortestPathDistance(v1,v2);
            avgDist += (dist == -1 ? 0 : dist);
        }
        avgDist /= 100;
        timeTaken = System.nanoTime()-startTime;
        System.out.println("Avg. Distance: " + avgDist);
        System.out.println("Time taken (ns): " + timeTaken);

        System.out.println("Timing AdjMatrix again...");
        startTime = System.nanoTime();
        avgDist = 0;
        for (TreeSet<String> p : pairs) {
            String v1 = p.first();
            String v2 = p.last();
            int dist = adjgraph.shortestPathDistance(v1,v2);
            avgDist += (dist == -1 ? 0 : dist);
        }
        avgDist /= 100;
        timeTaken = System.nanoTime()-startTime;
        System.out.println("Avg. Distance: " + avgDist);
        System.out.println("Time taken (ns): " + timeTaken);

    }
    double avgDistance(FriendshipGraph<String> graph, Set<String> pairs) {

    }
}
