package Greedy_MST;

import javax.swing.text.html.parser.Entity;
import java.util.*;

/**
 * Created by XWT on 2017/3/28.
 */
public class MSTGenerator {
    private Graph graph;
    private Node root;
    private Tree mst;
    private PriorityQueue<Edge> priorityQueue;

    public MSTGenerator(Graph graph) {
        this.graph = graph;
        priorityQueue = new PriorityQueue<>(new Comparator<Edge>() {//优先队列储存startNode在MST中，endNode不在MST中的边。
            @Override
            public int compare(Edge edg1, Edge edge2) {
                if (edg1.getWeight() > edge2.getWeight())
                    return 1;
                else if (edg1.getWeight() == edge2.getWeight())
                    return 0;
                else
                    return -1;
            }
        });
    }

    public Tree primMethod(Node root) {
        mst = new Tree();
        this.root = root;
        priorityQueue.clear();
        mst.setRoot(this.root);
        Iterator<Map.Entry<Node, Double>> edgeIterator = graph.getEdges(root).iterator();

        //将root及相关的边放入堆。
        Map.Entry<Node, Double> rootEdge;
        while (edgeIterator.hasNext()) {
            rootEdge = edgeIterator.next();
            priorityQueue.add(new Edge(root, rootEdge.getKey(), rootEdge.getValue()));
        }//end.

        Node startNode;
        Node endNode;
        Node nextAdjNode;
        Edge edge;
        Iterator<Node> iterator;
        while (graph.size() != mst.size()) {//当树的大小和图的大小相等时，结束循环。
            edge = priorityQueue.poll();//权值最小的Edge出堆。
            startNode = edge.getStartNode();
            endNode = edge.getEndNode();
            if (!mst.contains(endNode)) {//判断若终边不在mst中（避免出现环）。
                mst.addNode(startNode, endNode);
                iterator = graph.adjIterator(endNode);
                while (iterator.hasNext()) {//遍历endNode的所有相邻结点。
                    nextAdjNode = iterator.next();
                    if (!mst.contains(nextAdjNode))//判断若此相邻结点不在mst中。
                        priorityQueue.add(new Edge(endNode, nextAdjNode, graph.getWeight(endNode, nextAdjNode)));//向堆中添加新的元素。
                }
            }
        }
        return mst;
    }

    public Tree kruskalMethod(Node root) {
        this.root = root;
        mst = new Tree();
        priorityQueue.clear();
        mst.setRoot(this.root);

        Set<Node>[] sets = new HashSet[graph.size()];

        /*
        *注释掉的部分用quickSort进行排序
         */
//        //初始化包含刚开始只包含单个结点的集合。
//        ArrayList<Edge> list = new ArrayList<>();
//        Iterator<Node> iterator = graph.keyIterator();
//        Iterator<Map.Entry<Node, Double>> entryIterator;
//        Map.Entry<Node, Double> entry;
//        Node node;
//        for(int i = 0; iterator.hasNext(); i++) {
//            node = iterator.next();
//            sets[i] = new HashSet<>() ;
//            sets[i].add(node);
//            entryIterator = graph.getEdges(node).iterator();
//            while (entryIterator.hasNext()) {
//                entry = entryIterator.next();
//                list.add(new Edge(node, entry.getKey(), entry.getValue()));
//            }
//        }
//        Edge[] edges = new Edge[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            edges[i] = list.get(i);
//        }
//        edges = quickSort(edges, 0, edges.length - 1);
//        ArrayList<Map.Entry<Node, Double>> entries = new ArrayList<>();

        //初始化包含刚开始只包含单个结点的集合。
        Node node;
        Iterator<Node> iterator = graph.keyIterator();
        for (int j = 0; iterator.hasNext(); j++) {
            node = iterator.next();
            sets[j] = new HashSet<>();
            sets[j].add(node);
        }//end


        iterator = graph.keyIterator();
        Iterator<Map.Entry<Node, Double>> adjNodeIterator;
        Map.Entry<Node, Double> entry;
        while (iterator.hasNext()) {//遍历图，图中所有边按权重增序排列，相当于堆排序。
            node = iterator.next();
            adjNodeIterator = graph.adjNodeIterator(node);
            while (adjNodeIterator.hasNext()) {
                entry = adjNodeIterator.next();
                priorityQueue.add(new Edge(node, entry.getKey(), entry.getValue()));
            }
        }

        Edge edge;
//        int index = 0;
        int index1 = -1;
        int index2 = -1;
        Node startNode;
        Node endNode;
        boolean continue1;
        boolean continue2;
        Set tmpSet;

        while (sets[0].size() != graph.size()) {//当connective component不包含图中所有结点时。
            continue1 = continue2 = true;
//            edge = edges[index++];
            edge = priorityQueue.poll();//出堆的边是当前堆中权值最小的边。
            startNode = edge.getStartNode();
            endNode = edge.getEndNode();

            for (int i = 0; i < sets.length && (continue1 || continue2); i++) {//找到此边对应的两个结点。
                if (sets[i].contains(startNode)) {
                    index1 = i;
                    continue1 = false;
                }
                if (sets[i].contains(endNode)) {
                    index2 = i;
                    continue2 = false;
                }
            }
            if (sets[index1] != sets[index2]) {//判断出堆的边的两个顶点不在一个connective component中。
                sets[index1].addAll(sets[index2]);
                tmpSet = sets[index2];
                for (int i = 0; i < sets.length; i++) {
                    if (sets[i] == tmpSet)
                        sets[i] = sets[index1];
                }
                mst.addNode(startNode, endNode);
            }
        }
        return mst;
    }

