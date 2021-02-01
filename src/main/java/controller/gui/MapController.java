
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
		
		// THE STARTING SIZE OF THE IMAGE
		private int image_width  = 2878	/10;
		private int image_height = 1634	/10;
		
		// THE CORNER LATITUDES AND LONGITUDES 
		final double lower_lat = decimal_converter(33,10,17); // Top of the screen
		final double upper_lat = decimal_converter(32,53,01); // Bottom of the screen
		
		final double lower_lon = decimal_converter(107,06,20); // Left of the screen
		final double upper_lon = decimal_converter(106,48,50); // Right of the screen
		
		
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
	    
		//	DON'T TOUCH THESE
		private String buttonname = "Resize";
		private boolean fullscreen = false;
		private int count = 0;
	    
	    @FXML
	    StackPane stackpane;
	    
	    @FXML
	    Button button;
	    
		public void initializeMap() throws Exception{
			
	    	double lat = decimal_converter(33,05,03);
	    	double lon = decimal_converter(107,03,30);
	        
	        Circle circle = new Circle();
	        Circle circle2 = new Circle();
	        
	        //	LOCATION OF CIRCLE
//	        circle.setTranslateX(find_x(lon));
//	        circle.setTranslateY(find_y(lat));
	        circle.setCenterY(find_x(lon));
	        circle.setCenterY(find_y(lat));
	        
	        //	SIZE & COLOR OF CIRCLE
	        circle.setRadius(5); 
	        circle.setFill(Color.RED);
	        
	        circle2.setRadius(5); 
	        circle2.setFill(Color.RED);
	        
	        
	        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	    	InputStream stream = classloader.getResourceAsStream("GoogleEarthMap.JPG");
	        Image image = new Image(stream);
	        ImageView iv = new ImageView(image);
	        ImageView iv2 = new ImageView(image);
	        iv.setFitWidth(image_width);
	        iv.setPreserveRatio(true);
	        
	    	button.setText("Enlarge Map");
	        
	        EventHandler <ActionEvent> event = new EventHandler <ActionEvent> () {
	        	public void handle(ActionEvent e) {
	        		
	        		e.consume();
	        		
	        		circle2.setCenterX(find_x(lon));
	                circle2.setCenterY(find_y(lat));
	        		 
	        		iv2.setFitWidth(image_width*10);
	        		
	        		
	        		StackPane secondaryLayout = new StackPane();
	                secondaryLayout.getChildren().add(iv2);
	                secondaryLayout.getChildren().add(circle2);
	                
	                
	                
	                Scene secondScene = new Scene(secondaryLayout, 1400,800);
	  
	                 // New window (Stage)
	                Stage newWindow = new Stage();
	                newWindow.setTitle("Second Stage");
	                newWindow.setScene(secondScene);
	                 
	                newWindow.show();
	                
	            } 
	        }; 
	        button.setOnAction(event);

	        stackpane.getChildren().add(iv);
	        stackpane.getChildren().add(circle);
	        stackpane.setPadding(new Insets(20));
		}
	}
