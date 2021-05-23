package org.copters.lab.three.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProfileMatrix implements SquareMatrix {
    private final int size;
    private final int[] ia;

    private final double[] lower;
    private final double[] upper;
    private final double[] diagonal;

    public ProfileMatrix(final int size, final int[] ia,
                         final double[] lower, final double[] upper, final double[] diagonal) {
        this.size = size;
        this.ia = ia;
        this.lower = lower;
        this.upper = upper;
        this.diagonal = diagonal;
    }

    public static ProfileMatrix of(final double[]... matrix) {
        final int size = matrix.length;
        final int[] ia = new int[size + 1];

        final List<Double> lowerList = new ArrayList<>();
        final List<Double> upperList = new ArrayList<>();
        IntStream.range(0, size).forEach(i -> {
            final int[] profile = IntStream.range(0, i)
                    .dropWhile(j -> matrix[i][j] == 0
                            && matrix[j][i] == 0)
                    .toArray();

            ia[i + 1] = ia[i] + profile.length;
            lowerList.addAll(Arrays.stream(profile)
                    .mapToObj(j -> matrix[i][j])
                    .collect(Collectors.toList()));
            upperList.addAll(Arrays.stream(profile)
                    .mapToObj(j -> matrix[j][i])
                    .collect(Collectors.toList()));
        });

        return new ProfileMatrix(size, ia,
                lowerList.stream().mapToDouble(d -> d).toArray(),
                upperList.stream().mapToDouble(d -> d).toArray(),
                IntStream.range(0, size).mapToDouble(i -> matrix[i][i]).toArray());
    }

    @Override
    public double get(final int i, final int j) {
        if (i == j) {
            return diagonal[i];
        }

        return i < j ? get(i, j, upper) : get(j, i, lower);
    }

    private double get(final int i, final int j, final double[] triangle) {
        final int start = j - ia[j + 1] + ia[j];
        return i < start ? 0 : triangle[ia[j] + i - start];
    }

    @Override
    public void set(final int i, final int j, final double value) {
        if (i == j) {
            diagonal[i] = value;
            return;
        }

        if (i < j) {
            set(i, j, upper, value);
        } else {
            set(j, i, lower, value);
        }
    }

    private void set(final int i, final int j, final double[] triangle, final double value) {
        final int start = j - ia[j + 1] + ia[j];
        if (i < start) {
            if (value != 0) {
                throw new IllegalArgumentException("Setting the profile of matrix is forbidden");
            }
            return;
        }
        triangle[ia[j] + i - start] = value;
    }

    @Override
    public int size() {
        return size;
    }
}
