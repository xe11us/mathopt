package org.copters.lab.four.minimizer.quasinewton;

import org.copters.lab.four.minimizer.MultiMinimizer;
import org.copters.lab.four.util.Function;
import org.copters.lab.four.util.Gradient;
import org.copters.lab.one.minimizer.Minimizer;
import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

public abstract class QuasiNewtonMinimizer implements MultiMinimizer {
    protected final double epsilon;
    protected final Minimizer singleMinimizer;

    public QuasiNewtonMinimizer(final double epsilon, final Minimizer singleMinimizer) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon must be greater than 0");
        }
        this.epsilon = epsilon;
        this.singleMinimizer = singleMinimizer;
    }

    protected abstract SquareMatrix step(Vector deltaW, Vector deltaX, SquareMatrix g);

    protected double getAlpha(final Function function, final Vector x, final Vector p) {
        return singleMinimizer.minimize(alpha ->
                function.applyAsDouble(x.add(p.multiply(alpha))));
    }

    protected boolean hasNext(final Vector diff) {
        return diff.length() >= epsilon;
    }

    @Override
    public final Vector minimize(final Function function, final Vector x0) {
        boolean first = true;

        Vector x = x0;
        Vector deltaX = null;

        SquareMatrix g = null;
        Vector w = null;
        Vector p;

        do {
            Vector prevW = w;
            w = new Gradient(function, x).negate();

            if (first) {
                g = SquareMatrix.identity(x.size());
                p = w;
                first = false;
            } else {
                Vector deltaW = w.subtract(prevW);
                g = step(deltaW, deltaX, g);
                p = g.multiply(w);
            }

            final double alpha = getAlpha(function, x, p);
            final Vector prevX = x;
            x = prevX.add(p.multiply(alpha));
            deltaX = x.subtract(prevX);
        } while (hasNext(deltaX));

        return x;
    }
}
