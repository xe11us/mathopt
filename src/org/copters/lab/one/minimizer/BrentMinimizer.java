package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

import java.util.Random;

import static java.lang.Math.abs;

public class BrentMinimizer extends AbstractMinimizer {
    private static final Random RANDOM = new Random();
    private double a;
    private double c;
    private double x;
    private double w;
    private double v;

    private double fa;
    private double fc;
    private double fv;
    private double fw;
    private double fx;

    private double previousStepLength;
    private double prePreviousStepLength;

    public BrentMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
    }

    private void update(UnimodalFunction function, double u) {
        if (function.applyAsDouble(u) < fx) {
            v = w;
            w = x;
            x = u;
            fv = fw;
            fw = fx;
            fx = function.applyAsDouble(u);
        }
    }

    private Segment getResult(UnimodalFunction function, double u) {
        double len = segment.length();

        if (u < x) {
            if (function.applyAsDouble(u) < fx) {
                c = x;
                fc = function.applyAsDouble(c);
                segment = new Segment(a, x);
            } else {
                a = u;
                fa = function.applyAsDouble(a);
                segment = new Segment(u, c);
            }
        } else {
            if (fx < function.applyAsDouble(u)) {
                c = u;
                fc = function.applyAsDouble(c);
                segment = new Segment(a, u);
            } else {
                a = x;
                fa = function.applyAsDouble(a);
                segment = new Segment(x, c);
            }
        }

        update(function, u);
        prePreviousStepLength = previousStepLength;
        previousStepLength = Math.abs(len - segment.length());
        return segment;
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        double u;
        if (fx != fw && fw != fv && fx != fv) {
            ParabolicMinimizer parabolicMinimizer = new ParabolicMinimizer(new Segment(a, c), epsilon);
            parabolicMinimizer.reinitialize(function);

            if (parabolicMinimizer.hasNext()) {
                Segment result = parabolicMinimizer.next(function);
                u = result.getFrom() == segment.getFrom() ? result.getTo() : result.getFrom();
                if (abs(u - x) <= prePreviousStepLength) {
                    return getResult(function, u);
                }
            }
        }

        Segment segment1 = new Segment(a, x);
        Segment segment2 = new Segment(x, c);
        Segment goldenRatioSegment = segment1.length() > segment2.length() ? segment1 : segment2;

        GoldenRatioMinimizer goldenRatioMinimizer = new GoldenRatioMinimizer(goldenRatioSegment, epsilon);
        goldenRatioMinimizer.reinitialize(function);

        if (goldenRatioMinimizer.hasNext()) {
            Segment result = goldenRatioMinimizer.next(function);
            u = result.getFrom() == goldenRatioSegment.getFrom() ? result.getTo() : result.getFrom();
            return getResult(function, u);
        }

        throw new RuntimeException("GoldenRatio doesn't have ");
    }

    @Override
    protected boolean hasNext() {
        return segment.length() > epsilon;
    }

    @Override
    protected double getMinX() {
        return x;
    }

    @Override
    protected void reinitialize(UnimodalFunction function) {
        a = segment.getFrom();
        c = segment.getTo();
        double b = (a + c) / 2.;

        double fb = function.applyAsDouble(b);
        fa = function.applyAsDouble(a);
        fc = function.applyAsDouble(c);

        while (!(fa > fb && fb < fc)) {
            b = a + (c - a) * RANDOM.nextDouble();
            fb = function.applyAsDouble(b);
        }

        x = w = v = b;
        fx = fw = fv = function.applyAsDouble(b);

        prePreviousStepLength = previousStepLength = 0;
    }
}
