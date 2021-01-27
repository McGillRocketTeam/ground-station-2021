

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

	final Group axisGroup = new Group();
    final PerspectiveCamera camera = new PerspectiveCamera(true);

    private static final double CAMERA_INITIAL_DISTANCE = -450;
    private static final double CAMERA_INITIAL_X_ANGLE = 0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 250.0;
    
    @Override
    public void start(Stage stage) throws Exception {
    	Box box = new Box(100, 20, 50);
    	SmartGroup group = new SmartGroup();
    	group.getChildren().add(box);
    	buildCamera();
   //     buildAxes();
    	
        Label l = new Label("McGill Rocket Team Ground Station");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MainApp.fxml"));
//        Parent root = fxmlLoader.load();
//        Scene mainApp = new Scene(root, 1920,1080);
//        MainAppController mainAppController = (MainAppController)fxmlLoader.getController();
// 	      mainAppController.mainAppInitializeGraphs();
//        mainAppController.mainAppInitializeMap();
        Scene scene = new Scene(group, 1024, 768, true);
        scene.setFill(Color.GREY);
   
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
					group.setRotateYAnimated(test);
					group.setRotateXAnimated(test);
					//cameraXform.rx.setAngle(data[8]);
//					mainAppControlle.mainAppAddGraphData(data, DataFormat);
//					mainAppController.startTimer(data,DataFormat);

				
				});
				}, 0, 1000, TimeUnit.MILLISECONDS);
				
			case SIMULATION:
				break;
			case LIVE:
				break;
		}

	
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

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
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
    //    root.getChildren().addAll(axisGroup);
    }
	
	class SmartGroup extends Group {
		Rotate r;
		Transform t = new Rotate();
		RotateTransition rXTransition = new RotateTransition(Duration.millis(100),this);
		RotateTransition rYTransition = new RotateTransition(Duration.millis(100),this);
		void rotateByX(double ang) {
			r = new Rotate(ang, Rotate.X_AXIS);
			t = t.createConcatenation(r);
			this.getTransforms().clear();
			this.getTransforms().addAll(t);
		}

		void rotateByY(double ang) {
			r = new Rotate(ang, Rotate.Y_AXIS);
			t = t.createConcatenation(r);
			this.getTransforms().clear();
			this.getTransforms().addAll(t);
		}
		void setRotateX(double ang) {
//			t = new Rotate(data, new Point3D(1,0,0));
//			this.getTransforms().add(t);

		}
		void setRotateY(double ang) {
			t = new Rotate(ang, new Point3D(0,1,0));
			this.getTransforms().add(t);
		}
		void setRotateXAnimated(double ang) {
			rXTransition.setToAngle(ang);
			rXTransition.setAxis(new Point3D(10,50,0));
			rXTransition.play();
		}
		void setRotateYAnimated(double ang) {
			rYTransition.setToAngle(ang);
			rYTransition.setAxis(new Point3D(10,50,0));
			rYTransition.play();
		}
	}

}
