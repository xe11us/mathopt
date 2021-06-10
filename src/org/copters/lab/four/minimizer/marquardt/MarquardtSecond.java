package org.copters.lab.four.minimizer.marquardt;

import org.copters.lab.four.minimizer.MultiMinimizer;
import org.copters.lab.four.util.Function;
import org.copters.lab.four.util.Gradient;
import org.copters.lab.four.util.HessianMatrix;
import org.copters.lab.three.solver.ConjugateGradientSolver;
import org.copters.lab.three.solver.Solver;
import org.copters.lab.three.util.*;

public class MarquardtSecond implements MultiMinimizer {
    private final double epsilon;
    private final double tau;

    public MarquardtSecond(final double epsilon) {
        this.epsilon = epsilon;
        this.tau = 0;
    }

    private boolean isPositive(SquareMatrix matrix) {
        double[][] answer = new double[matrix.size()][matrix.size()];

        for (int i = 1; i < matrix.size(); i++) {
            for (int j = 1; j < i; j++) {
                double sum = 0;
                for (int p = 1; p < i; p++) {
                    sum += answer[i][p] * answer[j][p];
                }
                if (Math.abs(answer[j][j]) < epsilon) {
                    return false;
                }
                answer[i][j] = (matrix.get(i, j) - sum) / answer[j][j];
            }

            double sum = 0;
            for (int p = 1; p < i; p++) {
                sum += answer[i][p] * answer[i][p];
            }
            sum = matrix.get(i, i) - sum;
            if (sum < 0) {
                return false;
            }
            answer[i][i] = Math.sqrt(sum);
        }
        return true;
    }

    @Override
    public Vector minimize(Function function, Vector x0) {
        Solver solver = new ConjugateGradientSolver(x0);
        Vector x = x0;
        Vector p;
        double tau = this.tau;

        do {
            final Vector antiGradient = new Gradient(function, x).negate();
            final SquareMatrix hessian = new HessianMatrix(function, x);

            while (true) {
                SquareMatrix tauI = SquareMatrix.identity(hessian.size()).multiply(tau);
                SquareMatrix matrix = hessian.add(tauI);

                if (!isPositive(matrix)) {
                    tau = Math.max(1., 2 * tau);
                    continue;
                }
                p = solver.solve(matrix, antiGradient);
                x = x.add(p);
                break;
            }
        } while (p.length() > epsilon);

        return x;
    }

    @Override
    public String getRussianName() {
        return "Метод Марквардта (вторая версия)";
    }
}