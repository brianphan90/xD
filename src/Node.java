
public class Node {
    private Node last;
    private Point pos;
    private int g;
    private int h;


    public Node (Point pos, int g, int h, Node last) {
        this.pos = pos;
        this.g = g;
        this.h = h;
        this.last = last;
    }

    public void setlast(Node a) {
        last = a;
    }

    public void setG(int gee) {
        gee = g;
    }

    public void setH(int ach) {
        ach = h;
    }

    public Point getPos() {
        return pos;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return g + h ;
    }

    public Node getlast() {
        return last;
    }


}





/*

public class Node {
    private Point pos;
    private Node last;
    private int g;
    private int h;


    public Node (Point pos, int g, int h, Node last) {
        this.pos = pos;
        this.last = last;
        this.g = g;
        this.h = h;
        this.last = last;
    }

    public void setlast(Node a) {
        last = a;
    }

    public void setPos(Point a) {
        pos = a;
    }

    public void setG(int gee) {
        gee = g;
    }

    public void setH(int ach) {
        ach = h;
    }


    public Point getPos() {
        return pos;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return g + h ;
    }

     public Node getlast() {
         return last;
     }


}
*/