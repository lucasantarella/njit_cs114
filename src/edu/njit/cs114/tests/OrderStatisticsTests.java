package edu.njit.cs114.tests;

import edu.njit.cs114.OrderStatistics;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/26/20
 */
public class OrderStatisticsTests extends UnitTests {

    private int [] readIncomeData(String fileName) throws Exception {
        BufferedReader rdr = null;
        ArrayList<Integer> lst = new ArrayList<>();
        try {
            rdr = new BufferedReader(new FileReader(fileName));
            String line = null;
            while (((line=rdr.readLine())) != null) {
                lst.add(Integer.parseInt(line.trim()));
            }
        } finally {
            rdr.close();
        }
        int [] arr = new int[lst.size()];
        for (int i=0; i < arr.length; i++) {
            arr[i] = lst.get(i);
        }
        return arr;
    }

    @Test
    public void partitionTest1() {
        try {
            int[] simpleArr = new int[]{1, 2};
            int pivIndex = OrderStatistics.partition(simpleArr, 0, simpleArr.length, 1);
            assertEquals(1, pivIndex);
            totalScore += 3;
            assertEquals(true, Arrays.equals(simpleArr, new int [] {1,2}));
            totalScore += 3;
            success("partitionTest1");
        } catch (Exception e) {
            failure("partitionTest1", e);
        }
    }

    @Test
    public void partitionTest2() {
        try {
            int[] dataArr = new int[]{44,75,23,43,55,12,64,77,33};
            int pivIndex = OrderStatistics.partition(dataArr, 0, dataArr.length, 0);
            assertEquals(4, pivIndex);
            totalScore += 5;
            assertEquals(true, Arrays.equals(dataArr, new int [] {12,33,23,43,44,55,64,77,75}));
            totalScore += 5;
            success("partitionTest2");
        } catch (Exception e) {
            failure("partitionTest2", e);
        }
    }

    @Test
    public void kthSmallestRandomPivot1() {
        try {
            int[] dataArr = new int[]{44,75,23,43,55,12,64,77,33};
            int [] dataArr1 = Arrays.copyOf(dataArr, dataArr.length);
            Arrays.sort(dataArr1);
            int val = OrderStatistics.kthSmallestRandomPivot(dataArr, 7);
            assertEquals(64, val);
            totalScore += 10;
            val = OrderStatistics.kthSmallestRandomPivot(dataArr, 5);
            // should not sort the array
            //assertEquals(false, Arrays.equals(dataArr, dataArr1));
            assertEquals(44, val);
            totalScore += 10;
            success("kthSmallestRandomPivot1");
        } catch (Exception e) {
            failure("kthSmallestRandomPivot1", e);
        }
    }

    @Test
    public void kthSmallestRandomPivot2() {
        try {
            int [] incomes = readIncomeData("Incomes.txt");
            int [] incomes1 = Arrays.copyOf(incomes, incomes.length);
            long startMillis = System.currentTimeMillis();
            Arrays.sort(incomes1);
            long endMillis = System.currentTimeMillis();
            long sortTime = endMillis - startMillis;
            startMillis = System.currentTimeMillis();
            int medianIncome = OrderStatistics.kthSmallestRandomPivot(incomes,
                                 (int) Math.ceil(incomes.length/2.0));
            endMillis = System.currentTimeMillis();
            // should not sort the array
            assertEquals(false, Arrays.equals(incomes, incomes1));
            assertEquals(incomes1[(int) Math.ceil(incomes.length/2.0)], medianIncome);
            long selTime = endMillis - startMillis;
            totalScore += 15;
            System.out.println("sortTime(ms)=" +sortTime+",selTime)ms)="+selTime);
            assertEquals(true, sortTime/selTime >= 2);
            totalScore += 5;
            success("kthSmallestRandomPivot2");
        } catch (Exception e) {
            failure("kthSmallestRandomPivot2", e);
        }
    }

    @Test
    public void findClosestToMedianTest1() {
        try {
            int[] origDataArr = new int[]{10,5,4,15,3,7,8,20,3,11,21,16};
            int[] dataArr = Arrays.copyOf(origDataArr, origDataArr.length);
            int [] dataArr1 = Arrays.copyOf(dataArr, dataArr.length);
            Arrays.sort(dataArr1);
            int values [] = OrderStatistics.findClosestToMedian(dataArr, 1);
            //assertEquals(false, Arrays.equals(dataArr, dataArr1));
            assertEquals(3, values.length);
            totalScore += 2;
            Arrays.sort(values);
            assertEquals(true, Arrays.equals(Arrays.copyOfRange(dataArr1,4,7), values));
            totalScore += 6;
            dataArr = Arrays.copyOf(origDataArr, origDataArr.length);
            values = OrderStatistics.findClosestToMedian(dataArr, 2);
            //assertEquals(false, Arrays.equals(dataArr, dataArr1));
            assertEquals(5, values.length);
            totalScore += 2;
            Arrays.sort(values);
            assertEquals(true, Arrays.equals(Arrays.copyOfRange(dataArr1,3,8), values));
            totalScore += 6;
            dataArr = Arrays.copyOf(origDataArr, origDataArr.length);
            values = OrderStatistics.findClosestToMedian(dataArr, 3);
            // should not sort the array
            //assertEquals(false, Arrays.equals(dataArr, dataArr1));
            assertEquals(7, values.length);
            totalScore += 2;
            Arrays.sort(values);
            assertEquals(true, Arrays.equals(Arrays.copyOfRange(dataArr1,2,9), values));
            totalScore += 6;
            success("findClosestToMedianTest1");
        } catch (Exception e) {
            failure("findClosestToMedianTest1", e);
        }
    }

}
