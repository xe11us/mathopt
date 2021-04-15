package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

import javax.naming.LimitExceededException;

public abstract class AbstractGradientMinimizer implements GradientMinimizer {
    protected final double epsilon;

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
    public final Vector minimize(final QuadraticFunction function) throws LimitExceededException {
        final Vector zeros = Vector.ofZeros(function.getDimension());

        Tuple<Vector, Double> x = Tuple.of(zeros, function.applyAsDouble(zeros));
        Vector gradient = function.getGradient(zeros);

        final int upperBound = 1_000 * ((int) -Math.log10(epsilon) / 2);
        for (int count = 0; hasNext(gradient); ++count) {
            if (count >= upperBound) {
                throw new LimitExceededException(
                        String.format("Number of iterations exceeded %d", upperBound));
            }

            x = next(x, gradient, function);
            gradient = function.getGradient(x.getFirst());
        }
        return x.getFirst();
    }
}
