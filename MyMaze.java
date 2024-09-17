// Names: Anish Saraf & Joshua Sheih
// x500's: saraf055 & sheih004

import java.util.*;
import java.io.*;
import java.util.Random;


public class MyMaze {

    Cell[][] maze;
    public MyMaze(int rows, int cols) { //populates a maze with cells

        maze = new Cell[rows][cols]; //

        for (int i = 0; i < rows; i++) { // loops to assign each index of maze with a cell
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell();

            }
        }
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */


    public static MyMaze makeMaze(int rows, int cols) { //creates a random maze with given size
        MyMaze newMaze = new MyMaze(rows, cols);
        Stack<int[]> stack = new Stack<>();
        int[] startIndex = new int[]{0, 0};
        stack.push(startIndex); //adds {0,0} as the first element of the stack
        newMaze.maze[0][0].setVisited(true);

        int[] currIndex = null;
        while (!stack.isEmpty()) {
            currIndex = stack.peek(); //sets the top element of the stack to the current index


            int[][] unvisitedNeighbors = new int[4][2]; //array which will contain the neighboring cells
            int row = 0;
            int col = 0;
            if (currIndex != null) {
                row = currIndex[0];
                col = currIndex[1];
            }
            int unvisitedCount = 0;
            if (row - 1 >= 0 && !newMaze.maze[row - 1][col].getVisited()) { //this section adds the indexes of the neighbors to unvisitedNeighbors
                unvisitedNeighbors[unvisitedCount][0] = row - 1; // Up neighbor's row index

                unvisitedNeighbors[unvisitedCount][1] = col;     // Up neighbor's column index
                unvisitedCount++;
            }
            if (row + 1 < rows && !newMaze.maze[row + 1][col].getVisited()) {
                unvisitedNeighbors[unvisitedCount][0] = row + 1; // Down neighbor's row index
                unvisitedNeighbors[unvisitedCount][1] = col;     // Down neighbor's column index
                unvisitedCount++;
            }
            // Check left
            if (col - 1 >= 0 && !newMaze.maze[row][col - 1].getVisited()) {
                unvisitedNeighbors[unvisitedCount][0] = row;     // Left neighbor's row index
                unvisitedNeighbors[unvisitedCount][1] = col - 1; // Left neighbor's column index
                unvisitedCount++;
            }
            // Check right
            if (col + 1 < cols && !newMaze.maze[row][col + 1].getVisited()) {
                unvisitedNeighbors[unvisitedCount][0] = row;     // Right neighbor's row index
                unvisitedNeighbors[unvisitedCount][1] = col + 1; // Right neighbor's column index
                unvisitedCount++;
            }
            int randomIndex = 0;
            int[] randomNeighbor = new int[2];
            randomNeighbor[0] = -1;
            randomNeighbor[1] = -1;
            if (unvisitedCount != 0) {
                Random rndm = new Random();
                randomIndex = rndm.nextInt(unvisitedCount);
                randomNeighbor = unvisitedNeighbors[randomIndex]; //finds a random neighbor to the current element using a random index

                newMaze.maze[randomNeighbor[0]][randomNeighbor[1]].setVisited(true);
                stack.push(randomNeighbor);
            } else {
                stack.pop();
            }
            if (randomNeighbor[0] != -1) {
                if (randomNeighbor[0] < currIndex[0]) {
                    newMaze.maze[randomNeighbor[0]][randomNeighbor[1]].setBottom(false); //removes the bottom wall of the randomNeighbor's
                }
                if (randomNeighbor[0] > currIndex[0]) {
                    newMaze.maze[currIndex[0]][currIndex[1]].setBottom(false); //removes the bottom wall of the random neighbor
                }
                if (randomNeighbor[1] < currIndex[1]) {
                    newMaze.maze[randomNeighbor[0]][randomNeighbor[1]].setRight(false);//removes the right wall of the random neighbor
                }
                if (randomNeighbor[1] > currIndex[1]) {
                    newMaze.maze[currIndex[0]][currIndex[1]].setRight(false); //removes the right wall of the random neighbor
                }
            }

        }
        for (int i = 0; i < rows; i++) { // loops through and sets everything to false before returning the maze
            for (int j = 0; j < cols; j++) {
                newMaze.maze[i][j].setVisited(false);
            }
        }


        return newMaze;
    }

    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze(boolean path) { //prints a visual representation of the maze
        for (int i = 0; i < maze[0].length; i++) { //prints the top row
            System.out.print("|---");
        }
        System.out.print("|");
        System.out.println();
        maze[maze.length - 1][maze[0].length - 1].setRight(false); //removes the right wall of the last index of maze
        for (int i = 0; i < maze.length; i++) {
            if (i != 0) {
                System.out.print("|");
            } else {
                System.out.print(" ");
            }
//            System.out.print("|");
            for (int j = 0; j < maze[0].length; j++) {


                if (maze[i][j].getVisited() == true) { //prints an  asterisk if the cell has been visited
                    System.out.print(" * ");
                } else {
                    System.out.print("   ");
                }
                if (maze[i][j].getRight() == true) {
                    System.out.print("|"); //prints the right walls

                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j].getBottom() == true) {
                    System.out.print("|---"); //prints the bottom floors
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.print("|");
            System.out.println();
        }


        // for (int j = 0; j < maze[0].length; j++) {
        //    System.out.print("|---");
        //}
        //System.out.println("|");
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() { //this method solves the maze made in makeMaze

        Queue<int[]> queue = new LinkedList<int[]>(); //uses a queue to store the elements
        int[] startIndex;
        queue.add(new int[]{0, 0});
        int[] currIndex = null;
        while (!queue.isEmpty()) { //loops through the queue and sets each current index
            currIndex = queue.remove();


            int[][] unvisitedNeighbors = new int[4][2]; //array which will contain the neighboring cells
            int row = 0;
            int col = 0;

            row = currIndex[0]; //simplifies the indexes of the current index
            col = currIndex[1];
            Cell cell = maze[row][col];
            cell.setVisited(true);

            if (row == maze.length - 1 && col == maze[0].length - 1) { //ends the loop if at the last index
                break;
            }
            int unvisitedCount = 0;
            if (row - 1 >= 0 && !maze[row - 1][col].getVisited() && !maze[row - 1][col].getBottom()) {
                unvisitedNeighbors[unvisitedCount][0] = row - 1; // Up neighbor's row index
                unvisitedNeighbors[unvisitedCount][1] = col;     // Up neighbor's column index
                unvisitedCount++;
                queue.add(new int[]{row - 1, col});
            }
            if (row + 1 < maze.length && !maze[row + 1][col].getVisited() && !maze[row][col].getBottom()) {
                unvisitedNeighbors[unvisitedCount][0] = row + 1; // Down neighbor's row index
                unvisitedNeighbors[unvisitedCount][1] = col;     // Down neighbor's column index
                unvisitedCount++;
                queue.add(new int[]{row + 1, col});
            }
            // Check left
            if (col - 1 >= 0 && !maze[row][col - 1].getVisited() && !maze[row][col - 1].getRight()) {
                unvisitedNeighbors[unvisitedCount][0] = row;     // Left neighbor's row index
                unvisitedNeighbors[unvisitedCount][1] = col - 1; // Left neighbor's column index
                unvisitedCount++;
                queue.add(new int[]{row, col - 1});
            }
            // Check right
            if (col + 1 < maze[0].length && !maze[row][col + 1].getVisited() && !maze[row][col].getRight()) {
                unvisitedNeighbors[unvisitedCount][0] = row;     // Right neighbor's row index
                unvisitedNeighbors[unvisitedCount][1] = col + 1; // Right neighbor's column index
                unvisitedCount++;
                queue.add(new int[]{row, col + 1});
            }

//            int randomIndex = 0;
//            int[] randomNeighbor = new int[2];
//            randomNeighbor[0] = -1;
//            randomNeighbor[1] = -1;
//            if (unvisitedCount != 0) {
//                Random rndm = new Random();
//                randomIndex = rndm.nextInt(unvisitedCount);
//                randomNeighbor = unvisitedNeighbors[randomIndex];
//
//                newMaze.maze[randomNeighbor[0]][randomNeighbor[1]].setVisited(true);
//                queue.add(randomNeighbor);
//
//            }
        }

        printMaze(true);
    }

    public static void main(String[] args) {
        MyMaze myMaze1 = makeMaze(3, 3);    // black box testing for making and solving the
        System.out.println("Make maze for 5 x 20: \n"); // 5 rows x 20 columns maze (rectangular)
        myMaze1.printMaze(false);
        System.out.println();
        System.out.println("Solved maze for 5 x 20: \n");
        myMaze1.solveMaze();

        System.out.println();

        MyMaze myMaze2 = makeMaze(10, 10);    // black box testing for making and solving the
        System.out.println("Make maze for 10 x 10: \n"); // 10 rows x 10 columns maze (square)
        myMaze2.printMaze(false);
        System.out.println();
        System.out.println("Solved maze for 10 x 10: \n");
        myMaze2.solveMaze();

        System.out.println();

//        // starting the glass box (white box) testing for makeMaze method
//
//        Random r = new Random();
//        r.setSeed(1);
//        MyMaze myMaze3 = makeMaze(2, 2);
//        myMaze3.printMaze(false);

        // testing for cell class
        Cell newCell = new Cell();
        System.out.println(newCell.getVisited() == false); // cell should be not visited when object first created
        System.out.println(newCell.getRight() == true); // cell should have right wall when object first created
        System.out.println(newCell.getBottom() == true); //  cell should have bottom wall when object first created
        newCell.setVisited(true);
        newCell.setRight(false);
        newCell.setBottom(false);
        System.out.println(newCell.getVisited() == true); // cell should now be visited
        System.out.println(newCell.getRight() == false); // cell should now not have right wall
        System.out.println(newCell.getBottom() == false); // cell should now not have bottom wall
    }
}
