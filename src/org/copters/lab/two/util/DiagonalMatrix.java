package org.copters.lab.two.util;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DiagonalMatrix extends Matrix {

    private final Vector diagonal;

    public DiagonalMatrix(final Vector diagonal) {
        this.diagonal = diagonal;
    }

    public static DiagonalMatrix of(final double... diagonal) {
        return new DiagonalMatrix(
                new Vector(Arrays.stream(diagonal).boxed()
                        .collect(Collectors.toList())));
    }

    @Override
    public Vector multiply(final Vector vector) {
        if (vector.size() != getDimensions().getSecond()) {
            throw new IllegalArgumentException("Cannot multiply matrix and vector of different size");
        }

        return new Vector(IntStream.range(0, vector.size())
                .mapToObj(index -> vector.get(index) * diagonal.get(index))
                .collect(Collectors.toList()));
    }

    @Override
    public double get(final int i, final int j) {
        return i == j ? diagonal.get(i) : 0;
    }

    @Override
    public Tuple<Integer, Integer> getDimensions() {
        return Tuple.of(diagonal.size(), diagonal.size());
    }

    @Override
    public Matrix symmetrized() {
        return new DiagonalMatrix(diagonal.multiply(2.));
    }
}
