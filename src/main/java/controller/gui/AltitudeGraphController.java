package controller.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.fxml.FXML;

import java.util.concurrent.ScheduledExecutorService;

import org.gillius.jfxutils.JFXUtil;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.FixedFormatTickFormatter;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.StableTicksAxis;

import controller.Parser;
import javafx.fxml.FXMLLoader;

import javafx.scene.input.ScrollEvent;
import javafx.scene.Node;

public class AltitudeGraphController {

	private boolean isAltitudePlotFullHistory = false;
	
	final int window_size = 20;
	ScheduledExecutorService scheduledExecutorService;
	
	@FXML
	private LineChart<Number, Number> altitudeChart;

	XYChart.Series<Number, Number> altitudeData;
	
	public void initializeAltitudeGraph() {
		initializeAltitudeChart();
	}

	/**
	 * Initializes the altitude chart on the main page
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
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() != MouseButton.PRIMARY || mouseEvent.isShortcutDown())
					mouseEvent.consume();
			}
		});

		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(altitudeChart);

	}

	public void addAltitudeGraphData(double[] data) {
		double x_val = data[DataIndex.TIME_INDEX.getOrder()]*60 + data[DataIndex.TIME_INDEX.getOrder()+1] + data[DataIndex.TIME_INDEX.getOrder()+2]/100.0;
//		System.out.println(x_val);
		
		addAltitudeData(x_val, data[DataIndex.ALTITUDE_INDEX.getOrder()]);
	}

	/**
	 * Adds a data point to the altitude chart
	 * 
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the altitude that was measured in meters
	 */
	private void addAltitudeData(Double x, Double y) {

//		altitudeData.getData()
//			.add(new XYChart.Data<>(x, getAltitude(y)));
		boolean is_error = false;
		double last_plotted_altitude = 0.0;
		double difference = 0.0;
		
		boolean is_debug = true; // make true to plot all points
		
		try {
			last_plotted_altitude = (double) altitudeData.getData().get(altitudeData.getData().size() - 1).getYValue();
			difference = y - last_plotted_altitude;
		}
		catch(IndexOutOfBoundsException e) {
			last_plotted_altitude = 0;
			is_error = true;
		}
		
		if (Math.abs(difference) > 1000 || is_error || (y < 500 && difference < 0) || is_debug) {
			altitudeData.getData().add(new XYChart.Data<>(x, y));
		}

		if (!isAltitudePlotFullHistory) {

			// Remove previous plot from graph
			if (altitudeData.getData().size() > window_size)
				altitudeData.getData().remove(0);
			
		}
	}
	
	/**
	 * Setter for launchButton 
	 */
	public void setAltitudePlotFullHistory() {
		isAltitudePlotFullHistory = true;
	}
	
	/**
	 * Setter for resetButton 
	 */
	public void deleteAltitudePoints() {
		isAltitudePlotFullHistory = false;
	}
}
