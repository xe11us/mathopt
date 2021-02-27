package org.copters.lab1.method;

import org.copters.lab1.function.UnimodalFunction;

public class DichotomyMinimizer extends AbstractMinimizer {
    private final double delta;

    public DichotomyMinimizer(double epsilon, double delta) {
        super(epsilon);
        if (!(0 < delta && delta < 2 * epsilon)) {
            throw new IllegalArgumentException("Delta must be between 0 and 2 * epsilon");
        }
        this.delta = delta;
    }

    @Override
    protected double minimizeImpl(UnimodalFunction function, double lower, double upper) {
        while (upper - lower > 2 * epsilon) {
            double x1 = (lower + upper - delta) / 2;
            double x2 = (lower + upper + delta) / 2;

            if (function.apply(x1) <= function.apply(x2)) {
                upper = x2;
            } else {
                lower = x1;
            }
        }
        return (lower + upper) / 2;
    }
}
