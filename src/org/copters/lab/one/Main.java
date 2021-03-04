package org.copters.lab.one;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.copters.lab.one.minimizer.DichotomyMinimizer;
import org.copters.lab.one.minimizer.FibonacciMinimizer;
import org.copters.lab.one.minimizer.Minimizer;
import org.copters.lab.one.util.Segment;
import org.copters.lab.one.util.UnimodalFunction;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private static final UnimodalFunction FUNCTION =
            x -> 0.2 * x * Math.log(x) + (x - 2.3) * (x - 2.3);

    private static final Segment SEGMENT = new Segment(0.5, 2.5);
    private static final double EPS = 1e-6;

    private static void run(Minimizer minimizer) {
        double minX = minimizer.minimize(FUNCTION);
        System.out.printf("%s: f(%f) = %f\n",
                minimizer.getClass().getSimpleName(),
                minX,
                FUNCTION.applyAsDouble(minX));
    }

    public static void main(String[] args) {
        run(new DichotomyMinimizer(SEGMENT, EPS));
        run(new FibonacciMinimizer(SEGMENT, EPS));
        launch(args);
    }
}
