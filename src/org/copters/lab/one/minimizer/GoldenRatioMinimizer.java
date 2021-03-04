package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class GoldenRatioMinimizer extends AbstractMinimizer {
    private static final double PHI = (1. + Math.sqrt(5)) / 2;
    private static final double TAU = PHI - 1;

    public GoldenRatioMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        double x1 = segment.getFrom() + (1 - TAU) * segment.length();
        double x2 = segment.getFrom() + TAU * segment.length();

        if (function.applyAsDouble(x1) <= function.applyAsDouble(x2)) {
            return new Segment(segment.getFrom(), x2);
        }
        return new Segment(x1, segment.getTo());
    }

    @Override
    protected boolean hasNext() {
        return segment.length() > 2 * epsilon;
    }

    @Override
    protected double getMinX() {
        return (segment.getFrom() + segment.getTo()) / 2;
    }
}
