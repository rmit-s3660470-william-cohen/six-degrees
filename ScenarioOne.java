import java.io.*;
import java.util.*;
import java.util.Random;

public class ScenarioOne {
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
        
        //Add new vertices
        long time = timeAddVertex(adjgraph);
        long time2 = timeAddVertex(indgraph);

        StringWriter vertexData = new StringWriter();
        adjgraph.printVertices(new PrintWriter(vertexData));
        String[] vertices = vertexData.toString().split(" ");
        
        HashSet<TreeSet<String>> newEdges = new HashSet<TreeSet<String>>();
        
        //Create exactly 100 edges
        int i = 0;
        int edgesCreated = 0;
        int sampleSize = vertices.length-1;
        String v1, v2;
        Random r = new Random();
        int x;
        while(i < 100)
        {
        	x = r.nextInt(5099 - 5000 + 1) + 5000;
        	v1 = String.valueOf(x);
            v2 = vertices[new Random().nextInt(sampleSize)];
        	TreeSet<String> edge = new TreeSet<String>();
        	edge.add(v1);
     	    edge.add(v2);
     	    //Stops duplicate edges in new edges
     	    if(newEdges.contains(edge) || v1.equals(v2))
     	    	continue;
            newEdges.add(edge);
        	i++;
        }
        //Add in the new edges
        time += timeAddEdge(adjgraph,newEdges);
        System.out.println("AdjMatrix time: " + time);
        time2 += timeAddEdge(indgraph,newEdges);
        System.out.println("IndMatrix time: " + time2);

    }
    static long timeAddVertex(FriendshipGraph<String> graph) {
    	long startTime = System.nanoTime();
    	for(int i = 5000; i < 5100; i++)
        {
        	graph.addVertex(String.valueOf(i));
        }
    	return System.nanoTime() - startTime;
    }
    
    static long timeAddEdge(FriendshipGraph<String> graph, Set<TreeSet<String>> newEdges) {
    	long startTime = System.nanoTime();
    	for (TreeSet<String> edge : newEdges) {
        	graph.addEdge(edge.first(),edge.last());
        }
    	return System.nanoTime() - startTime;
    }
    
}
