package org.copters.lab.three.solver;

import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

import java.util.stream.IntStream;

public class LUSolver extends AbstractSolver {
    private final GaussSolver gauss;

    public LUSolver(final double epsilon) {
        super(epsilon);
        this.gauss = new GaussSolver(epsilon);
    }

    @Override
    public Vector solve(final SquareMatrix matrix, final Vector values) {
        decompose(matrix);
        final Vector y = gauss.forwardSolve(matrix, values);
        return gauss.backwardSolve(matrix, y);
    }

    private void decompose(final SquareMatrix matrix) {
        IntStream.range(0, matrix.size()).forEach(i -> {
            IntStream.range(i, matrix.size()).forEach(j -> {
                final double sum = IntStream.range(0, i)
                        .mapToDouble(k -> matrix.get(i, k) * matrix.get(k, j))
                        .sum();
                matrix.set(i, j, matrix.get(i, j) - sum);
            });

            IntStream.range(i + 1, matrix.size()).forEach(j -> {
                final double sum = IntStream.range(0, i)
                        .mapToDouble(k -> matrix.get(j, k) * matrix.get(k, i))
                        .sum();
                matrix.set(j, i, (matrix.get(j, i) - sum) / matrix.get(i, i));
            });
        });
    }
}
