package org.copters.lab.one.minimizer;

import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

import java.util.ArrayList;
import java.util.List;

public class FibonacciMinimizer extends AbstractMinimizer {
    private final List<Double> fibonacci;
    private final int maxStep;

    private int currentStep;

    private double x1;
    private double x2;

    private double f1;
    private double f2;

    public FibonacciMinimizer(Segment segment, double epsilon) {
        super(segment, epsilon);
        this.fibonacci = new ArrayList<>(List.of(0., 1.));
        this.maxStep = initMaxStep();
        this.currentStep = 0;
    }

    private int initMaxStep() {
        int step = -1;
        while (initialSegment.length() >= epsilon * fibonacci.get(step + 2)) {
            ++step;
            fibonacci.add(fibonacci.get(fibonacci.size() - 1) +
                            fibonacci.get(fibonacci.size() - 2));
        }
        return step;
    }

    @Override
    protected boolean hasNext() {
        return currentStep <= maxStep;
    }

    @Override
    protected Segment next(UnimodalFunction function) {
        ++currentStep;
        if (f1 <= f2) {
            segment = new Segment(segment.getFrom(), x2);
            x2 = x1;
            f2 = f1;
            x1 = segment.getFrom() + initialSegment.length()
                    * fibonacci.get(maxStep - currentStep + 1) / fibonacci.get(maxStep + 2);
            f1 = function.applyAsDouble(x1);
            ++counter;
        } else {
            segment = new Segment(x1, segment.getTo());
            x1 = x2;
            f1 = f2;
            x2 = segment.getFrom() + initialSegment.length()
                    * fibonacci.get(maxStep - currentStep + 2) / fibonacci.get(maxStep + 2);
            f2 = function.applyAsDouble(x2);
            ++counter;
        }
        return segment;
    }

    @Override
    protected double getMinX() {
        return (segment.getFrom() + segment.getTo()) / 2;
    }

    @Override
    protected void reinitialize(UnimodalFunction function) {
        super.reinitialize(function);

        x1 = segment.getFrom() + initialSegment.length()
                * fibonacci.get(maxStep - currentStep + 1) / fibonacci.get(maxStep + 2);
        x2 = segment.getFrom() + initialSegment.length()
                * fibonacci.get(maxStep - currentStep + 2) / fibonacci.get(maxStep + 2);

        f1 = function.applyAsDouble(x1);
        f2 = function.applyAsDouble(x2);

        counter += 2;

        currentStep = 0;
    }
}
