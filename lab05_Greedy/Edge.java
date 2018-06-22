package cl05_greedy;

public class Edge implements Comparable{   
	    public int i,j,w;  
	    public Edge(int i,int j,int w){  
	        this.i=i;  
	        this.j=j;  
	        this.w=w;  
	    }  
	 
		@Override  
	    public int compareTo(Object o) {  
	        Edge to=(Edge)o;  
	        if(this.w>to.w) return 1;  
	        else if(this.w==to.w) return 0;  
	        else return -1;  
	          
	    }  
		
		 @Override  
		    public String toString() {  
		        return "start="+i+"||end="+j+"||w="+w;  
		    }  
		 
		 
	    /*
	     * int [] V={1,2,3,4,5,6,7,8};
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
			E[10]=new Edge(7,8,7);
			MST.KRUSKAL(V, E);
			System.out.println("Minimum spining tree edge "+(i)+" <"+start+","+end+">, weight: "+E[i].w);
	     */
	    
	}  


