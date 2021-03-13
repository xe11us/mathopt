package org.copters.lab.one.minimizer;

import java.util.Random;
import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class ParabolicMinimizer extends AbstractMinimizer {
    private static final Random RANDOM = new Random();

    private double x1;
    private double x2;
    private double x3;
    
    private double f1;
    private double f2;
    private double f3;

    public ParabolicMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
    }

    @Override
    protected boolean hasNext() {
        return (x3 - x1) > epsilon;
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        double a1 = (f2 - f1) / (x2 - x1);
        double a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);

        double x = 0.5 * (x1 + x2 - a1 / a2);
        double f = function.applyAsDouble(x);

        if (f >= f2) {
            if (x < x2) {
                x1 = x;
                f1 = f;
            } else {
                x3 = x;
                f3 = f;
            }
        } else {
            if (x < x2) {
                x3 = x2;
                f3 = f2;
            } else {
                x1 = x2;
                f1 = f2;
            }
            x2 = x;
            f2 = f;
        }

        return new Segment(x1, x3);
    }

    @Override
    protected double getMinX() {
        return x2;
    }

    @Override
    protected void reinitialize(UnimodalFunction function) {
        super.reinitialize(function);

        x1 = segment.getFrom();
        x2 = (segment.getFrom() + segment.getTo()) / 2.;
        x3 = segment.getTo();

        f1 = function.applyAsDouble(x1);
        f2 = function.applyAsDouble(x2);
        f3 = function.applyAsDouble(x3);

        while (!(f1 >= f2 && f2 <= f3)) {
            x2 = x1 + (x3 - x1) * RANDOM.nextDouble();
            f2 = function.applyAsDouble(x2);
        }
    }
}
