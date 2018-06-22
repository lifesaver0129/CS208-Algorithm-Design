package DAGsort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ArrayGraph implements Graph{
	/*
	 * ArrayGraph类用邻接矩阵实现了Graph接口，并提供了Graph接口声明的所有抽象方法。
	 * Node型矩阵nodeList存储了图上所有的节点。
	 * double型矩阵adjMat表示邻接矩阵，及所有节点间的距离。
	 * 整型变量nNodes表示当前图上的节点总数。
	 * 布尔型变量coordinated用于表示图表是否包含坐标，当为真时，相邻节点的距离通过坐标计算得到；当为假时，相邻节点的距离默认为1。
	 */
	private Node nodeList[];
	private double adjMat[][];
	private int nNodes = 0;
	private boolean coordinated = false;
	
	public ArrayGraph(){
		/*
		 * 默认构造方法。
		 */
		nodeList = new Node[10];
		adjMat = new double[10][10];
	}
	
	public ArrayGraph(boolean coordinated){
		/*
		 * 含coordinated属性的构造方法。
		 */
		nodeList = new Node[10];
		adjMat = new double[10][10];
		this.coordinated = coordinated;
	}
	
	public ArrayGraph(int nNodes){
		/*
		 * 含nNodes属性的构造方法。
		 */
		this.nNodes = nNodes;
		nodeList = new Node[nNodes];
		adjMat = new double[nNodes][nNodes];
	}

	@Override
	public Node getNode(String name) {
		/*
		 * 返回Graph中节点名称对应的节点。
		 */
		for (Node node : nodeList){
			if (node.getName().equals(name))
				return node;
		}
		return null;
	}

	@Override
	public Node addNode(String name) {
		/*
		 * 在Graph中添加新节点
		 */
		Node node = new Node(name);
		node.setNo(nNodes);
		if (nNodes > nodeList.length)
			enlargeCapcity();
		nodeList[nNodes++] = node;
		return node;
	}

	private void enlargeCapcity() {
		/*
		 * 扩展nodeList与adjMat的大小，以避免溢出。
		 */
		int currentLength = nodeList.length;
		Node newNodeList[] = new Node[currentLength * 2];
		double newAdjMat[][] = new double[currentLength * 2][currentLength * 2];
		for (int i = 0; i < currentLength; i++){
			newNodeList[i] = nodeList[i];
			for (int j = 0; j < currentLength; j++){
				newAdjMat[i][j] = adjMat[i][j];
			}
		}
		nodeList = newNodeList;
		adjMat = newAdjMat;
		
	}

	@Override
	public void addEdge(String name1, String name2) {
		/*
		 * 在Graph中添加边。
		 */
		Node node1 = getNode(name1);
		Node node2 = getNode(name2);
		if (coordinated){
			adjMat[node1.getNo()][node2.getNo()] = Math.sqrt(Math.pow(node1.getX() - node2.getX(), 2)+
					Math.pow(node1.getY() - node2.getY(), 2));
		}
		else{
			adjMat[node1.getNo()][node2.getNo()] = 1;
			//adjMat[node2.getNo()][node1.getNo()] = 1;
		}
		
	}

	@Override
	public void getAllAdjNodes(Node node, ArrayList<Node> allAdjNodes, ArrayList<Double> allAdjDistance) {
		/*
		 * 返回指定节点的所有邻近节点及其距离。
		 */
		int no = node.getNo();
		for (int i = 0; i < nNodes; i++){
			if (adjMat[no][i] != 0){
				Node adjNode = nodeList[i];
				allAdjNodes.add(adjNode);
				allAdjDistance.add(adjMat[no][i]);
			}
		}
	}

	@Override
	public ArrayList<Node> getAllNodes() {
		/*
		 * 返回Graph中所有节点
		 */
		ArrayList<Node> allNodes = new ArrayList<Node>();
		for (Node node : nodeList){
			if (node == null)
				break;
			allNodes.add(node);
		}
		return allNodes;
	}

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
		Queue<Node> queue = new LinkedList<Node>();
		for (int i = 0; i < nNodes; i++){
			boolean zeroOut = true;
			for (int j = 0; j < nNodes; j++){
				if (adjMat[i][j] != 0)
					zeroOut = false;
			}
			if (zeroOut)
				queue.add(nodeList[i]);
		}
		return queue;
	}

	@Override
	public boolean hasEdge(Node node1, Node node2) {
		/*
		 * 返回节点1至节点2是否含有边。
		 */
		return (adjMat[node1.getNo()][node2.getNo()] != 0);
	}

	@Override
	public void buildInverse() {
	/*
	 * 空方法
	 */
	}

	@Override
	public Queue<Node> getZeroInNodes() {
		/*
		 * 返回所有入度为0的节点，用于Kahn的拓扑排序。
		 */
		Queue<Node> queue = new LinkedList<Node>();
		for (int i = 0; i < nNodes; i++){
			boolean zeroIn = true;
			for (int j = 0; j < nNodes; j++){
				if (adjMat[j][i] != 0)
					zeroIn = false;
			}
			if (zeroIn){
				queue.add(nodeList[i]);
				//System.out.println(nodeList[i].getName());
			}
		}
		return queue;
	}

	@Override
	public void removeEdge(Node node1, Node node2) {
		/*
		 * 删除从节点1至节点2的边
		 */
		adjMat[node1.getNo()][node2.getNo()] = 0;
	}

	@Override
	public int getInDegree(Node node) {
		/*
		 * 返回指定节点的入度。
		 */
		int inDegree = 0;
		for (int i = 0; i < nNodes; i++){
			if (adjMat[i][node.getNo()] != 0)
				inDegree++;
		}
		return inDegree;
	}

	@Override
	public int size() {
		/*
		 * 返回图的节点个数。
		 */
		return nNodes;
	}

	
}
