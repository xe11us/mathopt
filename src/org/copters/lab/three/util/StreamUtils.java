package org.copters.lab.three.util;

import java.util.stream.IntStream;

public final class StreamUtils {

    private StreamUtils() {
    }

    public static IntStream reversedRange(final int from, final int to) {
        return IntStream.range(from, to)
                .map(i -> to - i + from - 1);
    }
}
