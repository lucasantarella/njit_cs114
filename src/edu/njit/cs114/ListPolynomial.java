package edu.njit.cs114;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Ravi Varadarajan
 * Date created: 2/25/20
 */
public class ListPolynomial extends AbstractPolynomial {

    /**
     * TO be completed : Initialize the list
     */
    private List<PolynomialTerm> termList;

    private class ListPolyIterator implements Iterator<PolynomialTerm> {

        private Iterator<PolynomialTerm> iterator = termList.iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public PolynomialTerm next() {
            return iterator.next();
        }

    }

    // Default constructor
    public ListPolynomial() {
        this.termList = new LinkedList<>();
    }

    /**
     * Create a single term polynomial
     *
     * @param power
     * @param coefficient
     * @throws Exception
     */
    public ListPolynomial(int power, double coefficient) throws Exception {
        if (power < 0) {
            throw new Exception("Invalid power for the term");
        }
        this.termList = new LinkedList<>();
        PolynomialTerm term = new PolynomialTerm(coefficient, power);
        term.setLeadingTerm(true);
        this.termList.add(term);
    }

    /**
     * Create a new polynomial that is a copy of "another".
     * NOTE : you should use only the interface methods of Polynomial
     *
     * @param another
     * @throws Exception
     */
    public ListPolynomial(Polynomial another) throws Exception {
        this.termList = new LinkedList<>();
        Iterator<PolynomialTerm> iter = another.getIterator();
        while (iter.hasNext()) {
            PolynomialTerm term = iter.next();
            this.termList.add(new PolynomialTerm(term.getCoefficient(), term.getPower()));
        }
    }


    /**
     * Returns coefficient of power
     *
     * @param power
     * @return
     */
    @Override
    public double coefficient(int power) {
        Iterator<PolynomialTerm> iter = this.getIterator();
        while (iter.hasNext()) {
            PolynomialTerm term = iter.next();
            if (term.getPower() == power)
                return term.getCoefficient();
        }
        return 0;
    }

    /**
     * Returns degree of the polynomial
     *
     * @return
     */
    @Override
    public int degree() {
        if (this.termList.isEmpty())
            return 0;
        else
            return this.termList.get(0).getPower();
    }

    /**
     * Adds polynomial term; add to existing term if power already exists
     *
     * @param power
     * @param coefficient
     * @throws Exception if power < 0
     */
    @Override
    public void addTerm(int power, double coefficient) throws Exception {
        if (power < 0)
            throw new Exception("Invalid power for the term");

        if (power > this.degree()) {
            // Just add the term to the beginning
            PolynomialTerm term = new PolynomialTerm(coefficient, power);
            term.setLeadingTerm(true);
            if (this.termList.size() > 0)
                termList.get(0).setLeadingTerm(false); // Set the current leading term to no longer leading
            termList.add(0, term);
        } else {
            Iterator<PolynomialTerm> iter = this.getIterator();
            while (iter.hasNext()) {
                PolynomialTerm term = iter.next();
                if (term.getPower() == power) {
                    double newCoefficient = term.getCoefficient() + coefficient;
                    if (newCoefficient != 0)
                        termList.set(termList.indexOf(term), new PolynomialTerm(newCoefficient, power));
                    else
                        termList.remove(term); // If the sum of the new coefficients is 0, then it's no longer a term
                    return;
                }

                if (power > term.getPower()) {
                    // Insert before
                    termList.add(termList.indexOf(term), new PolynomialTerm(coefficient, power));
                    return;
                }
            }

            // No term found, so we add it in at the end
            termList.add(new PolynomialTerm(coefficient, power));
        }
    }

    /**
     * Remove and return the term for the specified power
     *
     * @param power null if power has zero coefficient
     * @return
     */
    @Override
    public PolynomialTerm removeTerm(int power) {
        Iterator<PolynomialTerm> iter = this.getIterator();
        while (iter.hasNext()) {
            PolynomialTerm term = iter.next();
            if (term.getPower() == power) {
                termList.remove(term);
                return term;
            }
        }
        return null;
    }

    /**
     * Evaluate polynomial at point
     *
     * @param point
     * @return
     */
    @Override
    public double evaluate(double point) {
        double value = 0.0;
        // Iterate over all the coefficients from the highest degree
        Iterator<PolynomialTerm> iter = this.getIterator();
        while (iter.hasNext()) {
            PolynomialTerm term = iter.next();
            // value += c (x ^ p)
            value += Math.pow(point, term.getPower()) * term.getCoefficient();
        }
        return value;
    }

