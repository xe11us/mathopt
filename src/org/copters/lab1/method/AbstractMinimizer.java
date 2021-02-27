package org.copters.lab1.method;

import org.copters.lab1.function.UnimodalFunction;

public abstract class AbstractMinimizer implements Minimizer {
    protected final double epsilon;

    protected AbstractMinimizer(double epsilon) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon must be greater than 0");
        }
        this.epsilon = epsilon;
    }

    @Override
    public double minimize(UnimodalFunction function, double lower, double upper) {
        if (upper < lower) {
            throw new IllegalArgumentException("Interval [lower, upper] must not be empty");
        }
        return minimizeImpl(function, lower, upper);
    }

    protected abstract double minimizeImpl(UnimodalFunction function, double lower, double upper);
}
