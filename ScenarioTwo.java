import java.io.*;
import java.util.*;

class Pair<T extends Object> {
    T t1;
    T t2;
    public Pair(T t1, T t2) {
        this.t1 = t1;
        this.t2 = t2;
    }
    @Override
    public boolean equals(Object o) {
        //if ()
        return false;
    }

}

public class ScenarioTwo {
    static final int NUMBER_OF_PAIRS = 100;
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println();
        }
        System.out.println("Loading graph data...");
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
        System.out.println("Generatinig pairs...");
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
        System.out.println("-- Testing with " + NUMBER_OF_PAIRS + "pairs --");
        timeAvgDistance(adjgraph, pairs);
        long time = timeAvgDistance(adjgraph, pairs);
        System.out.println("AdjMatrix time: " + time);

        long time2 = timeAvgDistance(adjgraph, pairs);
        System.out.println("IndMatrix time: " + time2);
    }
    static long timeAvgDistance(FriendshipGraph<String> graph, Set<TreeSet<String>> pairs) {
        long startTime = System.nanoTime();
        double avgDist = 0;
        for (TreeSet<String> p : pairs) {
            String v1 = p.first();
            String v2 = p.last();
            int dist = graph.shortestPathDistance(v1,v2);
            avgDist += (dist == -1 ? 0 : dist);
        }
        avgDist /= 100;
        return System.nanoTime() - startTime;
    }
}
