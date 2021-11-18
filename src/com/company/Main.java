package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //0 represents empty cell, sudoku board can only have values 1-9
         SudokuBoard sudokuBoard = new SudokuBoard(new int[][]{
                {4, 7, 3, 5, 1, 6, 2, 9, 8},
                {1, 8, 2, 9, 3, 7, 0, 5, 4},
                {0, 6, 9, 0, 4, 8, 3, 7, 0},
                {8, 9, 5, 7, 2, 1, 0, 3, 6},
                {3, 1, 4, 8, 0, 0, 7, 2, 5},
                {7, 0, 6, 4, 5, 3, 1, 0, 9},
                {9, 4, 0, 6, 8, 0, 5, 1, 3},
                {2, 5, 1, 3, 9, 4, 8, 6, 0},
                {6, 3, 8, 1, 7, 5, 9, 0, 2},
        });

        SudokuBoard sudokuBoard1 = new SudokuBoard(new int[][]{
                {0, 0, 0, 2, 6, 0, 7, 0, 1},
                {6, 8, 0, 0, 7, 0, 0, 9, 0},
                {1, 9, 0, 0, 0, 4, 5, 0, 0},
                {8, 2, 0, 1, 0, 0, 0, 4, 0},
                {0, 0, 4, 6, 0, 2, 9, 0, 0},
                {0, 5, 0, 0, 0, 3, 0, 2, 8},
                {0, 0, 9, 3, 0, 0, 0, 7, 4},
                {0, 4, 0, 0, 5, 0, 0, 3, 6},
                {7, 0, 3, 0, 1, 8, 0, 0, 0},
        });

        int[][] solution_to_sudokuBoard1 = new int[][]{
                {4, 3, 5, 2, 6, 9, 7, 8, 1},
                {6, 8, 2, 5, 7, 1, 4, 9, 3},
                {1, 9, 7, 8, 3, 4, 5, 6, 2},
                {8, 2, 6, 1, 9, 5, 3, 4, 7},
                {3, 7, 4, 6, 8, 2, 9, 1, 5},
                {9, 5, 1, 7, 4, 3, 6, 2, 8},
                {5, 1, 9, 3, 2, 6, 8, 7, 4},
                {2, 4, 8, 9, 5, 7, 1, 3, 6},
                {7, 6, 3, 4, 1, 8, 2, 5, 9},
        };

        sudokuBoard1.printBoard();//print initial board
        sudokuBoard1.drawGraphicFrame("unsolved board");
        int[][] result = sudokuBoard1.solve_sudoku(sudokuBoard1.getBoard());//solve sudoku_board
        System.out.println("solved \n");
        sudokuBoard1.printBoard();//print solved board
        sudokuBoard1.drawGraphicFrame("solved board");
        System.out.println(sudokuBoard1.solvedBoard_To_RealSolution_Comparison(Arrays.deepHashCode(result),
                Arrays.deepHashCode(solution_to_sudokuBoard1)));
        //System.out.println(" hash of solution "+ Arrays.deepHashCode(result));//deepHash calculation since we have a 2D primitive array
        //System.out.println(" hash of solution to sudokuBoard "+Arrays.deepHashCode(solution_to_sudokuBoard1));//deepHash calculation since we have a 2D primitive array

        //boolean flag = Arrays.deepHashCode(result)==Arrays.deepHashCode(solution_to_sudokuBoard1);//compare hashes of calculated solution vs real solution
        //System.out.println(" sudoku resolved by program is equal to solution sudoku "+ flag);

    }

}
