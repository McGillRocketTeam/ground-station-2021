
package controller.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import controller.Parser;
import javafx.fxml.FXML;

import javafx.scene.input.ScrollEvent;
import javafx.scene.Node;
import javafx.event.EventHandler;

public class GraphController {
	
//	@FXML private GridPane numbers;
//	@FXML private NumbersController numbersController;
//	
//	public void startTimer(double[] data, EnumMap<DataIndex, Integer> DataFormat) {
//		System.out.println(numbersController);
//		numbersController.updateNumDisplay(data, DataFormat);
//	}

	
	final int window_size = 20;
	ScheduledExecutorService scheduledExecutorService;
	
	@FXML
	private LineChart<String, Number> altitudeChart;
	
	XYChart.Series<String, Number> altitudeData;
	
	@FXML
	private LineChart<String, Number> velocityChart;
	
	XYChart.Series<String, Number> velocityData;
	
	@FXML
	private LineChart<String, Number> accelerationChart;
	
	XYChart.Series<String, Number> accelerationData;
	
	@FXML
	private LineChart<String, Number> RSSIChart;
	
	XYChart.Series<String, Number> RSSIData;
	
	public void initializeGraphs() {
        initializeAltitudeChart();
        initializeVelocityChart();
        initializeAccelerationChart();
        initializeRSSIChart();
	}
	
	private void initializeAltitudeChart() {
		altitudeData = new XYChart.Series<>();
		altitudeData.setName("altitudeData");
		altitudeChart.getData().add(altitudeData);
		
		NumberAxis yAxis = (NumberAxis) altitudeChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		
		
		// addMouseScrolling(altitudeChart); 

	}
	
	private void initializeVelocityChart() {
		velocityData = new XYChart.Series<>();
		velocityData.setName("velocityData");
		velocityChart.getData().add(velocityData);
		
		NumberAxis yAxis = (NumberAxis) velocityChart.getYAxis();
		yAxis.setForceZeroInRange(false);
	}
	
	private void initializeAccelerationChart() {
		accelerationData = new XYChart.Series<>();
		accelerationData.setName("accelerationData");
		accelerationChart.getData().add(accelerationData);	
		
		NumberAxis yAxis = (NumberAxis) accelerationChart.getYAxis();
		yAxis.setForceZeroInRange(false);
	}
	
	private void initializeRSSIChart() {
		RSSIData = new XYChart.Series<>();
		RSSIData.setName("MYDATA");
		RSSIChart.getData().add(RSSIData);	
		
		NumberAxis yAxis = (NumberAxis) RSSIChart.getYAxis();
		yAxis.setForceZeroInRange(false);
	}
	
	public void addGraphData(double[] data, EnumMap<DataIndex, Integer> DataFormat) {
		
		
		addAltitudeData(String.valueOf(data[DataFormat.get(DataIndex.TIME_INDEX)]), data[DataFormat.get(DataIndex.ALTITUDE_INDEX)]);
		addVelocityData(String.valueOf(data[DataFormat.get(DataIndex.TIME_INDEX)]), data[DataFormat.get(DataIndex.VELOCITY_INDEX)]);
		addAccelerationData(String.valueOf(data[DataFormat.get(DataIndex.TIME_INDEX)]), data[DataFormat.get(DataIndex.ACCELERATION_INDEX)]);
		addRSSIData(String.valueOf(data[DataFormat.get(DataIndex.TIME_INDEX)]), data[DataFormat.get(DataIndex.RSSI_INDEX)]);
	}
	
	private void addAltitudeData(String x, Double y) {
		altitudeData.getData().add(new XYChart.Data<>(x, y));
		if (altitudeData.getData().size() > window_size)
			altitudeData.getData().remove(0);
	}
	
	private void addVelocityData(String x, Double y) {
		velocityData.getData().add(new XYChart.Data<>(x, y));
		if (velocityData.getData().size() > window_size)
			velocityData.getData().remove(0);
	}
	
	private void addAccelerationData(String x, Double y) {
		accelerationData.getData().add(new XYChart.Data<>(x, y));
		if (accelerationData.getData().size() > window_size)
			accelerationData.getData().remove(0);
	}
	
	private void addRSSIData(String x, Double y) {
		RSSIData.getData().add(new XYChart.Data<>(x, y));
		if (RSSIData.getData().size() > window_size)
			RSSIData.getData().remove(0);
	}
	
	/*
    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) zoomFactor = 2.0 - zoomFactor;
            node.setScaleX(node.getScaleX() * zoomFactor);
            node.setScaleY(node.getScaleY() * zoomFactor);
        });
    }
    */
	
}


