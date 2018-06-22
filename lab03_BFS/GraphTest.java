package cl03_graph;

import java.util.Scanner;

public class GraphTest {

	public static void main(String args[]){
		int n=8;
		Graph grfa=new Graph(n);
		
		Vertex ver1=new Vertex(1);
		Vertex ver2=new Vertex(2);
		Vertex ver3=new Vertex(3);
		Vertex ver4=new Vertex(4);
		Vertex ver5=new Vertex(5);
		Vertex ver6=new Vertex(6);
		Vertex ver7=new Vertex(7);
		Vertex ver8=new Vertex(8);
		
		System.out.print("Input the starting Vertex: ");
		Scanner input =new Scanner(System.in);
		int a=input.nextInt();
		System.out.print("Do we need to consider the coordinate of the Vertex? ");
		String b = input.next();
		if("yes".equals(b)){
			ver1.setDis(2,6);
			ver2.setDis(1,4);
			ver3.setDis(3,4);
			ver4.setDis(0,2);
			ver5.setDis(2,2);
			ver6.setDis(2,0);
			ver7.setDis(2,6);
			ver8.setDis(4,6);
		}
		
		grfa.addVertex(ver1);
		grfa.addVertex(ver2);
		grfa.addVertex(ver3);
		grfa.addVertex(ver4);
		grfa.addVertex(ver5);
		grfa.addVertex(ver6);
		grfa.addVertex(ver7);
		grfa.addVertex(ver8);
/*		
		int[][] edges = { 
		            { 0, 1, 1, 0, 0, 0, 0, 0 }, 
		            { 1, 0, 0, 1, 1, 0, 0, 0 }, 
		            { 1, 0, 0, 0, 1, 0, 1, 1 }, 
		            { 0, 1, 0, 0, 1, 0, 0, 0 }, 
		            { 0, 1, 1, 1, 0, 1, 0, 0 }, 
		            { 0, 0, 0, 0, 1, 0, 0, 0 }, 
		            { 0, 0, 1, 0, 0, 0, 0, 1 },
		            { 0, 0, 1, 0, 0, 0, 1, 0 },
		            };

		for(int i=0;i<8;i++){
			for(int j=i+1;j<8;j++){
				if(edges[i][j]==1){
					grfa.addEdge(i+1,j+1);
				}
			}
		}
	*/	
		grfa.addEdge(1, 2);
		grfa.addEdge(1, 3);
		grfa.addEdge(2, 4);
		grfa.addEdge(2, 5);
		grfa.addEdge(3, 5);
		grfa.addEdge(3, 7);
		grfa.addEdge(3, 8);
		grfa.addEdge(4, 5);
		grfa.addEdge(5, 6);
		grfa.addEdge(7, 8);
		
		grfa.displayGraph();
		grfa.bfs(grfa.getspecV(a));
		input.close();
		
	}
}
