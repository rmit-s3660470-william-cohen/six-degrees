public class WillTest {
    public static void main(String[] args) {
        FriendshipGraph<String> graph = new AdjMatrix<String>();
        System.out.println("Added vertices...");
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        System.out.println("Adding edges...");
        graph.addEdge("A","B");
        graph.addEdge("C","B");
        graph.addEdge("B","D");
        graph.addEdge("A","E");
        graph.addEdge("D","C");
        //graph.addEdge("E", "F"); //Test edge for removal
        //System.out.println("Removing vertex 'C'...");
        //graph.removeVertex("C");
        System.out.println("Distance from E to B...");
        System.out.println(graph.shortestPathDistance("E", "B"));
        System.out.println("Distance from E to C...");
        System.out.println(graph.shortestPathDistance("E", "C"));
        System.out.println("Distance from B to F...");
        System.out.println(graph.shortestPathDistance("B", "F"));


    }
}
