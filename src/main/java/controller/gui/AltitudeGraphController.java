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

	private static double alt_ground;
	
	private int ALT_YMIN = -100;
	private int ALT_YMAX = +12000;
	
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
		altitudeChart.setCreateSymbols(false);

		NumberAxis yAxis = (NumberAxis) altitudeChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(ALT_YMIN);
		yAxis.setUpperBound(ALT_YMAX);
		yAxis.setTickUnit(1000);
//		yAxis.setTickLabelsVisible(false);

		NumberAxis xAxis = (NumberAxis) altitudeChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		xAxis.setTickLabelsVisible(false);

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
		double subseconds = (255.0 - data[DataIndex.TIME_INDEX.getOrder() + 2]) / (256.0);
		double x_val = data[DataIndex.TIME_INDEX.getOrder()]*60 + data[DataIndex.TIME_INDEX.getOrder()+1] + subseconds;
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
		
		int length = altitudeData.getData().size();
		if (length == 0) {
			alt_ground = Parser.getAltGround();
		}
		if (length > 0) {
			double prev_x = altitudeData.getData().get(length - 1).getXValue().doubleValue();
			if (prev_x > x || x > prev_x + 1) {	// new tick should not much further than previous tick
				return;
			}
		}
		
		if (Math.abs(difference) > 1000 || is_error || (y < 500 && difference < 0) || is_debug) {
			altitudeData.getData().add(new XYChart.Data<>(x, y - alt_ground));
			
			if (y - alt_ground > ALT_YMAX) {
				ALT_YMAX += 2000;
				NumberAxis yAxis = (NumberAxis) altitudeChart.getYAxis();
				yAxis.setUpperBound(ALT_YMAX);
			}
		}

		if (!isAltitudePlotFullHistory) {

			// Remove previous plot from graph
			if (altitudeData.getData().size() > window_size)
				altitudeData.getData().remove(0);
			
		}
	}
	
	/**
	 * Setter for button boolean
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

