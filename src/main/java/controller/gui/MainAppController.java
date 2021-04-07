package controller.gui;

import java.util.EnumMap;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainAppController {
	
	@FXML private GridPane numbers;
	@FXML private NumbersController numbersController;
	
	/**
	 * Start the asynchronous timer for updating the numbers display when running old data that is read in its entirety
	 * @param data
	 */
	public void startTimer(double[] data) {
//		System.out.println(numbersController);
		numbersController.updateNumDisplay(data);
	}
	
	@FXML private GridPane graph;
	@FXML private GraphController graphController;
	
	
	/**
	 * Initialize the graphs
	 */
	public void mainAppInitializeGraphs() {
        graphController.initializeGraphs();
	}
	
	/**
	 * Method to add data to the graphs
	 * @param data array of telemetry data
	 */
	public void mainAppAddGraphData(double[] data) {
		graphController.addGraphData(data);
	}
	
	@FXML private VBox map;
	@FXML private MapController mapController;
	
	/**
	 * Method to initialize the map
	 * @throws Exception
	 */
	public void mainAppInitializeMap() throws Exception {
		mapController.initializeMap();
	}
	
	@FXML private SplitPane rawData;
	@FXML private RawDataController rawDataController;
	
	/**
	 * Method to initialize the Raw Data tab of the application
	 * @throws Exception
	 */
	public void mainAppIntitializeRawData() throws Exception {
		rawDataController.initializeRawDataController();
	}
	
	/**
	 * Method to add data to the map on the main tab
	 * @param data array of telemetry data
	 */
	public void mainAppAddMapData(double[] data) {
		mapController.addMapData(data);
	}
	
	/**
	 * Method to add data to the raw data tab of the application
	 * @param data array of telemetry data
	 */
	public void mainAppAddRawData(double[] data) {
		rawDataController.addRawData(data);
	}
	
	@FXML private VBox gyro3d;
	@FXML private Gyro3dController gyro3dController;
	
	/**
	 * Method to initialize the 3D Gyroscope view of the application
	 * @throws Exception
	 */
	public void mainAppInitializeGyro() throws Exception {
		gyro3d.getChildren().add(gyro3dController.initializeGyro());
	}
	
	/**
	 * Method to add data to the 3D Gyroscope view of the application
	 * @param data
	 */
	public void mainAppAddGyroData(double[] data){
		gyro3dController.addGyroData(data);
	}
}
