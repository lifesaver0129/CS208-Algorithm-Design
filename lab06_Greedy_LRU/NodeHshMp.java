package cl06_lru;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class NodeHshMp {
	   
	private class Node{
		Node prev;
		Node next;
		int key;
		int value;
		
		public Node(int key, int value) {
			this.key = key;
			this.value = value;
			this.prev = null;
			this.next = null;
		} 
	}

	private int capacity;
	private HashMap<Integer, Node> hs = new HashMap<Integer, Node>();
	private Node head = new Node(-1, -1);
	private Node tail = new Node(-1, -1);

	public NodeHshMp(int capacity) {
		this.capacity = capacity;
		tail.prev = head;
		head.next = tail;    
	}

	public int get(int key) {
		if( !hs.containsKey(key)) {
			return -1;    
		}
	     
		Node current = hs.get(key);
		current.prev.next = current.next;
		current.next.prev = current.prev;
		move_to_tail(current);
		return hs.get(key).value;    
	}

	   
	public void set(int key, int value) {
		if( get(key) != -1) {
			hs.get(key).value = value;
			return;    
		}

		if (hs.size() == capacity) {    	
			hs.remove(head.next.key);
			System.out.println("erase eldest key："+head.next.key);     
			head.next = head.next.next;
			head.next.prev = head;    
		}
		Node insert = new Node(key, value);
		hs.put(key, insert);
		move_to_tail(insert);    
	}

	   
	private void move_to_tail(Node current) {
		current.prev = tail.prev;
		tail.prev = current;
		current.prev.next = current;
		current.next = tail;    
	}
	
	public static void main(String[] args){
		 Scanner sc = new Scanner(System.in); 
		 System.out.println("Input the capacity of cache: ");
	     
		 int n = sc.nextInt();
		 System.out.println("Input the capacity of access numbers: ");  
		 int y = sc.nextInt();
	     
		 int count=0;
		 NodeHshMp cache=new NodeHshMp(n);
		 System.out.println("Input the access order once a time(after enter each number press enter): ");
		 for(int i=1;i<=y;i++){
			 
			 int x = sc.nextInt();
			 if(cache.hs.containsKey(x))
				 count++;
			 cache.set(x,  x);   
		 }
		 sc.close();
		 Node a=cache.head.next;
		 while(a!=null&&a.next!=null){
			 System.out.print(a.value+" ");
			 a=a.next;  
		 }
		 System.out.println();
		 DecimalFormat df = new DecimalFormat();
	        df.setMaximumFractionDigits(4);
	        df.setMinimumFractionDigits(2);
	        String k = df.format(count * 100.00 / y) + "%";
	        System.out.println("Hit rate for lru: "+k);
	}
	
	  
}
