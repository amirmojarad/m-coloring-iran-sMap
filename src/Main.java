import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {
    static FlowPane pane = new FlowPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Manager manager = new Manager();
        ColoringWorker cw = new ColoringWorker(manager, 500);

        Line line = new Line(350, 0, 350, 700);


        TextField delay = new TextField();
        delay.setPrefSize(100, 25);
        delay.setPromptText("Delay");
        delay.setAlignment(Pos.CENTER);

        Button start = new Button("START");
        start.setPrefSize(100, 25);
        start.setAlignment(Pos.CENTER);

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.getChildren().addAll(manager.getMap(), line, delay, start);
        Scene scene = new Scene(pane, 900, 700);

        //Actions
        start.setOnAction(event -> {
            if (delay.getText().length() != 0)
                cw.setDelay(Integer.parseInt(delay.getText()));
            new Thread(cw).start();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("M-Coloring Graph");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
