package edu.njit.cs114.tests;

import edu.njit.cs114.CashRegister;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 2/14/20
 */
public class CashRegisterTests extends UnitTests {

    @Test
    public void Test1() {
        // test for unit value check
        try {
            CashRegister reg = new CashRegister(new int [] {25, 10, 5});
            assertTrue(false);
        } catch (Exception e) {
            // Should be reach here by valid code
            assertTrue(true);
            totalScore += 5;
            success("Test1");
        }
    }

    private static boolean verifyOutput(int [] outputArr,
                                        int [] denominations,
                                        int change,
                                        int [] expectedOutputArr) {
        int totCoins = 0;
        int totValue = 0;
        int minCoins = 0;
        for (int i=0; i < denominations.length; i++) {
            totCoins += outputArr[i];
            minCoins += expectedOutputArr[i];
            totValue += (denominations[i] * outputArr[i]);
        }
        return totValue == change && totCoins == minCoins;
    }

    @Test
    public void Test2() {
        // check for greedy solution case 1
        try {
            int [] denominations = new int [] {1, 10, 5, 25};
            CashRegister reg = new CashRegister(new int [] {1, 10, 5, 25});
            int [] coins = reg.makeChange(68);
            assertTrue(verifyOutput(coins, denominations, 68, new int [] {3, 1, 1, 2}));
            totalScore += 15;
            success("Test2");
        } catch (Exception e) {
            failure("Test2", e);
        }
    }

    @Test
    public void Test3() {
        // check for greedy solution case 2
        try {
            int [] denominations = new int [] {1, 3, 9, 27};
            CashRegister reg = new CashRegister(denominations);
            int [] coins = reg.makeChange(24);
            assertTrue(verifyOutput(coins, denominations, 24, new int [] {0, 2, 2, 0}));
            totalScore += 15;
            success("Test3");
        } catch (Exception e) {
            failure("Test3", e);
        }
    }

    @Test
    public void Test4() {
        // check for non-greedy solution case 1
        try {
            int [] denominations = new int [] {1, 3, 16, 30, 50};
            CashRegister reg = new CashRegister(denominations);
            int [] coins = reg.makeChange(65);
            assertTrue(verifyOutput(coins, denominations, 65, new int [] {0, 1, 2, 1, 0}));
            totalScore += 15;
            success("Test4");
        } catch (Exception e) {
            failure("Test4", e);
        }
    }

    @Test
    public void Test5() {
        // check for non-greedy solution case 2
        try {
            int [] denominations = new int [] {25, 1, 10};
            CashRegister reg = new CashRegister(denominations);
            int [] coins = reg.makeChange(44);
            assertTrue(verifyOutput(coins, denominations, 44, new int [] {0, 4, 4}));
            totalScore += 15;
            success("Test5");
        } catch (Exception e) {
            failure("Test5", e);
        }
    }

    @Test
    public void Test6() {
        // check for random case
        try {
            int [] denominations = new int[] {1, 49, 46, 30, 28, 41, 21, 8,
                                             31, 18, 39, 23, 13, 27, 48, 12, 34, 7, 19, 56 };
            CashRegister reg = new CashRegister(denominations);
            int change = 167;
            int [] coins = reg.makeChange(change);
            int [] minCoinsArr = new int[20];
            minCoinsArr[14] = minCoinsArr[17] = 1;
            minCoinsArr[19] = 2;
            assertTrue(verifyOutput(coins, denominations, change, minCoinsArr));
            totalScore += 15;
            success("Test6");
        } catch (Exception e) {
            failure("Test6", e);
        }
    }

    @Test
    public void Test7() {
        // check for prime number denominations other than unit value
        try {
            int [] denominations = new int[] {5, 11, 17, 29, 37, 53, 1};
            CashRegister reg = new CashRegister(denominations);
            int change = 195;
            int [] coins = reg.makeChange(change);
            int [] minCoinsArr = new int[denominations.length];
            minCoinsArr[5] = 1;
            minCoinsArr[4] = 3;
            minCoinsArr[3] = 1;
            minCoinsArr[6] = 2;
            assertTrue(verifyOutput(coins, denominations, change, minCoinsArr));
            totalScore += 10;
            success("Test7");
        } catch (Exception e) {
            failure("Test7", e);
        }
    }

}
