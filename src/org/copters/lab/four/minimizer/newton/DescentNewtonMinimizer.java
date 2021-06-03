package org.copters.lab.four.minimizer.newton;

import org.copters.lab.one.minimizer.Minimizer;
import org.copters.lab.three.solver.Solver;
import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

import java.util.function.DoubleFunction;

public class DescentNewtonMinimizer extends SingleNewtonMinimizer {

    public DescentNewtonMinimizer(final double epsilon, final Minimizer singleMinimizer) {
        super(epsilon, singleMinimizer);
    }

    public DescentNewtonMinimizer(final double epsilon, final Minimizer singleMinimizer,
                                  final DoubleFunction<? extends Solver> solverFactory) {
        super(epsilon, singleMinimizer, solverFactory);
    }

    @Override
    protected Vector step(final SquareMatrix hessian, final Vector antiGradient) {
        final Vector p = super.step(hessian, antiGradient);
        return p.dot(antiGradient) > 0 ? p : antiGradient;

    }

    @Override
    public String getRussianName() {
        return String.format("Метод Ньютона с направлением спуска (%s)", singleMinimizer.getRussianName());
    }
}
