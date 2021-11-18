package com.company;

import javax.swing.*;
import java.awt.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class SudokuBoard {
    private int[][] sudoku_board;
    private int[][] my_solution;

    //constructor
    public SudokuBoard(int [][] sudoku_board){
        this.sudoku_board = sudoku_board;
    }

    /**
     * return an instance of the 2D matrix sudoku_board
     * @return int[][] sudoku_board
     */
    public int[][] getBoard(){
        return this.sudoku_board;
    }


    /**
     * check if sudoku board has any empty cells marked with 0
     * @param current_sudoku int[][] current_sudoku instance of this.sudoku_board
     * @return boolean => true if board is filled, false if empty
     */
    public boolean sudoku_filled(int[][] current_sudoku){
        boolean filled = true;

        rows:for(int[]arr:current_sudoku){
            ArrayList<Integer> row = Arrays.stream(arr).boxed().collect(Collectors.toCollection(ArrayList::new));
            if(row.contains(0)){//if any row has unfilled cell (contains 0) board isn't filled
                filled = false;
                break rows;//stop exploration
            }
        }
        return filled;
    }

    /**
     * solve sudoku equation using backtracking
     * @param current_sudoku_board int[][] instance of this.sudoku_board
     * @return int[][] solved sudoku board
     */
    public int[][] solve_sudoku(int[][] current_sudoku_board){
        int[][] answer = current_sudoku_board;
        //System.out.println("----------------------------------------------------------\nnew stack generated \n");
        boolean sudoku_full = this.sudoku_filled(current_sudoku_board);
        if(sudoku_full) return current_sudoku_board;//stopping condition sudoku board is full
        else{

            outer: for(int y=0; y<9; y++){
                for(int x=0; x<9; x++){
                    //if cell is empty
                    if(this.getBoard()[y][x]==0){
                        int outer_n = 0;
                        for(int n =1; n<10; n++){

                            if(this.possibleInsert(y, x, n)) {
                                this.getBoard()[y][x]=n;//insert at position on board
                                solve_sudoku(this.getBoard());//recursive solve to find next solution
                                if(!this.sudoku_filled(current_sudoku_board))//if board isn't full
                                    this.getBoard()[y][x]=0;//reset choice (bad choice was made)
                            }

                        }
                        return this.getBoard();//return to previous stack frame

                    }

                }

            }
        }
        return current_sudoku_board;
    }

    /**
     * checks if number to insert (n) is neither in row, column or sub-grid
     * i.e. can it be inserted there
     *
     * @param y int index of insert of y-axis
     * @param x int index of insert of x-axis
     * @param n int number to insert
     * @return boolean canInsert if number can be insert in position
     */
    public boolean possibleInsert(int y, int x, int n){
        boolean canInsert = true;
        //if number already either in row, column or sub-grid
        //return false (cannot insert it here), else return true
        if(this.getCol(x).contains(n) || this.getRow(y).contains(n) || this.inSubGrid(y, x, n))
            canInsert = false;

        return canInsert;
    }

    /**
     *
     * helper function of possibleInsert
     *
     * @return boolean in_sub_grid => true if number (n) in sub-grid, otherwise return false
     */
    public boolean inSubGrid(int y, int x, int n){
        boolean in_sub_grid = false;
        ArrayList<ArrayList<Integer>> sub_grid = this.fetchSubGrid(y, x);//fetch corresponding sub-grid
        //System.out.println(" inner => \n"+sub_grid);

        ArrayList<Integer> col_1 = sub_grid.get(0);
        ArrayList<Integer> col_2 = sub_grid.get(1);
        ArrayList<Integer> col_3 = sub_grid.get(2);
        //if any columns in the sub-grid contains the test number n, it cannot be added to it
        if(col_1.contains(n)||col_2.contains(n)||col_3.contains(n))
            in_sub_grid = true;

        return in_sub_grid;
    }

    /**
     * found the sub-grid in which the target is
     * @param y_pos_insert int y position of insertion
     * @param x_pos_insert int x postion of insertion
     * @return ArrayList<ArrayList<Integer>> representing sub-grid
     */
    public ArrayList<ArrayList<Integer>> fetchSubGrid(int y_pos_insert, int x_pos_insert){
        ArrayList<ArrayList<Integer>> sub_grid = new ArrayList<>();
        //will group all number less than 2 into a single block
        //same for those less than 3 and 6
        // ([0..2]/3)*3 will always be group 0
        // ([3..5]/3)*3 will always be group 3
        // ([6..9]/3)*3 will always be group 6
        int X0 = (x_pos_insert/3)*3;
        //will group
        int Y0 = (y_pos_insert/3)*3;
        for(int y=0; y<3;y++){
            //current row
            ArrayList<Integer> c_row = new ArrayList<>();
            //
            for(int x = 0; x<3;x++){c_row.add(this.getBoard()[Y0+y][X0+x]);}
            sub_grid.add(c_row);//add to row of rows
        }
        return  sub_grid;
    }

    /**
     * helper function of possibleInsert
     * get an ArrayList of values in target row
     *
     * @param row_index int index of row
     * @return ArrayList of ints in row
     */
    public ArrayList<Integer> getRow(int row_index){
        ArrayList<Integer> row = new ArrayList<>();
        //if row is within bounds
        //we do not need to check specific rows lengths
        //sudoku rows are always of equal length so sudoku_board row1.length = row2.length = row_n.length
        if(this.getBoard().length>row_index)
            row = Arrays.stream(this.sudoku_board[row_index]).boxed().collect(Collectors.toCollection(ArrayList::new));

        return row;
    }

    /**
     * helper function of possibleInsert
     * get an ArrayList of values in target column
     *
     * @param col_index int index of column
     * @return ArrayList of ints in column
     */
    public ArrayList<Integer> getCol(int col_index){
        ArrayList<Integer> col = new ArrayList<>();
        //if column index is within bounds
        if(this.getBoard()[col_index].length>col_index)
            for(int[]arr:this.sudoku_board)//collect all instances of at column index in each row
                col.add(arr[col_index]);

        return col;
    }

    /**
     * prints 2D array into readable format
     * return void
     */
    public void printBoard(){
        Arrays.stream(this.sudoku_board)
                //terminal function forEach, once used stream will be closed
                .forEach(x->{
                    //for each stream box element of stream x to int using boxed
                    //collect items and call collectors and turn to new collection ArrayList
                    ArrayList<Integer> c = Arrays.stream(x).boxed().collect(Collectors.toCollection(ArrayList::new));
                    System.out.println(c);
                });
    }

    /**
     * draw a GUI of the sudoku using JFrame
     */
    public JFrame drawGraphicFrame(String frameTitle){

        JFrame frame = new JFrame(frameTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//
        frame.setSize(500, 500);//set size
        frame.setLayout(new GridLayout(9, 9));//grid of 9x9
        int[][] board = this.getBoard();

        //draw board button
        for(int y=0; y<board[0].length;y++)
            for(int x=0; x<board.length;x++){
                //System.out.println("kk> "+board[y][x]);
                JLabel jLabel = new JLabel(String.valueOf(board[y][x]));
                jLabel.setPreferredSize(new Dimension(500,500));//set dimension of text
                JButton jButton = new JButton(jLabel.getText());//.setBackground(Color.CYAN);

                jButton.setBackground(Color.cyan);//set initial background color
                //jButton.setBorderPainted(true);
                if(board[y][x]==0)//if empty cell set it to pink
                    jButton.setBackground(Color.pink);
                frame.add(jButton);//add button to frame
            }
        frame.setVisible(true);//show frame
        //frame.get
        //JButton test = (JButton) frame.getContentPane().getComponentAt(0,0).getComponentAt(new Point(0, 0));
        //test.setText("HEX");
        //System.out.println(test.getText());//test.getText());
        return frame;
    }

    /**
     * Compare the board resolved by the program with the real solution board
     * this is an optional function (useful to investigate results)
     * @param deepHashSolvedBoard int deepHash of the 2d calculated solution matrix
     * @param deepHashRealSolution int deepHash of the 2d real solution matrix
     * @return String human readable interpretation of solution state
     */
    public String solvedBoard_To_RealSolution_Comparison(int deepHashSolvedBoard, int deepHashRealSolution){
        String solution = " sudoku resolved by program is NOT equal to solution sudoku ";
        if(deepHashSolvedBoard == deepHashRealSolution)//compare hashes of calculated solution vs real solution
            solution = " sudoku resolved by program is equal to solution sudoku ";
        return solution;
    }
}
