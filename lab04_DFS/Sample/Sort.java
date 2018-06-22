package DAGsort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Sort {
	/*
	 * Sort类为用于有向图DAG的拓扑排序类，提供以下静态方法：
	 * DFSsort方法：对图进行基于DFS的拓扑排序，并返回排序结果。
	 * KahnSort方法：对图进行Kahn算法的拓扑排序，并返回排序结果。
	 */
	
	public static Queue<Node> DFSsort(Graph graph){
		/*
		 * 对图进行基于DFS的拓扑排序，并返回排序结果。
		 */
		Queue<Node> queue = new LinkedList<Node>();
		Queue<Node> list = new LinkedList<Node>();
		queue.addAll(graph.getZeroOutNodes());
		for (Node node : queue){
			visit(node, graph, list);
		}
		return list;
	}

	private static void visit(Node node, Graph graph, Queue list) {
		/*
		 * 用于基于DFS的拓扑排序中实现递归的方法。
		 */
		if (!node.isVisited()){
			node.setVisited(true);
			for (Node mNode : graph.getAllNodes()){
				if (graph.hasEdge(mNode, node)){
					visit(mNode, graph, list);
				}
			}
			list.add(node);
		}
		
	}
	
	public static Queue<Node> KahnSort(Graph graph){
		/*
		 * 对图进行Kahn算法的拓扑排序，并返回排序结果。
		 */
		graph.buildInverse();
		Queue<Node> queue = new LinkedList<Node>();
		Queue<Node> list = new LinkedList<Node>();
		queue.addAll(graph.getZeroInNodes());
		while (!queue.isEmpty()){
			Node node = queue.poll();
			list.add(node);
			ArrayList<Node> allAdjNodes = new ArrayList<Node>();
			ArrayList<Double> allAdjDistance = new ArrayList<Double>();
			graph.getAllAdjNodes(node, allAdjNodes, allAdjDistance);
			for (Node mNode : allAdjNodes){
				graph.removeEdge(node, mNode);
				if (graph.getInDegree(mNode) == 0)
					queue.add(mNode);
			}
		}
		return list;
	}
	
	public static void printResult(Queue<Node> nodeList){
		System.out.println("Sorting Result:");
		if (nodeList == null || nodeList.size() == 0){
			System.out.println("No result!");
			return;
		}
		System.out.println();
		while(!nodeList.isEmpty()){
			Node node = nodeList.poll();
			System.out.printf("%s\t", node.getName());
		}
		System.out.println("\n");
	}

}
