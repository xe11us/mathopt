package org.copters.lab.three.gen;

import org.copters.lab.three.util.DenseMatrix;
import org.copters.lab.three.util.ProfileMatrix;
import org.copters.lab.three.util.SquareMatrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Function;

public final class FileUtils {

    private FileUtils() {
    }

    public static void dump(final SquareMatrix matrix, final Path path) throws IOException {
        try (final BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(matrix.toString());
        }
    }

    public static SquareMatrix loadProfile(final Path path) throws IOException {
        return read(path, lines -> {
            final int size = Integer.parseInt(lines[0]);
            final int[] ia = Arrays.stream(readVector(lines[1]))
                    .mapToInt(i -> (int) i).toArray();
            final double[] lower = readVector(lines[2]);
            final double[] upper = readVector(lines[3]);
            final double[] diagonal = readVector(lines[4]);
            return new ProfileMatrix(size, ia, lower, upper, diagonal);
        });
    }

    public static SquareMatrix loadDense(final Path path) throws IOException {
        return read(path, lines -> {
            final int size = Integer.parseInt(lines[0]);
            final double[][] matrix = new double[size][size];
            for (int i = 0; i < size; i++) {
                matrix[i] = readVector(lines[i + 1]);
            }
            return new DenseMatrix(matrix);
        });
    }

    private static double[] readVector(final String line) {
        return Arrays.stream(line.split("\\s+"))
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    private static SquareMatrix read(final Path path, final Function<String[], SquareMatrix> parser)
            throws IOException {
        try (final BufferedReader reader = Files.newBufferedReader(path)) {
            return parser.apply(reader.lines().toArray(String[]::new));
        }
    }
}