    /**
     * Add polynomial p to this polynomial and return the result
     *
     * @param p
     * @return
     */
    @Override
    public Polynomial add(Polynomial p) {
        // Setup result variable
        Polynomial result = null;
        try {
            result = new ListPolynomial(this);

            // Iterate over all the terms from the highest degree
            Iterator<PolynomialTerm> iter = p.getIterator();
            while (iter.hasNext()) {
                PolynomialTerm term = iter.next();
                result.addTerm(term.getPower(), term.getCoefficient());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return result
        return result;
    }

    /**
     * Substract polynomial p from this polynomial and return the result
     *
     * @param p
     * @return
     */
    @Override
    public Polynomial subtract(Polynomial p) {
        // Setup result variable
        Polynomial result = null;
        try {
            result = new ListPolynomial(this);

            // Iterate over all the terms from the highest degree
            Iterator<PolynomialTerm> iter = p.getIterator();
            while (iter.hasNext()) {
                PolynomialTerm term = iter.next();
                result.addTerm(term.getPower(), (-1) * term.getCoefficient());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return result
        return result;
    }

    /**
     * Multiply polynomial p with this polynomial and return the result
     *
     * @param p
     * @return
     */
    @Override
    public Polynomial multiply(Polynomial p) {
        // Setup result variable
        Polynomial result = new ListPolynomial();

        // Iterate over all the terms from the highest degree
        Iterator<PolynomialTerm> iter = this.getIterator();
        while (iter.hasNext()) {
            PolynomialTerm term = iter.next();
            // Iterate over all the terms of the other polynomial
            Iterator<PolynomialTerm> otherIter = p.getIterator();
            while (otherIter.hasNext()) {
                PolynomialTerm otherTerm = otherIter.next();
                // value += c (x ^ p)
                try {
                    result.addTerm(term.getPower() + otherTerm.getPower(), (term.getCoefficient() * otherTerm.getCoefficient()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // return result
        return result;
    }

    @Override
    public Iterator<PolynomialTerm> getIterator() {
        return new ListPolyIterator();
    }

    public static void main(String[] args) throws Exception {
        /** Uncomment after you have implemented all the functions */
        Polynomial p1 = new ListPolynomial();
        System.out.println("p1(x) = " + p1);
        assert p1.degree() == 0;
        assert p1.coefficient(0) == 0;
        assert p1.coefficient(2) == 0;
        assert p1.equals(new ListPolynomial());
        Polynomial p2 = new ListPolynomial(4, 5.6);
        p2.addTerm(0, 3.1);
        p2.addTerm(3, 2.5);
        p2.addTerm(2, -2.5);
        System.out.println("p2(x) = " + p2);
        assert p2.degree() == 4;
        assert p2.coefficient(2) == -2.5;
        assert p2.toString().equals("5.6x^4 + 2.5x^3 - 2.5x^2 + 3.1");
        System.out.println("p2(1) = " + p2.evaluate(1));
        assert p2.evaluate(1) == 8.7;
        Polynomial p3 = new ListPolynomial(0, -4);
        p3.addTerm(5, 3);
        p3.addTerm(5, -1);
        System.out.println("p3(x) = " + p3);
        assert p3.degree() == 5;
        assert p3.coefficient(5) == 2;
        assert p3.coefficient(0) == -4;
        System.out.println("p3(2) = " + p3.evaluate(2));
        assert p3.evaluate(2) == 60;
        Polynomial p21 = new ListPolynomial(p2);
        System.out.println("p21(x) = " + p21);
        assert p21.equals(p2);
        p21.removeTerm(4);
        System.out.println("p21(x) = " + p21);
        assert !p21.equals(p2);
        assert p21.coefficient(4) == 0;
        System.out.println("p2(x) = " + p2);
        try {
            Polynomial p5 = new ListPolynomial(-5, 4);
            assert false;
        } catch (Exception e) {
            // Exception expected
            assert true;
        }
        System.out.println("p2(x) + p3(x) = " + p2.add(p3));
        Polynomial result = p2.add(p3);
        assert result.degree() == 5;
        assert Math.abs(result.coefficient(5) - 2) <= 0.0001;
        ;
        System.out.println("p2(x) - p3(x) = " + p2.subtract(p3));
        result = p2.subtract(p3);
        assert result.degree() == 5;
        assert Math.abs(result.coefficient(5) - -2) <= 0.0001;
        assert Math.abs(result.coefficient(0) - 7.1) <= 0.0001;
        System.out.println("p2(x) * p3(x) = " + p2.multiply(p3));
        result = p2.multiply(p3);
        assert result.degree() == 9;
        assert Math.abs(result.coefficient(9) - 11.2) <= 0.0001;
        assert Math.abs(result.coefficient(5) - 6.2) <= 0.0001;
        assert Math.abs(result.coefficient(0) - -12.4) <= 0.0001;
        assert Math.abs(p2.evaluate(1) * p3.evaluate(1) - result.evaluate(1)) <= 0.0001;
    }
}
