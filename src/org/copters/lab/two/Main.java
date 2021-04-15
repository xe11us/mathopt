package org.copters.lab.two;

import org.copters.lab.one.minimizer.*;
import org.copters.lab.one.util.Segment;
import org.copters.lab.two.minimizer.GradientDescentMinimizer;
import org.copters.lab.two.minimizer.GradientMinimizer;
import org.copters.lab.two.minimizer.SteepestDescentMinimizer;
import org.copters.lab.two.util.Matrix;
import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Vector;

import javax.naming.LimitExceededException;
import java.util.List;

public class Main {
    private static final double EPSILON = 1e-8;

    private static void run(final GradientMinimizer minimizer, final QuadraticFunction function) {
        System.out.print(minimizer.getRussianName() + ": ");
        try {
            System.out.println(minimizer.minimize(function));
        } catch (final LimitExceededException e) {
            System.out.println("Превышено максимальное количество итераций");
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        final QuadraticFunction function = new QuadraticFunction(
                Matrix.of(
                        List.of(64., 126.),
                        List.of(0., 64.)),
                Vector.of(-10, 30),
                13
        );
        final double l = 2;
        final double L = 254;

        final Segment segment = new Segment(0, 2. / L);
        final List<Minimizer> singleVariableMinimizers = List.of(
                new DichotomyMinimizer(segment, EPSILON),
                new GoldenRatioMinimizer(segment, EPSILON),
                new FibonacciMinimizer(segment, EPSILON),
                new ParabolicMinimizer(segment, EPSILON),
                new BrentMinimizer(segment, EPSILON)
        );

        final List<GradientMinimizer> minimizers = List.of(
//                new GradientDescentMinimizer(EPSILON),
                new SteepestDescentMinimizer(EPSILON, singleVariableMinimizers.get(1))
        );

        minimizers.forEach(minimizer -> run(minimizer, function));
    }
}
