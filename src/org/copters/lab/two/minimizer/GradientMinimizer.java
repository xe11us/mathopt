package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

import javax.naming.LimitExceededException;

public interface GradientMinimizer {
    Tuple<Vector, Integer> minimize(QuadraticFunction function) throws LimitExceededException;

    String getRussianName();
}
