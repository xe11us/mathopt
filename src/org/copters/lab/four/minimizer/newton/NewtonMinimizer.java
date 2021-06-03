package org.copters.lab.four.minimizer.newton;

import org.copters.lab.four.minimizer.MultiMinimizer;
import org.copters.lab.four.util.Function;
import org.copters.lab.four.util.Gradient;
import org.copters.lab.four.util.HessianMatrix;
import org.copters.lab.three.solver.GaussSolver;
import org.copters.lab.three.solver.Solver;
import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

import java.util.function.DoubleFunction;

public class NewtonMinimizer implements MultiMinimizer {
    protected final double epsilon;
    protected final Solver solver;

    public NewtonMinimizer(final double epsilon) {
        this(epsilon, GaussSolver::new);
    }

    public NewtonMinimizer(final double epsilon, final DoubleFunction<? extends Solver> solverFactory) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon must be greater than 0");
        }
        this.epsilon = epsilon;
        this.solver = solverFactory.apply(epsilon);
    }

    protected Vector step(final SquareMatrix hessian, final Vector antiGradient) {
        return solver.solve(hessian, antiGradient);
    }

    protected boolean hasNext(final Vector diff) {
        return diff.length() >= epsilon;
    }

    protected double getAlpha(final Function function, final Vector x, final Vector p) {
        return 1;
    }

    @Override
    public final Vector minimize(final Function function, final Vector x0) {
        Vector prev;
        Vector x = x0;

        do {
            prev = x;
            final Vector antiGradient = new Gradient(function, x).negate();
            final SquareMatrix hessian = new HessianMatrix(function, x);
            final Vector p = step(hessian, antiGradient);
            final double alpha = getAlpha(function, x, p);
            x = x.add(p.multiply(alpha));
        } while (hasNext(x.subtract(prev)));

        return x;
    }

    @Override
    public String getRussianName() {
        return "Метод Ньютона";
    }
}
