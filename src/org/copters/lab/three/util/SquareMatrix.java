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

    static SquareMatrix identity(final int n) {
        final double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i][i] = 1;
        }
        return new DenseMatrix(matrix);
    }

    default SquareMatrix multiply(final double scale) {
        final double[][] rows = rows();
        return new DenseMatrix(Arrays.stream(rows)
                .map(row -> new Vector(row).multiply(scale).toArray())
                .toArray(double[][]::new));
    }

    default SquareMatrix add(final SquareMatrix matrix) {
        final double[][] rows = rows();
        final double[][] matrixRows = matrix.rows();

        return new DenseMatrix(IntStream.range(0, size())
                .mapToObj(i -> {
                    final Vector row = new Vector(rows[i]);
                    final Vector matrixRow = new Vector(matrixRows[i]);
                    return row.add(matrixRow).toArray();
                })
                .toArray(double[][]::new));
    }

    default SquareMatrix subtract(final SquareMatrix matrix) {
        return add(matrix.multiply(-1));
    }

    default SquareMatrix multiply(final SquareMatrix matrix) {
        final int size = size();
        final double[][] result = new double[size][size];
        final double[][] rows = rows();
        final double[][] matrixRows = matrix.rows();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int t = 0; t < size; t++) {
                    result[i][j] += rows[i][t] * matrixRows[t][j];
                }
            }
        }

        return new DenseMatrix(result);
    }

    default SquareMatrix transposed() {
        final int size = size();
        final double[][] matrix = new double[size][size];
        final double[][] rows = rows();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[j][i] = rows[i][j];
            }
        }
        return DenseMatrix.of(matrix);
    }
}
