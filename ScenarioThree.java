import java.io.*;
import java.util.*;
import java.util.Random;

public class ScenarioThree {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("No file specifed.");
            return;
        }
        String fileName = args[0];
        System.out.println("-- ScenarioThree --");
        System.out.println("File: " + fileName);

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
        
        ArrayList<String> removeVertices = new ArrayList<String>();
        HashSet<TreeSet<String>> removeEdges = new HashSet<TreeSet<String>>();
        
        //Create exactly 100 vertices to be removed
        int i = 0;
        int edgesCreated = 0;
        int sampleSize = vertices.length-1;
        String v1, v2;
        while(i < 100)
        {
        	v1 = vertices[new Random().nextInt(sampleSize)];
     	    //Stops from removing same vertex twice
     	    if(removeVertices.contains(v1))
     	    	continue;
            removeVertices.add(v1);
        	i++;
        }
        i = 0;
        //Create exactly 100 edges to remove
        while(i < 100)
        {
        	v1 = vertices[new Random().nextInt(sampleSize)];
            v2 = vertices[new Random().nextInt(sampleSize)];
        	TreeSet<String> edge = new TreeSet<String>();
        	edge.add(v1);
     	    edge.add(v2);
     	    //Stops duplicate edges in new edges
     	    if(removeEdges.contains(edge) || v1.equals(v2))
     	    	continue;
            removeEdges.add(edge);
        	i++;
        }
        //Add in the new edges
        long time = remove(adjgraph,removeVertices,removeEdges);
        System.out.println("AdjMatrix time: " + time);
        long time2 = remove(indgraph,removeVertices,removeEdges);
        System.out.println("IndMatrix time: " + time2);

    }
    static long remove(FriendshipGraph<String> graph,ArrayList<String> removeVertices,Set<TreeSet<String>> removeEdges) {
    	long startTime = System.nanoTime();
    	for(String v1 : removeVertices)
        {
         	graph.removeVertex(v1);
        	
       }
    	for (TreeSet<String> edge : removeEdges)
    	{
    		graph.removeEdge(edge.first(),edge.last());
    	}
    	return System.nanoTime() - startTime;
    }
    
}
