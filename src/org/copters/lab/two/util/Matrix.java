package org.copters.lab.two.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Matrix {
    private final List<Vector> rows;

    protected Matrix(final List<Vector> rows) {
        final int dimension = rows.get(0).getDimension();
        final Stream<Vector> stream = rows.stream();
        if (stream.anyMatch(row -> row.getDimension() != dimension)) {
            throw new IllegalArgumentException("Cannot create matrix with different row sizes");
        }

        this.rows = rows;
    }

    @SafeVarargs
    public static Matrix of(final List<Double>... rows) {
        return new Matrix(Arrays.stream(rows)
                .map(Vector::new)
                .collect(Collectors.toList()));
    }

    public static Matrix of(final double[]... rows) {
        return new Matrix(Arrays.stream(rows)
                .map(Vector::of)
                .collect(Collectors.toList()));
    }

    public Vector multiply(final Vector vector) {
        if (vector.getDimension() != getDimensions().getSecond()) {
            throw new IllegalArgumentException("Cannot multiply matrix and vector of different size");
        }
        return new Vector(rows().stream()
                .map(row -> row.dot(vector))
                .collect(Collectors.toList()));
    }

    public List<Vector> rows() {
        return rows;
    }

    public double get(final int i, final int j) {
        return rows.get(i).get(j);
    }

    public Tuple<Integer, Integer> getDimensions() {
        return Tuple.of(rows.size(), rows.get(0).getDimension());
    }
}
