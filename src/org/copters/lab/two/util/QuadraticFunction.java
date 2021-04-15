package org.copters.lab.two.util;

import java.util.function.ToDoubleFunction;

public class QuadraticFunction implements ToDoubleFunction<Vector> {
    private final Matrix a;
    private final Vector b;
    private final double c;

    public QuadraticFunction(final Matrix a, final Vector b, final double c) {
        final Tuple<Integer, Integer> size = a.getDimensions();
        final double[][] symmetric = new double[size.getFirst()][size.getSecond()];

        for (int i = 0; i < size.getFirst(); ++i) {
            for (int j = 0; j < size.getSecond(); ++j) {
                symmetric[i][j] = a.get(i, j) + a.get(j, i);
            }
        }

        this.a = Matrix.of(symmetric);
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
}
