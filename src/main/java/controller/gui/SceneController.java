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
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * SceneController is the controller class for the main GUI scene. It contains
 * methods that control different part of the scene. Once instantiated in
 * MainApp, we can then control all functionalities in the GUI through this one
 * class.
 * 
 */

public class SceneController {

	// === Number Table ===

	// initialize variables
	@FXML
	private AnchorPane numbertable;
	@FXML
	private NumbertableController numbertableController;
	

	/**
	 * Start the asynchronous timer for updating the numbers display when running
	 * old data that is read in its entirety
	 * 
	 * @param data
	 */
	public void startTimer(double[] data) {
//		System.out.println(numbersController);
		numbertableController.updateNumberDisplay(data);
	}
	

	// === Rocket Model ===

	// initialize variables
	@FXML
	private AnchorPane rocketmodel;
	@FXML
	private RocketModelController rocketmodelController;

	/**
	 * Method to initialize the 3D Gyroscope view of the application
	 * 
	 * @throws Exception
	 */
	public void sceneInitializeGyro() throws Exception {
		rocketmodel.getChildren().add(rocketmodelController.initializeGyro());
	}

	/**
	 * Method to add data to the 3D Gyroscope view of the application
	 * 
	 * @param data
	 */
	public void sceneAddGyroData(double[] data) {
		rocketmodelController.addGyroData(data);
	}

	// === Acceleration & Altitude Graphs ===

	// initialize variables
	@FXML
	private AnchorPane accelerationgraphs;
	@FXML
	private AccelerationGraphsController accelerationgraphsController;
	@FXML
	private AltitudeGraphController altitudegraphController;
	@FXML
	private ToggleButton fullHistory;

	/**
	 * Initialize the graphs
	 */
	public void sceneInitializeGraphs() {
		accelerationgraphsController.initializeGraphs();
		altitudegraphController.initializeAltitudeGraph();
	}

	/**
	 * Method to add data to the graphs
	 * 
	 * @param data array of telemetry data
	 */
	public void sceneAddGraphData(double[] data) {
		accelerationgraphsController.addGraphData(data);
		altitudegraphController.addAltitudeGraphData(data); // add points

		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			// Toggle Button controlling graph plotting
			public void handle(ActionEvent e) {
				if (fullHistory.isSelected()) {
					altitudegraphController.setAltitudePlotFullHistory();
					accelerationgraphsController.setAccelerationPlotFullHistory();
					propulsionGraphsController.setPropulsionPlotFullHistory();
				} else {
					altitudegraphController.deleteAltitudePoints();
					accelerationgraphsController.deleteAccelerationPoints();
					propulsionGraphsController.deletePropulsionPoints();
				}
			}
		};

		fullHistory.setOnAction(event);
	}
	
	
	
	////////////////////////// GRAPHS //////////////////////////////
	
	@FXML private AnchorPane propulsionGraphs;
	@FXML private PropulsionController propulsionGraphsController;
	
	
	
	public void sceneInitializePropulsionGraphs() {
		propulsionGraphsController.initializePropulsionGraphs();
	}
	
	public void sceneAddPropulsionGraphData(double[] data) {
		propulsionGraphsController.addPropulsionGraphData(data);
	}
	
	
	
	
	////////////////////////// TABLE //////////////////////////////
	
	@FXML private AnchorPane propulsionNumberTable;
	@FXML private PropulsionNumberTableController propulsionNumberTableController;
	
	
	public void startPropulsionTimer(double data[]) {
		propulsionNumberTableController.updateNumDisplay(data);
	}	
	
	
	
	private static int launchStatus = 0;
	
	public static int getLaunchStatus() {
		return launchStatus;
	}
	
	public static void setLaunchStatus(int status) {
		launchStatus = status;
	}
	
//	public void setLaunchListener(int launchStatus) {
//		launchStatus = launchStatus;
//	}
	
	@FXML private Button launch;
	
	
	
	private int num_commands_sent = 1; // to send different commands using launch button
	public void sceneInitializeLaunchButton() {	
		launch.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
	
				launchStatus = num_commands_sent;
				num_commands_sent += 1;
				
				if (num_commands_sent == RadioCommands.CMD_VR_POWER_ON.index() + 1) {
					num_commands_sent = 0;
				}
				
//				launch.setDisable(true);
				
			}
		});
	}
	
	
	

	
	////////////////////////// MAP //////////////////////////////
	
	
	
	@FXML private VBox dynamicmap;
	@FXML private DynamicMapController dynamicmapController;
	
	public void sceneInitializeMap() throws Exception {
		if (dynamicmapController != null) {
			dynamicmapController.initializeMap();
		} else {
			System.out.println("dynamicMapController is NULL");
		}
	}
	
	public void sceneAddMapData(double[] data) {
		if (dynamicmapController != null) {
			dynamicmapController.addMapData(data);
		} else {
			System.out.println("dynamicMapController is NULL");
		}
	}
	
	
	
	
	////////////////////////// COORDINATES //////////////////////////////
	
	
	
	
	@FXML
	private AnchorPane coordinatestable;
	@FXML
	private CoordinatesController coordinatestableController;
	
	
	public void startCoordinates(double[] data) {
		if (coordinatestableController != null) {
			coordinatestableController.updateCoordinatesDisplay(data);
		} else {
			System.out.println("CoordinatesController is NULL");
		}
		
	}
	
	///////////////////////// PROPULSION /////////////////
	
	@FXML 
	private AnchorPane radioCommandButtons;
	@FXML
	private RadioCommandButtonsController radioCommandButtonsController;
	
	public void sceneInitializeRadioCommandButtons() {
		radioCommandButtonsController.initialize();
	}
	
	
	
	

	////////////////////////////////////////////////////////
	//	=== Launch Page ===
	
//	@FXML Button launchButton;
//	
//	/**
//	 * Creates a new Scene, the Launch Page. Launch Page becomes visible once the
//	 * "Launch Page" button is clicked.
//	 * 
//	 * @throws Exception
//	 */
//	public void initializeScene() throws Exception {
//
//		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent e) {
//
////        		e.consume();
//
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_21_22/LaunchPage.fxml"));
//				// FXMLLoader fxmlLoader = new
//				// FXMLLoader(getClass().getResource("fxml/MainApp.fxml"));
//
//				try {
//					Parent launchRoot = fxmlLoader.load();
//					Scene launchScene = new Scene(launchRoot, 800, 600);
//
//					Stage newWindow = new Stage();
//					newWindow.setTitle("Launch");
//					newWindow.setScene(launchScene);
//					newWindow.show();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					System.out.println("oh no");
//				}
//
////        		StackPane secondaryLayout = new StackPane();
////                
////                Scene secondScene = new Scene(secondaryLayout, 500,500);
////                Stage newWindow = new Stage();
////                newWindow.setTitle("Launch");
////                newWindow.setScene(secondScene);
////                newWindow.show();
//			}
//		};
//
//		launchButton.setOnAction(event);
//	}
}
