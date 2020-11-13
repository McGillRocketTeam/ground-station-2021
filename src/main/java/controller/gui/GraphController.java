
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
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;


public class GraphController extends Application {
	
	final int window_size = 30;
	private ScheduledExecutorService scheduledExecutorService;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		initializeGraphs(primaryStage);
	}
	
	@FXML
	private LineChart<String, Number> altitudeChart;
	
	public void initializeAltitudeChart() {
		XYChart.Series<String, Number> altitudeData = new XYChart.Series<>();
		altitudeData.setName("altitudeData");
		
		altitudeChart.getData().add(altitudeData);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
		
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			Integer testing = ThreadLocalRandom.current().nextInt(10);
			
			
		Platform.runLater(()-> {
			Date now = new Date();
			altitudeData.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), testing));
			
			if (altitudeData.getData().size() > window_size)
				altitudeData.getData().remove(0);
		});
		}, 0, 1000, TimeUnit.MILLISECONDS);
		
	}
	
	@FXML
	private LineChart<String, Number> velocityChart;
	
	public void initializeVelocityChart() {
		XYChart.Series<String, Number> velocityData = new XYChart.Series<>();
		velocityData.setName("velocityData");
	//	velocityChart.setTitle("TEST");
		
		velocityChart.getData().add(velocityData);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
		
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			Integer testing = ThreadLocalRandom.current().nextInt(10);
			
			
		Platform.runLater(()-> {
			Date now = new Date();
			velocityData.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), testing));
			
			if (velocityData.getData().size() > window_size)
				velocityData.getData().remove(0);
		});
		}, 0, 800, TimeUnit.MILLISECONDS);
		
	}
	
	@FXML
	private LineChart<String, Number> accelerationChart;
	
	public void initializeAccelerationChart() {
		XYChart.Series<String, Number> accelerationData = new XYChart.Series<>();
		accelerationData.setName("accelerationData");
		
		accelerationChart.getData().add(accelerationData);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
		
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			Integer testing = ThreadLocalRandom.current().nextInt(10);
			
			
		Platform.runLater(()-> {
			Date now = new Date();
			accelerationData.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), testing));
			
			if (accelerationData.getData().size() > window_size)
				accelerationData.getData().remove(0);
		});
		}, 0, 1200, TimeUnit.MILLISECONDS);
		
	}
	
	@FXML
	private LineChart<String, Number> RSSIChart;
	
	public void initializeRSSIChart() {
		XYChart.Series<String, Number> RSSIData = new XYChart.Series<>();
		RSSIData.setName("MYDATA");
		
		RSSIChart.getData().add(RSSIData);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
		
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			Integer testing = ThreadLocalRandom.current().nextInt(10);
			
			
		Platform.runLater(()-> {
			Date now = new Date();
			RSSIData.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), testing));
			
			if (RSSIData.getData().size() > window_size)
				RSSIData.getData().remove(0);
		});
		}, 0, 1400, TimeUnit.MILLISECONDS);
		
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
	
	@FXML
	public void setPeakAltitudeLabel(String value){
		this.peakAltitudeLabel.setText(value);
	}
	@FXML
	public Label currentAltitudeLabel;
	@FXML
	public void setCurrentAltitudeLabel(String value) {
		this.currentAltitudeLabel.setText(value);
	}
	

    int count = 0;
	public void startTimer() {
		List<String> numberDisplayTestData = new ArrayList<String>();
		
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			Integer testing = ThreadLocalRandom.current().nextInt(100000);
			
			
		Platform.runLater(()-> {
			Date now = new Date();
			numberDisplayTestData.add(Integer.toString(testing));
			
			if (numberDisplayTestData.size() > window_size)
				numberDisplayTestData.remove(0);
		});
		}, 0, 1000, TimeUnit.MILLISECONDS);
		

	    Timeline timeline = new Timeline();
	    timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, event -> {
	    	setCurrentAltitudeLabel(Integer.toString(count));
	        if (Integer.parseInt(numberDisplayTestData.get(count)) > Integer.parseInt(peakAltitudeLabel.getText())) setPeakAltitudeLabel(numberDisplayTestData.get(count).toString());
	        count++;
	    }));
	    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1)));
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.play();
	    
	 }
	
	
	
	
	
	public void initializeGraphs(Stage primaryStage) {
		// Initialize javafx graphs

		/*
		 * Data format:
		 *                  S32.943012,-106.914963,3097894,4.71,371546.406250,C,8.63,36.80,0.000000,-79,E
		 *                  telemetry long data format:     Slat,long,time,alt,vel,sat,acc,temp,gyro_x,RSSI,E\n
		 *                  backup GPS data:                Slat,long,time,gps_alt,gps_speed,sat,RSSI,E\n
		 *
		 */
		
		
		
		
		FlowPane root = new FlowPane();
		Scene scene = new Scene(root, 1000, 1000);
		
		
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Time");
		xAxis.setAnimated(false);
		
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Height");
		yAxis.setAnimated(false);
		
		LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
		lineChart.setTitle("Altitude");
		
		XYChart.Series<String, Number> data = new XYChart.Series<>();
		data.setName("DATA");
		
		
		
		/*
		
		List<Double> height = new ArrayList<>();
		double theight [] = {123.0, 130.3, 133.2, 149.2, 151.1, 167.8, 168.1, 187.0, 166.5}; // telemetry_data[3]
		
		for (int i = 0; i < theight.length; i++) {
			height.add(theight[i]);
		}
		
		List<String> time = new ArrayList<>();
		int ttime[] = { 11, 22, 33, 44, 55,66, 77, 88,99}; // telemetry_data[2]
		for (int i = 0; i < ttime.length; i++) {
			time.add(Integer.toString(ttime[i]));
		}
		
		for (int i = 0 ; i < height.size(); i++) {
			data.getData().add(new XYChart.Data<String,Number>(time.get(i),height.get(i)));
			
		}
		*/
		
		
		
		lineChart.getData().add(data);
		root.getChildren().add(lineChart);
		
		primaryStage.setTitle("LineChart");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
		
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			Integer testing = ThreadLocalRandom.current().nextInt(10);
			
			
		Platform.runLater(()-> {
			Date now = new Date();
			data.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), testing));
			
			if (data.getData().size() > window_size)
				data.getData().remove(0);
		});
		}, 0, 1, TimeUnit.SECONDS);
		
	}
	
	@Override
	public void stop() throws Exception{
		super.stop();
		scheduledExecutorService.shutdownNow();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}


