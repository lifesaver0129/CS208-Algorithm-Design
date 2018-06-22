package cl03_graph;

import java.util.ArrayList;

public class Graph {
	
	private int max = 20;
	private Vertex vertexList[];
	private boolean is = false;
	private int nVerts = 0;
	
	private Vertex bfs[];
	
	public Graph(){
		vertexList = new Vertex[max];
		bfs = new Vertex[max];
	}
	
	public Graph(int n){
		vertexList = new Vertex[n];
		bfs = new Vertex[n];
	}
	
	public Graph(int n, boolean is){
		this.is = is;
		vertexList = new Vertex[n];
		bfs = new Vertex[n];
	}
	
	public boolean isIs() {
		return is;
	}
	
	public void setIs(boolean is) {
		this.is = is;
	}
	
	public Vertex[] getVertexList() {
		return vertexList;
	}
	
	public Vertex getspecV(int a){
		return vertexList[a-1];
	}
	
	public Vertex[] getBfs() {
		return bfs;
	}
	
	public void addVertex(Vertex vertex){
		vertex.setIndex(nVerts);
		vertexList[nVerts] = vertex;
		nVerts++;
	}
	
	public void addEdge(int startnum, int endnum){
		vertexList[startnum-1].addAdj(vertexList[endnum-1]);
		if (!is) {vertexList[endnum-1].addAdj(vertexList[startnum-1]);
		}
	}
	
	public Vertex getAdjVertex(Vertex vertex){	
		ArrayList<Vertex> adjVertexs = vertex.getAdj();
		for (int i = 0; i < adjVertexs.size(); i++) {
			if(!adjVertexs.get(i).isVisted){
				return adjVertexs.get(i);
			}
		}
		return null;
	}
	
	public void displayGraph(){
		for (int i = 0; i < vertexList.length; i++) {
			printVertx(vertexList[i]);
		}
	}
	
	public void printVertx(Vertex vertex){
		ArrayList<Vertex> next = vertex.getAdj();
		if(next == null){ System.out.println(vertex.toString()+" no adjacnent");}
		else{
			System.out.print(vertex.toString()+"adjacent:");
			for (int i = 0; i < next.size(); i++) {
				System.out.print(next.get(i).label+" ");
			}
			System.out.println();
		}
	}	

	public void bfs(Vertex x) {
		x.index=0;
		System.out.print("BFS search order: ");
		Queue queue = new Queue(max);
		int a=x.getData()-1;
		vertexList[a].isVisted = true;
		bfs[0] = vertexList[a];
		queue.enQueue(vertexList[a]);
		int bfsIndex = 0;
		Vertex vertex1;
		while(!queue.isEmpty()){
			Vertex vertex2 = (Vertex)vertexList[queue.deQueue()-1];
			
			while((vertex1 = getAdjVertex(vertex2))!=null){
				vertex1.isVisted = true;
				if(x.isFix()){
					vertex1.index=vertex2.index+(int)Math.sqrt((vertex2.x-vertex1.x)*
							(vertex2.x-vertex1.x)+(vertex2.y-vertex1.y)*(vertex2.y-vertex1.y));
				}
				else
					vertex1.index=vertex2.index+1;
				bfs[++bfsIndex] = vertex1;
				queue.enQueue(vertex1);
			}
		}
		System.out.println();
		showdis(x);
	}
	
	public void showdis(Vertex x){
		for (int i = 0; i < vertexList.length; i++) {
			System.out.println("Label "+vertexList[i].getData()
					+" distance from "+ x+" :"+ vertexList[i].index);
		}
		System.out.println();
	}
		
}


//This is the matrix way of improve bfs, it may not load correctly
//Because I write another Queue class different than sheet way's bfs 
//But in without using this method . the matrix in test class will still process successfully
/*
// bfs
public void BFSTraverse() {
    flag = new boolean[number];
    Queue<Integer> queue = new LinkedList<Integer>();
    for (int i = 0; i < number; i++) {
        if (flag[i] == false) {
            flag[i] = true;
            System.out.print(vertexs[i] + " ");
            queue.add(i);
            while (!queue.isEmpty()) {
                int j = queue.poll();
                for (int k = 0; k < number; k++) {
                    if (edges[j][k] == 1 && flag[k] == false) {
                        flag[k] = true;
                        System.out.print(vertexs[k] + " ");
                        queue.add(k);
                    }
                }
            }
        }
    }
}
*/
