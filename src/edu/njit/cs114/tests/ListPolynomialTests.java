package edu.njit.cs114.tests;

import edu.njit.cs114.ListPolynomial;
import edu.njit.cs114.Polynomial;
import edu.njit.cs114.PolynomialTerm;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 1/31/20
 */
public class ListPolynomialTests extends UnitTests {

    private static class MockPolynomial implements Polynomial {

        private class MockPolyIterator implements Iterator<PolynomialTerm> {

            int [] powers = new int [] {3, 1, 0};
            int index = 0;
            public boolean hasNext() {
                return index < powers.length;
            }
            public PolynomialTerm next() {
                if (!hasNext()) {
                    return null;
                }
                return new PolynomialTerm(coefficient(powers[index]), powers[index++]);
            }
        }

        @Override
        public double coefficient(int power) {
            if (power == 0) {
                return -6.7;
            }
            if (power == 1) {
                return -5.4;
            }
            if (power == 3) {
                return 2.8;
            }
            return 0;
        }

        @Override
        public int degree() {
            return 3;
        }

        @Override
        public void addTerm(int power, double coefficient) throws Exception {

        }

        @Override
        public PolynomialTerm removeTerm(int power) {
            return null;
        }

        @Override
        public double evaluate(double point) {
            return 0;
        }

        @Override
        public Polynomial add(Polynomial p) {
            return null;
        }

        @Override
        public Polynomial subtract(Polynomial p) {
            return null;
        }

        @Override
        public Polynomial multiply(Polynomial p) {
            return null;
        }

        @Override
        public Iterator<PolynomialTerm> getIterator() {
            return new MockPolyIterator();
        }
    }

    @Test
    public void singleTermConstructorTest() {
        try {
            Polynomial p = new ListPolynomial(0, -3.6);
            assertEquals(p.toString(), "-3.6");
            totalScore += 2;
            p = new ListPolynomial(4, 2.8);
            assertEquals(p.toString(), "2.8x^4");
            totalScore += 3;
            success("singleTermConstructorTest");
        } catch (Exception e) {
            failure("singleTermConstructorTest", e);
        }
    }

    @Test
    public void copyConstructorTest1() {
        try {
            Polynomial p1 = new ListPolynomial(5, -3.6);
            p1.addTerm(3,1.9);
            Polynomial p2 = new ListPolynomial(p1);
            assertEquals(p2.toString(), "-3.6x^5 + 1.9x^3");
            totalScore += 5;
            p1.addTerm(0,3.2);
            assertEquals(p2.toString(), "-3.6x^5 + 1.9x^3");
            totalScore += 1;
            success("copyConstructorTest1");
        } catch (Exception e) {
            failure("copyConstructorTest1", e);
        }
    }

    @Test
    public void copyConstructorTest2() {
        try {
            Polynomial p1 = new MockPolynomial();
            Polynomial p2 = new ListPolynomial(p1);
            assertEquals(p2.toString(), "2.8x^3 - 5.4x - 6.7");
            totalScore += 2;
            success("copyConstructorTest2");
        } catch (Exception e) {
            failure("copyConstructorTest2", e);
        }
    }

