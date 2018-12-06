import java.util.Comparator;
import java.util.Map;

public class FComparator implements Comparator<Node> {


    @Override
    public int compare(Node a1, Node a2) {
        if (a1.getF() > a2.getF()) {
            return 1;
        }
        if (a1.getF() < a2.getF()) {
            return -1;
        }


        return 0;
    }
}


