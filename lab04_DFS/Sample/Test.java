package DAGsort;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Test {
	/*
	 * 测试类
	 */

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		while(true){
			System.out.println("Choose the one you want to test:\n"
					+ "1. build ArrayGraph Method\n"
					+ "2. build ListGraph Method\n"
					+ "3. ArrayGraph with DFS Sort\n"
					+ "4. ListGraph with DFS Sort\n"
					+ "5. ArrayGraph with Kahn Sort\n"
					+ "6. ListGraph with Kahn Sort\n"
					+ "7: Exit");
			int a = input.nextInt();
			if (a == 7)
				break;
			switch (a){
			case 1: testBuildGraph(false); break;
			case 2: testBuildGraph(true); break;
			case 3: testGraph(false, true); break;
			case 4: testGraph(true, true); break;
			case 5: testGraph(false, false); break;
			case 6: testGraph(true, false); break;
			default: break;
			}
		}
	}
	
	private static void testGraph(boolean usingList, boolean dfs){
		Graph graph;
		String graphString = "";
		
		if (usingList){
			graphString += "ListGraph";
			graph = new ListGraph(false);
		}
		else{
			graphString += "ArrayGraph";
			graph = new ArrayGraph(false);
		}
		
		setGraph(graph);
		graph.printGraph();
		Queue<Node> result;
		
		if (dfs){
			graphString += " with DFS Sort";
			result = Sort.DFSsort(graph);
		}
		else{
			graphString += " with Kahn Sort";
			result = Sort.KahnSort(graph);
		}
		
		int resultSize = result.size();
		
		System.out.printf("\nTesting %s:\n\n", graphString);
		
		Sort.printResult(result);
		
		if (!dfs){
			if (resultSize != graph.size())
				System.out.println("The graph has a circle!\n");
			else
				System.out.println("The graph doesn't have a circle!\n");
		}
		
		pressToContinue();
	}
	


	private static void testBuildGraph(boolean usingList) {
		Graph g;
		if (usingList){
			g = new ListGraph(true);
		}
		else{
			g = new ArrayGraph(true);
		}
		g.buildGraph(false);
		
		System.out.println("Enter 1 for DFS Sort and 2 for Kahn Sort");
		Scanner input = new Scanner(System.in);
		Queue<Node> result = new LinkedList<Node>();
		int a =input.nextInt();
		switch (a){
		case 1: result = Sort.DFSsort(g); break;
		case 2: result = Sort.KahnSort(g); break;
		default: break;
		}
		int resultSize = result.size();
		System.out.println();
		Sort.printResult(result);
		if (a == 2){
			if (resultSize != g.size())
				System.out.println("\nThe graph has a circle!\n");
			else
				System.out.println("\nThe graph doesn't have a circle!\n");
		}
		
		pressToContinue();
	}
	
	private static void pressToContinue(){
		System.out.println("Press Enter to Continue");
		Scanner input = new Scanner(System.in);
		input.nextLine();
	}
	

	private static void setGraph(Graph graph){
		/*
		 * 设置graph为测试用特定图表
		 */
		graph.addNode("1");
		graph.addNode("2");
		graph.addNode("3");
		graph.addNode("4");
		graph.addNode("5");
		graph.addNode("6");
		graph.addNode("7");
		graph.addNode("8");
		graph.setXY("1", 2, 6);
		graph.setXY("2", 1, 4);
		graph.setXY("3", 3, 4);
		graph.setXY("4", 0, 2);
		graph.setXY("5", 2, 2);
		graph.setXY("6", 2, 0);
		graph.setXY("7", 4, 6);
		graph.setXY("8", 4, 2);
		
		graph.addEdge("1", "2");
		graph.addEdge("1", "3");
		graph.addEdge("2", "3");
		graph.addEdge("2", "4");
		graph.addEdge("2", "5");
		graph.addEdge("3", "5");
		graph.addEdge("3", "7");
		graph.addEdge("3", "8");
		graph.addEdge("4", "5");
		graph.addEdge("5", "6");
		graph.addEdge("7", "8");
		
		
//		graph.addAdjNode("1", "2");
//		graph.addAdjNode("2", "1");
//		graph.addAdjNode("1", "3");
//		graph.addAdjNode("3", "1");
//		graph.addAdjNode("2", "3");
//		graph.addAdjNode("3", "2");
//		graph.addAdjNode("2", "4");
//		graph.addAdjNode("4", "2");
//		graph.addAdjNode("2", "5");
//		graph.addAdjNode("5", "2");
//		graph.addAdjNode("4", "5");
//		graph.addAdjNode("5", "4");
//		graph.addAdjNode("5", "3");
//		graph.addAdjNode("3", "5");
//		graph.addAdjNode("3", "7");
//		graph.addAdjNode("7", "3");
//		graph.addAdjNode("3", "8");
//		graph.addAdjNode("8", "3");
//		graph.addAdjNode("8", "7");
//		graph.addAdjNode("7", "8");
//		graph.addAdjNode("5", "6");
//		graph.addAdjNode("6", "5");
	}

}
