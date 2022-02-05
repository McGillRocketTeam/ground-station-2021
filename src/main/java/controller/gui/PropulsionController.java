
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
 * The controller class for the graph section of the propulsion integration page
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
public class PropulsionController {


	

	// --- displaying pressure and temperature graphs --- //
	
	final int window_size = 20;
	ScheduledExecutorService scheduledExecutorService;

	@FXML
	private LineChart<Number, Number> pressureChart;
	
	XYChart.Series<Number, Number> pressureData;
	
	
	@FXML
	private LineChart<Number, Number> temperatureChart;
	
	XYChart.Series<Number, Number> temperatureData;
	
	private boolean isPlotFullHistory = false;
	
	/**
	 * Setter for button boolean
	 */
	public void setPropulsionPlotFullHistory() {
		isPlotFullHistory = true;
	}
	
	/**
	 * Setter for button boolean
	 */
	public void deletePropulsionPoints() {
		isPlotFullHistory = false;
	}
	
	
	
	/**
	 *  Initializes all charts on the propulsion integration page
	 */
	
	public void initializePropulsionGraphs() {
		initializePressureChart();
        initializeTemperatureChart();
        
	}
	

	
	/**
	 * Initializes the pressure chart on the propulsion integration page
	 */
	
	private void initializePressureChart() {
		pressureData = new XYChart.Series<>();
		pressureData.setName("pressureData");
		pressureChart.getData().add(pressureData);
		
		NumberAxis yAxis = (NumberAxis) pressureChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		
		NumberAxis xAxis = (NumberAxis) pressureChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		

		
		JFXChartUtil.setupZooming(pressureChart, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					mouseEvent.consume();
			}
		} );

		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(pressureChart);
		
	}
	
	
	/**
	 * Initializes the temperature chart on the propulsion integration page
	 */
	
	private void initializeTemperatureChart() {
		temperatureData = new XYChart.Series<>();
		temperatureData.setName("temperatureData");
		temperatureChart.getData().add(temperatureData);	
		
		NumberAxis yAxis = (NumberAxis) temperatureChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		
		NumberAxis xAxis = (NumberAxis) temperatureChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		
		JFXChartUtil.setupZooming(temperatureChart, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					mouseEvent.consume();
			}
		} );

		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(temperatureChart);
		
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
	public void addPropulsionGraphData(double[] data) {

		int pressure_index = 0, temp_index = 1, time_index = 3;

		System.out.print("addPropGraphIn: ");
		for (int i = 0; i < data.length; i++) System.out.print(data[i] + " ");
		System.out.println(""); // newline
		
		double x_val = data[time_index]*60 + data[time_index+1] + data[time_index+2]/100.0;
		System.out.printf("addPropGraphData: time = %f, pressure = %f, temp = %f\n", x_val, data[pressure_index], data[temp_index]);
		
//		addPressureData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.PRESSURE_INDEX.getOrder()]);
//		addTemperatureData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.TEMP_INDEX.getOrder()]);
		addPressureData(x_val, data[pressure_index]);
		addTemperatureData(x_val, data[temp_index]);
		
	}
	

	
	/**
	 * Adds a data point to the velocity chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the temperature that was measured in C
	 * 
	 * numbers are rounded to nearest integer
	 * TEMPERATURE = temperature of the oxidizer tank (starts at 25 and drops to 0)
	 */
	
	private void addTemperatureData(Double x, Double y) {
//		temperatureData.getData().add(new XYChart.Data<>(x, y));
		temperatureData.getData().add(new XYChart.Data<>(x, (int) Math.round(y)));
		if (temperatureData.getData().size() > window_size && !isPlotFullHistory)
			temperatureData.getData().remove(0);
	}
	
	
	/**
	 * Adds a data point to the acceleration chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the pressure that was measured in psi
	 * 
	 * numbers are rounded to nearest integer
	 * PRESSURE = pressure of the oxidizer tank (starts at 750 and drops to 0)
	 */
	
	private void addPressureData(Double x, Double y) {
//		pressureData.getData().add(new XYChart.Data<>(x,y));
		pressureData.getData().add(new XYChart.Data<>(x,(int) Math.round(y)));
		if (pressureData.getData().size() > window_size && !isPlotFullHistory)
			pressureData.getData().remove(0);
	}

	

}


