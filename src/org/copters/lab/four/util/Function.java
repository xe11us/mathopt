package org.copters.lab.four.util;

import org.copters.lab.three.util.Vector;

import java.util.function.ToDoubleFunction;

@FunctionalInterface
public interface Function extends ToDoubleFunction<Vector> {
}
