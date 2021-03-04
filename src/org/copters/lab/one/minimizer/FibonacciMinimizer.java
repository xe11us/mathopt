package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

import java.util.ArrayList;
import java.util.List;

public class FibonacciMinimizer extends AbstractMinimizer {
    private final double initialLength;
    private final List<Double> fibonacci;
    private final int maxStep;

    private int currentStep;

    public FibonacciMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
        this.initialLength = segment.length();
        this.fibonacci = new ArrayList<>(List.of(0., 1.));
        this.maxStep = initMaxStep();
        this.currentStep = 0;
    }

    private int initMaxStep() {
        int step = -1;
        while (initialLength >= epsilon * fibonacci.get(step + 2)) {
            ++step;
            fibonacci.add(fibonacci.get(fibonacci.size() - 1) +
                            fibonacci.get(fibonacci.size() - 2));
        }
        return step;
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        double x1 = segment.getFrom() +
                initialLength * fibonacci.get(maxStep - currentStep + 1) / fibonacci.get(maxStep + 2);
        double x2 = segment.getFrom() +
                initialLength * fibonacci.get(maxStep - currentStep + 2) / fibonacci.get(maxStep + 2);

        ++currentStep;
        if (function.applyAsDouble(x1) <= function.applyAsDouble(x2)) {
            return new Segment(segment.getFrom(), x2);
        }
        return new Segment(x1, segment.getTo());
    }

    @Override
    protected boolean hasNext() {
        return currentStep <= maxStep;
    }

    @Override
    protected double getMinX() {
        return (segment.getFrom() + segment.getTo()) / 2;
    }
}
