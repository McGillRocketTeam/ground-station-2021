

import java.io.FileNotFoundException;
import java.io.IOException;
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

public class MainApp extends Application {

	private final Mode mode = Mode.OLD;
	private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void start(Stage stage) throws Exception {
    	
    	
    	
        Label l = new Label("McGill Rocket Team Ground Station");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MainApp.fxml"));
        Parent root = fxmlLoader.load();
        Scene mainApp = new Scene(root, 1024,768);
        MainAppController mainAppController = (MainAppController)fxmlLoader.getController();
        mainAppController.mainAppInitializeGraphs();
        mainAppController.mainAppInitializeMap();
        mainAppController.mainAppIntitializeRawData();     
        mainAppController.mainAppInitializeGyro();
//        ((Pane)mainApp.getRoot()).getChildren().add(gyroController.initializeGyro().getRoot());

   
        stage.setTitle("McGill Rocket Team Ground Station");
        
        
		Parser parser = new Parser(11);
		ArrayList<String> myData = new ArrayList<String>();
		ArrayList<double[]> myDataArrays = new ArrayList<double[]>();
		switch (mode) {
			case OLD:
				try {
					myData = (ArrayList<String>) Parser.storeData("src/main/resources/zheng1.txt");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (String str: myData) {
					try {
						myDataArrays.add(parser.parse((str)));
					} catch (IllegalArgumentException e) {
						System.out.println("Bad line");
						System.out.println(e.toString());
					}
				}
				
				scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
				Iterator<double[]> dataItr = myDataArrays.iterator();
				
				scheduledExecutorService.scheduleAtFixedRate(() -> {
					double[] data = dataItr.next();
					
				Platform.runLater(()-> {

				//	System.out.println(data[3]);
					Date now = new Date();
					mainAppController.mainAppAddGraphData(data);
					mainAppController.mainAppAddMapData(data);
					mainAppController.mainAppAddRawData(data);
					mainAppController.startTimer(data);
					mainAppController.mainAppAddGyroData(data);

				
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
