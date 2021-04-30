
package controller.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
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

import javafx.fxml.FXML;


import org.gillius.jfxutils.JFXUtil;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.FixedFormatTickFormatter;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.StableTicksAxis;


import controller.Parser;
import javafx.fxml.FXMLLoader;

import javafx.scene.input.ScrollEvent;
import javafx.scene.Node;
import javafx.event.EventHandler;

/**
 * The controller class for the graph section of the main page
 * 
 *  ---------------INFO------------------
 *  
 * Drag/scroll to zoom
 * 
 * Double click to reset to auto-zoom
 * 
 * @author Soomin Lee
 * @author Jeremy Chow
 *
 */
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
	private LineChart<Number, Number> altitudeChart;
	
	XYChart.Series<Number, Number> altitudeData;
	
	@FXML
	private LineChart<Number, Number> velocityChart;
	
	XYChart.Series<Number, Number> velocityData;
	
	@FXML
	private LineChart<Number, Number> accelerationChart;
	
	XYChart.Series<Number, Number> accelerationData;
	
	@FXML
	private LineChart<Number, Number> RSSIChart;
	
	XYChart.Series<Number, Number> RSSIData;
	
	/**
	 *  Initializes all charts on the main page
	 */
	
	public void initializeGraphs() {
        initializeAltitudeChart();
        initializeVelocityChart();
        initializeAccelerationChart();
        initializeRSSIChart();
	}
	
	/**
	*	Initializes the altitude chart on the main page
	*/
	private void initializeAltitudeChart() {
		
		altitudeData = new XYChart.Series<>();
		altitudeData.setName("altitudeData");
		altitudeChart.getData().add(altitudeData);
		
		NumberAxis yAxis = (NumberAxis) altitudeChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		
		NumberAxis xAxis = (NumberAxis) altitudeChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		
		JFXChartUtil.setupZooming(altitudeChart, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					mouseEvent.consume();
			}
		} );
		
		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(altitudeChart);

	}
	
	/**
	 * Initializes the velocity chart on the main page
	 */
	
	private void initializeVelocityChart() {
		velocityData = new XYChart.Series<>();
		velocityData.setName("velocityData");
		velocityChart.getData().add(velocityData);
		
		NumberAxis yAxis = (NumberAxis) velocityChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		
		NumberAxis xAxis = (NumberAxis) velocityChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		
		JFXChartUtil.setupZooming(velocityChart, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					mouseEvent.consume();
			}
			
		} );
		
		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(velocityChart);
		
	}
	
	/**
	 * Initializes the acceleration chart on the main page
	 */
	
	private void initializeAccelerationChart() {
		accelerationData = new XYChart.Series<>();
		accelerationData.setName("accelerationData");
		accelerationChart.getData().add(accelerationData);	
		
		NumberAxis yAxis = (NumberAxis) accelerationChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		
		NumberAxis xAxis = (NumberAxis) accelerationChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		
		JFXChartUtil.setupZooming(accelerationChart, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					mouseEvent.consume();
			}
		} );

		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(accelerationChart);
		
	}
	
	/**
	 * Initializes the RSSI chart on the main page
	 */
	
	private void initializeRSSIChart() {
		RSSIData = new XYChart.Series<>();
		RSSIData.setName("MYDATA");
		RSSIChart.getData().add(RSSIData);	
		
		NumberAxis yAxis = (NumberAxis) RSSIChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		
		NumberAxis xAxis = (NumberAxis) RSSIChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		
		JFXChartUtil.setupZooming(RSSIChart, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					mouseEvent.consume();
			}
		} );
		
		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(RSSIChart);
		
	}
	
	/**
	 * Adds the array of data values to the graphs.
	 * The order and meaning of the values in <code>data</code> is determined by the DataIndex class
	 * 
	 * @param data the array of data values to be plotted
	 * 
	 * @see src.main.java.controller.gui.DataIndex.java DataIndex
	 * 
	 */
	public void addGraphData(double[] data) {
		addAltitudeData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ALTITUDE_INDEX.getOrder()]);

//		addVelocityData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.VELOCITY_INDEX.getOrder()]);
		addVelocityData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCEL_X_INDEX.getOrder()]);
		
//		addAccelerationData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCELERATION_INDEX.getOrder()]);
		addAccelerationData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCEL_Y_INDEX.getOrder()]);
		
//		addRSSIData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.RSSI_INDEX.getOrder()]);
		addRSSIData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCEL_Z_INDEX.getOrder()]);
	}
	
	/**
	 * Adds a data point to the altitude chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the altitude that was measured in meters
	 */
	
	private void addAltitudeData(Double x, Double y) {
		altitudeData.getData().add(new XYChart.Data<>(x, y));
		if (altitudeData.getData().size() > window_size)
			altitudeData.getData().remove(0);
	}
	
	/**
	 * Adds a data point to the velocity chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the velocity that was measured in meters/second
	 */
	
	private void addVelocityData(Double x, Double y) {
		velocityData.getData().add(new XYChart.Data<>(x, y));
		if (velocityData.getData().size() > window_size)
			velocityData.getData().remove(0);
	}
	
	/**
	 * Adds a data point to the acceleration chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the acceleration that was measured in meters/second**2
	 */
	
	private void addAccelerationData(Double x, Double y) {
		accelerationData.getData().add(new XYChart.Data<>(x, y));
		if (accelerationData.getData().size() > window_size)
			accelerationData.getData().remove(0);
	}
	
	/**
	 * Adds a data point to the RSSI chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the RSSI that was measured in decibels
	 */
	
	private void addRSSIData(Double x, Double y) {
		RSSIData.getData().add(new XYChart.Data<>(x, y));
		if (RSSIData.getData().size() > window_size)
			RSSIData.getData().remove(0);
	}
	

}


