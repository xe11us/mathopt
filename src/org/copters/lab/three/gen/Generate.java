package org.copters.lab.three.gen;

import org.copters.lab.three.util.ProfileMatrix;
import org.copters.lab.three.util.SquareMatrix;

public final class Generate {

    private Generate() {
    }

    public static SquareMatrix hilbert(final int size) {
        final double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 1. / (i + j + 1);
            }
        }
        return ProfileMatrix.of(matrix);
    }
}