    @Test
    public void addTermTest() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(3,1.9);
            assertEquals(p1.toString(), "1.9x^3");
            totalScore += 2;
            p1.addTerm(7,3.6);
            assertEquals(p1.toString(), "3.6x^7 + 1.9x^3");
            totalScore += 2;
            p1.addTerm(0,-1.4);
            assertEquals(p1.toString(), "3.6x^7 + 1.9x^3 - 1.4");
            totalScore += 3;
            success("addTermTest");
        } catch (Exception e) {
            failure("addTermTest", e);
        }
    }

    @Test
    public void removeTermTest() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(3,1.9);
            p1.addTerm(7,3.6);
            assertEquals(p1.toString(), "3.6x^7 + 1.9x^3");
            p1.removeTerm(7);
            assertEquals(p1.toString(), "1.9x^3");
            totalScore += 2;
            p1.removeTerm(4);
            assertEquals(p1.toString(), "1.9x^3");
            totalScore += 2;
            p1.removeTerm(3);
            assertEquals(p1.toString(), "0.0");
            totalScore += 2;
            success("removeTermTest");
        } catch (Exception e) {
            failure("removeTermTest", e);
        }
    }

    @Test
    public void degreeTest() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(3, 1.9);
            p1.addTerm(7, 3.6);
            assertEquals(p1.degree(), 7);
            totalScore += 1;
            success("degreeTest");
        } catch (Exception e) {
            failure("degreeTest", e);
        }
    }

    @Test
    public void coefficientTest() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(3, 1.9);
            p1.addTerm(7, 3.6);
            assertEquals(p1.coefficient(7), 3.6);
            assertEquals(p1.coefficient(3), 1.9);
            assertEquals(p1.coefficient(2), 0);
            totalScore += 3;
            success("coefficientTest");
        } catch (Exception e) {
            failure("coefficientTest", e);
        }
    }

    @Test
    public void evaluateTest() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(3, -1.8);
            p1.addTerm(7, 3.6);
            p1.addTerm(0, -5);
            double expectedVal = 3.6*Math.pow(2,7) - 1.8*Math.pow(2,3) -5;
            assertTrue(approxEquals(p1.evaluate(2), expectedVal));
            totalScore += 5;
            success("evaluateTest");
        } catch (Exception e) {
            failure("evaluateTest", e);
        }
    }

    @Test
    public void addTest1() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(3, 3.6);
            Polynomial p2 = new ListPolynomial();
            p2.addTerm(0, -1.8);
            p2.addTerm(2, 3.6);
            p2.addTerm(5, -7.1);
            Polynomial p3 = p1.add(p2);
            assertEquals(p1.toString(), "3.6x^3 - 1.8x^2");
            totalScore += 1;
            assertEquals(p3.degree(), 5);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(5), -7.1));
            assertTrue(approxEquals(p3.coefficient(3), 3.6));
            assertTrue(approxEquals(p3.coefficient(2), 1.8));
            assertTrue(approxEquals(p3.coefficient(0), -1.8));
            totalScore += 5;
            success("addTest1");
        } catch (Exception e) {
            failure("addTest1", e);
        }
    }

    @Test
    public void addTest2() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(3, 3.6);
            Polynomial p2 = new MockPolynomial();
            Polynomial p3 = p1.add(p2);
            assertEquals(p3.degree(), 3);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(3), 3.6+p2.coefficient(3)));
            assertTrue(approxEquals(p3.coefficient(2), -1.8+p2.coefficient(2)));
            assertTrue(approxEquals(p3.coefficient(1), p2.coefficient(1)));
            assertTrue(approxEquals(p3.coefficient(0), p2.coefficient(0)));
            totalScore += 4;
            success("addTest2");
        } catch (Exception e) {
            failure("addTest2", e);
        }
    }

    @Test
    public void subtractTest1() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(3, 3.6);
            Polynomial p2 = new ListPolynomial();
            p2.addTerm(0, -1.8);
            p2.addTerm(2, 5.4);
            p2.addTerm(3, 3.6);
            Polynomial p3 = p1.subtract(p2);
            assertEquals(p1.toString(), "3.6x^3 - 1.8x^2");
            totalScore += 1;
            assertEquals(p3.degree(), 2);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(3), 0));
            assertTrue(approxEquals(p3.coefficient(2), -7.2));
            assertTrue(approxEquals(p3.coefficient(1), 0));
            assertTrue(approxEquals(p3.coefficient(0), 1.8));
            totalScore += 5;
            success("subtractTest1");
        } catch (Exception e) {
            failure("subtractTest1", e);
        }
    }

    @Test
    public void subtractTest2() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(1, 3.6);
            Polynomial p2 = new MockPolynomial();
            Polynomial p3 = p1.subtract(p2);
            assertEquals(p3.degree(), 3);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(3), -p2.coefficient(3)));
            assertTrue(approxEquals(p3.coefficient(2), -1.8 - p2.coefficient(2)));
            assertTrue(approxEquals(p3.coefficient(1), 3.6 - p2.coefficient(1)));
            assertTrue(approxEquals(p3.coefficient(0), -p2.coefficient(0)));
            totalScore += 4;
            success("subtractTest2");
        } catch (Exception e) {
            failure("subtractTest2", e);
        }
    }

    @Test
    public void multiplyTest1() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(3, 3.6);
            assertEquals(p1.multiply(new ListPolynomial()), new ListPolynomial());
            totalScore += 2;
            Polynomial p2 = new ListPolynomial();
            p2.addTerm(0, -1.8);
            p2.addTerm(2, 5.4);
            p2.addTerm(3, 3.6);
            Polynomial p3 = p1.multiply(p2);
            assertEquals(p1.toString(), "3.6x^3 - 1.8x^2");
            totalScore += 1;
            assertEquals(p3.degree(), 6);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(6), 3.6*3.6));
            assertTrue(approxEquals(p3.coefficient(5), -1.8*3.6+3.6*5.4));
            assertTrue(approxEquals(p3.coefficient(4), -1.8*5.4));
            assertTrue(approxEquals(p3.coefficient(3), -1.8*3.6));
            assertTrue(approxEquals(p3.coefficient(2), -1.8*-1.8));
            assertTrue(approxEquals(p3.coefficient(1), 0));
            assertTrue(approxEquals(p3.coefficient(0), 0));
            totalScore += 7;
            success("multiplyTest1");
        } catch (Exception e) {
            failure("multiplyTest1", e);
        }
    }

    @Test
    public void multiplyTest2() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(1, 3.6);
            assertEquals(p1.multiply(new ListPolynomial()), new ListPolynomial());
            Polynomial p2 = new MockPolynomial();
            Polynomial p3 = p1.multiply(p2);
            assertEquals(p3.degree(), 5);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(5), -1.8*p2.coefficient(3)));
            assertTrue(approxEquals(p3.coefficient(4), 3.6*p2.coefficient(3)));
            assertTrue(approxEquals(p3.coefficient(3), -1.8*p2.coefficient(1)));
            assertTrue(approxEquals(p3.coefficient(2),
                    3.6*p2.coefficient(1)-1.8*p2.coefficient(0)));
            assertTrue(approxEquals(p3.coefficient(1), 3.6*p2.coefficient(0)));
            assertTrue(approxEquals(p3.coefficient(0), 0));
            totalScore += 5;
            success("multiplyTest2");
        } catch (Exception e) {
            failure("multiplyTest2", e);
        }
    }

    @Test
    public void equalsTest() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(5, -1.8);
            p1.addTerm(1, 3.6);
            Polynomial p2 = new ListPolynomial();
            p2.addTerm(5, -1.8);
            p2.addTerm(1, 3.6);
            assertTrue(p1.equals(p2));
            totalScore += 3;
            p1.removeTerm(5);
            p1.addTerm(5, 1.8);
            assertFalse(p1.equals(p2));
            totalScore += 2;
            success("equalsTest1");
        } catch (Exception e) {
            failure("equalsTest1", e);
        }
    }


    @Test
    public void iteratorTest() {
        try {
            Polynomial p1 = new ListPolynomial();
            p1.addTerm(2, 4.5);
            p1.addTerm(0, -5.4);
            p1.addTerm(1, 2.1);
            Iterator<PolynomialTerm> iter = p1.getIterator();
            int index = 0;
            PolynomialTerm [] terms = new PolynomialTerm []{
                    new PolynomialTerm(4.5,2),new PolynomialTerm(2.1,1),
                     new PolynomialTerm(-5.4, 0)
                                      };
            while (iter.hasNext()) {
                assertEquals(iter.next(), terms[index]);
                index++;
            }
            totalScore += 3;
            success("iteratorTest");
        } catch (Exception e) {
            failure("iteratorTest", e);
        }
    }


}

