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

    public FibonacciMinimizer(final Segment segment, final double epsilon) {
        super(segment, epsilon);
        this.fibonacci = new ArrayList<>(List.of(1., 1., 2.));
        this.maxStep = initMaxStep();
    }

    private int initMaxStep() {
        int step = 0;
        while (initialSegment.length() >= epsilon * fibonacci.get(step + 2)) {
            ++step;
            fibonacci.add(fibonacci.get(fibonacci.size() - 1) +
                            fibonacci.get(fibonacci.size() - 2));
        }
        return step;
    }

    @Override
    protected boolean hasNext() {
        return segment.length() >= epsilon && currentStep <= maxStep;
    }

    @Override
    protected Segment next(final UnimodalFunction function) {
        ++currentStep;
        if (f1 <= f2) {
            segment = new Segment(segment.getFrom(), x2);
            x2 = x1;
            f2 = f1;
            x1 = segment.getFrom() + initialSegment.length()
                    * fibonacci.get(maxStep - currentStep + 1) / fibonacci.get(maxStep + 2);
            f1 = function.applyAsDouble(x1);
        } else {
            segment = new Segment(x1, segment.getTo());
            x1 = x2;
            f1 = f2;
            x2 = segment.getFrom() + initialSegment.length()
                    * fibonacci.get(maxStep - currentStep + 2) / fibonacci.get(maxStep + 2);
            f2 = function.applyAsDouble(x2);
        }
        return segment;
    }

    @Override
    protected double getXMin() {
        return (segment.getFrom() + segment.getTo()) / 2;
    }

    @Override
    protected void reinitialize(final UnimodalFunction function) {
        super.reinitialize(function);

        currentStep = 0;
        x1 = segment.getFrom() + initialSegment.length()
                * fibonacci.get(maxStep - currentStep + 1) / fibonacci.get(maxStep + 2);
        x2 = segment.getFrom() + initialSegment.length()
                * fibonacci.get(maxStep - currentStep + 2) / fibonacci.get(maxStep + 2);

        f1 = function.applyAsDouble(x1);
        f2 = function.applyAsDouble(x2);
    }

    @Override
    public String getRussianName() {
        return "Метод Фибоначчи";
    }
}
