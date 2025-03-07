package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class DichotomyMinimizer extends AbstractMinimizer {
    private final double delta;

    public DichotomyMinimizer(final Segment segment, final double epsilon, final double delta) {
        super(segment, epsilon);
        if (!(0 < delta && delta < 2 * epsilon)) {
            throw new IllegalArgumentException("Delta must be between 0 and 2 * epsilon");
        }
        this.delta = delta;
    }

    public DichotomyMinimizer(final Segment segment, final double epsilon) {
        this(segment, epsilon, epsilon);
    }

    @Override
    protected boolean hasNext() {
        return segment.length() > 2 * epsilon;
    }

    @Override
    protected Segment next(final UnimodalFunction function) {
        final double x1 = (segment.getFrom() + segment.getTo() - delta) / 2;
        final double x2 = (segment.getFrom() + segment.getTo() + delta) / 2;

        if (function.applyAsDouble(x1) <= function.applyAsDouble(x2)) {
            segment = new Segment(segment.getFrom(), x2);
        } else {
            segment = new Segment(x1, segment.getTo());
        }
        return segment;
    }

    @Override
    protected double getXMin() {
        return (segment.getFrom() + segment.getTo()) / 2;
    }

    @Override
    public String getRussianName() {
        return "Метод дихотомии";
    }
}
