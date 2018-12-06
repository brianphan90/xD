
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {


    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {

        if (start == null) {
            return Collections.emptyList();
        }

        HashMap<Point, Node> open = new HashMap<>();
        HashMap<Point, Node> closed = new HashMap<>();


        PriorityQueue<Node> pq = new PriorityQueue<>(new FComparator());


        int h_first = Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
        Node first_node = new Node(start, 0, h_first, null);

        pq.add(first_node);
        open.put(start, first_node);
        Node current = first_node;


        while (pq.size() != 0) {
            if (withinReach.test(current.getPos(), end)) {
                break;
            }

            if (current.getPos() == end){

                return Collections.emptyList();
            }

            List<Point> neighbors = potentialNeighbors.apply(current.getPos())     //finding da list of neighbors, copy dis directly, call it neighbors
                    .filter(canPassThrough)
                    .filter(point -> !closed.containsKey(point))
                    .collect(Collectors.toList());


            if (neighbors.size() == 0) {
                break;

//                for (Node n: pq) {
//                    System.out.println("PQ: (" + n.getPos().x + "," + n.getPos().y + ")");
//                }
            }

            if (neighbors.size() != 0) {

                for (Point p : neighbors) {
                    int h = Math.abs(p.x - end.x) + Math.abs(p.y - end.y);
                    Node new_node = new Node(p, current.getG() + 1, h, current);


                    //add neighbors to open list if not already in it
                    if (!open.containsKey(p)) {
                        open.put(p, new_node);
                        pq.add(new_node);
                    }

                    //if open already has that point checked, update g value if it's lower (better)
                    if (open.containsKey(p)) {
                        if (new_node.getG() < open.get(p).getG()) {
                            open.remove(p);
                            open.put(p,new_node);
                            //open.replace(p, open.get(p), new_node); //replace
                            pq.remove(open.get(p));
                            pq.add(new_node);
                        }
                    }
                }

                //current's point is the key for it's spot in open
                closed.put(current.getPos(), current);  //1
                open.remove(current.getPos()); //4
                pq.remove(current); //4


//                System.out.println("Current is: (" + pq.peek().getPos().x + "," + pq.peek().getPos().y + ")");
                current = pq.peek(); //pq 3

            }
        }


        List<Node> path = new ArrayList<>();
        path.add(current);
        if (current.getlast() == null) {
            return Collections.emptyList();
        }

        while (!current.getlast().equals(first_node)) {

            path.add(current.getlast());
            current = path.get(path.size() - 1);
        }

        Collections.reverse(path);

        List<Point> final_path = new ArrayList<>();
        for (Node n : path) {
            final_path.add(n.getPos());
        }

//        for (Point p : final_path) {
//            System.out.println("FINAL POINT LIST: (" + p.x + "," + p.y + ")");
//        }

        return final_path;

    }
}



















        /*
        List<Node> node_path = new ArrayList<>();  //create open set of nodes (possible nodes to explore)
        List<Point> closedPoints = new ArrayList<>();  //create closed points
        List <Point> openPoints = new ArrayList <>();  //create open points


        int h_first = Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
        Node first_node = new Node(start, 0, h_first, null);

        node_path.add(first_node);
        openPoints.add(first_node.getPos());
        Node current = first_node;

        /////////////////////////////////////////////////////////////

        while (!current.getPos().adjacent(end)) {


            List<Point> neighbors = potentialNeighbors.apply(current.getPos())     //finding da list of neighbors, copy dis directly, call it neighbors
                    .filter(canPassThrough)
                    .filter(point -> !closedPoints.contains(point))
                    .collect(Collectors.toList());

//            if (neighbors.size() == 0) {
//                blacklist.add(current.getPos());
//                openPoints.remove(current.getPos());
//                node_path.remove(current);
//                current = current.getlast();
//            }


            //for all the neighbors, add them to open
            for (Point p : neighbors) {
                int h = Math.abs(p.x - end.x) + Math.abs(p.y - end.y);
                int g = Math.abs(p.x - start.x) + Math.abs(p.y - start.y);
                Node new_node = new Node(p, g, h, current);

                //replace g value if new calculated g-score is higher than old calculated one

                if (openPoints.contains(p))  {      // if the new node's position is already in open,
                    int copy = openPoints.indexOf(p);    //get index of that point in open points, will be same index as open node

                    if (new_node.getG() < node_path.get(copy).getG()) {   //if the new nodes g-score is better than the old open node's g score
                        //replace it in open points and open nodes
                        node_path.set(copy, new_node);
                    }
                }

                if (!openPoints.contains(p) && !closedPoints.contains(p)) {
                    node_path.add(new_node);  //add new node to open node
                    openPoints.add(p);   //add point to openPoints
                }
            }

                node_path.remove(current);
                openPoints.remove(current.getPos());
                closedPoints.add(current.getPos());

                //System.out.println(openPoints.size());


                    // for the nodes in open that are adjacent to the current node, find da new current node based on f score, if not then g score
                Node min = node_path.get(node_path.size() - 1);   //min = last item in node_path

                for (Node nodes : node_path) {
                    if (nodes.getPos().adjacent(current.getPos())) {
                        if (nodes.getF() < min.getF()) {
                            min = nodes;
                        }
                    }
                }
                current = min;
                System.out.println("(" + (current.getPos().x) + "," + (current.getPos().y) + ")");


            }
        closedPoints.add(current.getPos());
        closedPoints.remove(0);

        System.out.println("This is last current: (" + current.getPos().x + "," + current.getPos().y + ")");

        List<Node> path = new ArrayList<>();
        path.add(current);

        while (!current.getlast().equals(first_node)) {
            path.add(current.getlast());
            current = path.get(path.size() -1);
        }

        Collections.reverse(path);

        List <Point> final_path = new ArrayList<>();
        for (Node n : path) {
            final_path.add(n.getPos());
        }

        for (Point p : final_path) {
            System.out.println("FINAL POINT LIST: (" + p.x + "," + p.y + ")");
        }

        return final_path;
    }
}

*/











