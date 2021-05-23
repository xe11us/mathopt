package org.copters.lab.three.solver;

import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

public interface Solver {
    Vector solve(SquareMatrix matrix, Vector values);
}
