package org.copters.lab.one;

import org.copters.lab.one.minimizer.*;
import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {
    private static final UnimodalFunction FUNCTION =
            x -> 0.2 * x * Math.log10(x) + (x - 2.3) * (x - 2.3);

    private static final Segment SEGMENT = new Segment(0.5, 2.5);
    private static final double EPS = 1e-6;

    private static final List<Class<? extends Minimizer>> MINIMIZERS = List.of(
            DichotomyMinimizer.class,
            GoldenRatioMinimizer.class,
            FibonacciMinimizer.class,
            ParabolicMinimizer.class,
            BrentMinimizer.class
    );

    private static void run(Class<? extends Minimizer> clazz) throws Exception {
        var constructor = clazz.getConstructor(Segment.class, double.class);
        Minimizer minimizer = constructor.newInstance(SEGMENT, EPS);

        double minX = minimizer.minimize(FUNCTION);
        System.out.printf("%s: f(%f) = %f\n",
                minimizer.getClass().getSimpleName(),
                minX,
                FUNCTION.applyAsDouble(minX));
    }

    private static void runAll() throws Exception {
        for (Class<? extends Minimizer> clazz : MINIMIZERS) {
            run(clazz);
        }
    }

    public static void main(String[] args) throws Exception {
        runAll();
    }
}