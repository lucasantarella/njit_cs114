package edu.njit.cs114;

import java.util.Iterator;

/**
 * Author: Ravi Varadarajan
 * Date created: 1/24/20
 */
public interface Polynomial {

    /**
     * Returns coefficient of the term with 'power'
     * @param power
     * @return
     */
    public double coefficient(int power);

    /**
     * Returns the degree of polynomial (i.e. largest power of a non-zero term)
     * @return
     */
    public int degree();

    /**
     * Add a term to the polynomial
     * @param power
     * @param coefficient
     * @throws Exception when the power is invalid (e.g. negative)
     */
    public void addTerm(int power, double coefficient) throws Exception;

    /**
     * Remove the term corresponding to the power from the polynomial
     * @param power
     */
    public PolynomialTerm removeTerm(int power);

    /**
     * Returns evaluation of the polynomial at 'point'
     * @param point
     * @return
     */
    public double evaluate(double point);

    /**
     * Returns a new polynomial that is the result of addition of polynomial p to this polynomial
     * @param p
     * @return
     */
    public Polynomial add(Polynomial p);

    /**
     * Returns a new polynomial that is the result of subtraction of polynomial p to this polynomial
     * @param p
     * @return
     */
    public Polynomial subtract(Polynomial p);

    /**
     * Returns a new polynomial that is the result of multiplication of polynomial p with this polynomial
     * @param p
     * @return
     */
    public Polynomial multiply(Polynomial p);

    /**
     * Get polynomial terms with non-zero coefficients in decreasing order of power
     * @return
     */
    public Iterator<PolynomialTerm> getIterator();

}
