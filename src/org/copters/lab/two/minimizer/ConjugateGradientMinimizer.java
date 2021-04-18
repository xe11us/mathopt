package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

public class ConjugateGradientMinimizer extends AbstractGradientMinimizer {

    public ConjugateGradientMinimizer(final double epsilon) {
        super(epsilon);
    }

    protected Tuple<Vector, Double> next(final Tuple<Vector, Double> x,
                                         final Vector gradient,
                                         final QuadraticFunction function) {
        final Vector direction = gradient.multiply(-1).add(previousDirection);
        final Vector aByDir = function.getA().multiply(direction);
        final double alpha = gradient.sqrLength() / aByDir.dot(direction);

        nextGradient = gradient.add(aByDir.multiply(alpha));
        final double beta = nextGradient.sqrLength() / gradient.sqrLength();
        previousDirection = direction.multiply(beta);

        final Vector v = x.getFirst().add(direction.multiply(alpha));
        return Tuple.of(v, function.applyAsDouble(v));
    }

    @Override
    public String getRussianName() {
        return "Метод сопряженных градиентов";
    }
}
