package org.copters.lab.three.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SparseMatrix implements SquareMatrix {
    private final int size;
    private final int[] ia;
    private final int[] ja;

    private final double[] lower;
    private final double[] upper;
    private final double[] diagonal;

    public SparseMatrix(final int size, final int[] ia, final int[] ja,
                        final double[] lower, final double[] upper, final double[] diagonal) {
        this.size = size;
        this.ia = ia;
        this.ja = ja;
        this.lower = lower;
        this.upper = upper;
        this.diagonal = diagonal;
    }

    public static SparseMatrix of(final double[]... matrix) {
        final int size = matrix.length;
        final List<Double> lower = new ArrayList<>();
        final List<Double> upper = new ArrayList<>();

        final int[] ia = new int[size + 1];
        final List<Integer> ja = new ArrayList<>();

        IntStream.range(0, size).forEach(i ->  {
            final int[] profile = IntStream.range(0, i)
                    .filter(j -> matrix[i][j] != 0 || matrix[j][i] != 0)
                    .toArray();

            ia[i + 1] = ia[i] + profile.length;
            lower.addAll(Arrays.stream(profile)
                    .mapToObj(j -> matrix[i][j])
                    .collect(Collectors.toList()));
            upper.addAll(Arrays.stream(profile)
                    .mapToObj(j -> matrix[j][i])
                    .collect(Collectors.toList()));

            ja.addAll(Arrays.stream(profile)
                    .boxed()
                    .collect(Collectors.toList()));
        });

        return new SparseMatrix(size, ia,
                ja.stream().mapToInt(i -> i).toArray(),
                lower.stream().mapToDouble(d -> d).toArray(),
                upper.stream().mapToDouble(d -> d).toArray(),
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
        for (int index = ia[j]; index < ia[j + 1]; index++) {
            if (ja[index] == i) {
                return triangle[index];
            }
        }
        return 0;
    }

    @Override
    public void set(int i, int j, double value) {
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
        for (int index = ia[j]; index < ia[j + 1]; index++) {
            if (ja[index] == i) {
                triangle[index] = value;
                return;
            }
        }
        if (value != 0) {
            throw new IllegalArgumentException("Setting the profile of matrix is forbidden");
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        final String SEP = System.lineSeparator();
        return size +
                SEP +
                StreamUtils.join(ia, " ") +
                SEP +
                StreamUtils.join(ja, " ") +
                SEP +
                StreamUtils.join(lower, " ") +
                SEP +
                StreamUtils.join(upper, " ") +
                SEP +
                StreamUtils.join(diagonal, " ");
    }
}
