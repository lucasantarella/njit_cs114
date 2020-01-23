package edu.njit.cs114;

/**
 * Author:
 * Date created:
 */
public class ArrayStudy {

    public static double[][] manipulate(double[][] a) {
        // Perform step (a)
        double[][] a1 = extract(a);
        System.out.println("Content of array after step (a)");
        printArray(a1);
        double[][] a2 = replace(a1);
        System.out.println("Content of array after step (b)");
        printArray(a2);
        return a2;
    }

    // prints 2D array
    private static void printArray(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + ",");
            }
            System.out.println();
        }
    }

    private static double[][] extract(double[][] a) {
        int halfCol = 0;
        int noOfNegativesInRow = 0;
        int noOfRowsRemoved = 0;
        boolean[] removeRow = new boolean[a.length];
        // Loop through each row of a to check if number of negatives is at least halfCol
        for (int i = 0; i < a.length; i++) {
            halfCol = a[i].length / 2;
            noOfNegativesInRow = 0;

            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] < 0)
                    noOfNegativesInRow++;
            }

            if (noOfNegativesInRow >= halfCol) {
                removeRow[i] = true;
                noOfRowsRemoved++;
            }
        }
        double[][] b = new double[a.length - noOfRowsRemoved][];
        int validRowIndex = 0;
        for (int i = 0; i < a.length; i++) {
            if (!removeRow[i]) {
                b[validRowIndex] = a[i];
                validRowIndex++;
            }
        }
        return b;
    }


    private static double[][] replace(double[][] b) {
        double columnSum = 0.0; //Column sum
        double columnAverage = 0.0;//Column average
        int numPositives = 0; // number of positives in the column
        //Loop through each column j to replace negative with average of positives in the same column
        for (int j = 0; j < b[0].length; j++) {
            columnSum = 0.0;
            numPositives = 0;

            for (int i = 0; i < b.length; i++) {
                if (b[i][j] >= 0) {
                    numPositives++;
                    columnSum += b[i][j];
                }
            }

            columnAverage = numPositives > 0 ? columnSum / numPositives : 0;

            for (int i = 0; i < b.length; i++)
                if (b[i][j] < 0)
                    b[i][j] = columnAverage;

        }
        return b;
    }


    //-----------------------------------------------------------------
    //
    //-----------------------------------------------------------------
    public static void main(String[] args) {
        double[][] a = {{-1, 4, 3, 2, -3, 2}, {-2, 3, 5, -4, 0, 1}, {-1, -3, 4, 1, -1, 0}, {-1, 2, -3, 6, 5, 3}, {-3, 2, -3, -5, 0, 0}}; //A 4 x 5 Dinmension;

        System.out.println("Number of rows of the 2D array is " + a.length);

        double[][] b = manipulate(a);


    }

}




