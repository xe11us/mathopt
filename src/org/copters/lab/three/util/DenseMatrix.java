package org.copters.lab.three.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DenseMatrix implements SquareMatrix {
    private final double[][] rows;

    public DenseMatrix(final double[][] rows) {
        this.rows = rows;
    }

    public static DenseMatrix of(final double[]... rows) {
        return new DenseMatrix(rows);
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

    @Override
    public String toString() {
        return size() + System.lineSeparator() +
                Arrays.stream(rows)
                        .map(row -> StreamUtils.join(row, " "))
                        .collect(Collectors.joining(System.lineSeparator()));
    }
}
