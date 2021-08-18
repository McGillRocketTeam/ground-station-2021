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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneController {

	//initialize variables
	@FXML private AnchorPane numbertable;
	@FXML private NumbertableController numbertableController;
	
	/**
	 * Start the asynchronous timer for updating the numbers display when running old data that is read in its entirety
	 * @param data
	 */
	public void startTimer(double[] data) {
//		System.out.println(numbersController);
		numbertableController.updateNumberDisplay(data);
	}
	
	@FXML private AnchorPane rocketmodel;
	@FXML private RocketModelController rocketmodelController;
	
	/**
	 * Method to initialize the 3D Gyroscope view of the application
	 * @throws Exception
	 */
	public void sceneInitializeGyro() throws Exception {
		rocketmodel.getChildren().add(rocketmodelController.initializeGyro());
	}
	
	/**
	 * Method to add data to the 3D Gyroscope view of the application
	 * @param data
	 */
	public void sceneAddGyroData(double[] data){
		rocketmodelController.addGyroData(data);
	}
	
	
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
