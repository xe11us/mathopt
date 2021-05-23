package org.copters.lab.three.solver;

import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.StreamUtils;
import org.copters.lab.three.util.Vector;

import java.util.stream.IntStream;

public class GaussSolver extends AbstractSolver {
    public GaussSolver(final double epsilon) {
        super(epsilon);
    }

    @Override
    public Vector solve(final SquareMatrix matrix, final Vector values) {
        final int[] permutation = IntStream.range(0, matrix.size())
                .toArray();

        for (int k = 0; k < matrix.size() - 1; k++) {
            if (!swapWithMax(matrix, k, permutation)) {
                return null;
            }

            for (int i = k + 1; i < matrix.size(); i++) {
                final double t = matrix.get(permutation[i], k) / matrix.get(permutation[k], k);
                values.set(permutation[i], values.get(permutation[i]) - t * values.get(permutation[k]));
                for (int j = k + 1; j < matrix.size(); j++) {
                    matrix.set(permutation[i], j, matrix.get(permutation[i], j) - t * matrix.get(permutation[k], j));
                }
                matrix.set(permutation[i], k, 0);
            }
        }
        return backwardSolve(matrix, values, permutation);
    }

    private boolean swapWithMax(final SquareMatrix matrix, final int k, final int[] permutation) {
        int m = k;
        double amk = Math.abs(matrix.get(permutation[k], k));
        for (int i = m + 1; i < matrix.size(); i++) {
            final double x = Math.abs(matrix.get(permutation[i], k));
            if (x > amk) {
                amk = x;
                m = i;
            }
        }
        if (amk < epsilon) {
            return false;
        }
        final int tmp = permutation[k];
        permutation[k] = permutation[m];
        permutation[m] = tmp;
        return true;
    }

    public Vector forwardSolve(final SquareMatrix l, final Vector values) {
        final Vector x = Vector.ofZeros(l.size());
        IntStream.range(0, l.size()).forEach(i -> {
            final double sum = IntStream.range(0, i)
                    .mapToDouble(j -> x.get(j) * l.get(i, j))
                    .sum();
            x.set(i, values.get(i) - sum);
        });
        return x;
    }

    public Vector backwardSolve(final SquareMatrix u, final Vector values) {
        return backwardSolve(u, values, IntStream.range(0, values.size()).toArray());
    }

    private Vector backwardSolve(final SquareMatrix u, final Vector values, final int[] permutation) {
        final Vector x = Vector.ofZeros(u.size());
        StreamUtils.reversedRange(0, u.size()).forEach(i -> {
            final double sum = IntStream.range(i + 1, u.size())
                    .mapToDouble(j -> u.get(permutation[i], j) * x.get(j))
                    .sum();
            x.set(i, (values.get(permutation[i]) - sum) / u.get(permutation[i], i));
        });
        return x;
    }


}
