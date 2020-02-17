package edu.njit.cs114;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
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
        Coins coins = new Coins();
        for (int denomination : this.denominations) {
            coins.add(new CoinCount(denomination, INFINITY));
        }

        int[] result = makeChange(new Coins(), 0, value).createDenominationsArray(this.denominations);
        return result;
    }

    public Coins makeChange(Coins coinValues, int startDenominatedValueIndex, int remainingValue) {
        if (remainingValue == 0 || startDenominatedValueIndex == this.denominations.length)
            return coinValues; // Base case, nothing more to calculate

        for (int i = startDenominatedValueIndex; i < this.denominations.length; i++) {
            int denomination = denominations[i];
            if (remainingValue >= denomination) {
                int numOfCoins = remainingValue / denomination;
                Coins otherCoins = coinValues.newInstance();
                CoinCount coinToRemove = otherCoins.getCoinCountByDenomination(denomination);
                otherCoins.remove(coinToRemove);
                otherCoins.add(new CoinCount(denomination, numOfCoins));
                otherCoins = makeChange(otherCoins, i + 1, remainingValue - (numOfCoins * denomination));

                if (otherCoins.coinsCount() < coinValues.coinsCount())
                    coinValues = otherCoins;
            }
        }

        return coinValues;
    }

    /**
     * Helper function for merging two arrays for the makeChange function
     *
     * @param <T>    Type for the list
     * @param arrays List of arrays in order to combine
     * @return An array containing [ ...a , ...b ]
     */
    @SafeVarargs
    public static <T> List<T> arrayMerge(List<T>... arrays) {
        return Stream.of(arrays)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
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

}

class CoinCount {

    private int denomination;

    private int quantity = Integer.MAX_VALUE;

    public CoinCount(int denomination, int quantity) {
        this.denomination = denomination;
        this.quantity = quantity;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getValue() {
        return this.quantity * this.denomination;
    }

    public CoinCount newInstance() {
        return new CoinCount(this.denomination, this.quantity);
    }

}

class Coins extends ArrayList<CoinCount> {

    public int coinsCount() {
        int count = 0;
        for (CoinCount coinCount : this) {
            count += coinCount.getQuantity();
        }
        return count;
    }

    public int coinsValue() {
        int value = 0;
        for (CoinCount coinCount : this) {
            value += coinCount.getValue();
        }
        return value;
    }

    public CoinCount getCoinCountByDenomination(int denomination) {
        for (CoinCount coinCount : this) {
            if (coinCount.getDenomination() == denomination)
                return coinCount;
        }

        return null;
    }

    public int[] createDenominationsArray(int[] denominations) {
        int[] result = new int[denominations.length];
        CoinCount coinCount;
        for (int i = 0; i < denominations.length; i++) {
            coinCount = this.getCoinCountByDenomination(denominations[i]);
            result[i] = (coinCount != null ? coinCount.getQuantity() : 0);
        }
        return result;
    }

    public Coins newInstance() {
        Coins coins = new Coins();
        coins.addAll(this);
        return coins;
    }

}

