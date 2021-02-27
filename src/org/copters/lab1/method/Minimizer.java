package org.copters.lab1.method;

import org.copters.lab1.function.UnimodalFunction;

public interface Minimizer {
    double minimize(UnimodalFunction function, double lower, double upper);
}
