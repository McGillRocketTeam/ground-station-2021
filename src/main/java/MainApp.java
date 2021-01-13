

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

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class MainApp extends Application {

	private final Mode mode = Mode.OLD;
	private final EnumMap<DataIndex, Integer> DataFormat = new EnumMap<DataIndex, Integer>(DataIndex.class);
	private ScheduledExecutorService scheduledExecutorService;

	final Group root = new Group();
	final Xform axisGroup = new Xform();
    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    private static final double CAMERA_INITIAL_DISTANCE = -450;
    private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 250.0;
    
    @Override
    public void start(Stage stage) throws Exception {
    	
    	buildCamera();
        buildAxes();
    	
        Label l = new Label("McGill Rocket Team Ground Station");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MainApp.fxml"));
        Parent root = fxmlLoader.load();
//        Scene mainApp = new Scene(root, 1920,1080);
//        MainAppController mainAppController = (MainAppController)fxmlLoader.getController();
//        mainAppController.mainAppInitializeGraphs();
//        mainAppController.mainAppInitializeMap();
        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.GREY);
   
        stage.setTitle("McGill Rocket Team Ground Station");
        
        
//		Parser parser = new Parser(10);
//		ArrayList<String> myData = new ArrayList<String>();
//		ArrayList<double[]> myDataArrays = new ArrayList<double[]>();
//		setDataFormat();
//		switch (mode) {
//			case OLD:
//				try {
//					myData = (ArrayList<String>) Parser.storeData("src/main/resources/Data_last_year.txt");
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				for (String str: myData) {
//					try {
//						myDataArrays.add(parser.parse((str)));
//					} catch (IllegalArgumentException e) {
//						System.out.println("Bad line");
//					}
//				}
//				
//				scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//				Iterator<double[]> dataItr = myDataArrays.iterator();
//				
//				scheduledExecutorService.scheduleAtFixedRate(() -> {
//					double[] data = dataItr.next();
//					
//				Platform.runLater(()-> {
//
//					System.out.println(data[3]);
//					Date now = new Date();
//					mainAppController.mainAppAddGraphData(data, DataFormat);
//					mainAppController.startTimer(data,DataFormat);
//
//				
//				});
//				}, 0, 1000, TimeUnit.MILLISECONDS);
//				
//			case SIMULATION:
//				break;
//			case LIVE:
//				break;
//		}

	
//        stage.setScene(mainApp);
        stage.setScene(scene);
        stage.show();
        scene.setCamera(camera);


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
	
	private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);
 
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }
	private void buildAxes() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
 
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
 
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
 
        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);
        
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
 
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(true);
        world.getChildren().addAll(axisGroup);
    }

}
