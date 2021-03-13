
	package controller.gui;

	import java.io.InputStream;

	import javafx.fxml.FXML;
	import javafx.scene.image.Image;
	import javafx.scene.image.ImageView;
	import javafx.scene.paint.Color;
	import javafx.scene.shape.Circle;
	import javafx.scene.text.Text;

	import java.io.FileInputStream; 
	import java.io.IOException;
	import java.io.InputStream;
	import javafx.application.Application; 
	import javafx.scene.Group; 
	import javafx.scene.Scene;  
	import javafx.stage.Stage;  
	import javafx.geometry.Insets;

	import javafx.scene.layout.StackPane;
	import javafx.scene.control.Label;
	import javafx.scene.control.Button;
	import javafx.scene.layout.*; 
	import javafx.event.ActionEvent; 
	import javafx.event.EventHandler; 
	import javafx.scene.transform.Scale;

	import javafx.scene.canvas.Canvas;
	import javafx.scene.canvas.GraphicsContext;
	import javafx.event.EventHandler;
	import javafx.scene.layout.Pane;
	import javafx.scene.paint.CycleMethod;
	import javafx.scene.paint.LinearGradient;
	import javafx.scene.paint.Stop;
	import javafx.scene.layout.VBox;
	import java.util.*;

	import javafx.collections.ObservableList; 

	import javafx.application.Application;
	import javafx.beans.InvalidationListener;
	import javafx.beans.Observable;
	import javafx.beans.property.DoubleProperty;
	import javafx.beans.property.SimpleDoubleProperty;
	import javafx.event.EventHandler;
	import javafx.scene.Scene;
	import javafx.scene.control.ScrollPane;
	import javafx.scene.image.Image;
	import javafx.scene.input.ScrollEvent;
	import javafx.fxml.FXMLLoader;

	import javafx.scene.text.TextAlignment;
	
	
	public class MapController {
		
		/* -=-=-=-=-=-=-=-=-=-=-=-INFO-=-=-=-=-=-=-=-=-=-=-=-
		 * 
		 * IF YOU CHOOSE A NEW MAP IMAGE, MAKE SURE TO UPDATE THE SIZE AND CORNERS
		 * 
		 * ALL NEW MAP IMAGES SHOULD BE ORIENTED WITH NORTH == Y-AXIS
		 * OTHERWISE DIRECTIONS WILL BE OFF
		 * 
		 * 
		*/
		
		//	=== Create labels ===
//        //	(Initially no data is entered)
//        Label latlabel = new Label ("Lat: No data");
//        Label lonlabel = new Label ("Lon: No data");
        
		Circle circle = new Circle();
        Circle circle2 = new Circle();
		
		// THE STARTING SIZE OF THE IMAGE
		private int image_width  = 2878	 	/10;
		private int image_height = 1634		/10;
		
		// THE ENLARGED SIZE OF THE IMAGE
		private int image_width2  = 2878 	/2;
		private int image_height2 = 1634 	/2;
		
		// THE CORNER LATITUDES AND LONGITUDES 
		final double lower_lat = 33.1736; // Top of the screen
		final double upper_lat = 32.8802; // Bottom of the screen
		
		final double lower_lon = 107.2299; // Left of the screen
		final double upper_lon = 106.7599; // Right of the screen
		
		//	CONVERTING LATS AND LONS TO DECIMALS
	    public static double decimal_converter(double degrees, double minutes, double seconds) {
	    	// ***ONLY USE THIS IF YOU WANT TO PASS IN THE FORM DEGREES, MINUTES, SECONDS.
	    	// note: old data is not in this form
	    	return degrees + minutes/60 + seconds/3600;
	    }
	    
	    //	CONVERTING LON TO X VALUE
	    public int find_x (double lon, int width) {
//	    	System.out.println("x before conversion: "+lon);
	    	int x = Math.abs((int)(((lon - lower_lon)/(upper_lon-lower_lon))*width));
//	    	System.out.println("x after conversion: "+x);
	    	return x;
	    }
	    
	    //	CONVERTING LAT TO Y VALUE
	    public int find_y (double lat, int height) {
//	    	System.out.println("y before conversion: "+lat);
	    	int y = Math.abs((int)(((lat - lower_lat)/(upper_lat-lower_lat))*height));
//	    	System.out.println("y after conversion: "+y);
	    	return y;
	    }
	    
	    @FXML
	    StackPane stackpane;
	    
	    @FXML
	    StackPane stackpane2;
	    
	    @FXML
	    Label latlabel;
	   
	    @FXML
	    Label lonlabel;
	    
		public void initializeMap() throws Exception{
			
	        // 	=== Get the image ===
	        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	    	InputStream stream = classloader.getResourceAsStream("GoogleEarthMap.JPG");
	        Image image = new Image(stream);
	        ImageView iv = new ImageView(image);
	        ImageView iv2 = new ImageView(image);
	        iv.setFitWidth(image_width);
	        iv.setPreserveRatio(true);
	        
	        //	=== Create button and button activation ===
	        Button button = new Button();
	        
	    	button.setText("Enlarge Map");
	        
	        EventHandler <ActionEvent> event = new EventHandler <ActionEvent> () {
	        	public void handle(ActionEvent e) {
	        		
	        		e.consume();
	        		
	        		iv2.setFitWidth(image_width2);
	        		iv2.setPreserveRatio(true);
	        		
	        		StackPane secondaryLayout = new StackPane();
	                secondaryLayout.getChildren().add(iv2);
	                secondaryLayout.getChildren().add(circle2);
	                
	                Scene secondScene = new Scene(secondaryLayout, image_width2,image_height2);

	                Stage newWindow = new Stage();
	                newWindow.setTitle("Map");
	                newWindow.setScene(secondScene);
	                newWindow.show();
	            } 
	        }; 
	        
	        button.setOnAction(event);
	        
	        //	==== Add to stackpanes to send to FXML file ====
	        stackpane.getChildren().add(iv);
	        stackpane.getChildren().add(circle);
	        stackpane.setPadding(new Insets(20));
	       
	        stackpane2.getChildren().add(button);
	        stackpane2.setPadding(new Insets(20));
	        
//	        stackpane3.getChildren().add(latlabel);
//	        stackpane4.getChildren().add(lonlabel);
		}
		
		
		public void addMapData(double[] data) {
			
//			=== SIZE & COLOR OF CIRCLE ===
			//  Integrated display
	        circle.setRadius(5); 
	        circle.setFill(Color.RED);
			       
			//	Enlarged display
			circle2.setRadius(5); 
			circle2.setFill(Color.RED);
			
			//	=== Get data ===
			float x = Math.abs(Float.parseFloat(String.valueOf(data[DataIndex.LONGITUDE_INDEX.getOrder()])));
			float y = Math.abs(Float.parseFloat(String.valueOf(data[DataIndex.LATITUDE_INDEX .getOrder()])));
			
			//	=== Apply data ===
			//	Integrated Display
			circle.setTranslateX(-image_width  / 2 + find_x(x,image_width ));
			circle.setTranslateY(-image_height / 2 + find_y(y,image_height));
			
			//	Enlarged Display
			circle2.setTranslateX(-image_width2  / 2 + find_x(x,image_width2 ));
			circle2.setTranslateY(-image_height2 / 2 + find_y(y,image_height2));
			
			//	Create labels 
			latlabel.setText("Lat: "+x);
			lonlabel.setText("Lon: "+y);
			latlabel.setTextAlignment(TextAlignment.CENTER);
			lonlabel.setTextAlignment(TextAlignment.CENTER);
		}
	}
