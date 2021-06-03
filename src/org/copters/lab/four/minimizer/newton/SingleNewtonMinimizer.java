package org.copters.lab.four.minimizer.newton;

import org.copters.lab.four.util.Function;
import org.copters.lab.one.minimizer.Minimizer;
import org.copters.lab.three.solver.Solver;
import org.copters.lab.three.util.Vector;

import java.util.function.DoubleFunction;

public class SingleNewtonMinimizer extends NewtonMinimizer {
    protected final Minimizer singleMinimizer;

    public SingleNewtonMinimizer(final double epsilon, final Minimizer singleMinimizer) {
        super(epsilon);
        this.singleMinimizer = singleMinimizer;
    }

    public SingleNewtonMinimizer(final double epsilon, final Minimizer singleMinimizer,
                                 final DoubleFunction<? extends Solver> solverFactory) {
        super(epsilon, solverFactory);
        this.singleMinimizer = singleMinimizer;
    }

    @Override
    protected double getAlpha(final Function function, final Vector x, final Vector p) {
        return singleMinimizer.minimize(alpha ->
            function.applyAsDouble(x.add(p.multiply(alpha))));
    }

    @Override
    public String getRussianName() {
        return String.format("Метод Ньютона с одномерным поиском (%s)", singleMinimizer.getRussianName());
    }
}
