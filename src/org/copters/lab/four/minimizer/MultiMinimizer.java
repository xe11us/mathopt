package org.copters.lab.four.minimizer;

import org.copters.lab.four.util.Function;
import org.copters.lab.three.util.Vector;

public interface MultiMinimizer {
    Vector minimize(Function function, Vector x0);

    String getRussianName();
}
