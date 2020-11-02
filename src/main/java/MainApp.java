

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class MainApp extends Application {
	

    @Override
    public void start(Stage stage) throws Exception {

		StringProperty n;
        Label l = new Label("McGill Rocket Team Ground Station");
        Parent root = FXMLLoader.load(getClass().getResource("fxml/MainApp.fxml"));
        Scene mainApp = new Scene(root, 1920,1080);
        Label myLabel = new Label();
    	myLabel.setText("numbers");
    	String numbers[] = {"1","2","3","4","5","6","7","8","9","10"};
    	
	//	IntegerProperty x = new SimpleIntegerProperty(0);
	//	IntegerProperty y = new SimpleIntegerProperty();
	//	y.bind(x); //update to x will automatically update y
			
        stage.setTitle("McGill Rocket Team Ground Station");
        stage.setScene(mainApp);
        stage.show();
        
		
		for (int i = 0;i<numbers.length;i++) {
			StringProperty n = numbers[i];
			Thread.sleep(1000);
			myLabel.textProperty().bind(n);
		}
    }

    public static void main(String[] args) {
        launch();
    }

}
