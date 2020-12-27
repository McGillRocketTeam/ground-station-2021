
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
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


public class GraphController {
	
	final int window_size = 30;
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
		
		
	}
	
	private void initializeVelocityChart() {
		velocityData = new XYChart.Series<>();
		velocityData.setName("velocityData");
	//	velocityChart.setTitle("TEST");
		velocityChart.getData().add(velocityData);
		
	}
	
	private void initializeAccelerationChart() {
		accelerationData = new XYChart.Series<>();
		accelerationData.setName("accelerationData");
		accelerationChart.getData().add(accelerationData);	
	}
	
	private void initializeRSSIChart() {
		RSSIData = new XYChart.Series<>();
		RSSIData.setName("MYDATA");
		RSSIChart.getData().add(RSSIData);	
	}
	
	public void addGraphData(double[] data, EnumMap<DataIndex, Integer> DataFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
		Date now = new Date();
		String strNow = simpleDateFormat.format(now);
		addAltitudeData(strNow, data[DataFormat.get(DataIndex.ALTITUDE_INDEX)]);
		addVelocityData(strNow, data[DataFormat.get(DataIndex.VELOCITY_INDEX)]);
		addAccelerationData(strNow, data[DataFormat.get(DataIndex.ACCELERATION_INDEX)]);
		addRSSIData(strNow, data[DataFormat.get(DataIndex.RSSI_INDEX)]);
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
	

	
	/* -=-=-=-=-=-=-=-=-=-=-=-INFO-=-=-=-=-=-=-=-=-=-=-=-
	 * 
	 * IF YOU CHOOSE A NEW MAP IMAGE, MAKE SURE TO UPDATE THE SIZE AND CORNERS
	 * 
	 * ALL NEW MAP IMAGES SHOULD BE ORIENTED WITH NORTH == Y-AXIS
	 * OTHERWISE DIRECTIONS WILL BE OFF
	 * 
	 * 
	*/
	
	// THE SIZE OF THE IMAGE
	final int image_width  = 2878	/2; // divided by two to fit computer size
	final int image_height = 1634	/2;
	
	// THE CORNER LATITUDES AND LONGITUDES 
	final double lower_lat = decimal_converter(33,10,17); //top of the screen
	final double upper_lat = decimal_converter(32,53,01); //bottom of the screen
	
	final double lower_lon = decimal_converter(107,06,20); //left of the screen
	final double upper_lon = decimal_converter(106,48,50); //right of the screen
	
	
	//	CONVERTING LATS AND LONS TO DECIMALS
    public static double decimal_converter(double degrees, double minutes, double seconds) {
    	return degrees + minutes/60 + seconds/3600;
    }
    
    //	CONVERTING LON TO X VALUE
    public int find_x (double lon) {
    	int x = Math.abs((int)(((lon - lower_lon)/(upper_lon-lower_lon))*image_width));
    	System.out.println(x);
    	return x;
    }
    
    //	CONVERTING LAT TO Y VALUE
    public int find_y (double lat) {
    	int y = Math.abs((int)(((lat - lower_lat)/(upper_lat-lower_lat))*image_height));
    	System.out.println(y);
    	return y;
    }
    
    @FXML
    ImageView mapImageView;
    
	public void initializeMap() throws Exception{
		
    	double lat = decimal_converter(33,05,03);
    	double lon = decimal_converter(107,03,30);
        
        Circle circle = new Circle();
        
        //	LOCATION OF CIRCLE
        circle.setCenterX(find_x(lon));
        circle.setCenterY(find_y(lat));
        
        //	SIZE & COLOR OF CIRCLE
        circle.setRadius(5); 
        circle.setFill(Color.RED);
        
        //creating final product
    	//obtaining and setting the size of our image
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    	InputStream stream = classloader.getResourceAsStream("GoogleEarthMap.JPG");
        Image image = new Image(stream);
        mapImageView.setImage(image);
    //    Group root = new Group(mapImageView,circle);
		
		
	}
	
	@FXML
	public Label peakAltitudeLabel;
	public Label currentAltitudeLabel;
	public Label peakVelocityLabel;
	public Label currentVelocityLabel;
	public Label peakAccelerationLabel;
	public Label currentAccelerationLabel;
	public Label currentRSSILabel;
	
	@FXML
	public void setPeakAltitudeLabel(String value){
		this.peakAltitudeLabel.setText(value);
	}
	@FXML
	public void setCurrentAltitudeLabel(String value){
		this.currentAltitudeLabel.setText(value);
	}
	@FXML
	public void setPeakVelocityLabel(String value){
		this.peakVelocityLabel.setText(value);
	}
	@FXML
	public void setCurrentVelocityLabel(String value){
		this.currentVelocityLabel.setText(value);
	}
	@FXML
	public void setPeakAccelerationLabel(String value) {
		this.peakAccelerationLabel.setText(value);
	}
	@FXML
	public void setCurrentAccelerationLabel(String value){
		this.currentAccelerationLabel.setText(value);
	}
	@FXML
	public void setCurrentRSSILabel(String value) {
		this.currentRSSILabel.setText(value);
	}

	public void initializeNumDisplay() {
		setCurrentAltitudeLabel("0");
		setPeakVelocityLabel("0");
		setCurrentVelocityLabel("0");
		setPeakAccelerationLabel("0");
		setCurrentAccelerationLabel("0");
		setCurrentRSSILabel("0");
	}
	public void updateNumDisplay(double[] data, EnumMap<DataIndex, Integer> DataFormat) {
		//System.out.println(myDataList.get(i)[5]);
		//set Peak Altitude 
		if (data[DataFormat.get(DataIndex.ALTITUDE_INDEX)] > Double.parseDouble(peakAltitudeLabel.getText())) 
			setPeakAltitudeLabel(String.valueOf(data[DataFormat.get(DataIndex.ALTITUDE_INDEX)]));
		//set Current Altitude 
		setCurrentAltitudeLabel(String.valueOf(data[DataFormat.get(DataIndex.ALTITUDE_INDEX)]));
		//set Peak Velocity 
		if (data[DataFormat.get(DataIndex.VELOCITY_INDEX)]>Double.parseDouble(peakVelocityLabel.getText())) 
			setPeakVelocityLabel(String.valueOf(data[DataFormat.get(DataIndex.VELOCITY_INDEX)]));
		//set Current Velocity 
		setCurrentVelocityLabel(String.valueOf(data[DataFormat.get(DataIndex.VELOCITY_INDEX)]));
		//set Peak Acceleration
		if (data[DataFormat.get(DataIndex.ACCELERATION_INDEX)]>Double.parseDouble(peakAccelerationLabel.getText())) 
		setPeakAccelerationLabel(String.valueOf(data[DataFormat.get(DataIndex.ACCELERATION_INDEX)]));
		//set Current Acceleration
		setCurrentAccelerationLabel(String.valueOf(data[DataFormat.get(DataIndex.ACCELERATION_INDEX)]));
		//setCurrentRSSI
		setCurrentRSSILabel(String.valueOf(data[DataFormat.get(DataIndex.RSSI_INDEX)]));
	}


}


