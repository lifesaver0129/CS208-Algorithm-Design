package cl03_graph;

import java.util.ArrayList;

public class Vertex{
	public int label; 
	public boolean isVisted=false;
	public int index=0;
	private ArrayList<Vertex> next = null;
	public double x;
	public double y;
	
	public Vertex(int lab){	
		label = lab;
	}
	
	public Vertex(int lab,int x, int y){
		this.label=lab;
		this.x=x;
		this.y=y;
	}
	
	public void addAdj(Vertex ver){
		if(next == null) next = new ArrayList<Vertex>();
		next.add(ver);
	}
	
	public ArrayList<Vertex> getAdj(){
		return next;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public void setDis(double x, double y){
		this.x =x;
		this.y =y;
	}
	
	public String toString(){
		return "Vertex "+label+" ";
	}

	public int getData() {
		return label;
	}
	
	public boolean isFix(){
		if(x==0&&y==0){
			return false;
		}else
			return true;
	}
}
