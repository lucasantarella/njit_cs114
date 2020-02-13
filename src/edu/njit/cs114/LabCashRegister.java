package edu.njit.cs114;


/**
 * Author: Ravi Varadarajan
 * Date created: 2/11/20
 */
public class LabCashRegister {

    private static final int INFINITY = Integer.MAX_VALUE;

    private int[] denominations;

    /**
     * Constructor
     *
     * @param denominations values of coin types not in any order
     * @throws Exception when a coin of denomination 1 does not exist
     */
    public LabCashRegister(int[] denominations) throws Exception {
        this.denominations = denominations;
    }

    /**
     * Find the minimum number of coins to make change
     *
     * @param value value for which to make change
     * @return
     */
    public int minimumCoinsForChange(int value) {
        /**
         * Complete code here
         */
        return minimumCoinsForChange(0, value);
    }

    private int minimumCoinsForChange(int startDenominatedValueIndex, int value) {
        if (value == 0 || startDenominatedValueIndex == this.denominations.length)
            return 0;

        int count = INFINITY;

        for (int coinValue : this.denominations) {
            if (value >= coinValue) {
                int otherCount = minimumCoinsForChange(startDenominatedValueIndex, value - coinValue) + 1;
                if (otherCount < count)
                    count = otherCount;
            }
        }
        return count;
    }

    public static void main(String[] args) throws Exception {
        LabCashRegister reg = new LabCashRegister(new int[]{50, 25, 10, 5, 1});
        // should have a total of 6 coins
        System.out.println("Minimum coins to make change for " + 48
                + " from {50,25,10,5,1} = " + reg.minimumCoinsForChange(48));
        // should have a total of 3 coins
        System.out.println("Minimum coins to make change for " + 56
                + " from {50,25,10,5,1} = " + reg.minimumCoinsForChange(56));
        reg = new LabCashRegister(new int[]{25, 10, 1});
        // should have a total of 6 coins
        System.out.println("Minimum coins to make change for " + 33
                + " from {25,10,1} = " + reg.minimumCoinsForChange(33));
        reg = new LabCashRegister(new int[]{1, 7, 24, 42});
        // should have a total of 2 coins
        System.out.println("Minimum coins to make change for " + 48
                + " from {1,7,24,42} = " + reg.minimumCoinsForChange(48));
        reg = new LabCashRegister(new int[]{50, 1, 3, 16, 30});
        // should have a total of 3 coins
        System.out.println("Minimum coins to make change for " + 35
                + " from {50,1,3,16,30} = " + reg.minimumCoinsForChange(35));
    }
}

