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
	
	
	
	
	////////////////////////// NUMBER TABLE & COORDINATES //////////////////////////////


	// initialize variables
	@FXML
	private AnchorPane numbertable;
	@FXML
	private NumbertableController numbertableController;
	@FXML
	private AnchorPane coordinatestable;
	@FXML
	private CoordinatesController coordinatestableController;


	public void startTimer(double[] data) {
		numbertableController.updateNumberDisplay(data);
		coordinatestableController.updateCoordinatesDisplay(data);
	}
	

	
	///////////////////////// RADIO COMMANDS /////////////////
	
	@FXML 
	private AnchorPane radioCommandButtons;
	@FXML
	private RadioCommandButtonsController radioCommandButtonsController;
	
	@FXML
	private AnchorPane radioCommandNumberTable;
	@FXML
	private RadioCommandNumberTableController radioCommandNumberTableController;
	
	public void sceneInitializeRadioCommandButtons() {
		radioCommandButtonsController.initialize();
	}
	
	public void startRadioCommandsTimer(double data[]) {
		radioCommandButtonsController.updateDumpValveLabel(data);
	}
	
	public void startRadioCommandsNumberTableTimer(double data[]) {
		radioCommandNumberTableController.updateNumDisplay(data);
	}
	

}
