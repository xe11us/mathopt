package org.copters.lab.two.minimizer;

import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Vector;

public interface GradientMinimizer {
    Vector minimize(QuadraticFunction function);

    String getRussianName();
}
