package DAGsort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.Map.Entry;

public class BFSsearch {
	/*
	 * BFSsearch类提供了BFS Search的静态方法。
	 * searchFrom方法：从指定节点开始BFS搜索，并返回所有可达到节点构成的队列。
	 * printResult方法：打印searchFrom方法的结果。
	 */
	
	public BFSsearch(){
		
	}
	
	public static Queue<Node> searchFrom(String name, Graph graph){
		/*
		 * 从指定节点开始BFS搜索，并返回所有可达到节点构成的队列。
		 */
		Node startNode = graph.getNode(name);
		startNode.setLastNode(null);
		startNode.setDistance(0.0);
		Queue<Node> queue = new LinkedList<Node>();
		Queue<Node> path = new LinkedList<Node>();
		// 将起点放入搜索路径与队列
		path.add(startNode);
		queue.add(startNode);
		while (!queue.isEmpty()){
			// 当队列不为空时，取出队首节点
			Node node = queue.poll();
			ArrayList<Node> allAdjNodes = new ArrayList<Node>();
			ArrayList<Double> allAdjDistance = new ArrayList<Double>();
			graph.getAllAdjNodes(node, allAdjNodes, allAdjDistance);
			// 判断队首节点是否含有相邻节点，若否，则跳过此次循环。
			if (allAdjNodes.size() == 0)
				continue;
			for (int i = 0; i < allAdjNodes.size(); i++){
				// 遍历相邻节点。
				Node adjNode = allAdjNodes.get(i);
				// 若相邻节点在搜索路径中，则跳过此次循环。
				if (path.contains(adjNode))
					continue;
				adjNode.setLastNode(node);
				adjNode.setDistance(node.getDistance() + allAdjDistance.get(i));
				// 将相邻元素放入搜索路径与队列中。
				path.add(adjNode);
				queue.add(adjNode);
				}
		}
		return path;
		
	}
	
	public static void printResult(Queue<Node> path){
		/*
		 * 打印searchFrom方法的结果。
		 */
		Node startNode = path.poll();
		System.out.println("Printing the Result:\n");
		System.out.printf("Starting From Node %s\n", startNode.getName());
		while (!path.isEmpty()){
			Node node = path.poll();
			String name = node.getName();
			System.out.printf("To Node %s:\tDistance %.2f\tPath \t", node.getName(), node.getDistance());
			Stack<Node> tempPath = new Stack<Node>();
			while (node.hasLastNode()){
				tempPath.add(node.getLastNode());
				node = node.getLastNode();
			}
			while (!tempPath.isEmpty()){
				System.out.printf("%s -> ", tempPath.pop().getName());
			}
			System.out.printf("%s\n", name);
		}
		System.out.println();
	}
	

}
