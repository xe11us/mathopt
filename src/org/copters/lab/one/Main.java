package org.copters.lab.one;

import org.copters.lab.one.minimizer.*;
import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class Main {
    private static final UnimodalFunction FUNCTION =
            x -> 0.2 * x * Math.log(x) + (x - 2.3) * (x - 2.3);

    private static final Segment SEGMENT = new Segment(0.5, 2.5);
    private static final double EPS = 1e-6;

    private static void run(Minimizer minimizer) {
        double minX = minimizer.minimize(FUNCTION);
        System.out.printf("%s: f(%f) = %f\n",
                minimizer.getClass().getSimpleName(),
                minX,
                FUNCTION.applyAsDouble(minX));
    }

    public static void main(String[] args) {
        run(new DichotomyMinimizer(SEGMENT, EPS));
        run(new FibonacciMinimizer(SEGMENT, EPS));
        run(new GoldenRatioMinimizer(SEGMENT, EPS));
        run(new ParabolicMinimizer(SEGMENT, EPS));
        run(new BrentMinimizer(SEGMENT, EPS));
    }
}
