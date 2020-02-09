package edu.njit.cs114;

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
        /**
         * Complete code here
         */
        return false;
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
}
