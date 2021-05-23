package org.copters.lab.three.solver;

public abstract class AbstractSolver implements Solver {
    protected final double epsilon;

    protected AbstractSolver(final double epsilon) {
        this.epsilon = epsilon;
    }
}
