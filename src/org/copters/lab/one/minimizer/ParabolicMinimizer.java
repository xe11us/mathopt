package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Parabola;
import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

import java.util.Random;

public class ParabolicMinimizer extends AbstractMinimizer {
    private static final Random RANDOM = new Random();
    private static final int MAX_TRIES = 100;

    private double x;
    private double prevX;

    private double x2;
    
    private double f1;
    private double f2;
    private double f3;

    public ParabolicMinimizer(final Segment segment, final double epsilon) {
        super(segment, epsilon);
    }

    @Override
    protected boolean hasNext() {
        return Math.abs(prevX - x) > epsilon;
    }

    @Override
    protected Segment next(final UnimodalFunction function) {
        final Parabola parabola = new Parabola(segment.getFrom(), x2, segment.getTo(), f1, f2, f3);

        prevX = x;
        x = parabola.getXMin();
        final double f = function.applyAsDouble(x);

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
        return x;
    }

    private boolean tryFindX2(final UnimodalFunction function) {
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
    protected void reinitialize(final UnimodalFunction function) {
        super.reinitialize(function);

        f1 = function.applyAsDouble(segment.getFrom());
        f3 = function.applyAsDouble(segment.getTo());

        final boolean found = tryFindX2(function);

        if (!found) {
            x = (f1 < f3 ? segment.getFrom() : segment.getTo());
            prevX = x;
        } else {
            x = x2;
            prevX = x + 2 * epsilon;
        }
    }

    @Override
    public String getRussianName() {
        return "Метод парабол";
    }
}
