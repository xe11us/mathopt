package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public abstract class AbstractMinimizer implements Minimizer {
    protected final Segment initialSegment;
    protected final double epsilon;

    protected Segment segment;

    protected AbstractMinimizer(Segment segment, double epsilon) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon must be greater than 0");
        }
        this.initialSegment = segment;
        this.epsilon = epsilon;
        this.segment = segment;
    }

    protected abstract Segment next(UnimodalFunction function);

    protected abstract boolean hasNext();

    protected abstract double getMinX();

    @Override
    public final double minimize(UnimodalFunction function) {
        reinitialize(function);
        while (hasNext()) {
            segment = next(function);
        }
        return getMinX();
    }

    protected void reinitialize(UnimodalFunction function) {
        segment = initialSegment;
    }
}
