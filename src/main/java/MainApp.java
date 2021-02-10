

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
import controller.gui.MainAppController;
import controller.gui.Mode;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class MainApp extends Application {

	private final Mode mode = Mode.OLD;
	private final EnumMap<DataIndex, Integer> DataFormat = new EnumMap<DataIndex, Integer>(DataIndex.class);
	private ScheduledExecutorService scheduledExecutorService;


    
    @Override
    public void start(Stage stage) throws Exception {
        Label l = new Label("McGill Rocket Team Ground Station");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MainApp.fxml"));
        Parent root = fxmlLoader.load();
        Scene mainApp = new Scene(root, 1920,1080);
        MainAppController mainAppController = (MainAppController)fxmlLoader.getController();
 	    mainAppController.mainAppInitializeGraphs();
        mainAppController.mainAppInitializeMap();

   
        stage.setTitle("McGill Rocket Team Ground Station");
        
        
		Parser parser = new Parser(10);
		ArrayList<String> myData = new ArrayList<String>();
		ArrayList<double[]> myDataArrays = new ArrayList<double[]>();
		setDataFormat();
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
				
				scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
				Iterator<double[]> dataItr = myDataArrays.iterator();
				ArrayList<Integer> testArr = new ArrayList<>();
				testArr.add(1);
				testArr.add(10);
				testArr.add(20);
				testArr.add(30);
				testArr.add(40);
				testArr.add(41);
				testArr.add(42);
				Iterator<Integer> testArrItr = testArr.iterator();
				scheduledExecutorService.scheduleAtFixedRate(() -> {
						double[] data = dataItr.next();
						int test = testArrItr.next();
						Platform.runLater(()-> {
							System.out.println(data[3]);
							Date now = new Date();
							System.out.println(data[8]);
//							group.setRotateYAnimated(test);
//							group.setRotateXAnimated(test);
							//cameraXform.rx.setAngle(data[8]);
							mainAppController.mainAppAddGraphData(data, DataFormat);
							mainAppController.startTimer(data,DataFormat);
		
					
					});
				}, 0, 1000, TimeUnit.MILLISECONDS);
				
			case SIMULATION:
				break;
			case LIVE:
				break;
		}

	
//        stage.setScene(mainApp);
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
	private void setDataFormat() {
		DataFormat.put(DataIndex.ALTITUDE_INDEX, 3);
		DataFormat.put(DataIndex.VELOCITY_INDEX, 4);
		DataFormat.put(DataIndex.ACCELERATION_INDEX, 5);
		DataFormat.put(DataIndex.RSSI_INDEX, 9);
	}
	

}


