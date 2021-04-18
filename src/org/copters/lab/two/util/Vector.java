package org.copters.lab.two.util;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Vector extends AbstractList<Double> {
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

    public Vector multiply(final double scale) {
        return new Vector(arguments.stream()
                .map(arg ->  arg * scale)
                .collect(Collectors.toList()));
    }

    public Vector add(final Vector vector) {
        if (size() != vector.size()) {
            throw new IllegalArgumentException("Cannot sum vectors of different size");
        }
        return new Vector(IntStream.range(0, arguments.size())
                .mapToObj(ind -> get(ind) + vector.get(ind))
                .collect(Collectors.toList()));
    }

    public Vector subtract(final Vector vector) {
        return add(vector.multiply(-1));
    }

    public Double get(final int index) {
        return arguments.get(index);
    }

    public double sqrLength() {
        return arguments.stream()
                .mapToDouble(arg -> arg * arg)
                .sum();
    }

    public double length() {
        return Math.sqrt(sqrLength());
    }

    public double dot(final Vector vector) {
        if (size() != vector.size()) {
            throw new IllegalArgumentException("Cannot multiply vectors of different size");
        }
        return IntStream.range(0, arguments.size())
                .mapToDouble(ind -> get(ind) * vector.get(ind))
                .sum();
    }

    @Override
    public String toString() {
        return arguments.toString();
    }

    @Override
    public Iterator<Double> iterator() {
        return arguments.iterator();
    }

    @Override
    public int size() {
        return arguments.size();
    }

    @Override
    public void forEach(final Consumer<? super Double> action) {
        arguments.forEach(action);
    }

    @Override
    public Spliterator<Double> spliterator() {
        return arguments.spliterator();
    }

    @Override
    public Stream<Double> stream() {
        return arguments.stream();
    }
}
