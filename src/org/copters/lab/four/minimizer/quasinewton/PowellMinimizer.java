package org.copters.lab.four.minimizer.quasinewton;

import org.copters.lab.one.minimizer.Minimizer;
import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

public class PowellMinimizer extends QuasiNewtonMinimizer {

    public PowellMinimizer(final double epsilon, final Minimizer singleMinimizer) {
        super(epsilon, singleMinimizer);
    }

    @Override
    protected SquareMatrix step(final Vector deltaW, final Vector deltaX, final SquareMatrix g) {
        final Vector curlyDeltaX = deltaX.add(g.multiply(deltaW));
        final double frac = 1. / deltaW.dot(curlyDeltaX);
        return g.subtract(curlyDeltaX.multiply(curlyDeltaX).multiply(frac));
    }

    @Override
    public String getRussianName() {
        return String.format("Метод Пауэлла (%s)", singleMinimizer.getRussianName());
    }
}
