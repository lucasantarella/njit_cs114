package edu.njit.cs114;

/**
 * Author: Luca Santarella
 * Date created: 1/30/20
 */
public class ArrayPolynomial implements Polynomial {

    // maximum degree of a polynomial
    public static final int MAX_DEGREE = 100;

    // coefficient array
    private double [] coefficients = new double[MAX_DEGREE+1];

    // Default constructor
    public ArrayPolynomial() {}

    /**
     * Create a single term polynomial
     * @param power
     * @param coefficient
     * @throws Exception when power is negative or exceeds MAX_DEGREE
     */
    public ArrayPolynomial(int power, double coefficient) throws Exception {
        if (power < 0 || power > MAX_DEGREE) {
            throw new Exception("Invalid power for the term");
        }

        this.coefficients[power] = coefficient;
    }

    /**
     * Create a new polynomial that is a copy of "another".
     * NOTE : you should use only the interface methods of Polynomial to get
     * the coefficients of "another"
     *
     * @param another
     * @throws Exception when degree of another exceeds MAX_DEGREE
     */
    public ArrayPolynomial(Polynomial another) throws Exception {
        if (another.degree() > MAX_DEGREE) {
            throw new Exception("Invalid power for the term");
        }

        for (int i = 0; i <= another.degree(); i++)
            this.addTerm(i, another.coefficient(i));
    }

    @Override
    public void addTerm(int power, double coefficient) throws Exception {
        if (power < 0 || power > MAX_DEGREE) {
            throw new Exception("Invalid power for the term");
        }

        this.coefficients[power] += coefficient;
    }

    @Override
    public void removeTerm(int power) {
        if (power < 0 || power > MAX_DEGREE)
            return;

        this.coefficients[power] = 0;
    }

    @Override
    public double coefficient(int power) {
        if (power < 0 || power > MAX_DEGREE) {
            return 0;
        } else return this.coefficients[power];
    }

    @Override
    public int degree() {
        int highestDegree = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            if (this.coefficient(i) > 0)
                highestDegree = i;
        }
        return highestDegree;
    }

    @Override
    public double evaluate(double point) {
        double value = 0.0;
        /**
         * Complete code here for homework
         */
        return value;
    }

    @Override
    public Polynomial add(Polynomial p) {
        Polynomial result = new ArrayPolynomial();
        /**
         * Complete code here for homework
         */
        return result;
    }

    @Override
    public Polynomial subtract(Polynomial p) {
        Polynomial result = new ArrayPolynomial();
        /**
         * Complete code here for homework
         */
        return result;
    }

    @Override
    public Polynomial multiply(Polynomial p) {
        Polynomial result = new ArrayPolynomial();
        /**
         * Complete code here for homework
         */
        return result;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        int degree = degree();
        if (degree == 0) {
            return "" + coefficients[0];
        }
        for (int i=degree; i >=0; i--) {
            if (coefficients[i] == 0) {
                continue;
            }
            if (i < degree) {
                builder.append(coefficients[i] > 0 ? " + " : " - ");
                builder.append(Math.abs(coefficients[i]));
            } else {
                builder.append(coefficients[i]);
            }
            if (i > 0) {
                builder.append("x");
                if (i > 1) {
                    builder.append("^" + i);
                }
            }
        }
        return builder.toString();
    }

    /**
     * Returns true if the object obj is a Polynomial and its coefficients are the same
     *   for all the terms in the polynomial
     * @param obj
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        Polynomial other = (Polynomial) obj;

        if (other.degree() != this.degree())
            return false;

        for (int i = 0; i <= this.degree(); i++) {
            if (this.coefficient(i) != other.coefficient(i))
                return false;
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        // Remove comments in code after you have implemented all the functions in
        // homework assignment
        Polynomial p1 = new ArrayPolynomial();
        System.out.println("p1(x) = " + p1);
        assert p1.degree() == 0;
        assert p1.coefficient(0) == 0;
        assert p1.coefficient(2) == 0;
        assert p1.equals(new ArrayPolynomial());
        Polynomial p2 = new ArrayPolynomial(4, 5.6);
        p2.addTerm(0,3.1);
        p2.addTerm(3,2.5);
        p2.addTerm(2,-2.5);
        System.out.println("p2(x) = " + p2);
        assert p2.degree() == 4;
        assert p2.coefficient(2) == -2.5;
        assert p2.toString().equals("5.6x^4 + 2.5x^3 - 2.5x^2 + 3.1");
//        System.out.println("p2(1) = " + p2.evaluate(1));
//        assert p2.evaluate(1) == 8.7;
        Polynomial p3 = new ArrayPolynomial(0, -4);
        p3.addTerm(5,3);
        p3.addTerm(5,-1);
        System.out.println("p3(x) = " + p3);
        assert p3.degree() == 5;
        assert p3.coefficient(5) == 2;
        assert p3.coefficient(0) == -4;
//        System.out.println("p3(2) = " + p3.evaluate(2));
//        assert p3.evaluate(2) == 60;
        Polynomial p21 = new ArrayPolynomial(p2);
        System.out.println("p21(x) = " + p21);
        assert p21.equals(p2);
//        p21.removeTerm(4);
//        System.out.println("p21(x) = " + p21);
//        assert !p21.equals(p2);
//        assert p21.coefficient(4) == 0;
        try {
            Polynomial p5 = new ArrayPolynomial(-5, 4);
            assert false;
        } catch (Exception e) {
            // Exception expected
            assert true;
        }
//        System.out.println("p2(x) + p3(x) = " + p2.add(p3));
//        Polynomial result = p2.add(p3);
//        assert result.degree() == 5;
//        assert Math.abs(result.coefficient(5) - 2) <= 0.0001;;
//        System.out.println("p2(x) - p3(x) = " +p2.subtract(p3));
//        result = p2.subtract(p3);
//        assert result.degree() == 5;
//        assert Math.abs(result.coefficient(5) - -2) <= 0.0001;
//        assert Math.abs(result.coefficient(0) - 7.1) <= 0.0001;
//        System.out.println("p2(x) * p3(x) = " +p2.multiply(p3));
//        result = p2.multiply(p3);
//        assert result.degree() == 9;
//        assert Math.abs(result.coefficient(9) - 11.2) <= 0.0001;
//        assert Math.abs(result.coefficient(5) - 6.2) <= 0.0001;
//        assert Math.abs(result.coefficient(0) - -12.4) <= 0.0001;
//        assert Math.abs(p2.evaluate(1) * p3.evaluate(1) - result.evaluate(1)) <= 0.0001;

    }
}
