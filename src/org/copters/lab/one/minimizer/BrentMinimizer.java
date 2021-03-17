package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

import java.util.Random;

import static java.lang.Math.abs;

public class BrentMinimizer extends AbstractMinimizer {
    private static final Random RANDOM = new Random();
    private static final double RATIOCONST = (3 - Math.sqrt(5)) / 2;
    private double a;
    private double c;
    private double x;
    private double w;
    private double v;

    private final ParabolicMinimizer parabolicMinimizer;

    private double fa;
    private double fc;
    private double fv;
    private double fw;
    private double fx;

    private double previousStepLength;
    private double prePreviousStepLength;

    public BrentMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
        parabolicMinimizer = new ParabolicMinimizer(segment, epsilon);
    }


    private boolean equals(double a, double b) {
        return Math.abs(a - b) < 1e-20;
    }

    private Segment getResult(double u, double fu) {
        prePreviousStepLength = previousStepLength;
        previousStepLength = Math.abs(u - x);
        if (u < x) {
            ///a - u - x - c
            if (fu < fx) {
                c = x;
                fc = fx;
            } else {
                a = u;
                fa = fu;
            }
        } else {
            //a - x - u - c
            if (fx < fu) {
                c = u;
                fc = fu;
            } else {
                a = x;
                fa = fx;
            }
        }

        segment = new Segment(a, c);

        if (fu < fx) {
            v = w;
            w = x;
            x = u;
            fv = fw;
            fw = fx;
            fx = fu;
        }

        return segment;
    }


    @Override
    protected Segment next(UnimodalFunction function) {
        parabolicMinimizer.counter = 0;
        double u;
        if (!equals(x, w) && !equals(w, v) && !equals(x, v)) {

            if (parabolicMinimizer.hasNext()) {
                Segment result = parabolicMinimizer.next(function);

                u = result.getFrom() == segment.getFrom() ? result.getTo() : result.getFrom();
                if (abs(u - x) <= prePreviousStepLength / 1.5) {
                    counter += parabolicMinimizer.counter + 1;
                    return getResult(u, function.applyAsDouble(u));
                }
            }
            counter += parabolicMinimizer.counter;
        }

        Segment segment1 = new Segment(a, x);
        Segment segment2 = new Segment(x, c);
        Segment goldenRatioSegment = segment1.length() > segment2.length() ? segment1 : segment2;

        double f1 = goldenRatioSegment.getFrom() == a ? fa : fx;
        double f2 = goldenRatioSegment.getTo() == x ? fx : fc;

        GoldenRatioMinimizer goldenRatioMinimizer = new GoldenRatioMinimizer(goldenRatioSegment, epsilon, f1, f2);
        goldenRatioMinimizer.reinitialize(function);

        if (goldenRatioMinimizer.hasNext()) {
            Segment result = goldenRatioMinimizer.next(function);
            u = result.getFrom() == goldenRatioSegment.getFrom() ? result.getTo() : result.getFrom();
            counter += goldenRatioMinimizer.counter + 1;
            return getResult(u, function.applyAsDouble(u));
        }

        throw new RuntimeException("golden ratio doesn't have next");
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
        fa = function.applyAsDouble(a);
        fc = function.applyAsDouble(c);
        counter += 3;

        parabolicMinimizer.reinitialize(function);
        parabolicMinimizer.counter = 0;

        x = w = v = a + RATIOCONST * (c - a) / 2.;
        fx = fw = fv = function.applyAsDouble(x);

        prePreviousStepLength = previousStepLength = 0;
    }
}