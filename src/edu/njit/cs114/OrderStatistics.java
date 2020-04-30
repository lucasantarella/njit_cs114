package edu.njit.cs114;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/21/20
 */
public class OrderStatistics {

    private static Random random = new Random();

    private static void swap(int[] arr, int k1, int k2) {
        int temp = arr[k1];
        arr[k1] = arr[k2];
        arr[k2] = temp;
    }

    /**
     * Return the index pivIndex of the pivot element after partitioning sub array
     * After partitioning left sub array will be in dataArr[fromIndex..pivIndex-1]
     * right sub array will be in dataArr[pivIndex...toIndex-1]
     *
     * @param dataArr
     * @param fromIndex       starting index of sub array
     * @param toIndex         ending index (not including) of sub array i.e sub array is given
     *                        by dataArr[fromIndex..toIndex-1]
     * @param pivotElementIdx index of pivot element before partitioning
     * @return
     */
    public static int partition(int [] dataArr, int fromIndex, int toIndex,
                                int pivotElementIdx) {
        int low = fromIndex + 1;
        int high = toIndex - 1;
        swap(dataArr, fromIndex, pivotElementIdx);

        int pivot = dataArr[fromIndex];
        do {
            while (low < toIndex && dataArr[low] < pivot)
                low++;
            while (high >= fromIndex && dataArr[high] >= pivot)
                high--;

            if (low < high)
                swap(dataArr, low, high);
        } while (low < high);

        if (high >= fromIndex) {
            swap(dataArr, fromIndex, high);
            return high;
        } else
            return fromIndex;
    }

    public static int randomPartition(int[] dataArr, int fromIndex, int toIndex) {
        int randomOffset = random.nextInt(toIndex - fromIndex);
        return partition(dataArr, fromIndex, toIndex, fromIndex + randomOffset);
    }

    private static int kthSmallestRandomPivot(int[] dataArr, int fromIndex, int toIndex, int k) {

        if (toIndex - fromIndex == 1)
            return dataArr[fromIndex];

        int pivotIndex = randomPartition(dataArr, fromIndex, toIndex);
        int partition = pivotIndex - fromIndex + 1;

        if (k < partition)
            return kthSmallestRandomPivot(dataArr, fromIndex, pivotIndex, k);
        else if (k == partition)
            return dataArr[pivotIndex];
        else
            return kthSmallestRandomPivot(dataArr, pivotIndex + 1, toIndex, k - partition);

    }

    /**
     * Find k-th smallest element using a random pivot
     *
     * @param dataArr
     * @param k
     * @return
     */
    public static int kthSmallestRandomPivot(int[] dataArr, int k) {
        return kthSmallestRandomPivot(dataArr, 0, dataArr.length, k);
    }

    public static int[] readIncomeData(String fileName) throws Exception {
        BufferedReader rdr = null;
        ArrayList<Integer> lst = new ArrayList<>();
        try {
            rdr = new BufferedReader(new FileReader(fileName));
            String line = null;
            while (((line = rdr.readLine())) != null) {
                lst.add(Integer.parseInt(line.trim()));
            }
        } finally {
            rdr.close();
        }
        int[] arr = new int[lst.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = lst.get(i);
        }
        return arr;
    }



    private static int kthSmallestRandomPivotIdx(int[] dataArr, int fromIndex, int toIndex, int k) {
        // complete the code here

        if (toIndex - fromIndex == 1) {
            return dataArr[fromIndex];
        }
        int pivIndex = randomPartition(dataArr, fromIndex, toIndex);
        int p = pivIndex - fromIndex + 1;
        if (k < p) {
            return kthSmallestRandomPivotIdx(dataArr, fromIndex, pivIndex, k);
        } else if (k == p) {
            return pivIndex;
        } else {
            return kthSmallestRandomPivotIdx(dataArr, pivIndex + 1, toIndex, k - p);
        }

    }

