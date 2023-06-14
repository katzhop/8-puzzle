public class Node {

    //holds Node values
    int[][] current;
    Node next;
    int cost = 0;
    int totalcost = 0;


    //constructor for Node
    public Node(int[][] current){
        this.current = current;
        next = null;
        cost = 0;
    }

    //Get and Set methods
    public Node getNext() {
        return next;
    }

    public int[][] getCurrent() {
        return current;
    }

    public void setCurrent(int[][] current) {
        this.current = current;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setCost(int cost) {
        this.cost = cost;
        totalcost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setTotalcost(int totalcost) {
        this.totalcost = totalcost;
    }

    public int getTotalcost() {
        return totalcost;
    }

    public int getValue(int i, int j){
        return current[i][j];
    }
}