    public Tree boruvkaMethod(Node root) {
        mst = new Tree();
        this.root = root;
        mst.setRoot(this.root);
        Iterator<Node> iterator = graph.keyIterator();
        Set<Tree> components = new HashSet<>();
        int i = 0;
        while (iterator.hasNext()) {//初始化components，每一个component为只有一个结点的树。
            components.add(new Tree(iterator.next()));
        }

        Iterator<Tree> compoIterator;
        Iterator<Map.Entry<Node, Double>> adjNodeIterator;
        Iterator<Tree> tmpIterator;
        Tree tmpComponent;
        Tree component;
        Node node;
        double minWeight;
        Tree addedComponent1 = null;
        Tree addedComponent2 = null;
        Node addedNode1 = null;
        Node addedNode2 = null;
        Map.Entry<Node, Double> entry;
        while (components.size() != 1) {//当组件的个数不为1时。
            minWeight = Double.MAX_VALUE;
            compoIterator = components.iterator();
            while (compoIterator.hasNext()) {//遍历每个组件。
                component = compoIterator.next();
                iterator = component.iterator();
                while (iterator.hasNext()) {//遍历组件中的每一个点。
                    node = iterator.next();
                    adjNodeIterator = graph.adjNodeIterator(node);
                    while (adjNodeIterator.hasNext()) {//遍历于此node相邻的所有Node。
                        entry = adjNodeIterator.next();
                        if ((!component.contains(entry.getKey())) && entry.getValue() <= minWeight) {//判断若此边的权重比记录的最小值minWeight还小。
                            minWeight = entry.getValue();
                            addedNode1 = node;
                            addedNode2 = entry.getKey();
                            addedComponent1 = component;
                        }
                    }
                }
            }
            tmpIterator = components.iterator();
            while (tmpIterator.hasNext()) {//遍历寻找记录下的addedNode2来自哪个组件。
                tmpComponent = tmpIterator.next();
                if (tmpComponent.contains(addedNode2))
                    addedComponent2 = tmpComponent;
            }
            addedComponent1.combine(addedComponent2, addedNode1, addedNode2);
            components.remove(addedComponent2);
        }
        compoIterator = components.iterator();
        if (compoIterator.hasNext())
            mst = compoIterator.next();
        return mst;
    }

    //快速排序
    private Edge[] quickSort(Edge[] edges, int left, int right) {
        int temp;

        if (left == right)
            return edges;
        if (right == left + 1) {
            if (edges[left].getWeight() > edges[right].getWeight())
                swap(edges, left, right);
            return edges;
        }

        int center = (left + right) / 2;

        if (edges[center].getWeight() < edges[left].getWeight())
            swap(edges, center, left);

        if (edges[right].getWeight() < edges[left].getWeight())
            swap(edges, right, left);

        if (edges[right].getWeight() < edges[center].getWeight())
            swap(edges, right, center);

        Edge pivot = edges[center];

        swap(edges, center, right - 1);

        int i = left;
        int j = right - 1;

        while (true) {
            while ((double) edges[++i].getWeight() < (double) pivot.getWeight()) ;
            while ((double) edges[--j].getWeight() > (double) pivot.getWeight()) ;
            if (i < j) {
                swap(edges, i, j);
            } else
                break;
        }

        swap(edges, i, right - 1);

        quickSort(edges, left, i);
        quickSort(edges, i + 1, right);

        return edges;
    }

    private void swap(Edge[] a, int i, int j) {
        Edge temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
