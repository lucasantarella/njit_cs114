package edu.njit.cs114;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class AtoY {

    public static void printTable(char[][] t) {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j)
                System.out.print(t[i][j]);
            System.out.println();
        }
    }

    private static boolean solve(char[][] t, int row, int col, char c) {
        if (c == 'y')
            return true; // Base case, we've finished the maze

        // Increment character to get the next one in the alphabet
        char nextCharacter = (char) ((int) c + 1);

        // Check for next character in sequence in neighbor cells
        Cell nextCharNeighbor = getNextNeighbor(t, row, col, nextCharacter);
        if (nextCharNeighbor != null) // if not null, then there is one
            return solve(t, nextCharNeighbor.getRow(), nextCharNeighbor.getCol(), nextCharacter); // recursive call to continue finding

        // No next char neighbor found, continue to 'empty' cells and place characters

        // Get all valid neighbor cells
        Cell[] nextCells = findValidNextCells(t, row, col);
        for (Cell cell : nextCells) {
            // Place next character
            t[cell.getRow()][cell.getCol()] = nextCharacter;

            // Try to solve
            if (solve(t, cell.getRow(), cell.getCol(), nextCharacter))
                return true;
            else
                t[cell.getRow()][cell.getCol()] = 'z'; // No valid path, set back to 'empty'
        }

        return false; // All else fails, no path is found and return false;
    }

    public static void main(String[] args) {
        System.out.println("Enter 5 rows of lower-case letters a to z below. Note z indicates empty cell");
        Scanner sc = new Scanner(System.in);
        char[][] tbl = new char[5][5];
        int row = -1, col = -1;
        String inp;
        for (int i = 0; i < 5; ++i) {
            inp = sc.next();
            for (int j = 0; j < 5; ++j) {
                tbl[i][j] = inp.charAt(j);
                if (tbl[i][j] == 'a') {
                    row = i;
                    col = j;
                }
            }
        }

        if (solve(tbl, row, col, 'a')) {
            System.out.println("Printing the solution...");
            printTable(tbl);
        } else {
            System.out.println("There is no solution");
        }
    }

    /**
     * Finds a neighboring cell with a matching next character.
     *
     * @param t        Char array of points
     * @param row      Current row
     * @param col      Current col
     * @param nextChar Next char to search for
     * @return Cell|null depending of a neighbor is valid or not
     */
    private static Cell getNextNeighbor(char[][] t, int row, int col, char nextChar) {

        // Check all neighbors for existence and then check if they have the next char
        if (cellExists(t, row - 1, col) && t[row - 1][col] == nextChar)
            return new Cell(row - 1, col, t[row - 1][col]);

        if (cellExists(t, row + 1, col) && t[row + 1][col] == nextChar)
            return new Cell(row + 1, col, t[row + 1][col]);

        if (cellExists(t, row, col - 1) && t[row][col - 1] == nextChar)
            return new Cell(row, col - 1, t[row][col - 1]);

        if (cellExists(t, row, col + 1) && t[row][col + 1] == nextChar)
            return new Cell(row, col + 1, t[row][col + 1]);

        // Base case there are no next char cells around the current
        return null;
    }

    private static Cell[] findValidNextCells(char[][] t, int row, int col) {
        ArrayList<Cell> cells = new ArrayList<Cell>();

        if (emptyCellCheck(t, row - 1, col))
            cells.add(new Cell(row - 1, col, t[row - 1][col]));
        if (emptyCellCheck(t, row + 1, col))
            cells.add(new Cell(row + 1, col, t[row + 1][col]));
        if (emptyCellCheck(t, row, col - 1))
            cells.add(new Cell(row, col - 1, t[row][col - 1]));
        if (emptyCellCheck(t, row, col + 1))
            cells.add(new Cell(row, col + 1, t[row][col + 1]));

        return cells.toArray(new Cell[0]);
    }

    private static boolean emptyCellCheck(char[][] t, int row, int col) {
        if (cellExists(t, row, col))
            return t[row][col] == 'z';
        else
            return false;
    }

    private static boolean cellExists(char[][] t, int row, int col) {
        return row >= 0 && t.length > row && col >= 0 && t[0].length > col;
    }

}

/**
 * A Cell class to hold all three variables pertaining to a cell in the maze
 */
class Cell {

    /**
     * The integer row of the cell
     */
    private int row;

    /**
     * The integer column of the cell
     */
    private int col;

    /**
     * The character container at this point in the maze
     */
    private char letter;

    public Cell(int row, int col, char letter) {
        this.row = row;
        this.col = col;
        this.letter = letter;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getLetter() {
        return letter;
    }

}
