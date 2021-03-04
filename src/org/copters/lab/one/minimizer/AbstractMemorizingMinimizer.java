package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public abstract class AbstractMemorizingMinimizer extends AbstractMinimizer {
    private double x1, x2;
    private double f1, f2;

    protected AbstractMemorizingMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        if (f1 <= f2) {
            segment = new Segment(segment.getFrom(), x2);
            x2 = x1;
            f2 = f1;
            x1 = getX1();
            f1 = function.applyAsDouble(x1);
        } else {
            segment = new Segment(x1, segment.getTo());
            x1 = x2;
            f1 = f2;
            x2 = getX2();
            f2 = function.applyAsDouble(x2);
        }

        return segment;
    }

    protected abstract double getX1();

    protected abstract double getX2();

    @Override
    protected void reinitialize(UnimodalFunction function) {
        x1 = getX1();
        x2 = getX2();
        f1 = function.applyAsDouble(x1);
        f2 = function.applyAsDouble(x2);
    }
}
