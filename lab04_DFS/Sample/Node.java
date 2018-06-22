package DAGsort;

public class Node {
	/*
	 * Node类用于表示单个节点。
	 * String变量name表示节点名称。
	 * 整型变量x, y表示节点坐标（当Graph无坐标时，x,y为0,0），No表示在邻接矩阵Graph中的编号。
	 * Node变量lastNode保存BFS搜索过程中，当前节点的上一节点，用于输出路径。
	 * double变量distance保存BFS搜索过程中，从始发节点至当前节点的距离。
	 * boolean变量visited保存在基于DFS的拓扑排序中，该节点是否已访问。
	 * 始发节点的distance默认为0，其余节点的distance为上一节点的distance加上与上一节点的距离，用于BFS搜索。
	 */
	
	private String name;
	private int x, y, No;
	private Node lastNode;
	private double distance;
	private boolean visited;
	
	public Node(String name) {
		/*
		 * Node的初始化，默认设置坐标为(0, 0)
		 */
		this.setName(name);
		this.setX(0);
		this.setY(0);
	}
	
	public Node(){
		setName("");
		setX(setY(0));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public int setY(int y) {
		this.y = y;
		return y;
	}

	public Node getLastNode() {
		return lastNode;
	}

	public void setLastNode(Node lastNode) {
		this.lastNode = lastNode;
	}
	
	public boolean hasLastNode(){
		/*
		 * 返回当前节点是否含上一节点。
		 */
		return (this.lastNode != null);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getNo() {
		return No;
	}

	public void setNo(int no) {
		No = no;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}
