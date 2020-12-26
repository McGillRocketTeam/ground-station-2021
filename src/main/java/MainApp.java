

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import controller.Parser;
import controller.gui.GraphController;
import controller.gui.MainController;
import controller.gui.Mode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MainApp extends Application {

	static final Mode mode = Mode.OLD;
	private ScheduledExecutorService scheduledExecutorService;
	
    @Override
    public void start(Stage stage) throws Exception {
    	
    	
    	
        Label l = new Label("McGill Rocket Team Ground Station");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MainApp.fxml"));
     //   FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("fxml/MainApp.fxml"));
        Parent root = fxmlLoader.load();
        Scene mainApp = new Scene(root, 1920,1080);
        GraphController graphController = (GraphController)fxmlLoader.getController();
        graphController.initializeAltitudeChart();
        graphController.initializeVelocityChart();
        graphController.initializeAccelerationChart();
        graphController.initializeRSSIChart();
        graphController.initializeMap();
   
        stage.setTitle("McGill Rocket Team Ground Station");
        
        
		Parser parser = new Parser(10);
		ArrayList<String> myData = new ArrayList<String>();
		ArrayList<double[]> myDataArrays = new ArrayList<double[]>();
		switch (mode) {
			case OLD:
				try {
					myData = (ArrayList<String>) Parser.storeData("src/main/resources/Data_last_year.txt");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (String str: myData) {
					try {
						myDataArrays.add(parser.parse((str)));
					} catch (IllegalArgumentException e) {
						System.out.println("Bad line");
					}
				}
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
				
				scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
				Iterator<double[]> dataItr = myDataArrays.iterator();
				
				scheduledExecutorService.scheduleAtFixedRate(() -> {
					double[] data = dataItr.next();
					
				Platform.runLater(()-> {
					System.out.println(data[3]);
					Date now = new Date();
					graphController.addAltitudeData(simpleDateFormat.format(now), data[3]);
					graphController.addVelocityData(simpleDateFormat.format(now), data[4]);
					graphController.addAccelerationData(simpleDateFormat.format(now), data[5]);
					graphController.addRSSIData(simpleDateFormat.format(now), data[9]);
					graphController.initializeNumDisplay(myDataArrays);
				
				});
				}, 0, 1000, TimeUnit.MILLISECONDS);
				
			case SIMULATION:
				break;
			case LIVE:
				break;
		}

	
        stage.setScene(mainApp);
        stage.show();

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
