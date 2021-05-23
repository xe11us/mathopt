package org.copters.lab.three;

import org.copters.lab.three.gen.Generate;
import org.copters.lab.three.solver.LUSolver;
import org.copters.lab.three.solver.Solver;
import org.copters.lab.three.util.ProfileMatrix;
import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

public class Main {
    private static final double EPS = 1e-8;

    public static void main(final String[] args) {
        final Solver solver = new LUSolver(EPS);
        final SquareMatrix matrix = ProfileMatrix.of(
                new double[]{1., 7., 0.},
                new double[]{0., 1. ,0.},
                new double[]{0., 0. ,9.}
        );

        final SquareMatrix matrix1 = Generate.hilbert(3);
        final Vector vector = Vector.of(1, 2, 3);

        System.out.println(solver
                .solve(matrix, vector));

        System.out.println(solver
                .solve(matrix1, vector));
    }
}
