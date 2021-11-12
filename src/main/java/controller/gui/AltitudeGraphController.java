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

public class AltitudeGraphController {
	
	final int window_size = 20;
	ScheduledExecutorService scheduledExecutorService;
	
	@FXML
	private LineChart<Number, Number> altitudeChart;
	
	XYChart.Series<Number, Number> altitudeData;
	
	public void initializeAltitudeGraph() {
		initializeAltitudeChart();
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
	
	public void addAltitudeGraphData(double[] data) {
		addAltitudeData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ALTITUDE_INDEX.getOrder()]);
	}
	
	/**
	 * Adds a data point to the altitude chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the altitude that was measured in meters
	 */
	
	private void addAltitudeData(Double x, Double y) {
		altitudeData.getData().add(new XYChart.Data<>(x, y));
//		if (altitudeData.getData().size() > window_size)
//			altitudeData.getData().remove(0);
	}
}
