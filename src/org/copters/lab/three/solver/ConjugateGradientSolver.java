package org.copters.lab.three.solver;

import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

public class ConjugateGradientSolver implements Solver {
    private static final double EPS = 1e-7;
    private static final int ITERATIONS = 100000;
    private Vector xPrev;

    public ConjugateGradientSolver(final Vector x0) {
        this.xPrev = x0;
    }

    @Override
    public Vector solve(final SquareMatrix matrix, final Vector values) {
        Vector rPrev = values.subtract(matrix.multiply(xPrev));
        Vector zPrev = rPrev;
        Vector x = xPrev;

        double rSqr = rPrev.dot(rPrev);

        int iteration = 0;
        while (rPrev.length() > EPS * values.length() && iteration++ < ITERATIONS) {
            final double alpha = rSqr / matrix.multiply(zPrev).dot(zPrev);
            x = x.add(zPrev.multiply(alpha));
            rPrev = rPrev.subtract(matrix.multiply(zPrev).multiply(alpha));
            final double beta = rPrev.dot(rPrev) / rSqr;
            zPrev = rPrev.add(zPrev.multiply(beta));
            rSqr = rPrev.dot(rPrev);
        }

        return x;
    }
}
