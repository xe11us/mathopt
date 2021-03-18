package org.copters.lab.one.util;

public class Parabola {
    private final double a0;
    private final double a1;
    private final double a2;

    private final double xMin;

    public Parabola(double x1, double x2, double x3, double f1, double f2, double f3) {
        this.a0 = f1;
        this.a1 = (f2 - f1) / (x2 - x1);
        this.a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);

        this.xMin = 0.5 * (x1 + x2 - a1 / a2);
    }

    public double getA0() {
        return a0;
    }

    public double getA1() {
        return a1;
    }

    public double getA2() {
        return a2;
    }

    public double getXMin() {
        return xMin;
    }
}