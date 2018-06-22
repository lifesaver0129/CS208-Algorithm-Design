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
        priorityQueue = new PriorityQueue<>(new Comparator<Edge>() {//���ȶ��д���startNode��MST�У�endNode����MST�еıߡ�
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

        //��root����صı߷���ѡ�
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
        while (graph.size() != mst.size()) {//�����Ĵ�С��ͼ�Ĵ�С���ʱ������ѭ����
            edge = priorityQueue.poll();//Ȩֵ��С��Edge���ѡ�
            startNode = edge.getStartNode();
            endNode = edge.getEndNode();
            if (!mst.contains(endNode)) {//�ж����ձ߲���mst�У�������ֻ�����
                mst.addNode(startNode, endNode);
                iterator = graph.adjIterator(endNode);
                while (iterator.hasNext()) {//����endNode���������ڽ�㡣
                    nextAdjNode = iterator.next();
                    if (!mst.contains(nextAdjNode))//�ж��������ڽ�㲻��mst�С�
                        priorityQueue.add(new Edge(endNode, nextAdjNode, graph.getWeight(endNode, nextAdjNode)));//���������µ�Ԫ�ء�
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
        *ע�͵��Ĳ�����quickSort��������
         */
//        //��ʼ�������տ�ʼֻ�����������ļ��ϡ�
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

        //��ʼ�������տ�ʼֻ�����������ļ��ϡ�
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
        while (iterator.hasNext()) {//����ͼ��ͼ�����б߰�Ȩ���������У��൱�ڶ�����
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

        while (sets[0].size() != graph.size()) {//��connective component������ͼ�����н��ʱ��
            continue1 = continue2 = true;
//            edge = edges[index++];
            edge = priorityQueue.poll();//���ѵı��ǵ�ǰ����Ȩֵ��С�ıߡ�
            startNode = edge.getStartNode();
            endNode = edge.getEndNode();

            for (int i = 0; i < sets.length && (continue1 || continue2); i++) {//�ҵ��˱߶�Ӧ��������㡣
                if (sets[i].contains(startNode)) {
                    index1 = i;
                    continue1 = false;
                }
                if (sets[i].contains(endNode)) {
                    index2 = i;
                    continue2 = false;
                }
            }
            if (sets[index1] != sets[index2]) {//�жϳ��ѵıߵ��������㲻��һ��connective component�С�
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
        while (iterator.hasNext()) {//��ʼ��components��ÿһ��componentΪֻ��һ����������
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
        while (components.size() != 1) {//������ĸ�����Ϊ1ʱ��
            minWeight = Double.MAX_VALUE;
            compoIterator = components.iterator();
            while (compoIterator.hasNext()) {//����ÿ�������
                component = compoIterator.next();
                iterator = component.iterator();
                while (iterator.hasNext()) {//��������е�ÿһ���㡣
                    node = iterator.next();
                    adjNodeIterator = graph.adjNodeIterator(node);
                    while (adjNodeIterator.hasNext()) {//�����ڴ�node���ڵ�����Node��
                        entry = adjNodeIterator.next();
                        if ((!component.contains(entry.getKey())) && entry.getValue() <= minWeight) {//�ж����˱ߵ�Ȩ�رȼ�¼����СֵminWeight��С��
                            minWeight = entry.getValue();
                            addedNode1 = node;
                            addedNode2 = entry.getKey();
                            addedComponent1 = component;
                        }
                    }
                }
            }
            tmpIterator = components.iterator();
            while (tmpIterator.hasNext()) {//����Ѱ�Ҽ�¼�µ�addedNode2�����ĸ������
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

    //��������
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
