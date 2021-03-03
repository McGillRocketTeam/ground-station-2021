package controller.gui;

import java.util.EnumMap;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainAppController {
	
	@FXML private GridPane numbers;
	@FXML private NumbersController numbersController;
	
	public void startTimer(double[] data, EnumMap<DataIndex, Integer> DataFormat) {
//		System.out.println(numbersController);
		numbersController.updateNumDisplay(data, DataFormat);
	}
	
	@FXML private GridPane graph;
	@FXML private GraphController graphController;
	
	public void mainAppInitializeGraphs() {
        graphController.initializeGraphs();
	}
	
	public void mainAppAddGraphData(double[] data, EnumMap<DataIndex, Integer> DataFormat) {
		graphController.addGraphData(data, DataFormat);
	}
	
	@FXML private VBox map;
	@FXML private MapController mapController;
	
	public void mainAppInitializeMap() throws Exception {
		mapController.initializeMap();
	}
	
	@FXML private SplitPane rawData;
	@FXML private RawDataController rawDataController;
	
	public void mainAppIntitializeRawData() throws Exception {
		rawDataController.initializeRawDataController();
	}

	public void mainAppAddRawData(double[] data) {
		rawDataController.addRawData(data);
	}
}
