package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

import java.util.ArrayList;
import java.util.List;

public class FibonacciMinimizer extends AbstractMemorizingMinimizer {
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
    protected double getX1() {
        return segment.getFrom() +
                initialLength * fibonacci.get(maxStep - currentStep + 1) / fibonacci.get(maxStep + 2);
    }

    @Override
    protected double getX2() {
        return segment.getFrom() +
                initialLength * fibonacci.get(maxStep - currentStep + 2) / fibonacci.get(maxStep + 2);
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        ++currentStep;
        return super.next(function);
    }

    @Override
    protected boolean hasNext() {
        return currentStep <= maxStep;
    }

    @Override
    protected double getMinX() {
        return (segment.getFrom() + segment.getTo()) / 2;
    }

    @Override
    protected void reinitialize(UnimodalFunction function) {
        super.reinitialize(function);
        currentStep = 0;
    }
}
