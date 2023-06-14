import java.util.*;

public class Astar {
    //keeps track of the position of the 0
    int posY = -1;
    int posX = -1;
    //global variables for all the methods
    Node current, goal;
    NodePriorityComparator nodePriorityComparator = new NodePriorityComparator();
    PriorityQueue<Node> path = new PriorityQueue<Node>(nodePriorityComparator);
    List<Node> seen = new ArrayList<>();

    //constructor for A*
    public Astar(int[][] start, int[][] goal){
        this.goal = new Node(goal);
        path.add(new Node(start));
    }

    //gets board for moving left
    public Node Left(){
        if(posX>0) { //makes sure it can move left
            int[][] temp = new int[3][3];
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    temp[i][j] = current.getValue(i,j);//sets the temp to the current board
                }
            }
            //Swap positions
            temp[posY][posX] = temp[posY][posX - 1];
            temp[posY][posX - 1] = 0;
            return new Node(temp);
        } else{
            return null;
        }
    }

    //gets board for moving right
    public Node Right(){
        if(posX<2) { //makes sure it can move right
            int[][] temp = new int[3][3];
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    temp[i][j] = current.getValue(i,j); //sets the temp to the current board
                }
            }
            //Swap positions
            temp[posY][posX] = temp[posY][posX + 1];
            temp[posY][posX + 1] = 0;
            return new Node(temp);
        }else{
            return null;
        }
    }

    //gets board for moving up
    public Node Up(){
        if(posY>0) { //makes sure it can move up
            int[][] temp = new int[3][3];
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    temp[i][j] = current.getValue(i,j);//sets the temp to the current board
                }
            }
            //Swap positions
            temp[posY][posX] = temp[posY - 1][posX];
            temp[posY - 1][posX] = 0;
            return new Node(temp);
        }else{
            return null;
        }
    }

    //gets board for moving down
    public Node Down(){
        if(posY<2) { //makes sure it can move down
            int[][] temp = new int[3][3];
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    temp[i][j] = current.getValue(i,j); //sets the temp to the current board
                }
            }
            //Swap positions
            temp[posY][posX] = temp[posY + 1][posX];
            temp[posY + 1][posX] = 0;
            return new Node(temp);
        }else{
            return null;
        }
    }

    //combines all moves into a list
    public List<Node> getMoves(){
        List<Node> moves = new ArrayList<>();
        moves.add(Up());
        moves.add(Left());
        moves.add(Down());
        moves.add(Right());
        return moves;
    }

    //counts how many spaces away each tile in the wrong place is
    public int heuristic(Node n){
        int cost = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                int looking = n.getValue(i,j);
                if(looking != goal.getValue(i,j)){
                    //if tile is in the wrong spot count how many spaces away it is for the correct spot
                    if(looking==1){
                        cost += i+j;
                    }
                    else if(looking==2){
                        if(j==1) {
                            cost += i;
                        }
                        else{
                            cost += i+1;
                        }
                    }
                    else if(looking==3){
                        if(j==2) {
                            cost += i;
                        }
                        else if(j==1) {
                            cost += i+1;
                        }
                        else{
                            cost += i+2;
                        }
                    }
                    else if(looking == 4){
                        if(j==2) {
                            cost += 1;
                        }
                        else if(j==1) {
                            if(i==1){
                                cost += 1;
                            }
                            else{
                                cost += 2;
                            }
                        }
                        else{
                            if(i==1){
                                cost += 2;
                            }
                            else{
                                cost += 3;
                            }
                        }
                    }
                    else if(looking==5) {
                        if (j == 2) {
                            if (i == 0) {
                                cost += 2;
                            } else {
                                cost += 1;
                            }
                        } else if (j == 1) {
                            if (i == 0) {
                                cost += 3;
                            } else {
                                cost += 2;
                            }
                        } else {
                            if (i == 0) {
                                cost += 4;
                            } else {
                                cost += 3;
                            }
                        }
                    }
                    else if(looking==6){
                        if (i == 2) {
                            cost+=1;
                        } else if (i == 1) {
                            if (j == 1) {
                                cost += 1;
                            } else {
                                cost += 2;
                            }
                        } else {
                            if (j == 1) {
                                cost += 2;
                            } else {
                                cost += 3;
                            }
                        }
                    }
                    else if(looking==7){
                        if(i==2) {
                            cost += j;
                        }
                        else if(i==1) {
                            cost += 1+j;
                        }
                        else{
                            cost += 2+j;
                        }
                    }
                    else if(looking==8){
                        if(i==1) {
                            cost += j;
                        }
                        else{
                            cost += 1+j;
                        }
                    }
                }
            }
        }
        return cost;
    }

    //checks if seen contains the current node
    public boolean contain(Node move) {
        boolean in = false;
        for (Node s : seen) {
            if(Arrays.deepEquals(s.getCurrent(), move.getCurrent())){
                in = true;
            }
        }
        return in;
    }


    public boolean search() {
        boolean found = false;
        while (!path.isEmpty()) { //cycles until path is empty or solution is found
            current = path.poll(); //sets first Node in path to current
            if(Arrays.deepEquals(current.getCurrent(), goal.getCurrent())){ //checks if current is goal
                found = true;
                return found;
            }
            //find position of 0
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (current.getValue(i, j) == 0) {
                        posY = i;
                        posX = j;
                    }
                }
            }
            seen.add(current); //adds current to seen Nodes (visited)
            for (Node move: getMoves()) { //cycles through each move
                if(move!=null && !contain(move) && !path.contains(move)) { //makes sure Node isn't null or hasn't been seen
                    if (path.isEmpty() || current.getTotalcost() == 0) {
                        move.setCost(current.cost + 1); //updates cost
                        move.setTotalcost(move.getCost() + heuristic(move)); //combines cost and heuristic to get total
                        move.setNext(current);
                        path.add(move);
                    } else if (current.cost+heuristic(move) <= path.peek().getTotalcost()) { //adds only if cost is < previous
                        move.setCost(current.cost + 1); //updates cost
                        move.setTotalcost(move.getCost() + heuristic(move)); //combines cost and heuristic to get total
                        move.setNext(current);
                        path.add(move); //adds node to path
                    }
                }
            }
        }
        return found;
    }

    //returns number of visited nodes
    public int visited(){
        return seen.size();
    }
}

