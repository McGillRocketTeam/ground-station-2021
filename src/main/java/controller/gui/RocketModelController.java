package controller.gui;

import controller.gui.Gyro3dController.SmartGroup;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class RocketModelController {
	final SmartGroup axisGroup = new SmartGroup();
	final SmartGroup rocketGroup = new SmartGroup();
    final PerspectiveCamera camera = new PerspectiveCamera(true);

    
    private static final double CAMERA_INITIAL_DISTANCE = -450;
    private static final double CAMERA_INITIAL_X_ANGLE = 0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 250.0;
    
    
    /**
     * Initialize the Gyro scene. Adds the 3D models to the scene as well as builds the 3D camera.
     * @return the subscene to be added to the main window
     */
    public SubScene initializeGyro() {
    	Cylinder rocket = new Cylinder(20, 100);
    	PhongMaterial rocketMaterial = new PhongMaterial();
    	rocketMaterial.setSelfIlluminationMap(new Image ("Logo_F_RW.png"));
    	rocket.setMaterial(rocketMaterial);
    	rocketGroup.getChildren().add(rocket);
    	
        buildAxes();
    	
    	Group group = new Group();
    	group.getChildren().add(axisGroup);
    	group.getChildren().add(rocketGroup);
    	
    	buildCamera();
    	
        SubScene scene = new SubScene(group, 400, 300, true, null);
        
        scene.setFill(Color.GREY);
        scene.setCamera(camera);
        return scene;
    }
    
    /**
     * Builds the 3D camera used in the scene
     */
	private void buildCamera() {

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
    }
	
	/** 
	 * Builds the 3D model of the x, y, and z axes
	 */
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
 //       root.getChildren().addAll(axisGroup);
    }
	
	/**
	 * Inner class used to apply attributes to a 3D object so that it can be rotated easily
	 * 
	 * @author Jeremy Chow
	 *
	 */
	class SmartGroup extends Group {
		Transform t = new Rotate();
		Rotate rx = new Rotate(0, new Point3D(1,0,0));
		Rotate ry = new Rotate(0, new Point3D(0,1,0));
		Rotate rz = new Rotate(0, new Point3D(0,0,1));
		
		void setRotateX(double ang) {;
			rx.setAngle(ang);
			this.getTransforms().add(rx);

		}
		void setRotateY(double ang) {
			ry.setAngle(ang);
			this.getTransforms().add(ry);
		}
		void setRotateZ(double ang) {
			rz.setAngle(ang);
			this.getTransforms().add(rz);
		}
		void rotate(double x, double y, double z) {

			setRotateX(x);
			setRotateY(y);
			setRotateZ(z);
		}
		
		
		/*
		 * pitch (around its X axis), yaw (around its Y axis) and roll (around its Z axis),
		 * alf is roll, bet is pitch and gam is yaw.
		 * https://stackoverflow.com/questions/30145414/rotate-a-3d-object-on-3-axis-in-javafx-properly
		 */
		
		/**
		 *  Math for converting pitch yaw and roll to usable coordinates for rotation. ** Untested **
		 * @param n
		 * @param alf
		 * @param bet
		 * @param gam
		 */
		private void matrixRotateNode(Node n, double alf, double bet, double gam){
		    double A11=Math.cos(alf)*Math.cos(gam);
		    double A12=Math.cos(bet)*Math.sin(alf)+Math.cos(alf)*Math.sin(bet)*Math.sin(gam);
		    double A13=Math.sin(alf)*Math.sin(bet)-Math.cos(alf)*Math.cos(bet)*Math.sin(gam);
		    double A21=-Math.cos(gam)*Math.sin(alf);
		    double A22=Math.cos(alf)*Math.cos(bet)-Math.sin(alf)*Math.sin(bet)*Math.sin(gam);
		    double A23=Math.cos(alf)*Math.sin(bet)+Math.cos(bet)*Math.sin(alf)*Math.sin(gam);
		    double A31=Math.sin(gam);
		    double A32=-Math.cos(gam)*Math.sin(bet);
		    double A33=Math.cos(bet)*Math.cos(gam);

		    double d = Math.acos((A11+A22+A33-1d)/2d);
		    if(d!=0d){
		        double den=2d*Math.sin(d);
		        Point3D p= new Point3D((A32-A23)/den,(A13-A31)/den,(A21-A12)/den);
		        n.setRotationAxis(p);
		        n.setRotate(Math.toDegrees(d));                    
		    }
		}
	}

	/**
	 * Rotate the the rocket model given the gyroscopic telemetry data
	 * @param data
	 */
	public void addGyroData(double[] data) {
//		rocketGroup.setRotateX(data[DataIndex.PITCH_INDEX.getOrder()]);
//		rocketGroup.setRotateY(data[DataIndex.ROLL_INDEX.getOrder()]);
//		rocketGroup.setRotateZ(data[DataIndex.ROLL_INDEX.getOrder()]);
		rocketGroup.rotate(data[DataIndex.PITCH_INDEX.getOrder()],data[DataIndex.ROLL_INDEX.getOrder()],data[DataIndex.YAW_INDEX.getOrder()]);
		//rocketGroup.rotate(45,45,45);
	}
}
