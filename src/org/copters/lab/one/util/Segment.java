package org.copters.lab.one.util;

public class Segment {
    private final double from;
    private final double to;

    public Segment(double from, double to) {
        if (to < from) {
            throw new IllegalArgumentException("Interval [from, to] must not be empty");
        }
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public double length() {
        return to - from;
    }
}
