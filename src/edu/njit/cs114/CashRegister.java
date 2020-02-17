package edu.njit.cs114;

import java.util.Arrays;

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
        return makeChange(0, value, new CoinCounts(value, this.denominations, new int[this.denominations.length])).getCoins();
    }

    public CoinCounts makeChange(int startDenominatedValueIndex, int value, CoinCounts coinCounts) {
        if (value == 0 || startDenominatedValueIndex == this.denominations.length)
            return coinCounts;

        for (int i = startDenominatedValueIndex; i < this.denominations.length; i++) {
            int denomination = denominations[i];
            if (value >= denomination) {

                int numOfCoins = value / denomination;

                CoinCounts useIt = coinCounts.newInstance();
                useIt.setQuantity(i, numOfCoins);
                useIt = makeChange(i + 1, value - (numOfCoins * denomination), useIt); // use it

                CoinCounts dontUseIt = makeChange(i + 1, value, coinCounts); // don't use it

                if (useIt.getCoinCount() < dontUseIt.getCoinCount() || !dontUseIt.isTotalMet())
                    coinCounts = useIt;
                else
                    coinCounts = dontUseIt;
            }
        }

        return coinCounts;
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

        private int desiredValue;

        private int[] denominations;

        private int[] coins;

        public CoinCounts(int desiredValue, int[] denominations, int[] coins) {
            this.desiredValue = desiredValue;
            this.denominations = denominations;
            this.coins = coins;
        }

        public int getCoinCount() {
            return Arrays.stream(this.coins).sum();
        }

        public CoinCounts newInstance() {
            return new CoinCounts(this.desiredValue, this.denominations, this.coins.clone());
        }

        public int getQuantity(int i) {
            return this.coins[i];
        }

        public void setQuantity(int i, int quantity) {
            this.coins[i] = quantity;
        }

        public int[] getCoins() {
            return this.coins;
        }

        public int getDesiredValue() {
            return desiredValue;
        }

        public int getTotalValue() {
            int total = 0;
            for (int i = 0; i < this.denominations.length; i++) {
                total += this.denominations[i] * this.coins[i];
            }
            return total;
        }

        public boolean isTotalMet() {
            return this.getTotalValue() == this.getDesiredValue();
        }

    }

}
