public class WillTest {
    public static void main(String[] args) {
        FriendshipGraph<String> graph = new AdjMatrix<String>();
        System.out.println("Added vertices...");
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        System.out.println("Adding edges...");
        graph.addEdge("A","B");
        graph.addEdge("B","C");
        for (Object n : graph.neighbours("B")) {
            System.out.print(n.toString() + " ");
        }
    }
}
