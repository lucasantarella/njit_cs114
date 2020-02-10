package edu.njit.cs114;

/**
 * Author: Ravi Varadarajan
 * Date created: 2/9/20
 */
public class CashRegister {

    private int [] denominations;

    /**
     * Constructor
     * @param denominations values of coin types not in any order
     * @throws Exception when a coin of denomination 1 does not exist
     */
    public CashRegister(int [] denominations) throws Exception {
    }

    /**
     * Make change for value
     * @param value
     * @return array of same length as denominations array that specifies
     *         coins of each denomination to use in making given change
     *         with minimum number of coins
     */
    public int [] makeChange(int value) {
        /**
         * Complete code here,
         */
        return new int[denominations.length];
    }

    /**
     * Specifies description of change in coins
     * @param coins
     * @return
     */
    public void printValues(int [] coins) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i < denominations.length; i++) {
            if (coins[i] > 0) {
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(coins[i] + " coins of value " + denominations[i]);
            }
        }
        System.out.println(builder.toString());
    }

    public static void main(String [] args) throws Exception {
        CashRegister reg = new CashRegister(new int [] {50, 25, 10, 5, 1});
        // should have a total of 6 coins
        reg.printValues(reg.makeChange(48));
        // should have a total of 3 coins
        reg.printValues(reg.makeChange(56));
        reg = new CashRegister(new int [] {25, 10, 1});
        // should have a total of 6 coins
        reg.printValues(reg.makeChange(33));
        reg = new CashRegister(new int [] {1, 7, 24, 42});
        // should have a total of 2 coins
        reg.printValues(reg.makeChange(48));
        reg = new CashRegister(new int [] {50, 1, 3, 16, 30});
        // should have a total of 3 coins
        reg.printValues(reg.makeChange(35));
    }
}
