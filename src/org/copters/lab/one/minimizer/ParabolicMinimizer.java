package org.copters.lab.one.minimizer;

import java.util.Random;

import org.copters.lab.one.util.Parabola;
import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class ParabolicMinimizer extends AbstractMinimizer {
    private static final Random RANDOM = new Random();
    private static final int MAX_TRIES = 100;

    private double x2;
    
    private double f1;
    private double f2;
    private double f3;

    public ParabolicMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
    }

    @Override
    protected boolean hasNext() {
        return segment.length() > epsilon;
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        Parabola parabola = new Parabola(segment.getFrom(), x2, segment.getTo(), f1, f2, f3);

        double x = parabola.getXMin();
        double f = function.applyAsDouble(x);

        if (f >= f2) {
            if (x < x2) {
                segment = new Segment(x, segment.getTo());
                f1 = f;
            } else {
                segment = new Segment(segment.getFrom(), x);
                f3 = f;
            }
        } else {
            if (x < x2) {
                segment = new Segment(segment.getFrom(), x2);
                f3 = f2;
            } else {
                segment = new Segment(x2, segment.getTo());
                f1 = f2;
            }
            x2 = x;
            f2 = f;
        }

        return segment;
    }

    @Override
    protected double getXMin() {
        return x2;
    }

    private boolean tryFindX2(UnimodalFunction function) {
        int tryNum = 0;

        do {
            x2 = segment.getFrom() + segment.length() * RANDOM.nextDouble();
            f2 = function.applyAsDouble(x2);
            ++tryNum;

            if (tryNum > MAX_TRIES) {
                return false;
            }
        } while (!(f1 >= f2 && f2 <= f3));

        return true;
    }

    @Override
    protected void reinitialize(UnimodalFunction function) {
        super.reinitialize(function);

        f1 = function.applyAsDouble(segment.getFrom());
        f3 = function.applyAsDouble(segment.getTo());

        boolean found = tryFindX2(function);

        if (!found) {
            System.err.println("Warning! The given result might be wrong.");

            if (f1 < f3) {
                x2 = segment.getFrom();
                f3 = f2 = f1;
                segment = new Segment(segment.getFrom(), segment.getFrom());
            } else {
                x2 = segment.getTo();
                f1 = f2 = f3;
                segment = new Segment(segment.getTo(), segment.getTo());
            }
        }
    }
}
