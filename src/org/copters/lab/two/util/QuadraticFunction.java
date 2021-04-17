package org.copters.lab.two.util;

import java.util.function.ToDoubleFunction;

public class QuadraticFunction implements ToDoubleFunction<Vector> {
    private final Matrix a;
    private final Vector b;
    private final double c;

    public QuadraticFunction(final Matrix a, final Vector b, final double c) {
        this.a = a.symmetrized();
        this.b = b;
        this.c = c;
    }

    @Override
    public double applyAsDouble(final Vector x) {
        return 0.5 * a.multiply(x).dot(x) + b.dot(x) + c;
    }

    public int getDimension() {
        return b.getDimension();
    }

    public Vector getGradient(final Vector x) {
        return a.multiply(x).add(b);
    }

    public Matrix getA() {
        return a;
    }
}
