package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Parabola;
import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class BrentMinimizer extends AbstractMinimizer {
    private static final double K = 1 - GoldenRatioMinimizer.TAU;

    private double x;
    private double w;
    private double v;

    private double fv;
    private double fw;
    private double fx;

    private double d;
    private double e;

    public BrentMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
    }

    private boolean equal(double a, double b) {
        return Math.abs(a - b) < epsilon;
    }

    private boolean notEqual(double a, double b, double c) {
        return !equal(a, b) && !equal(a, c) && !equal(b, c);
    }

    @Override
    protected boolean hasNext() {
        double tol = epsilon * Math.abs(x) + epsilon / 10;
        return Math.abs(x - (segment.getFrom() + segment.getTo()) / 2) + (segment.length()) / 2 > 2 * tol;
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        double g = e;
        e = d;

        boolean parabolaAccepted = false;
        double tol = epsilon * Math.abs(x) + epsilon / 10;
        double u = x;

        if (notEqual(x, w, v) && notEqual(fx, fw, fv)) {
            Parabola parabola = new Parabola(x, w, v, fx, fw, fv);
            u = parabola.getXMin();

            if (segment.contains(u) && 2 * Math.abs(u - x) < g) {
                parabolaAccepted = true;

                if (u - segment.getFrom() < 2 * tol || segment.getTo() - u < 2 * tol) {
                    u = x - Math.signum(x - (segment.getFrom() + segment.getTo()) / 2) * tol;
                }
            }
        }

        if (!parabolaAccepted) {
            if (2 * x < segment.getFrom() + segment.getTo()) {
                u = x + K * (segment.getTo() - x);
                e = segment.getTo() - x;
            } else {
                u = x - K * (x - segment.getFrom());
                e = x - segment.getFrom();
            }
        }

        if (Math.abs(u - x) < tol) {
            u = x + Math.signum(u - x) * tol;
        }

        d = Math.abs(u - x);
        double fu = function.applyAsDouble(u);

        if (fu <= fx) {
            if (u >= x) {
                segment = new Segment(x, segment.getTo());
            } else {
                segment = new Segment(segment.getFrom(), x);
            }

            v = w;
            w = x;
            x = u;
            fv = fw;
            fw = fx;
            fx = fu;
        } else {
            if (u >= x) {
                segment = new Segment(segment.getFrom(), x);
            } else {
                segment = new Segment(x, segment.getTo());
            }

            if (fu <= fw || equal(w, x)) {
                v = w;
                w = u;
                fv = fw;
                fw = fu;
            } else if (fu <= fv || equal(v, x) || equal(v, w)) {
                v = u;
                fv = fu;
            }
        }

        return segment;
    }

    @Override
    protected double getXMin() {
        return x;
    }

    @Override
    protected void reinitialize(UnimodalFunction function) {
        x = w = v = segment.getFrom() + K * segment.length();
        fx = fw = fv = function.applyAsDouble(x);

        d = e = segment.length();
    }
}