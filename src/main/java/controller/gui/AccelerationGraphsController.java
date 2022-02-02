
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
public class AccelerationGraphsController {
	
	
	private boolean isAccelerationPlotFullHistory = false;
	
	final int window_size = 20;
	ScheduledExecutorService scheduledExecutorService;
//	
//	@FXML
//	private LineChart<Number, Number> altitudeChart;
//	
//	XYChart.Series<Number, Number> altitudeData;
//	
	@FXML
	private LineChart<Number, Number> accelerationXChart;
	
	XYChart.Series<Number, Number> accelerationXData;
	
	@FXML
	private LineChart<Number, Number> accelerationYChart;
	
	XYChart.Series<Number, Number> accelerationYData;
	
	@FXML
	private LineChart<Number, Number> accelerationZChart;
	
	XYChart.Series<Number, Number> accelerationZData;
	
	/**
	 *  Initializes all charts on the main page
	 */
	
	public void initializeGraphs() {
//        initializeAltitudeChart();
        initializeAccelerationXChart();
        initializeAccelerationYChart();
        initializeAccelerationZChart();
	}
	
	/**
	 * Initializes the velocity chart on the main page
	 */
	
	private void initializeAccelerationXChart() {
		accelerationXData = new XYChart.Series<>();
		accelerationXData.setName("velocityData");
		accelerationXChart.getData().add(accelerationXData);
		
		NumberAxis yAxis = (NumberAxis) accelerationXChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		
		NumberAxis xAxis = (NumberAxis) accelerationXChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		
		JFXChartUtil.setupZooming(accelerationXChart, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					mouseEvent.consume();
			}
			
		} );
		
		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(accelerationXChart);
		
	}
	
	/**
	 * Initializes the acceleration chart on the main page
	 */
	
	private void initializeAccelerationYChart() {
		accelerationYData = new XYChart.Series<>();
		accelerationYData.setName("accelerationData");
		accelerationYChart.getData().add(accelerationYData);	
		
		NumberAxis yAxis = (NumberAxis) accelerationYChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		
		NumberAxis xAxis = (NumberAxis) accelerationYChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		
		JFXChartUtil.setupZooming(accelerationYChart, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					mouseEvent.consume();
			}
		} );

		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(accelerationYChart);
		
	}
	
	/**
	 * Initializes the RSSI chart on the main page
	 */
	
	private void initializeAccelerationZChart() {
		accelerationZData = new XYChart.Series<>();
		accelerationZData.setName("MYDATA");
		accelerationZChart.getData().add(accelerationZData);	
		
		NumberAxis yAxis = (NumberAxis) accelerationZChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		
		NumberAxis xAxis = (NumberAxis) accelerationZChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		
		JFXChartUtil.setupZooming(accelerationZChart, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					 mouseEvent.consume();
			}
		} );
		
		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(accelerationZChart);
		
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
//		addAltitudeData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ALTITUDE_INDEX.getOrder()]);
		
		double x_val = data[DataIndex.TIME_INDEX.getOrder()]*60 + data[DataIndex.TIME_INDEX.getOrder()+1] + data[DataIndex.TIME_INDEX.getOrder()+2]/100.0;

//		addVelocityData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.VELOCITY_INDEX.getOrder()]);
//		addAccelerationXData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCEL_X_INDEX.getOrder()]);
		addAccelerationXData(x_val, data[DataIndex.ACCEL_X_INDEX.getOrder()]);
		
//		addAccelerationData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCELERATION_INDEX.getOrder()]);
//		addAccelerationYData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCEL_Y_INDEX.getOrder()]);
		addAccelerationYData(x_val, data[DataIndex.ACCEL_Y_INDEX.getOrder()]);
		
//		addRSSIData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.RSSI_INDEX.getOrder()]);
//		addAccelerationZData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCEL_Z_INDEX.getOrder()]);
		addAccelerationZData(x_val, data[DataIndex.ACCEL_Z_INDEX.getOrder()]);
	}
	
	/**
	 * Adds a data point to the velocity chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the velocity that was measured in meters/second
	 */
	
	private void addAccelerationXData(Double x, Double y) {
		accelerationXData.getData().add(new XYChart.Data<>(x, y));
		
		if (!isAccelerationPlotFullHistory) {
			if (accelerationXData.getData().size() > window_size)
				accelerationXData.getData().remove(0);
		}
	}
	
	/**
	 * Adds a data point to the acceleration chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the acceleration that was measured in meters/second**2
	 */
	
	private void addAccelerationYData(Double x, Double y) {
		accelerationYData.getData().add(new XYChart.Data<>(x, y));
		
		if (!isAccelerationPlotFullHistory) {
			if (accelerationYData.getData().size() > window_size)
				accelerationYData.getData().remove(0);
		}
	}
	
	/**
	 * Adds a data point to the RSSI chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the RSSI that was measured in decibels
	 */
	
	private void addAccelerationZData(Double x, Double y) {
		accelerationZData.getData().add(new XYChart.Data<>(x, y));

		if (!isAccelerationPlotFullHistory) {
			if (accelerationZData.getData().size() > window_size)
				accelerationZData.getData().remove(0);
		}
	}
	
	/**
	 * Setter for button boolean
	 */
	public void setAccelerationPlotFullHistory() {
		isAccelerationPlotFullHistory = true;
	}
	

}


