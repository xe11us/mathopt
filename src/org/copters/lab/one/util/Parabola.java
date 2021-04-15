package org.copters.lab.one.util;

public class Parabola {
    private final double a0;
    private final double a1;
    private final double a2;

    private final double xMin;

    public Parabola(final double x1, final double x2, final double x3, final double f1, final double f2, final double f3) {
        this.a0 = f1;
        this.a1 = (f2 - f1) / (x2 - x1);
        this.a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);

        this.xMin = a2 == 0 ? (f1 < f3 ? x1 : x2) : 0.5 * (x1 + x2 - a1 / a2);
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