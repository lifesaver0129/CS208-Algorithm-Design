package DAGsort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;


public class ListGraph implements Graph{
	/*
	 * ListGraph类用邻接矩阵实现了Graph接口，并提供了Graph接口声明的所有抽象方法。
	 * Map变量graph用于存储节点及对应的含距离的邻接列表，方便快速得到相邻节点的距离。
	 * Map变量nameGraph用于存储节点名称及对应节点，方便快速定位节点。
	 * Map变量inverseGraph用于存储节点及对应的含距离的逆接列表，方便用于Kahn算法的拓扑排序。
	 * 布尔型变量coordinated用于表示图表是否包含坐标，当为真时，相邻节点的距离通过坐标计算得到；当为假时，相邻节点的距离默认为1。
	 */
	private Map<Node ,Map<Node, Double>> graph = new HashMap<Node, Map<Node, Double>>();;
	private Map<String, Node> nameGraph = new HashMap<String, Node>();;
	private Map<Node, Map<Node, Double>> inverseGraph;
	private boolean coordinated;
	
	public ListGraph(){
		/*
		 * 默认构造方法。
		 */
	}
	
	public ListGraph(boolean coordinated){
		/*
		 * 含coordinated的构造方法。
		 */
		this.coordinated = coordinated;
	}
	
	public Node getNode(String name){
		/*
		 * 返回Graph中节点名称对应的节点。
		 */
		return nameGraph.get(name);
	}
	

	
	public Node addNode(String name){
		/*
		 * 在Graph中添加新节点
		 */
		Node node = new Node(name);
		graph.put(node, null);
		nameGraph.put(name, node);
		return node;
	}
	
	public void addEdge(String name1,  String name2){
		/*
		 * 在Graph中添加边。
		 */
		addAdjNode(name1, name2);
		//addAdjNode(name2, name1);
	}
	
	
	private void addAdjNode(String name,  String adjName){
		/*
		 * 在Graph中往指定节点添加相邻节点。
		 */
		Node node = nameGraph.get(name);
		Node adjNode = nameGraph.get(adjName);
		Map<Node, Double> nodeMap = graph.get(node);
		if (nodeMap == null){
			nodeMap = new HashMap<Node, Double>();
		}
		addAdjNodeToMap(nodeMap, node, adjNode);
		graph.remove(node);
		graph.put(node, nodeMap);
	}
	
	private void addAdjNodeToMap(Map<Node, Double> nodeMap, Node node, Node adjNode){
		/*
		 * 在Graph中往指定节点的相邻链表添加节点，并设定距离。
		 */
		if (!coordinated)
			nodeMap.put(adjNode, 1.0);
		else{
			nodeMap.put(adjNode, 
					Math.sqrt(Math.pow(node.getX() - adjNode.getX(), 2)+
							Math.pow(node.getY() - adjNode.getY(), 2)));
			}
	}

	@Override
	public void getAllAdjNodes(Node node, 
			ArrayList<Node> allAdjNodes, ArrayList<Double> allAdjDistance) {
		/*
		 * 返回指定节点的所有邻近节点及其距离。
		 */
		Map<Node, Double> nodeMap = graph.get(node);
		if (nodeMap == null)
			return;
		Iterator<Entry<Node, Double>> it = nodeMap.entrySet().iterator();
		int i = 0;
		while (it.hasNext()){
			Entry<Node, Double> entry = it.next();
			allAdjNodes.add(i, entry.getKey());
			allAdjDistance.add(i, entry.getValue());
		}
		
	}

	@Override
	public ArrayList<Node> getAllNodes() {
		/*
		 * 返回Graph中所有节点
		 */
		ArrayList<Node> nodeList = new ArrayList<Node>();
		Iterator<Entry<String, Node>> it = nameGraph.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Node> entry = it.next();
			nodeList.add(entry.getValue());
		}
		return nodeList;
	}

	@Override
	public boolean isCoordinated() {
		/*
		 * 返回该图是否含有坐标的属性。
		 */
		return coordinated;
	}
	
	public void setCoordinated(boolean coordinated) {
		/*
		 * 设定该图是否含有坐标的属性。
		 */
		this.coordinated = coordinated;
	}

	@Override
	public Queue<Node> getZeroOutNodes() {
		/*
		 * 返回所有出度为0的节点，用于基于DFS的拓扑排序。
		 */
		Queue<Node> nodeQueue = new LinkedList<Node>();
		Iterator<Entry<Node, Map<Node, Double>>> it = graph.entrySet().iterator();
		while (it.hasNext()){
			Entry<Node, Map<Node, Double>> entry = it.next();
			if (entry.getValue() == null || entry.getValue().isEmpty()){
				nodeQueue.add(entry.getKey());
			}
		}
		return nodeQueue;
	}

	@Override
	public boolean hasEdge(Node node1, Node node2) {
		/*
		 * 返回节点1至节点2是否含有边。
		 */
		if (graph.get(node1) == null)
			return false;
		else if (graph.get(node1).get(node2) != null)
			return true;
		return false;
	}

	@Override
	public void buildInverse() {
		/*
		 * 构建逆接链表。
		 */
		inverseGraph = new HashMap<Node, Map<Node, Double>>();
		Iterator<Entry<String, Node>> it = nameGraph.entrySet().iterator();
		while (it.hasNext()){
			Entry<String, Node> entry = it.next();
			inverseGraph.put(entry.getValue(), new HashMap<Node, Double>());
		}
		Iterator<Entry<Node, Map<Node, Double>>> it2 = graph.entrySet().iterator();
		while (it2.hasNext()){
			Entry<Node, Map<Node, Double>> entry2 = it2.next();
			Map<Node, Double> map = entry2.getValue();
			if (map == null)
				continue;
			Iterator<Entry<Node, Double>> it3 = map.entrySet().iterator();
			while (it3.hasNext()){
				Entry<Node, Double> entry3 = it3.next();
				inverseGraph.get(entry3.getKey()).put(entry2.getKey(), entry3.getValue());
			}
		}
	}
	
	

	@Override
	public Queue<Node> getZeroInNodes() {
		/*
		 * 返回所有入度为0的节点，用于Kahn的拓扑排序。
		 */
		Queue<Node> queue = new LinkedList<Node>();
		Iterator<Entry<Node, Map<Node, Double>>> it = inverseGraph.entrySet().iterator();
		while (it.hasNext()){
			Entry<Node, Map<Node, Double>> entry = it.next();
			if (entry.getValue() == null || entry.getValue().isEmpty())
				queue.add(entry.getKey());
		}
		return queue;
	}

	@Override
	public void removeEdge(Node node1, Node node2) {
		/*
		 * 删除从节点1至节点2的边
		 */
		graph.get(node1).remove(node2);
		inverseGraph.get(node2).remove(node1);
		
	}

	@Override
	public int getInDegree(Node node) {
		/*
		 * 返回指定节点的入度。
		 */
		if (inverseGraph.get(node) == null)
			return 0;
		return inverseGraph.get(node).size();
	}

	@Override
	public int size() {
		/*
		 * 返回图的节点个数。
		 */
		return graph.size();
	}
	
}
