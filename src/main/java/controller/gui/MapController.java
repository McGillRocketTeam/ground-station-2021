package controller.gui;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
}
