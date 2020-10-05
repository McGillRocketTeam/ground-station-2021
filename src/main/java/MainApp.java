

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Label l = new Label("McGill Rocket Team Ground Station");
        Scene scene = new Scene(new StackPane(l), 1920, 1080);
        stage.setScene(scene);
        stage.setTitle("McGill Rocket Team Ground Station");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}