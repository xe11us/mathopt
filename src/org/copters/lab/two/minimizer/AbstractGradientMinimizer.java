package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

import javax.naming.LimitExceededException;

public abstract class AbstractGradientMinimizer implements GradientMinimizer {
    protected final double epsilon;

    protected double alpha;
    protected Vector previousDirection;
    protected Vector nextGradient;

    protected AbstractGradientMinimizer(final double epsilon) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon must be greater than 0");
        }
        this.epsilon = epsilon;
    }

    protected abstract Tuple<Vector, Double> next(Tuple<Vector, Double> x, Vector gradient, QuadraticFunction function);

    protected boolean hasNext(final Vector gradient) {
        return gradient.length() >= epsilon;
    }

    @Override
    public final Tuple<Vector, Integer> minimize(final QuadraticFunction function, final double alpha) {
        final Vector zeros = Vector.ofZeros(function.getDimension());

        Tuple<Vector, Double> x = Tuple.of(zeros, function.applyAsDouble(zeros));
        Vector gradient = function.getGradient(zeros);
        this.previousDirection = zeros;
        this.alpha = alpha;
        int iteration = 0;

        for (; hasNext(gradient); ++iteration) {
            nextGradient = null;
            x = next(x, gradient, function);
            gradient = nextGradient;
            if (gradient == null) {
                gradient = function.getGradient(x.getFirst());
            }
        }
        return Tuple.of(x.getFirst(), iteration);
    }
}
