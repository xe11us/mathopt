package org.copters.lab.four.util;

import org.copters.lab.three.util.Vector;

import java.util.stream.IntStream;

public class Gradient extends Vector {
    public static final double STEP = 1e-8;

    public Gradient(final Function function, final Vector vector) {
        this(function, vector, STEP);
    }

    public Gradient(final Function function, final Vector vector, final double step) {
        super(calculate(function, vector, step));
    }

    private static double[] calculate(final Function function, final Vector vector, final double step) {
        final int size = vector.size();
        final double value = function.applyAsDouble(vector);

        return IntStream.range(0, size)
                .mapToObj(i -> vector.add(Vector.unit(size, i).multiply(step)))
                .mapToDouble(function)
                .map(x -> (x - value) / step)
                .toArray();
    }
}
