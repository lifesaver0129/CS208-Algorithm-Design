package cl04_topological_sorting;

import java.util.*;

class Graphb{ 
	private int v;
	private List<Integer> adj[]; 
	Vector <Integer> topOrder=new Vector<Integer>();
	int cnt=0;

	@SuppressWarnings("unchecked")
	public Graphb(int v){
		this.v = v;
		adj = new ArrayList[v];
		for (int i=0; i<v; i++)
			adj[i] =new ArrayList<Integer>();	
	}

	public void addEdge(int o,int p){
		adj[o-1].add(p-1); 	
	}

	@SuppressWarnings("unused")
	public void topSortByD(){
		boolean visited[] = new boolean[v];
		for (int i = 0; i < v; i++)
			visited[i] = false;
		int outdegree[] = new int[v];
		for(int i = 0; i < v; i++){
			ArrayList<Integer> temp = (ArrayList<Integer>) adj[i];
			for(int node : temp){
				outdegree[i]++;
			}	
		}
		for(int i = 0;i < v; i++){
			if(outdegree[i]==0)
				topSort(i, visited);
		}
		if(cnt != v){
			System.out.println("There exists a cycle in the graph");
			return;
		}
		for(int i : topOrder){
			System.out.print(i+1+" ");
		}		
	}

	public void topSort(int v, boolean visited[]){
		visited[v] = true;
		topOrder.add(v);
		cnt++;
		for (int x=0; x<this.v; x++){
			if(adj[x].contains(v)&&!visited[x])
				topSort(x, visited);
		}
	}
}
public class DFS{
	public static void main(String args[]){
	 Graphb g=new Graphb(8);
	 /*		
		int[][] edges = { 
		            { 0, 1, 1, 0, 0, 0, 0, 0 }, 
		            { 1, 0, 1, 1, 1, 0, 0, 0 }, 
		            { 1, 1, 0, 0, 1, 0, 1, 1 }, 
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
     g.addEdge(1, 2);
     g.addEdge(1, 3);
     g.addEdge(2, 3);
     g.addEdge(2, 4);
     g.addEdge(2, 5);
     g.addEdge(3, 5);
     g.addEdge(3, 7);
     g.addEdge(3, 8);
     g.addEdge(4, 5);
     g.addEdge(5, 6);
     g.addEdge(7, 8);
     System.out.println("A Topological sort by DFS ");
     g.topSortByD();
 }
}

