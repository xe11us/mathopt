package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class GoldenRatioMinimizer extends AbstractMemorizingMinimizer {
    private static final double TAU = (Math.sqrt(5) - 1.) / 2;

    public GoldenRatioMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
    }

    @Override
    protected double getX1() {
        return segment.getTo() - TAU * segment.length();
    }

    @Override
    protected double getX2() {
        return segment.getFrom() + TAU * segment.length();
    }

    @Override
    protected boolean hasNext() {
        return segment.length() > epsilon;
    }

    @Override
    protected double getMinX() {
        return (segment.getFrom() + segment.getTo()) / 2;
    }
}
