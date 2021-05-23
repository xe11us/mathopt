package org.copters.lab.three.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class StreamUtils {

    private StreamUtils() {
    }

    public static IntStream reversedRange(final int from, final int to) {
        return IntStream.range(from, to)
                .map(i -> to - i + from - 1);
    }

    public static String join(final int[] values, final String separator) {
        return join(Arrays.stream(values).boxed().toArray(), separator);
    }

    public static String join(final double[] values, final String separator) {
        return join(Arrays.stream(values).boxed().toArray(), separator);
    }

    public static <T> String join(final T[] values, final String separator) {
        return Arrays.stream(values)
                .map(Objects::toString)
                .collect(Collectors.joining(separator));
    }
}
