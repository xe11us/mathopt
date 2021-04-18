package org.copters.lab.two;

import org.copters.lab.one.minimizer.*;
import org.copters.lab.one.util.Segment;
import org.copters.lab.two.minimizer.ConjugateGradientMinimizer;
import org.copters.lab.two.minimizer.GradientDescentMinimizer;
import org.copters.lab.two.minimizer.GradientMinimizer;
import org.copters.lab.two.minimizer.SteepestDescentMinimizer;
import org.copters.lab.two.util.Matrix;
import org.copters.lab.two.util.QuadraticFunction;
import org.copters.lab.two.util.Tuple;
import org.copters.lab.two.util.Vector;

import java.util.List;

public class Main {
    private static final double EPSILON = 1e-5;

    private static final List<TestCase> TEST_CASES = List.of(
            new TestCase(
                    new QuadraticFunction(
                            Matrix.of(
                                    List.of(64., 126.),
                                    List.of(0., 64.)),
                            Vector.of(-10, 30),
                            13
                    ),
                    2,
                    254
            ),

            new TestCase(
                    new QuadraticFunction(
                            Matrix.of(
                                    List.of(4., 1.),
                                    List.of(0., 2.)),
                            Vector.of(1, 1),
                            13
                    ),
                    6 - Math.sqrt(5),
                    6 + Math.sqrt(5)
            )
    );

    private static void run(final GradientMinimizer minimizer, final TestCase testCase) {
        String fullname = minimizer.getRussianName();
        if (minimizer instanceof SteepestDescentMinimizer) {
            final SteepestDescentMinimizer steepestMinimizer = (SteepestDescentMinimizer) minimizer;
            fullname += " (" + steepestMinimizer.getSingleMinimizer().getRussianName() + ")";
        }
        System.out.println(fullname + ": ");
        final Tuple<Vector, Integer> result = minimizer.minimize(
                testCase.getFunction(), testCase.getAlpha());
        System.out.println("\tКоличество итераций: " + result.getSecond());
        System.out.println("\tРезультат: " + result.getFirst());
    }

    public static void main(final String[] args) {
        final List<GradientMinimizer> minimizers = List.of(
                new GradientDescentMinimizer(EPSILON),
                new ConjugateGradientMinimizer(EPSILON)
        );

        TEST_CASES.forEach(test -> {
            final Segment segment = test.getSegment();
            final List<Minimizer> singleVariableMinimizers = List.of(
                    new DichotomyMinimizer(segment, EPSILON),
                    new GoldenRatioMinimizer(segment, EPSILON),
                    new FibonacciMinimizer(segment, EPSILON),
                    new ParabolicMinimizer(segment, EPSILON),
                    new BrentMinimizer(segment, EPSILON)
            );

            minimizers.forEach(minimizer -> run(minimizer, test));
            singleVariableMinimizers.stream()
                    .map(single -> new SteepestDescentMinimizer(EPSILON, single))
                    .forEach(minimizer -> run(minimizer, test));
        });
    }

    private static class TestCase {
        private final QuadraticFunction function;
        private final double minEigenvalue;
        private final double maxEigenvalue;

        public TestCase(final QuadraticFunction function, final double minEigenvalue, final double maxEigenvalue) {
            this.function = function;
            this.minEigenvalue = minEigenvalue;
            this.maxEigenvalue = maxEigenvalue;
        }

        public QuadraticFunction getFunction() {
            return function;
        }

        public double getAlpha() {
            return 2. / maxEigenvalue;
        }

        public Segment getSegment() {
            return new Segment(0, 2. / (minEigenvalue + maxEigenvalue));
        }
    }
}
