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

	public boolean isPlotFullHistory = false;

	final int window_size = 20;
	ScheduledExecutorService scheduledExecutorService;
	
	private final double LOCAL_PRESSURE = 1013.25;
	
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
		addAltitudeData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ALTITUDE_INDEX.getOrder()]);

	}

	/**
	 * Adds a data point to the altitude chart
	 * 
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the altitude that was measured in meters
	 */
	private void addAltitudeData(Double x, Double y) {
		altitudeData.getData().add(new XYChart.Data<>(x, getAltitude(y)));
		
		if (!isPlotFullHistory) {

			// Remove previous plot from graph
			if (altitudeData.getData().size() > window_size)
				altitudeData.getData().remove(0);
		}
	}

	/**
	 * Setter for button boolean
	 */
	public void setisPlotFullHistory() {
		isPlotFullHistory = true;
	}
	
	/**
	 * convert pressure to altitude
	 * 
	 */
	private double getAltitude(double pressure) {
		double altitude = 145442.1609 * (1.0 - Math.pow(pressure/LOCAL_PRESSURE, 0.190266436));
		return altitude;
	}
}

