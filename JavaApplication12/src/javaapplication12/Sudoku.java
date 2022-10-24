/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

/**
 *
 * @author kikas
 */
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sudoku {
    private int[][] grid = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
    };
    private int[][] copyGrid = new int[9][9];
    private int[][] finalGrid = new int[9][9];
    private int counter;
    private Integer[] numberList = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    Sudoku() {
        fillGrid();
        removeNumbers();
        this.copyGrid = new int[9][9];
        this.copyMatrix(this.grid, this.copyGrid);
    }

    private boolean solveGrid() {
        int row = 0, col = 0;
        for(int i = 0; i < 81; i++) {
            row = i / 9;
            col = i % 9;
            if(copyGrid[row][col] == 0) {
                for(int j = 1; j < 10; j++) {
                    if(this.notInRow(j, row, copyGrid)) {
                        if(this.notInCol(j, col, copyGrid)) {
                            int[][] square = computeSquare(row, col, this.grid);
                            if(notInSquare(j, square)) {
                                grid[row][col] = j;
                                if(this.checkGrid()) {
                                    this.counter++;
                                    break;
                                } 
                                if(this.solveGrid()) return true;
                            }
                        }
                    }
                }
                break;
            }
        }
        copyGrid[row][col] = 0;
        return false;
    }

    private void removeNumbers() {
        int attempts = 5;

        while(attempts > 0) {
            int row = (int)(Math.random() * 9), col = (int)(Math.random() * 9);
            while(this.grid[row][col] == 0) {
                row = (int)(Math.random() * 9); 
                col = (int)(Math.random() * 9);
            }
            int backup = this.grid[row][col];
            this.grid[row][col] = 0;
            copyGrid = this.grid;
            counter = 0;
            this.solveGrid();
            if(counter != 1) {
                grid[row][col] = backup;
                attempts--;
            }
        }
    }

    public void displayGrid(int[][] gridToDisplay) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(gridToDisplay[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean checkGrid() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(this.grid[i][j] == 0) return false;
            }
        }
        return true;
    }

    private int[][] computeSquare (int row, int col, int[][] toExtract) {
        int[][] square = { {0, 0, 0}, {0, 0, 0}, {0, 0, 0} };
        if(row < 3) {
            if (col<3) {
                square[0][1] = toExtract[0][1];
                square[0][2] = toExtract[0][2];
                square[0][0] = toExtract[0][0];
                square[1][0] = toExtract[1][0];
                square[1][1] = toExtract[1][1];
                square[1][2] = toExtract[1][2];
                square[2][0] = toExtract[2][0];
                square[2][1] = toExtract[2][0];
                square[2][2] = toExtract[2][0];
            } else if(col<6) {
                square[0][0] = toExtract[0][3];
                square[0][1] = toExtract[0][4];
                square[0][2] = toExtract[0][5];
                square[1][0] = toExtract[1][3];
                square[1][1] = toExtract[1][4];
                square[1][2] = toExtract[1][5];
                square[2][0] = toExtract[2][3];
                square[2][1] = toExtract[2][4];
                square[2][2] = toExtract[2][5];
            } else {
                square[0][0] = toExtract[0][6];
                square[0][1] = toExtract[0][7];
                square[0][2] = toExtract[0][8];
                square[1][0] = toExtract[1][6];
                square[1][1] = toExtract[1][7];
                square[1][2] = toExtract[1][8];
                square[2][0] = toExtract[2][6];
                square[2][1] = toExtract[2][7];
                square[2][2] = toExtract[2][8];
            }  
        } else if(row < 6) {
            if (col<3) {
                square[0][0] = toExtract[3][0];
                square[0][1] = toExtract[3][1];
                square[0][2] = toExtract[3][2];
                square[1][0] = toExtract[4][0];
                square[1][1] = toExtract[4][1];
                square[1][2] = toExtract[4][2];
                square[2][0] = toExtract[5][0];
                square[2][1] = toExtract[5][1];
                square[2][2] = toExtract[5][2];
            } else if(col<6) {
                square[0][0] = toExtract[3][3];
                square[0][1] = toExtract[3][4];
                square[0][2] = toExtract[3][5];
                square[1][0] = toExtract[4][3];
                square[1][1] = toExtract[4][4];
                square[1][2] = toExtract[4][5];
                square[2][0] = toExtract[5][3];
                square[2][1] = toExtract[5][4];
                square[2][2] = toExtract[5][5];
            } else {
                square[0][0] = toExtract[3][6];
                square[0][1] = toExtract[3][7];
                square[0][2] = toExtract[3][8];
                square[1][0] = toExtract[4][6];
                square[1][1] = toExtract[4][7];
                square[1][2] = toExtract[4][8];
                square[2][0] = toExtract[5][6];
                square[2][1] = toExtract[5][7];
                square[2][2] = toExtract[5][8];
            }  
        } else {
            if (col<3) {
                square[0][0] = toExtract[6][0];
                square[0][1] = toExtract[6][1];
                square[0][2] = toExtract[6][2];
                square[1][0] = toExtract[7][0];
                square[1][1] = toExtract[7][1];
                square[1][2] = toExtract[7][2];
                square[2][0] = toExtract[8][0];
                square[2][1] = toExtract[8][1];
                square[2][2] = toExtract[8][2];
            } else if(col<6) {
                square[0][0] = toExtract[6][3];
                square[0][1] = toExtract[6][4];
                square[0][2] = toExtract[6][5];
                square[1][0] = toExtract[7][3];
                square[1][1] = toExtract[7][4];
                square[1][2] = toExtract[7][5];
                square[2][0] = toExtract[8][3];
                square[2][1] = toExtract[8][4];
                square[2][2] = toExtract[8][5];
            } else {
                square[0][0] = toExtract[6][6];
                square[0][1] = toExtract[6][7];
                square[0][2] = toExtract[6][8];
                square[1][0] = toExtract[7][6];
                square[1][1] = toExtract[7][7];
                square[1][2] = toExtract[7][8];
                square[2][0] = toExtract[8][6];
                square[2][1] = toExtract[8][7];
                square[2][2] = toExtract[8][8];
            }  
        }
        
        return square;
    }

    private boolean notInRow(int value, int row, int[][] toCheck) {
        for(int i = 0; i < 9; i++) {
            if(toCheck[row][i] == value) return false;
        }
        return true;
    }

    private boolean notInCol(int value, int col, int[][] toCheck) {
        for(int i = 0; i < 9; i++) {
            if(toCheck[i][col] == value) return false;
        }
        return true;
    }

    private boolean notInSquare(int value, int[][] square) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(square[i][j] == value) return false;
            }
        }
        return true;
    }

    private boolean fillGrid() {
        int row = 0, col = 0;
        for(int i = 0; i < 81; i++) {
            row = i / 9; 
            col = i % 9;
            if(grid[row][col] == 0) {
                List<Integer> intList = Arrays.asList(this.numberList);
		        Collections.shuffle(intList);
		        intList.toArray(this.numberList);
                for(int j = 0; j < 9; j++) {
                    final int value = this.numberList[j];
                    if(notInRow(value, row, this.grid)) {
                        if(notInCol(value, col, this.grid)) {
                            int[][] square = computeSquare(row, col, this.grid);
                            if(notInSquare(value, square)) {
                                this.grid[row][col] = value;
                                if(this.checkGrid() || this.fillGrid()) {
                                    this.copyMatrix(this.grid, this.finalGrid);
                                    return true;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        grid[row][col] = 0;
        return false;
    }

    public boolean checkWin() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                System.out.println(this.grid[i][j] + " " + this.finalGrid[i][j]);
                if(this.grid[i][j] != this.finalGrid[i][j]) return false;
            }   
        }
        return true;
    }

    public void addNumber(int index, int number) {
        if(number == 0) return;
        final int row = index / 9, col = index % 9;
        if(this.copyGrid[row][col] != 0 || this.grid[row][col] != 0) return;
        this.grid[row][col] = number;
    }

    public int getNumber(int index) {
        final int row = index / 9, col = index % 9;
        return this.grid[row][col];
    }

    public int getNumberFromScratch(int i, int j) {
        return this.grid[i][j];
    }

    public boolean deleteNumber(int index) {
        final int row = index / 9, col = index % 9;
        if(this.copyGrid[row][col] != 0) return false;
        this.grid[row][col] = 0;   
        return true;
    }

    public void copyMatrix(int[][] first, int[][] second) {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j  <9; j++) second[i][j] = first[i][j];
        }
    }

    public void resetGrid() {
        this.copyMatrix(this.copyGrid, this.grid);
    }
}