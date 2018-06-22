package Huffman;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanCoding {
	Queue<HNode> priorityQueue;

	public HuffmanCoding() {
		priorityQueue = new PriorityQueue<HNode>(new Comparator<HNode>() {
			public int compare(HNode d1, HNode d2) {
				if ((d1.value - d2.value) < 0.0) {
					return -1;
				} else if ((d1.value - d2.value) > 0.0) {
					return 1;
				} else {
					return 0;
				}
			}
		});
	}

	// huffman coding according to the input statistic frequency numbers, output
	// the code table
	public void huffmanCoding(List<Integer> input, Map<Integer, String> codeTable) {
		for (int i = 0; i < input.size(); i++) {
			if (0 != input.get(i)) {
				HNode e = new HNode(Integer.toString(i), input.get(i));
				priorityQueue.add(e);
			}
		}
		// construct a huffman tree
		while (priorityQueue.size() != 1) {
			HNode e1 = priorityQueue.remove();
			HNode e2 = priorityQueue.remove();
			HNode e = new HNode(e1, e2);
			priorityQueue.add(e);
		}
		// convert to code table
		getCodeTable(priorityQueue.remove(), codeTable);

	}

	private void getCodeTable(HNode root, Map<Integer, String> codeTable) {
		StringBuilder sb = new StringBuilder();
		DFS(root, codeTable, sb);
	}

	// construct decode table according code table
	public void getDecodeTable(Map<Integer, String> codeTable, Map<String, Integer> decodeTable) {
		for (Entry<Integer, String> entry : codeTable.entrySet()) {
			decodeTable.put(entry.getValue(), entry.getKey());
		}
	}

	// convert huffman tree to code table
	public void DFS(HNode node, Map<Integer, String> codeTable, StringBuilder sb) {
		if (null != node.getLeftChild()) {
			sb.append('0');
			DFS(node.getLeftChild(), codeTable, sb);
			sb.deleteCharAt(sb.length() - 1);
		}
		if ((null == node.getLeftChild()) && (null == node.getRightChild())) {
			codeTable.put(Integer.parseInt(node.getData()), sb.toString());
		}
		if (null != node.getRightChild()) {
			sb.append('1');
			DFS(node.getRightChild(), codeTable, sb);
			sb.deleteCharAt(sb.length() - 1);
		}

	}

	class HNode {
		/**
		 * 
		 */
		String data;
		int value;
		HNode leftChild;
		HNode rightChild;

		public HNode(String data, int value) {
			this.leftChild = null;
			this.rightChild = null;
			this.data = data;
			this.value = value;
		}

		public HNode(HNode e1, HNode e2) {
			this.data = null;
			this.value = e1.getValue() + e2.getValue();
			this.leftChild = e1;
			this.rightChild = e2;
		}

		public String getData() {
			return this.data;
		}

		public int getValue() {
			return this.value;
		}

		public HNode getLeftChild() {
			return this.leftChild;
		}

		public HNode getRightChild() {
			return this.rightChild;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			sb.append(leftChild);
			sb.append(rightChild);
			return sb.toString();
		}
	}
}
