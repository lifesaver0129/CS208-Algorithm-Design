package cl05_greedy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public class MST {

	public static void PRIM(int [][] graph,int start,int n){
			int [][] mins=new int [n][2];
			for(int i=0;i<n;i++){
				if(i==start){
					mins[i][0]=0;
					mins[i][1]=0;
				}else if( graph[start][i]!=0){
					mins[i][0]=start;
					mins[i][1]= graph[start][i];
				}else{
					mins[i][0]=0;
					mins[i][1]=Integer.MAX_VALUE;
				}
			}
			System.out.println("KRUSKAL");
			for(int i=0;i<n-1;i++){
				int minV=0,minW=Integer.MAX_VALUE;
				for(int j=0;j<n;j++){
				    if(mins[j][1]!=0&&minW>mins[j][1]){
						minW=mins[j][1];
						minV=j;
					}
				}
				mins[minV][1]=0;
				
				System.out.println("Minimum spining tree edge "+(i+1)+" <"+(mins[minV][0]+1)+","+(minV+1)+">, weight: "+minW);
				for(int j=0;j<n;j++){
					if(mins[j][1]!=0){
						if( graph[minV][j]!=0&&graph[minV][j]<mins[j][1]){
							mins[j][0]=minV;
							mins[j][1]= graph[minV][j];
						}
					}
				}
			}
		}

	
		public static void KRUSKAL(int [] V,Edge [] E){
			 Arrays.sort(E);
		        ArrayList<HashSet> sets=new ArrayList<HashSet>();  
		       
		        for(int i=0;i<V.length;i++){  
		            HashSet set=new HashSet();  
		            set.add(V[i]);  
		            sets.add(set);            
		        }  
		        System.out.println("KRUSKAL");
		        for(int i=0;i<E.length;i++){  
		            int start=E[i].i,end=E[i].j;  
		            int counti=-1; 
		            int countj=-2;  
		            for(int j=0;j<sets.size();j++){  
		                HashSet set=sets.get(j);  
		                if(set.contains(start)){  
		                    counti=j;  
		                }  
		                      
		                if(set.contains(end)){  
		                    countj=j;  
		                }  
		            }  
		            if(counti<0||countj<0) System.err.println("没有在子树中找到节点，错误");  
		              
		            if(counti!=countj){  
		            	System.out.println("Minimum spining tree edge "+(i+1)+" <"+start+","+end+">, weight: "+E[i].w);
		                HashSet setj=sets.get(countj);  
		                sets.remove(countj);  
		                HashSet seti=sets.get(counti);  
		                sets.remove(counti);  
		                seti.addAll(setj);  
		                sets.add(seti);  
		            }else{  
		            }  
		        }  
		          
		          
		    }  
		
		
		public static void main(String [] args){
			int[][] Edge = { 
		            { 0, 1, 2, 0, 0, 0, 0, 0 }, 
		            { 1, 0, 4, 5, 7, 0, 0, 0 }, 
		            { 2, 4, 0, 0, 8, 0, 3, 4 }, 
		            { 0, 5, 0, 0, 6, 0, 0, 0 }, 
		            { 0, 7, 8, 6, 0, 5, 0, 0 }, 
		            { 0, 0, 0, 0, 5, 0, 0, 0 }, 
		            { 0, 0, 3, 0, 0, 0, 0, 7 },
		            { 0, 0, 4, 0, 0, 0, 7, 0 },
		            };

			MST.PRIM(Edge, 0, 8);

			/*int [] V={1,2,3,4,5,6,7,8};
			Edge [] E=new Edge[11];
			E[0]=new Edge(1,2,1);
			E[1]=new Edge(1,3,2);
			E[2]=new Edge(2,3,4);
			E[3]=new Edge(2,4,5);
			E[4]=new Edge(2,5,7);
			E[5]=new Edge(3,5,8);
			E[6]=new Edge(3,7,3);
			E[7]=new Edge(3,8,4);
			E[8]=new Edge(4,5,6);
			E[9]=new Edge(5,6,5);
			E[10]=new Edge(7,8,7);*/
			 
			int [] V={1,2,3,4,5,6};  
		        Edge [] E=new Edge[10];  
		        E[0]=new Edge(1,2,6);  
		        E[1]=new Edge(1,3,1);  
		        E[2]=new Edge(1,4,5);  
		        E[3]=new Edge(2,3,5);  
		        E[4]=new Edge(2,5,3);  
		        E[5]=new Edge(3,4,5);  
		        E[6]=new Edge(3,5,6);  
		        E[7]=new Edge(3,6,4);  
		        E[8]=new Edge(4,6,2);  
		        E[9]=new Edge(5,6,6);  
			MST.KRUSKAL(V, E);
		}

	}


