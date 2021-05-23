package org.copters.lab.three.util;

import java.util.Arrays;

public class DenseMatrix implements SquareMatrix {
    private final double[][] rows;

    public DenseMatrix(final double[][] rows) {
        this.rows = rows;
    }

    public static DenseMatrix of(final double[]... rows) {
        return new DenseMatrix(rows);
    }

    public Vector multiply(final Vector vector) {
        if (vector.size() != size()) {
            throw new IllegalArgumentException("Cannot multiply matrix and vector of different size");
        }
        return new Vector(Arrays.stream(rows)
                .mapToDouble(row -> new Vector(row).dot(vector))
                .toArray());
    }

    public double get(final int i, final int j) {
        return rows[i][j];
    }

    @Override
    public void set(final int i, final int j, final double value) {
        rows[i][j] = value;
    }

    @Override
    public int size() {
        return rows.length;
    }
}
