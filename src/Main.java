import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    static int[][] goalState = {{1,2,3},{8, 0, 4}, {7, 6, 5}};

    public static boolean solvable(int[][] board){
        boolean found = false;
        int inversions = -1; //keeps track of the needed inversions
        for(int i=0;i<3;i++){
            for (int j=0;j<3;j++) {
                for(int k=0;k<3;k++) {
                    if (goalState[k][j] != board[i][k]) {
                        inversions++; //cycles through and finds mismatched spots
                    }
                }
            }
        }
        if(inversions%2==0){ //if inversions are even then it is solvable
            found = true;
        }
        return found;
    }

    public static void main(String[] args){
        boolean found = false;
        //initializes values for table
        float avgNodeBFS = 0;
        float avgNodeDFS = 0;
        float avgNodeUCS = 0;
        float avgNodeAstar = 0;
        float avgTimeBFS = 0;
        float avgTimeDFS = 0;
        float avgTimeUCS = 0;
        float avgTimeAstar = 0;
        int runD=0;
        int runB=0;
        int runU=0;
        int runA=0;
        Integer[] arr = { 0, 1, 2, 3, 4, 5, 6, 7, 8 }; //creates array to hole 0-8
        List<Integer> intList = Arrays.asList(arr); //sets array to list
        Collections.shuffle(intList); //shuffles list
        intList.toArray(arr); //resets list to array

        int pos = 0;
        //creates board
        int[][] board = new int[3][3];
        Scanner sc = new Scanner(System.in);
        boolean stop = false;
        while(!stop){
            //prints menu
            System.out.println("""

                    -------------Menu-------------
                    1. Run DFS
                    2. Run BFS
                    3. Run UCS
                    4. Run A*
                    5. Print Table
                    6. Create New Board
                    7. Exit

                    Enter option:""");
            int choice = sc.nextInt(); //reads in user's choice
            switch (choice) {
                case 1 -> {
                    if(found) {
                        DFS D = new DFS(board, goalState); //creates DFS
                        long startDFS = System.currentTimeMillis(); //starts time
                        boolean d = D.search(); //runs search for DFS
                        long endDFS = System.currentTimeMillis(); //ends time
                        if (d) { //if search has solution adds stats to table
                            runD++;//increases number of runs
                            avgTimeDFS = (avgTimeDFS + (endDFS - startDFS)) / runD;
                            avgNodeDFS = (avgNodeDFS + D.visited()) / runD;
                        }
                    }
                }
                case 2 -> {
                    if(found) {
                        BFS B = new BFS(board, goalState); //creates BFS
                        long startBFS = System.currentTimeMillis(); //starts time
                        boolean b = B.search(); //runs search for BFS
                        long endBFS = System.currentTimeMillis(); //ends time
                        if (b) { //if search has solution adds stats to table
                            runB++;//increases number of runs
                            avgTimeBFS = (avgTimeBFS + (endBFS - startBFS)) / runB;
                            avgNodeBFS = (avgNodeBFS + B.visited()) / runB;
                        }
                    }
                }
                case 3 -> {
                    if(found) {
                        UCS U = new UCS(board, goalState);//creates UCS
                        long startUCS = System.currentTimeMillis(); //starts time
                        boolean u = U.search(); //runs search for UCS
                        long endUCS = System.currentTimeMillis(); //ends time
                        if (u) { //if search has solution adds stats to table
                            runU++; //increases number of runs
                            avgTimeUCS = (avgTimeUCS + (endUCS - startUCS)) / runU;
                            avgNodeUCS = (avgNodeUCS + U.visited()) / runU;
                        }
                    }
                }
                case 4 -> {
                    if(found) {
                        Astar A = new Astar(board, goalState); //creates A*
                        long startA = System.currentTimeMillis(); //starts time
                        boolean a = A.search(); //runs search for A*
                        long endA = System.currentTimeMillis(); //ends time
                        if (a) { //if search has solution adds stats to table
                            runA++; //increases number of runs
                            avgTimeAstar = (avgTimeAstar + (endA - startA)) / runA;
                            avgNodeAstar = (avgNodeAstar + A.visited()) / runA;
                        }
                    }
                }
                case 5 -> System.out.println("Algorithm\t\tAvg # visited\t\tAvg run time\t\t Comments" +
                        "\n----------------------------------------------------------------------------------------------" +
                        "\nDFS\t\t\t\t" + avgNodeDFS + "\t\t\t\t" + avgTimeDFS + "\t\t\t" +
                        "Randomly moves around not repeating states until finds solution" +
                        "\nBFS\t\t\t\t" + avgNodeBFS + "\t\t\t\t" + avgTimeBFS + "\t\t\t\t\t" +
                        "Looks for best path without repeating states until finds solution" +
                        "\nUCS\t\t\t\t" + avgNodeUCS + "\t\t\t\t" + avgTimeUCS + "\t\t\t\t" +
                        "Looks for shortest path" +
                        "\nA*\t\t\t\t" + avgNodeAstar + "\t\t\t\t" + avgTimeAstar + "\t\t\t\t" +
                        "Combines shortest path and best path" +
                        "\nHeuristics of UCS\t\tcost of any movement = 1" +
                        "\nHeuristics of A*\t\tbased on how may spaces away each tile is from the correct position ");
                //prints table
                case 6 -> {
                    Collections.shuffle(intList); //shuffles array of 0-8
                    intList.toArray(arr); //sets to array
                    pos = 0;
                    //prints out new board
                    for (int i = 0; i < 3; i++) {
                        System.out.print("[\t");
                        for (int j = 0; j < 3; j++) {
                            board[i][j] = arr[pos];
                            System.out.print(board[i][j] + "\t");
                            pos++;
                        }
                        System.out.println("]");
                    }
                    //checks if the board is solvable
                    found = solvable(board);
                }
                case 7 -> stop = true; //stops loop
                default -> System.out.println("Enter a valid option (1-7)"); //prompts user for valid option
            }
        }
    }
}
