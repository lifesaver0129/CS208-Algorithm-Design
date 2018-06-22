package Greedy_MST;

import java.util.*;
import java.util.Map.Entry;

public class Graph {
	private Map<Node, Map<Node, Double>> map;

	public Graph() {
		map = new HashMap<Node, Map<Node, Double>>();
	}

	@Override
	public Object clone() {
		Graph o = null;
		try {
			o = (Graph) super.clone();
			o.map = new HashMap<Node, Map<Node, Double>>();// 深度clone
			Iterator<Entry<Node, Map<Node, Double>>> it = map.entrySet().iterator();
			Entry<Node, Map<Node, Double>> entry;
			while (it.hasNext()) {
				entry = it.next();
				Map<Node, Double> edges = new HashMap<Node, Double>();
				edges.putAll(entry.getValue());
				o.map.put(entry.getKey(), edges);
			}

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public Graph addNode(Node node) {
		if (!map.containsKey(node)) {
			Map<Node, Double> edges = new LinkedHashMap<Node, Double>();
			map.put(node, edges);
		}
		return this;
	}

	public Graph addEdge(Node u, Node v, double value) {
		if (!map.containsKey(u)) {
			addNode(u);
		}
		if (!map.containsKey(v)) {
			addNode(v);
		}
		map.get(u).put(v, value);
		map.get(v).put(u, value);

		return this;
	}

	public Graph merge(Graph anotherGraph) {
		if (null != anotherGraph) {
			this.map.putAll(anotherGraph.map);
		}
		return this;
	}

	public boolean contains(Node node) {
		return map.containsKey(node);
	}

	public int size() {
		return map.size();
	}

	public Iterator<Entry<Node, Map<Node, Double>>> entryIterator() {
		return map.entrySet().iterator();
	}

	public Iterator<Entry<Node, Double>> adjNodeIterator(Node node) {
		return map.get(node).entrySet().iterator();
	}

	public Iterator<Node> keyIterator() {
		return map.keySet().iterator();
	}

	public Iterator<Node> adjIterator(Node node) {
		return map.get(node).keySet().iterator();
	}

	public double getWeight(Node startNode, Node endNode) {
		return map.get(startNode).get(endNode);
	}

	public Set<Entry<Node, Double>> getEdges(Node node) {
		return map.get(node).entrySet();
	}

	@Override
	public String toString() {
		Iterator<Entry<Node, Map<Node, Double>>> it = map.entrySet().iterator();
		String graph_str = "Graph:";
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");

		while (it.hasNext()) {
			Entry<Node, Map<Node, Double>> entry = it.next();
			stringBuilder.append(entry.getKey());
			Iterator<Entry<Node, Double>> sub_it = entry.getValue().entrySet().iterator();
			stringBuilder.append("{");

			while (sub_it.hasNext()) {
				Entry<Node, Double> subentry = sub_it.next();
				stringBuilder.append("(" + subentry.getKey() + "," + subentry.getValue() + ")" + ",");

			}
			stringBuilder.append("}\n");

		}
		graph_str += stringBuilder.toString();
		return graph_str;
	}
}
