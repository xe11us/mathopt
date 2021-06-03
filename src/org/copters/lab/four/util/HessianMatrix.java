package org.copters.lab.four.util;

import org.copters.lab.three.util.DenseMatrix;
import org.copters.lab.three.util.Vector;

import java.util.stream.IntStream;

public class HessianMatrix extends DenseMatrix {
    public static final double STEP = 1e-5;

    public HessianMatrix(final Function function, final Vector vector) {
        this(function, vector, STEP);
    }

    public HessianMatrix(final Function function, final Vector vector, final double step) {
        super(calculate(function, vector, step));
    }

    private static double[][] calculate(final Function function, final Vector vector, final double step) {
        final int size = vector.size();
        final double value = function.applyAsDouble(vector);

        final double[][] hessian = new double[size][size];
        final double[] plus = IntStream.range(0, size)
                .mapToObj(i -> vector.add(Vector.unit(size, i).multiply(step)))
                .mapToDouble(function)
                .toArray();

        for (int i = 0; i < size; i++) {
            final Vector unitI = Vector.unit(size, i);
            for (int j = i; j < size; j++) {
                final Vector unitJ = Vector.unit(size, j);
                final double x = function.applyAsDouble(vector.add(unitI.add(unitJ).multiply(step)));
                hessian[i][j] = (x - plus[i] - plus[j] + value) / (step * step);
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < i; j++) {
                hessian[i][j] = hessian[j][i];
            }
        }
        return hessian;
    }
}
