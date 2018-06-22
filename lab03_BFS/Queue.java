package cl03_graph;

public class Queue {
	private int mSize;
	private int front;
	private int rear;
	private int[] queue;

	public Queue(int size) {
		this.mSize = size;
		queue = new int[size];
		front = 0;
		rear = 0;
	}

	public int getFrontValue() {
		if (front == rear) {
			System.out.println("empty");
			return 0;
		} else {
			return queue[front];
		}
	}


	public void enQueue(Vertex vertex) {
		if (isFull()) {
			System.out.println("full");
		} else {
			queue[rear] = vertex.label;
			rear = (rear + 1) % mSize;
			System.out.print(vertex.label+" ");
		}
	}


	public int deQueue() {
		if (isEmpty()) {
			System.out.println("empty");
			return 0;
		} else {
			int element = queue[front];
			front = (front + 1) % mSize;
			return element;
		}
	}

	public int length() {
		return (mSize + rear - front) % mSize;
	}


	public int getFront() {
		return front;
	}

	public int getRear() {
		return rear;
	}

	
	public boolean isEmpty() {
		if (rear == front) {
			return true;
		} else {
			return false;
		}
	}

	
	public boolean isFull() {
		if ((rear + 1) % mSize == front) {
			return true;
		} else {
			return false;
		}
	}

	
	public void printQueue() {
		if (isEmpty()) {
			System.out.println("empty");
		} else {
			int i = front;
			while (i != rear) {
				System.out.print(queue[i] + " ");
				i = (i + 1) % mSize;
			}
			System.out.println();
		}
	}

}
