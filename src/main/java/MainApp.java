

import java.io.IOException;

import controller.gui.MainController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MainApp extends Application {


	
    @Override
    public void start(Stage stage) throws Exception {
        Label l = new Label("McGill Rocket Team Ground Station");
        Parent root = FXMLLoader.load(getClass().getResource("fxml/MainApp.fxml"));
        Scene mainApp = new Scene(root, 1920,1080);

        stage.setTitle("McGill Rocket Team Ground Station");
        stage.setScene(mainApp);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

}
