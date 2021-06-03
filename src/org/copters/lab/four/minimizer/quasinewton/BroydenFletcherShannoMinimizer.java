package org.copters.lab.four.minimizer.quasinewton;

import org.copters.lab.one.minimizer.Minimizer;
import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

public class BroydenFletcherShannoMinimizer extends QuasiNewtonMinimizer {

    public BroydenFletcherShannoMinimizer(final double epsilon, final Minimizer singleMinimizer) {
        super(epsilon, singleMinimizer);
    }

    @Override
    protected SquareMatrix step(final Vector deltaW, final Vector deltaX, final SquareMatrix g) {
        final Vector v = g.multiply(deltaW);
        final double rho = v.dot(deltaW);
        final double fact = 1 / deltaX.dot(deltaW);

        final Vector r = v.multiply(1 / rho).subtract(deltaX.multiply(fact));
        final SquareMatrix first = deltaX.multiply(deltaX).multiply(fact);
        final SquareMatrix second = v.multiply(deltaW)
                .multiply(g.transposed()).multiply(1 / rho);
        final SquareMatrix third = r.multiply(rho).multiply(r);
        return g.subtract(first).subtract(second).add(third);
    }

    @Override
    public String getRussianName() {
        return String.format("Метод Бройдена-Флетчера-Шено (%s)", singleMinimizer.getRussianName());
    }
}