    /**
     * Find k-th smallest element using a random pivot
     *
     * @param dataArr
     * @param k
     * @return
     */
    public static int kthSmallestRandomPivotIdx(int[] dataArr, int k) {
        return kthSmallestRandomPivotIdx(dataArr, 0, dataArr.length, k);
    }

    /**
     * Find k closest values on either side of the median
     *
     * @param dataArr
     * @param k
     * @return a total of 2k+1 values
     */
    public static int[] findClosestToMedian(int[] dataArr, int k) {
        /**
         * Complete code for homework
         */
        if (2 * k + 1 >= dataArr.length) {
            throw new IllegalArgumentException("k is too large");
        }
        int[] closestValues = new int[2 * k + 1];
        int medianIdx = (int) Math.ceil(dataArr.length / 2);
        /**
         * Complete code here
         * (i) find first medianIdx-k th smallest element and then partition array on this value
         * (ii) Find the 2k-th smallest in the right sub array of the partition
         * (ii) Partition this right sub array on this value
         * (iii) All elements in the left sub array of this new partition gives the required values
         *   and return them in an array (do not sort!!)
         */
        int pivot = kthSmallestRandomPivotIdx(dataArr, medianIdx - k);
        partition(dataArr, 0, dataArr.length, pivot);

        int pivot2 = kthSmallestRandomPivotIdx(dataArr, pivot, dataArr.length, 2 * k);
        partition(dataArr, pivot, dataArr.length, pivot2);

        System.arraycopy(dataArr, pivot, closestValues, 0, 2 * k + 1);

        return closestValues;
    }

    public static void main(String[] args) throws Exception {
        int[] arr = new int[]{10, 5, 4, 15, 3, 7, 8, 20, 3, 11, 21, 16};
        int[] arr1 = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arr1);
        int medianVal = kthSmallestRandomPivot(arr, (int) Math.ceil(arr.length / 2));
        System.out.println("median value=" + medianVal); // should be 8
        int eighthSmallest = kthSmallestRandomPivot(arr, 8);
        System.out.println("eighthSmallest value=" + eighthSmallest); // should be 11
        int[] closest = findClosestToMedian(arr, 2);
        System.out.println("4 closest values to median = " + Arrays.toString(closest));
        closest = findClosestToMedian(arr, 3);
        System.out.println("6 closest values to median = " + Arrays.toString(closest));
        int[] incomes = readIncomeData("Incomes.txt");
        long startMillis = System.currentTimeMillis();
        int medianIncome = kthSmallestRandomPivot(incomes, (int) Math.ceil(incomes.length / 2.0));
        long endMillis = System.currentTimeMillis();
        System.out.println("median income = " + medianIncome);
        System.out.println("Time(ms) for finding median using selection alg.:" + (endMillis - startMillis));
        startMillis = System.currentTimeMillis();
        int topOnePercentIncome = kthSmallestRandomPivot(incomes, (int) (0.99 * incomes.length));
        endMillis = System.currentTimeMillis();
        System.out.println("top one percent income = " + topOnePercentIncome);
        System.out.println("Time(ms) for finding top one percent income using selection alg.:" + (endMillis - startMillis));
        int[] incomes1 = Arrays.copyOf(incomes, incomes.length);
        startMillis = System.currentTimeMillis();
        Arrays.sort(incomes1);
        medianIncome = incomes1[(int) Math.ceil(incomes1.length / 2) - 1];
        endMillis = System.currentTimeMillis();
        System.out.println("median income = " + medianIncome);
        System.out.println("top one percent income = " +
                incomes1[((int) (0.99 * incomes1.length)) - 1]);
        System.out.println("Time(ms) for finding median using sorting :" + (endMillis - startMillis));
        incomes = readIncomeData("Incomes.txt");
        startMillis = System.currentTimeMillis();
        int[] closeToMedianIncomes = findClosestToMedian(incomes, 5000);
        endMillis = System.currentTimeMillis();
        System.out.println("Time(ms) for finding closest to median using selection alg. :" + (endMillis - startMillis));
    }
}

