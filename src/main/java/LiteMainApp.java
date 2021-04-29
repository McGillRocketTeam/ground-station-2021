

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import controller.Parser;
import controller.gui.DataIndex;
import controller.gui.GraphController;
import controller.gui.Gyro3dController;
import controller.gui.MainAppController;
import controller.gui.Mode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class LiteMainApp extends Application {

	private final Mode mode = Mode.OLD;
	private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void start(Stage stage) throws Exception {
    	
    	

   
        
        
		Parser parser = new Parser(12);
		ArrayList<String> myData = new ArrayList<String>();
		ArrayList<double[]> myDataArrays = new ArrayList<double[]>();
		switch (mode) {
			case OLD:
				try {
					myData = (ArrayList<String>) Parser.storeData("src/main/resources/jenni1.txt");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (String str: myData) {
					try {
						myDataArrays.add(parser.parseFC((str)));
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid message. Message was thrown out.");
						System.out.println(e.toString());
					} catch (NullPointerException e) {
						System.out.println("Why you passing null to the parser dude");
					}
				}
				for (String s: myData) {
					System.out.println(s);
				}
				
				
			case SIMULATION:
				break;
			case LIVE:
				break;
		}


    }

    public static void main(String[] args) {
        launch();
    }
    
	@Override
	public void stop() throws Exception{
		super.stop();
		scheduledExecutorService.shutdownNow();
	}

}
