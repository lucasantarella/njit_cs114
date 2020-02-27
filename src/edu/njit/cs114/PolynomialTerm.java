package edu.njit.cs114;

/**
 * Author: Ravi Varadarajan
 * Date created: 1/24/20
 */
public class PolynomialTerm {

    private final int power;
    private final double coefficient;

    private boolean leadingTerm;

    public PolynomialTerm(double coefficient, int power) {
        this.coefficient = coefficient;
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public boolean isLeadingTerm() {
        return leadingTerm;
    }

    public void setLeadingTerm(boolean leadingTerm) {
        this.leadingTerm = leadingTerm;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (coefficient < 0) {
            builder.append(isLeadingTerm() ? "-" : " - ");
        }
        builder.append(Math.abs(coefficient));
        if (power > 0) {
            builder.append("x");
            if (power > 1) {
                builder.append("^" + power);
            }
        }
        return builder.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PolynomialTerm)) {
            return false;
        }
        PolynomialTerm other = (PolynomialTerm) obj;
        return (this.power == other.power && this.coefficient == other.coefficient);
    }
}
