package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

public class GradientDescentMinimizer extends AbstractGradientMinimizer {

    protected GradientDescentMinimizer(final double epsilon) {
        super(epsilon);
    }

    @Override
    protected Tuple<Vector, Double> next(final Tuple<Vector, Double> x,
                                         final Vector gradient,
                                         final QuadraticFunction function) {
        final double length = gradient.length();
        for (double alpha = 1.; alpha > 0; alpha /= 2) {
            final Vector tmp = x.getFirst().subtract(gradient.multiply(alpha / length));
            final Tuple<Vector, Double> y = Tuple.of(tmp, function.applyAsDouble(tmp));

            if (y.getSecond() < x.getSecond()) {
                return y;
            }
        }
        throw new IllegalStateException("Alpha reached zero");
    }

    @Override
    public String getRussianName() {
        return "Метод градиентного спуска";
    }
}
