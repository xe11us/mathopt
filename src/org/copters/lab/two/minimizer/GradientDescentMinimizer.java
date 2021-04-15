package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

public class GradientDescentMinimizer extends AbstractGradientMinimizer {
    private static final double INITIAL_ALPHA = 1.;

    public GradientDescentMinimizer(final double epsilon) {
        super(epsilon);
    }

    @Override
    protected Tuple<Vector, Double> next(final Tuple<Vector, Double> x,
                                         final Vector gradient,
                                         final QuadraticFunction function) {
        final double length = gradient.length();
        for (double alpha = INITIAL_ALPHA; alpha > 0; alpha /= 2) {
            final Vector v = x.getFirst().subtract(gradient.multiply(alpha / length));
            final Tuple<Vector, Double> y = Tuple.of(v, function.applyAsDouble(v));

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
