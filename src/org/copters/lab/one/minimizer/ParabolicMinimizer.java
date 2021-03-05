package org.copters.lab.one.minimizer;

import java.util.Random;
import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class ParabolicMinimizer extends AbstractMinimizer {
    private double x1;
    private double x2;
    private double x3;

    public ParabolicMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
    }

    @Override
    protected boolean hasNext() {
        return (x3 - x1) > 2 * epsilon;
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        double f1 = function.applyAsDouble(x1);
        double f2 = function.applyAsDouble(x2);
        double f3 = function.applyAsDouble(x3);

        double a1 = (f2 - f1) / (x2 - x1);
        double a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);

        double x = 0.5 * (x1 + x2 - a1 / a2);
        double f = function.applyAsDouble(x);

        if (x1 < x  && x < x2 && x2 < x3 && f >= f2) {
            x1 = x;
        } else if (x1 < x && x < x2 && x2 < x3 && f < f2) {
            x3 = x2;
            x2 = x;
        } else if (x1 < x2 && x2 < x && x < x3 && f <= f2) {
            x1 = x2;
            x2 = x;
        } else if (x1 < x2 && x2 < x && x < x3 && f > f2) {
            x3 = x;
        }

        return new Segment(x1, x3);
    }

    @Override
    protected double getMinX() {
        return x2;
    }

    @Override
    protected void reinitialize(UnimodalFunction function) {
        x1 = segment.getFrom();
        x2 = (segment.getFrom() + segment.getTo()) / 2.;
        x3 = segment.getTo();

        Random random = new Random();
        double f1 = function.applyAsDouble(x1);
        double f2 = function.applyAsDouble(x2);
        double f3 = function.applyAsDouble(x3);

        while (!(f1 >= f2 && f2 <= f3)) {
            x2 = x1 + (x3 - x1) * random.nextDouble();
            f2 = function.applyAsDouble(x2);
        }
    }
}
