package controller.gui;

import java.io.IOException;
import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneController {

	//initialize variables
	@FXML Button launchButton;
	
	public void initializeScene() throws Exception {
		
		
		EventHandler <ActionEvent> event = new EventHandler <ActionEvent> () {
        	public void handle(ActionEvent e) {
        		
//        		e.consume();

        		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_21_22/LaunchPage.fxml"));
        		//	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MainApp.fxml"));

				try {
					Parent launchRoot = fxmlLoader.load();
					Scene launchScene = new Scene(launchRoot, 800,600);

	                Stage newWindow = new Stage();
	                newWindow.setTitle("Launch");
	                newWindow.setScene(launchScene);
	                newWindow.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("oh no");
				}
        		
//        		StackPane secondaryLayout = new StackPane();
//                
//                Scene secondScene = new Scene(secondaryLayout, 500,500);
//                Stage newWindow = new Stage();
//                newWindow.setTitle("Launch");
//                newWindow.setScene(secondScene);
//                newWindow.show();
            } 
        }; 
        
        launchButton.setOnAction(event);
	}
}
