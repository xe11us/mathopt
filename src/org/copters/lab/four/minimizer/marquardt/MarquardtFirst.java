package org.copters.lab.four.minimizer.marquardt;

import org.copters.lab.four.minimizer.MultiMinimizer;
import org.copters.lab.four.util.Function;
import org.copters.lab.four.util.Gradient;
import org.copters.lab.four.util.HessianMatrix;
import org.copters.lab.three.solver.ConjugateGradientSolver;
import org.copters.lab.three.solver.Solver;
import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

public class MarquardtFirst implements MultiMinimizer {
    private final double epsilon;
    private final double tau;
    private final double beta;

    public MarquardtFirst(final double tau, final double beta, final double epsilon) {
        this.tau = tau;
        this.beta = beta;
        this.epsilon = epsilon;
    }

    @Override
    public Vector minimize(Function function, Vector x0) {
        Solver solver = new ConjugateGradientSolver(x0);
        Vector x = x0;
        Vector p;
        double tau = this.tau;
        double fx = function.applyAsDouble(x);

        do {
            final Vector antiGradient = new Gradient(function, x).negate();
            final SquareMatrix hessian = new HessianMatrix(function, x);
            Vector y;
            double fy;
            do {
                tau /= beta;
                SquareMatrix tauI = SquareMatrix.identity(hessian.size()).multiply(tau);
                SquareMatrix matrix = hessian.add(tauI);
                p = solver.solve(matrix, antiGradient);
                y = x.add(p);
                fy = function.applyAsDouble(y);
            } while (fy - fx > epsilon);
            x = y;
            fx = fy;
            tau *= beta;
        } while (p.length() > epsilon);

        return x;
    }

    @Override
    public String getRussianName() {
        return "Метод Марквардта (первая версия)";
    }
}