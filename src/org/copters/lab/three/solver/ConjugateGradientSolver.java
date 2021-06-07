package org.copters.lab.three.solver;

import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

public class ConjugateGradientSolver implements Solver {
    private final double EPS = 1e-7;
    private final int ITERATIONS = 100000;
    private Vector xPrev;

    public ConjugateGradientSolver(Vector x0) {
        this.xPrev = x0;
    }

    @Override
    public Vector solve(SquareMatrix matrix, Vector values) {
        Vector rPrev = values.subtract(matrix.multiply(xPrev));
        Vector zPrev = rPrev;
        double rSqr = rPrev.dot(rPrev);

        int iteration = 0;
        while (rPrev.length() / values.length() > EPS && iteration++ < ITERATIONS) {
            double alpha = rSqr / matrix.multiply(zPrev).dot(zPrev);
            xPrev = xPrev.add(zPrev.multiply(alpha));
            rPrev = rPrev.subtract(matrix.multiply(zPrev).multiply(alpha));
            double beta = rPrev.dot(rPrev) / rSqr;
            zPrev = rPrev.add(zPrev.multiply(beta));
            rSqr = rPrev.dot(rPrev);
        }

        return xPrev;
    }
}
