package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

import javax.naming.LimitExceededException;

public interface GradientMinimizer {
    Tuple<Vector, Integer> minimize(QuadraticFunction function, double alpha) throws LimitExceededException;

    default Tuple<Vector, Integer> minimize(final QuadraticFunction function) throws LimitExceededException {
        return minimize(function, 1.);
    }

    String getRussianName();
}
