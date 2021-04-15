package org.copters.lab.two.minimizer;

import org.copters.lab.one.minimizer.Minimizer;
import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

public class SteepestDescentMinimizer extends AbstractGradientMinimizer {
    private final Minimizer singleMinimizer;

    public SteepestDescentMinimizer(final double epsilon, final Minimizer singleMinimizer) {
        super(epsilon);
        this.singleMinimizer = singleMinimizer;
    }

    @Override
    protected Tuple<Vector, Double> next(final Tuple<Vector, Double> x,
                                         final Vector gradient,
                                         final QuadraticFunction function) {
        final double alpha = singleMinimizer.minimize(a ->
                function.applyAsDouble(x.getFirst().subtract(gradient.multiply(a))));
        final Vector v = x.getFirst().subtract(gradient.multiply(alpha));
        return Tuple.of(v, function.applyAsDouble(v));
    }

    @Override
    public String getRussianName() {
        return "Метод наискорейшего спуска";
    }
}
