package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public abstract class AbstractMinimizer implements Minimizer {
    protected final Segment initialSegment;
    protected final double epsilon;

    protected Segment segment;

    protected AbstractMinimizer(final Segment segment, final double epsilon) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon must be greater than 0");
        }
        this.initialSegment = segment;
        this.epsilon = epsilon;
        this.segment = segment;
    }

    protected abstract Segment next(UnimodalFunction function);

    protected abstract boolean hasNext();

    protected abstract double getXMin();

    @Override
    public final double minimize(final UnimodalFunction function) {
        reinitialize(function);
        while (hasNext()) {
            segment = next(function);
        }
        return getXMin();
    }

    protected void reinitialize(final UnimodalFunction function) {
        segment = initialSegment;
    }
}
