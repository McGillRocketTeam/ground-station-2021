
package controller.gui;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.concurrent.ScheduledExecutorService;
import javafx.fxml.FXML;


import org.gillius.jfxutils.chart.JFXChartUtil;
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
	
	private final double ACC_X_YMIN = -2.0;
	private final double ACC_X_YMAX = +2.0;
	
	private final double ACC_Y_YMIN = -2.0;
	private final double ACC_Y_YMAX = +2.0;
	
	private final double ACC_Z_YMIN = -2.0; // TODO: change to +/- 12 G for competition
	private final double ACC_Z_YMAX = +2.0;
	
	
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
		accelerationXChart.setCreateSymbols(false);
		
		NumberAxis yAxis = (NumberAxis) accelerationXChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(ACC_X_YMIN);
		yAxis.setUpperBound(ACC_X_YMAX);
		
		NumberAxis xAxis = (NumberAxis) accelerationXChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		xAxis.setTickLabelsVisible(false);
		
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
		accelerationYChart.setCreateSymbols(false);
		
		NumberAxis yAxis = (NumberAxis) accelerationYChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(ACC_Y_YMIN);
		yAxis.setUpperBound(ACC_Y_YMAX);
		
		NumberAxis xAxis = (NumberAxis) accelerationYChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		xAxis.setTickLabelsVisible(false);
		
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
		accelerationZChart.setCreateSymbols(false);
		
		NumberAxis yAxis = (NumberAxis) accelerationZChart.getYAxis();
		yAxis.setForceZeroInRange(false);
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(ACC_Z_YMIN);
		yAxis.setUpperBound(ACC_Z_YMAX);
		
		NumberAxis xAxis = (NumberAxis) accelerationZChart.getXAxis();
		xAxis.setForceZeroInRange(false);
		xAxis.setTickLabelsVisible(false);
		
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
		double subseconds = (255.0 - data[DataIndex.TIME_INDEX.getOrder() + 2]) / (256.0);
		double x_val = data[DataIndex.TIME_INDEX.getOrder()]*60 + data[DataIndex.TIME_INDEX.getOrder()+1] + subseconds;

//		addVelocityData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.VELOCITY_INDEX.getOrder()]);
//		addAccelerationXData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCEL_X_INDEX.getOrder()]);
		addAccelerationXData(x_val, data[DataIndex.ACCEL_X_INDEX.getOrder()]/1000.0); // divide by 1000 to convert mg to g (1 g = 9.81 m/s^2)
		
//		addAccelerationData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCELERATION_INDEX.getOrder()]);
//		addAccelerationYData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCEL_Y_INDEX.getOrder()]);
		addAccelerationYData(x_val, data[DataIndex.ACCEL_Y_INDEX.getOrder()]/1000.0);
		
//		addRSSIData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.RSSI_INDEX.getOrder()]);
//		addAccelerationZData(data[DataIndex.TIME_INDEX.getOrder()], data[DataIndex.ACCEL_Z_INDEX.getOrder()]);
		addAccelerationZData(x_val, data[DataIndex.ACCEL_Z_INDEX.getOrder()]/1000.0);
	}
	
	/**
	 * Adds a data point to the velocity chart
	 * @param x the time the data was measured in #TODO What units????
	 * @param y the velocity that was measured in meters/second
	 */
	
	private void addAccelerationXData(Double x, Double y) {
		int length = accelerationXData.getData().size();
		if (length > 0) {
			double prev_x = accelerationXData.getData().get(length - 1).getXValue().doubleValue();
			if (prev_x > x || x > prev_x + 1) {	// new tick should not much further than previous tick
				return;
			}
		}
		
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
		int length = accelerationXData.getData().size();
		if (length > 0) {
			double prev_x = accelerationXData.getData().get(length - 1).getXValue().doubleValue();
			if (prev_x > x || x > prev_x + 1) {	// new tick should not much further than previous tick
				return;
			}
		}
		
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
		int length = accelerationXData.getData().size();
		if (length > 0) {
			double prev_x = accelerationXData.getData().get(length - 1).getXValue().doubleValue();
			if (prev_x > x || x > prev_x + 1) {	// new tick should not much further than previous tick
				return;
			}
		}
		
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
	
	/**
	 * Setter for resetButton
	 */
	public void deleteAccelerationPoints() {
		isAccelerationPlotFullHistory = false;
	}	
}


