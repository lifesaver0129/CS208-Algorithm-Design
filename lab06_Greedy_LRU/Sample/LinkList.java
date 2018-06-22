package homework6;

import java.util.Iterator;

public class LinkList<Item> implements Iterable<Item>
{
	private Node<Item> head = null;// ����ͷ�ڵ�
	private Node<Item> tail = null;// ����β�ڵ�
	private Node<Item> pointer = null;// ��������Ľڵ�

	public LinkList(Node<Item> node) {
		head = new Node<Item>();
		head.LinkNext(node);
		tail = node;
	}
	
	public LinkList(){}

	// ������ĩβ��ӽڵ�
	public void AddNode(Item obj) {	
		Node<Item> node = new Node<Item>(obj);
		if(tail == null)
		{
			head = node;
			tail = node;
		}
		else
		{
			Node<Item> oldhead = head;
			head = node;
			oldhead.LinkNext(head);
		}
	}

	// ����������
	public int getLength() {
		int length = 0;
		pointer = tail;
		while (pointer != null) {
			length++;
			pointer = pointer.next();
		}
		return length;
	}
	
	public Node<Item> getTail()
	{	return tail;}

	// ��ӡ�������е�Ԫ��
	public void printLink() {
		pointer = tail;
		while (pointer != null) {
			System.out.print(pointer.getValue() + "\t");
			pointer = pointer.next();
		}
		System.out.println();
	}

	// ������ָ��λ�ò���ڵ�
	public void insertAt(int index, Node<Item> node) {
		if (index > this.getLength()) {
			System.out.println("wrong");
		} else {
			pointer = tail;
			for(int i=0;i<index-1;i++)
			{
				pointer=pointer.next();
			}
			node.LinkNext(pointer.next());
			pointer.LinkNext(node);
		}
	}
	
	public Item getObj(int index)
	{
		pointer = tail;
		while(index-- != 0 && pointer != null)
			pointer = pointer.next();
		return pointer.getValue();
	}
	
	public Item removeOb(Object obj)
	{
		pointer = tail;
		Item del;
		
		if(pointer == null)
			return null;
		if(pointer.value == obj)
		{
			del = pointer.value;
			tail = tail.next;
			return del;
		}
		while(pointer.next != null && pointer.next.value != obj)
			pointer = pointer.next;
		
		if(pointer == head)
			return null;
		else if(pointer.next() == head)
		{
			del = pointer.next.value;
			pointer.LinkNext(null);
			head = pointer;
			return del;
		}
		else
		{
			del = pointer.next.value;
			pointer.LinkNext(pointer.next().next());
			return del;
		}
	}
	
	public Item removeTail()
	{	
		Item del = tail.value;
		tail = tail.next;
		return del;
	}

	// ������ָ��λ��ɾ���ڵ�
	public void removeAt(int index) {
		if (index > this.getLength()) {
			System.out.println("wrong");
		}else{
			pointer=tail;
			Node<Item> q=pointer;
			for(int i=0;i<index;i++){
				q=pointer;
				pointer=pointer.next();
			}
			q.LinkNext(pointer.next());
			pointer.LinkNext(null);
		}
	}
	
	@Override
	public String toString()
	{
		String str = "";
		pointer = tail;
		while(pointer != null)
		{
//System.out.println("test " + pointer.value);
			str = String.format("%s\t", pointer.getValue()) + str;
			pointer = pointer.next();
		}
		return str;
	}
	
	class Node<Item> {
		private Item value;// ֵ
		private Node<Item> next;// ��һ���ڵ�

		public Node() {
		}

		public Node(Item value) {
			this.value = value;
		}

		// ��ȡ��һ���ڵ�
		public Node<Item> next() {
			return this.next;
		}

		// ������һ���ڵ�
		public void LinkNext(Node<Item> next) {
			this.next = next;
		}

		// ���õ�ǰ�ڵ��ֵ
		public void setValue(Item value) {
			this.value = value;
		}

		// ��ȡ��ǰ�ڵ��ֵ
		public Item getValue() {
			return this.value;
		}
	}

	@Override
	public Iterator<Item> iterator() {
		return null;
	}
	
	class ListIterator<Item> implements Iterator<Item>
	{
		private Node<Item> current;
		
		public ListIterator(Node<Item> current)
		{	this.current = current;}
		
		@Override
		public boolean hasNext() 
		{	return current != null;}

		@Override
		public Item next()
		{
			Item ele = null;
			if(hasNext())
				ele = current.getValue();
			return ele;
		}

		@Override
		public void remove() {
		}
	}
	
}

