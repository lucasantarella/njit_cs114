package edu.njit.cs114.tests;

import edu.njit.cs114.CashRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Author: Ravi Varadarajan
 * Date created: 2/14/20
 */
public class CashRegisterTests2 extends UnitTests {

    @Test
    public void Test1() {
        // test for unit value check
        try {
            CashRegister reg = new CashRegister(new int[]{25, 10, 5, 1});
            int[] result = reg.makeChange(8);
            assertEquals(0, result[0]);
            assertEquals(0, result[1]);
            assertEquals(1, result[2]);
            assertEquals(3, result[3]);
        } catch (Exception e) {
            // Should be reach here by valid code
            assertTrue(true);
            totalScore += 5;
        }
    }

}
