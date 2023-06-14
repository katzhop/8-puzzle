import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DFS {
    //global variables for all the methods
    Stack<Node> path = new Stack<>();
    List<Node> seen = new ArrayList<>();
    Node start, goal, current;
    //keeps track of the position of the 0
    int posX = -1;
    int posY = -1;

    //constructor for DFS
    public DFS(int[][] start, int[][] goal){
        this.start = new Node(start); //initializes start
        this.goal = new Node(goal); //initializes goal
        path.push(this.start); //initializes path
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
                    temp[i][j] = current.getValue(i,j);//sets the temp to the current board
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
        while (!path.empty()) { //cycles until path is empty or solution is found
            current = path.pop(); //sets first Node in path to current
            if(Arrays.deepEquals(current.getCurrent(), goal.getCurrent())){ //checks if current is goal
                found = true;
                return found;
            }
            //find position of 0
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (current.getValue(i,j) == 0) {
                        posY = i;
                        posX = j;
                    }
                }
            }
            seen.add(current); //adds current to seen Nodes (visited)
            for (Node move: getMoves()) { //cycles through each move
                if(move!=null && !contain(move) && !path.contains(move)){ //makes sure Node isn't null or hasn't been seen
                    move.setNext(current);
                    path.push(move); //adds node to path
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
