package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.UnimodalFunction;

public interface Minimizer {
    double minimize(UnimodalFunction function);

    String getRussianName();
}
