package controller.gui;

import javafx.animation.RotateTransition;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;

public class Gyro3DController {
	final Group axisGroup = new Group();
    final PerspectiveCamera camera = new PerspectiveCamera(true);

    private static final double CAMERA_INITIAL_DISTANCE = -450;
    private static final double CAMERA_INITIAL_X_ANGLE = 0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 250.0;
    
    public void initializeGyro() {
    	Box box = new Box(100, 20, 50);
    	SmartGroup group = new SmartGroup();
    	group.getChildren().add(box);
    	buildCamera();
        Scene scene = new Scene(group, 1024, 768, true);
        scene.setFill(Color.GREY);
    	
        scene.setCamera(camera);
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
			t = new Rotate(ang, new Point3D(1,0,0));
			this.getTransforms().add(t);

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
		
		/*
		 * pitch (around its X axis), yaw (around its Y axis) and roll (around its Z axis),
		 * alf is roll, bet is pitch and gam is yaw.
		 * https://stackoverflow.com/questions/30145414/rotate-a-3d-object-on-3-axis-in-javafx-properly
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
}