package edu.njit.cs114;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Author: Ravi Varadarajan
 * Date created: 2/9/20
 */
public class CashRegister {

    private static final int INFINITY = Integer.MAX_VALUE;

    private int[] denominations;

    /**
     * Constructor
     *
     * @param denominations values of coin types not in any order
     * @throws Exception when a coin of denomination 1 does not exist
     */
    public CashRegister(int[] denominations) throws Exception {
        for (int value : denominations) {
            if (value == 1) {
                this.denominations = denominations;
                return;
            }
        }

        throw new Exception("No coin of denomination 1 exists!");
    }

    /**
     * Make change for value
     *
     * @param value
     * @return array of same length as denominations array that specifies
     * coins of each denomination to use in making given change
     * with minimum number of coins
     */
    public int[] makeChange(int value) {
        int[] result = new int[denominations.length];
        if (value == 0)
            return result; // recursive base case

        LinkedList<int[]> coins = new LinkedList<>(); // Setup a linked list to house the coins that will be used
        for (int i = 0; i < denominations.length; i++) {
            int denomination = denominations[i];
            if (value >= denomination) {
                int[] scenario = makeChange(value - denomination); // recursive call to make change for the rest
                scenario[i]++; // increment the coin count
                coins.add(scenario); // store the scenario
            }
        }

        int sum;
        int runningMinimum = INFINITY; // initialize the running min counter
        for (int[] coin : coins) {
            sum = countCoins(coin); // count the number of coins
            if (runningMinimum > sum) {
                runningMinimum = sum; // find the lowest coin count
                result = coin.clone(); // set the result to corresponding scenario result
            }
        }

        return result;
    }

    public static int countCoins(int[] coins) {
        return IntStream.of(coins).sum();
    }

    /**
     * Specifies description of change in coins
     *
     * @param coins
     * @return
     */
    public void printValues(int[] coins) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < denominations.length; i++) {
            if (coins[i] > 0) {
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(coins[i] + " coins of value " + denominations[i]);
            }
        }
        System.out.println(builder.toString());
    }

    public static void main(String[] args) throws Exception {
        CashRegister reg = new CashRegister(new int[]{50, 25, 10, 5, 1});
        // should have a total of 6 coins
        reg.printValues(reg.makeChange(48));
        // should have a total of 3 coins
        reg.printValues(reg.makeChange(56));
        reg = new CashRegister(new int[]{25, 10, 1});
        // should have a total of 6 coins
        reg.printValues(reg.makeChange(33));
        reg = new CashRegister(new int[]{1, 7, 24, 42});
        // should have a total of 2 coins
        reg.printValues(reg.makeChange(48));
        reg = new CashRegister(new int[]{50, 1, 3, 16, 30});
        // should have a total of 3 coins
        reg.printValues(reg.makeChange(35));
    }

    private static class CoinCounts {

        private int numOfCoins = Integer.MAX_VALUE;

        private int[] denominations;

        private int[] coins;

        public CoinCounts(int[] denominations, int[] coins) {
            this.denominations = denominations;
            this.coins = coins;
        }

        public int getCoinCount() {
            return this.numOfCoins;
        }

        public CoinCounts newInstance() {
            return new CoinCounts(this.denominations, this.coins.clone());
        }

        public int getQuantity(int i) {
            return this.coins[i];
        }

        public void setQuantity(int i, int quantity) {
            this.coins[i] = quantity;
            updateCoinCount();
        }

        public int[] getCoins() {
            return this.coins;
        }

        private void updateCoinCount() {
            this.numOfCoins = Arrays.stream(this.coins).sum();
        }

    }

}
