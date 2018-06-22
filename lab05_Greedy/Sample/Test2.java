package Greedy_MST;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by XWT on 2017/3/30.
 */
public class Test2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Graph graph = new Graph();
        System.out.println("Please enter the input size 'n': ");

        int inputSize = scanner.nextInt();
        Node[] nodes = new Node[inputSize];
        int index;
        for (int i = 0; i < inputSize; i++) {
            nodes[i] = new Node(i + "", 0, 0);
        }

        for(int i = 0; i < inputSize; i++) {
            for (int j = 0; j < inputSize / 3; j++) {
                index = random.nextInt(inputSize - 1);
                if (index != i)
                    graph.addEdge(nodes[i], nodes[index], random.nextDouble());
            }
        }

        long startTime = 0;
        long endTime = 0;

        System.out.println("MST from Prim method:");
        startTime = System.currentTimeMillis();
        MSTGenerator generator = new MSTGenerator(graph);
        Tree mst = generator.primMethod(nodes[0]);
        endTime = System.currentTimeMillis();
//        System.out.print(mst);
        System.out.println("Total time: " + (endTime - startTime) + "\n");

        System.out.println("MST from Kruskal method:");
        startTime = System.currentTimeMillis();
        Tree mst2 = generator.kruskalMethod(nodes[0]);
        endTime = System.currentTimeMillis();
//        System.out.print(mst2);
        System.out.println("Total time: " + (endTime - startTime) + "\n");

        System.out.println("MST from Boruvka method:");
        startTime = System.currentTimeMillis();
        Tree mst3 = generator.boruvkaMethod(nodes[0]);
        endTime = System.currentTimeMillis();
//        System.out.print(mst3);
        System.out.println("Total time: " + (endTime - startTime) + "\n");
    }
}
