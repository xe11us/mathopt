package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

public interface GradientMinimizer {
    Tuple<Vector, Integer> minimize(QuadraticFunction function, double alpha);

    default Tuple<Vector, Integer> minimize(final QuadraticFunction function) {
        return minimize(function, 1.);
    }

    String getRussianName();
}
