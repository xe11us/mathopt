package org.copters.lab.two.util;

import java.util.List;

public class DiagonalMatrix extends Matrix {

    private DiagonalMatrix(final List<Vector> rows) {
        super(rows);
    }

    public static DiagonalMatrix of(final double... diagonal) {
        final int n = diagonal.length;
        final double[][] matrix = new double[n][n];

        for (int i = 0; i < n; ++i) {
            matrix[i][i] = diagonal[i];
        }

        return (DiagonalMatrix) Matrix.of(matrix);
    }
}
