package org.copters.lab.four;

import org.copters.lab.four.minimizer.newton.DescentNewtonMinimizer;
import org.copters.lab.four.minimizer.newton.NewtonMinimizer;
import org.copters.lab.four.minimizer.newton.SingleNewtonMinimizer;
import org.copters.lab.four.minimizer.quasinewton.BroydenFletcherShannoMinimizer;
import org.copters.lab.four.minimizer.quasinewton.PowellMinimizer;
import org.copters.lab.four.util.Function;
import org.copters.lab.one.minimizer.BrentMinimizer;
import org.copters.lab.one.util.Segment;
import org.copters.lab.three.util.Vector;

import java.util.List;

public class Main {
    private static final double EPS = 1e-8;
    private static final Segment SEGMENT = new Segment(-100, 100);

    public static void main(final String[] args) {
        final Function function = xs -> {
            double xx = xs.get(1) - xs.get(0) * xs.get(0);
            double xy = 1 - xs.get(0);
            return 100 * xx * xx + xy * xy;
        };

        final Vector x0 = Vector.of(-1.2, 1);

        final BrentMinimizer brent = new BrentMinimizer(SEGMENT, EPS);
        List.of(
                new NewtonMinimizer(EPS),
                new SingleNewtonMinimizer(EPS, brent),
                new DescentNewtonMinimizer(EPS, brent),
                new PowellMinimizer(EPS, brent),
                new BroydenFletcherShannoMinimizer(EPS, brent)
        ).forEach(minimizer -> {
            System.out.println(minimizer.getRussianName());
            System.out.println(minimizer.minimize(function, x0));
        });
    }
}
