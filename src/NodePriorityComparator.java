import java.util.Comparator;

public class NodePriorityComparator implements Comparator<Node> {

    //overwrites the compare to look at the cost of the nodes
    @Override
    public int compare(Node x, Node y) {
        if (x.getCost() < y.getCost()) {
            return -1;
        }
        if (x.getTotalcost() > y.getTotalcost()) {
            return 1;
        }
        return 0;
    }
}
