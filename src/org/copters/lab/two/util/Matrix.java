package org.copters.lab.two.util;

import java.util.List;
import java.util.stream.Collectors;

public class Matrix {

    public Vector multiply(final Vector vector) {
        if (vector.getDimension() != getDimensions().getSecond()) {
            throw new IllegalArgumentException("Cannot multiply matrix and vector of different size");
        }
        return new Vector(rows().stream()
                .map(row -> row.dot(vector))
                .collect(Collectors.toList()));
    }

    public List<Vector> rows() {
        return null;
    }

    public List<Vector> columns() {
        return null;
    }

    public Tuple<Integer, Integer> getDimensions() {
        return Tuple.of(0, 0);
    }
}
