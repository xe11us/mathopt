package org.copters.lab.two.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Vector {
    private final List<Double> arguments;

    public Vector(final List<Double> arguments) {
        this.arguments = arguments;
    }

    public static Vector of(final double... args) {
        return new Vector(Arrays.stream(args).boxed()
                .collect(Collectors.toList()));
    }

    public static Vector ofZeros(final int n) {
        return new Vector(Collections.nCopies(n, 0.));
    }

    public Vector negate() {
        return new Vector(arguments.stream()
                .map(arg -> -arg)
                .collect(Collectors.toList()));
    }

    public Vector multiply(final double scale) {
        return new Vector(arguments.stream()
                .map(arg ->  arg * scale)
                .collect(Collectors.toList()));
    }

    public Vector add(final Vector vector) {
        if (getDimension() != vector.getDimension()) {
            throw new IllegalArgumentException("Cannot sum vectors of different size");
        }
        return new Vector(IntStream.range(0, arguments.size())
                .mapToObj(ind -> get(ind) + vector.get(ind))
                .collect(Collectors.toList()));
    }

    public Vector subtract(final Vector vector) {
        return add(vector.negate());
    }

    public Double get(final int index) {
        return arguments.get(index);
    }

    public int getDimension() {
        return arguments.size();
    }

    public double length() {
        return Math.sqrt(arguments.stream()
                .mapToDouble(arg -> arg)
                .sum());
    }

    public double dot(final Vector vector) {
        if (getDimension() != vector.getDimension()) {
            throw new IllegalArgumentException("Cannot multiply vectors of different size");
        }
        return IntStream.range(0, arguments.size())
                .mapToDouble(ind -> get(ind) * vector.get(ind))
                .sum();
    }
}
