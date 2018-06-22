package Greedy_MST;

/**
 * Created by admin on 2017/3/30.
 */
public class Test1 {
    public static void main(String[] args) {
        Node node1 = new Node("1", 0, 0);
        Node node2 = new Node("2", 0, 0);
        Node node3 = new Node("3", 0, 0);
        Node node4 = new Node("4", 0, 0);
        Node node5 = new Node("5", 0, 0);
        Node node6 = new Node("6", 0, 0);
        Node node7 = new Node("7", 0, 0);
        Node node8 = new Node("8", 0, 0);

        Graph graph = new Graph();
        graph.addEdge(node1, node2, 1);
        graph.addEdge(node1, node3, 2);
        graph.addEdge(node2, node3, 4);
        graph.addEdge(node2, node5, 7);
        graph.addEdge(node2, node4, 5);
        graph.addEdge(node3, node7, 3);
        graph.addEdge(node3, node8, 4);
        graph.addEdge(node4, node5, 6);
        graph.addEdge(node5, node6, 5);
        graph.addEdge(node7, node8, 7);

        long startTime = 0;
        long endTime = 0;

        System.out.println("MST from Prim method:");
        startTime = System.currentTimeMillis();
        MSTGenerator generator = new MSTGenerator(graph);
        Tree mst = generator.primMethod(node1);
        endTime = System.currentTimeMillis();
        System.out.print(mst);
        System.out.println("Total time: " + (endTime - startTime) + "\n");

        System.out.println("MST from Kruskal method:");
        startTime = System.currentTimeMillis();
        Tree mst2 = generator.kruskalMethod(node1);
        endTime = System.currentTimeMillis();
        System.out.print(mst2);
        System.out.println("Total time: " + (endTime - startTime) + "\n");

        System.out.println("MST from Boruvka method:");
        startTime = System.currentTimeMillis();
        Tree mst3 = generator.boruvkaMethod(node1);
        endTime = System.currentTimeMillis();
        System.out.print(mst3);
        System.out.println("Total time: " + (endTime - startTime) + "\n");
    }
}
