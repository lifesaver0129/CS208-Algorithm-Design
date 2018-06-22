package Greedy_MST;

/**
 * Created by XWT on 2017/3/14.
 * 此类表示一个节点。
 */
public class Node implements Cloneable{
    private String name;

    private int x_offset;
    private int y_offset;

    public Node(String name, int x_offset, int y_offset) {
        this.name = name;
        this.x_offset = x_offset;
        this.y_offset = y_offset;
    }

    @Override
    public String toString() {
        return String.format("node" + name);
    }

    public int getX_offset() {
        return x_offset;
    }

    public void setX_offset(int x_offset) {
        this.x_offset = x_offset;
    }

    public int getY_offset() {
        return y_offset;
    }

    public void setY_offset(int y_offset) {
        this.y_offset = y_offset;
    }

    public String getName() {
        return name;
    }

    @Override
    public Node clone() {
        Node object = null;
        try {
            object = (Node)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return object;
    }
}
