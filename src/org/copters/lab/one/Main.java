package org.copters.lab.one;

import org.copters.lab.one.minimizer.*;
import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class Main {
    private static final UnimodalFunction FUNCTION =
            x -> 0.2 * x * Math.log10(x) + (x - 2.3) * (x - 2.3);

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

//        for (double eps = 1; eps >= 0.1; eps /= 1.2) {
//            System.out.print(" (" + Math.log(eps) + ",");
//            run(new BrentMinimizer(SEGMENT, eps));
//        }
//
//        for (double eps = 0.1; eps >= 1e-15; eps /= 1.4) {
//            System.out.print(" (" + Math.log(eps) + ",");
//            run(new BrentMinimizer(SEGMENT, eps));
//        }
//
//        System.out.println();

//        run(new DichotomyMinimizer(SEGMENT, EPS));
//        run(new FibonacciMinimizer(SEGMENT, EPS));
//        run(new GoldenRatioMinimizer(SEGMENT, EPS));
//        run(new ParabolicMinimizer(SEGMENT, EPS));
        run(new BrentMinimizer(SEGMENT, EPS));
    }
}