package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

public class ConjugateGradientMinimizer extends AbstractGradientMinimizer {

    public ConjugateGradientMinimizer(final double epsilon) {
        super(epsilon);
    }

    @Override
    protected Tuple<Vector, Double> next(final Tuple<Vector, Double> x,
                                         final Vector gradient,
                                         final QuadraticFunction function) {
        return null;
    }

    @Override
    public String getRussianName() {
        return "Метод сопряженных градиентов";
    }
}
