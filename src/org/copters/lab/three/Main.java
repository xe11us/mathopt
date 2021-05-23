package org.copters.lab.three;

import org.copters.lab.three.gen.FileUtils;
import org.copters.lab.three.gen.Generate;
import org.copters.lab.three.solver.LUSolver;
import org.copters.lab.three.solver.Solver;
import org.copters.lab.three.util.SquareMatrix;
import org.copters.lab.three.util.Vector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private static final double EPS = 1e-8;
    private static final Path ROOT = Path.of("tests");

    public static void main(final String[] args) throws IOException {
        System.out.print("Enter directory name: ");
        final Scanner sc = new Scanner(System.in);
        final Path testDir = ROOT.resolve(Path.of(sc.next()));
        final Path testFile = testDir.resolve(Path.of("test.txt"));
        Files.createDirectories(testDir);

        final SquareMatrix matrix = Generate.hilbert(5);
        FileUtils.dump(matrix, testFile);

        final SquareMatrix read = FileUtils.loadProfile(testFile);
        final Vector b = Vector.of(1, 2, 3, 4, 5);
        final Solver solver = new LUSolver(EPS);

        System.out.println(solver.solve(read, b));
    }
}
