package DAGsort;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public interface Graph {
	/*
	 * Graph是用于表示图的接口，默认为有向图DAG，包含了以下抽象方法：
	 * getNode方法：返回Graph中节点名称对应的节点。
	 * addNode方法：在Graph中添加新节点。
	 * addEdge方法：在Graph中添加边。
	 * getAllNodes方法：返回Graph中所有节点。
	 * getAllAdjNodes方法：返回指定节点的所有邻近节点及其距离。
	 * isCoordinated方法：返回该图是否含有坐标的属性。
	 * setCoordinated方法：设定该图是否含有坐标的属性。
	 * getZeroOutNodes方法：返回所有出度为0的节点，用于基于DFS的拓扑排序。
	 * hasEdge方法：返回节点1至节点2是否含有边。
	 * buildInverse方法：构建逆接链表，只在ListGraph中有效，ArrayGraph中该方法为空。
	 * getZeroInNodes方法：返回所有入度为0的节点，用于Kahn的拓扑排序。
	 * removeEdge方法：删除从节点1至节点2的边
	 * getInDegree方法：返回指定节点的入度。
	 * size方法：返回图的节点个数。
	 * 同时，Graph还提供了以下方法：
	 * buildGraph方法：通过用户输入值，手动构造Graph，较为繁琐，不推荐使用。
	 * printGraph方法：打印所有节点及其相邻节点与距离。
	 * setXY方法：设置Graph中指定节点的坐标。
	 */
	
	public Node getNode(String name);
	public Node addNode(String name);
	public void addEdge(String name1,  String name2);
	public ArrayList<Node> getAllNodes();
	public void getAllAdjNodes(Node node, ArrayList<Node> allAdjNodes, ArrayList<Double> allAdjDistance);
	public boolean isCoordinated();
	public void setCoordinated(boolean coordinated);
	public Queue<Node> getZeroOutNodes();
	public boolean hasEdge(Node node1, Node node2);
	public void buildInverse();
	public Queue<Node> getZeroInNodes();
	public void removeEdge(Node node1, Node node2);
	public int getInDegree(Node node);
	public int size();
	
	public default void buildGraph(boolean coordinated){
		/*
		 * 通过用户输入值，手动构造Graph，较为繁琐，不推荐使用。
		 */
		setCoordinated(coordinated);
		Scanner input = new Scanner(System.in);
		ArrayList<Node> allNodes = new ArrayList<Node>();
		String name = "";
		System.out.println("Please enter all the nodes and end with -1:");
		while (!name.equals("-1")){
			name = input.next();
			if (name.equals("-1"))
				break;
			Node node = addNode(name);
			allNodes.add(node);
			
			
		}
		name = "";
		input.nextLine();
		for (Node node : allNodes){
			if (coordinated){
				System.out.printf("Enter the coordinate of %s in the form of (x, y)\n", node.getName());
				String a = input.nextLine();
				a = a.trim();
				a = a.substring(1, a.length() - 1);
				int x = Integer.parseInt(a.split(",")[0].trim());
				int y = Integer.parseInt(a.split(",")[1].trim());
				setXY(node.getName(), x, y);
			}
		}
		
		for (Node node : allNodes){
			System.out.printf("Enter the adjacent node of %s and end with -1\n", node.getName());
			String a = "";
			while (!a.equals("-1")){
				a = input.next();
				if (a.equals("-1"))
					break;
				addEdge(node.getName(), a);
			}
		}
		System.out.println();
		printGraph();
		
	}
	
	public default void printGraph(){
		/*
		 * printGraph方法：打印所有节点及其相邻节点与距离。
		 */
		
		ArrayList<Node> allNodes = getAllNodes();
		System.out.println("Printing the Graph:\n");
		//System.out.println(allNodes.size());
		for (Node node : allNodes){
			if (isCoordinated())
				System.out.printf("Node:\t%s\tCoordinate:\t(%d, %d)\tAdjancent Nodes:\t", node.getName(), node.getX(), node.getY());
			else
				System.out.printf("Node:\t%s\tAdjancent Nodes:\t", node.getName());
			ArrayList<Node> allAdjNodes = new ArrayList<Node>();
			ArrayList<Double> allAdjDistance = new ArrayList<Double>();
			getAllAdjNodes(node, allAdjNodes, allAdjDistance);
			for (int i = 0; i < allAdjNodes.size(); i++){
				System.out.printf("%s(%.2f)\t", allAdjNodes.get(i).getName(), allAdjDistance.get(i));
			}
			System.out.println();
		}
		System.out.println();
	};
	
	public default void setXY(String name, int x, int y){
		/*
		 * 设置Graph中指定节点的坐标。
		 */
		Node node = getNode(name);
		node.setX(x);
		node.setY(y);
	}
	
	
	
}
