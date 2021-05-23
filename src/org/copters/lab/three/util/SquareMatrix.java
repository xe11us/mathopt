package org.copters.lab.three.util;

import java.util.Arrays;
import java.util.stream.IntStream;

public interface SquareMatrix {

    double get(int i, int j);

    default double[][] rows() {
        return IntStream.range(0, size())
                .mapToObj(i ->
                        IntStream.range(0, size())
                                .mapToDouble(j -> get(i, j))
                                .toArray())
                .toArray(double[][]::new);
    }

    void set(int i, int j, double value);

    int size();

    default Vector multiply(final Vector vector) {
        if (vector.size() != size()) {
            throw new IllegalArgumentException("Cannot multiply matrix and vector of different size");
        }
        return new Vector(Arrays.stream(rows())
                .mapToDouble(row -> new Vector(row).dot(vector))
                .toArray());
    }
}
