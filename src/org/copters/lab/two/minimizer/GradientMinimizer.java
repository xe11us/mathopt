package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Vector;

import javax.naming.LimitExceededException;

public interface GradientMinimizer {
    Vector minimize(QuadraticFunction function) throws LimitExceededException;

    String getRussianName();
}
