package org.copters.lab.three.gen;

import org.copters.lab.three.util.ProfileMatrix;
import org.copters.lab.three.util.SquareMatrix;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

public final class Generate {
    private static final Random RANDOM = new Random(0xDEAD);

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

    public static SquareMatrix dense(final int size, final int k) {
        final double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                if (i == j) {
                    continue;
                }
                matrix[j][i] = -RANDOM.nextInt(5);
                matrix[i][j] = -RANDOM.nextInt(5);
            }
        }

        matrix[0][0] += Math.pow(10.0, -k);
        for (int i = 0; i < size; i++) {
            matrix[i][i] = -Arrays.stream(matrix[i]).sum();
        }

        return ProfileMatrix.of(matrix);
    }
}
