package cl04_topological_sorting;

import java.util.*;

class Graph{
	private int v;
	private List <Integer> adj[];
	
	@SuppressWarnings("unchecked")
	public Graph(int x){
		this.v = x;
		adj = new ArrayList[v];
		for(int i=0; i<v; i++)
			adj[i]=new ArrayList<Integer>();
	}

	public void addEdge(int o,int p){
		adj[o-1].add(p-1);
	}

	public void topSortByK(){
		int indegree[] = new int[v];
		for(int i = 0; i < v; i++){
			ArrayList<Integer> temp = (ArrayList<Integer>) adj[i];
			for(int node : temp){
				indegree[node]++;
			}	
		}
		Queue<Integer> q = new LinkedList<Integer>();
		for(int i = 0;i < v-1; i++){
			if(indegree[i]==0)
				q.add(i);
		}
		int cnt = 0;
		Vector <Integer> topOrder=new Vector<Integer>();
		while(!q.isEmpty()){
			int u=q.poll();
			topOrder.add(u);
			for(int node : adj[u]){
				if(--indegree[node] == 0)
					q.add(node);
			}
			cnt++;
		}
		if(cnt != v){
			System.out.println("There exists a cycle in the graph");
			return;
		}
		for(int i : topOrder){
			System.out.print(i+1+" ");
		}	
	}
}

public class Kahn{
	public static void main(String args[]){
		Graph g=new Graph(8);
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
		System.out.println("A Topological Sort by Kahn ");    
		g.topSortByK();
	}
}

