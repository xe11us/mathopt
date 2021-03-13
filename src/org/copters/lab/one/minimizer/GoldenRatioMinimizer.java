package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class GoldenRatioMinimizer extends AbstractMinimizer {
    private static final double TAU = (Math.sqrt(5) - 1.) / 2;

    private double x1;
    private double x2;

    private double f1;
    private double f2;

    public GoldenRatioMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
    }

    @Override
    protected boolean hasNext() {
        return segment.length() > epsilon;
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        if (f1 <= f2) {
            segment = new Segment(segment.getFrom(), x2);
            x2 = x1;
            f2 = f1;
            x1 = segment.getTo() - TAU * segment.length();
            f1 = function.applyAsDouble(x1);
        } else {
            segment = new Segment(x1, segment.getTo());
            x1 = x2;
            f1 = f2;
            x2 = segment.getFrom() + TAU * segment.length();
            f2 = function.applyAsDouble(x2);
        }

        return segment;
    }

    @Override
    protected double getMinX() {
        return (segment.getFrom() + segment.getTo()) / 2;
    }

    @Override
    protected void reinitialize(UnimodalFunction function) {
        super.reinitialize(function);

        x1 = segment.getTo() - TAU * segment.length();
        x2 = segment.getFrom() + TAU * segment.length();

        f1 = function.applyAsDouble(x1);
        f2 = function.applyAsDouble(x2);
    }
}
