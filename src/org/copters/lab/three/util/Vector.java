package org.copters.lab.three.util;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Vector {
    private final double[] values;

    public Vector(final double[] values) {
        this.values = values;
    }

    public static Vector of(final double... values) {
        return new Vector(values);
    }

    public static Vector ofZeros(final int n) {
        return new Vector(new double[n]);
    }

    public double dot(final Vector vector) {
        if (size() != vector.size()) {
            throw new IllegalArgumentException("Cannot multiply vectors of different size");
        }
        return IntStream.range(0, values.length)
                .mapToDouble(ind -> get(ind) * vector.get(ind))
                .sum();
    }

    public Vector multiply(final double scale) {
        return new Vector(Arrays.stream(values)
                .map(arg -> arg * scale)
                .toArray());
    }

    public Vector add(final Vector vector) {
        if (size() != vector.size()) {
            throw new IllegalArgumentException("Cannot sum vectors of different size");
        }

        return new Vector(IntStream.range(0, values.length)
                .mapToDouble(ind -> get(ind) + vector.get(ind))
                .toArray());
    }

    public Vector subtract(final Vector vector) {
        return add(vector.multiply(-1));
    }

    public double length() {
        return Math.sqrt(Arrays.stream(values)
                .map(arg -> arg * arg)
                .sum());
    }

    public void set(final int index, final double value) {
        values[index] = value;
    }

    public double get(final int index) {
        return values[index];
    }

    public int size() {
        return values.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }
}
