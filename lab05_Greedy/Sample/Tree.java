package Greedy_MST;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by XWT on 2017/3/28.
 */
public class Tree {
    private Map<Node, LinkedList<Node>> map;
    private Node root;

    public Tree() {
        map = new HashMap<>();
    }

    public Tree(Node root) {
        map = new HashMap<>();
        setRoot(root);
    }

    public Tree combine(Tree combinedTree, Node nodeThis, Node nodeCombinedTree) {
        map.putAll(combinedTree.getMap());
        map.get(nodeThis).add(nodeCombinedTree);
        map.get(nodeCombinedTree).add(nodeThis);
        return this;
    }

    public Node addNode(Node node) {
        if (!map.containsKey(node))
            map.put(node, new LinkedList<>());
        return node;
    }

    public void addNode(Node node, Node adjNode) {
        if(!map.containsKey(node))
            map.put(node, new LinkedList<>());
        if(!map.containsKey(adjNode))
            map.put(adjNode, new LinkedList<>());
        map.get(node).add(adjNode);
        map.get(adjNode).add(node);
    }

    public Iterator<Node> iterator() {
        return map.keySet().iterator();
    }

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
        root = null;
    }

    public boolean contains(Node node) {
        return map.containsKey(node);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
        map.put(this.root, new LinkedList<>());
    }

    public Map getMap() {
        return map;
    }

    @Override
    public String toString() {
        String string = String.format(root + "！！");
        Iterator<Node> nodeIterator = map.get(root).iterator();
        Node node;
        while (nodeIterator.hasNext()) {
            node = nodeIterator.next();
            string = string + node + "\t";
        }
        string = string + "\n";

        Iterator<Node> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            node = iterator.next();
            if (node != root) {
                string = string + node + "！！";
                nodeIterator = map.get(node).iterator();
                while (nodeIterator.hasNext()) {
                    node = nodeIterator.next();
                    string = string + node + "\t";
                }
                string = string + "\n";
            }
        }
        return string;
    }
}
