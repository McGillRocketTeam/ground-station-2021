package controller.gui;

import java.util.ArrayList;

import java.util.EnumMap;

//import controller.serial.SerialComm;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class NumbersController {
	
	//SerialComm serialComm = new SerialComm();
	ArrayList<String> read;
	
	@FXML
	public Label peakAltitudeLabel;
	public Label currentAltitudeLabel;
	public Label peakVelocityLabel;
	public Label currentVelocityLabel;
	public Label peakAccelerationLabel;
	public Label currentAccelerationLabel;
	public Label currentRSSILabel;
	
	@FXML
	public void setPeakAltitudeLabel(String value){
		this.peakAltitudeLabel.setText(value);
	}
	
	@FXML
	public void setCurrentAltitudeLabel(String value){
		this.currentAltitudeLabel.setText(value);
	}
	@FXML
	public void setPeakVelocityLabel(String value){
		this.peakVelocityLabel.setText(value);
	}
	@FXML
	public void setCurrentVelocityLabel(String value){
		this.currentVelocityLabel.setText(value);
	}
	@FXML
	public void setPeakAccelerationLabel(String value) {
		this.peakAccelerationLabel.setText(value);
	}
	@FXML
	public void setCurrentAccelerationLabel(String value){
		this.currentAccelerationLabel.setText(value);
	}
	@FXML
	public void setCurrentRSSILabel(String value) {
		this.currentRSSILabel.setText(value);
	}


	public void updateNumDisplay(double[] data) {

		//System.out.println(myDataList.get(i)[5]);
		
		//set Peak Altitude 
		if (data[DataIndex.ALTITUDE_INDEX.getOrder()] > Double.parseDouble(peakAltitudeLabel.getText())) 
			setPeakAltitudeLabel(String.valueOf(data[DataIndex.ALTITUDE_INDEX.getOrder()]));
		
		//set Current Altitude 
		setCurrentAltitudeLabel(String.valueOf(data[DataIndex.ALTITUDE_INDEX.getOrder()]));
		
		//set Peak Velocity 
//		if (data[DataIndex.VELOCITY_INDEX.getOrder()]>Double.parseDouble(peakVelocityLabel.getText())) 
//			setPeakVelocityLabel(String.valueOf(data[DataIndex.VELOCITY_INDEX.getOrder()]));
		if (data[DataIndex.ACCEL_X_INDEX.getOrder()] > Double.parseDouble(peakAltitudeLabel.getText())) 
			setPeakAltitudeLabel(String.valueOf(data[DataIndex.ACCEL_X_INDEX.getOrder()]));
		
		//set Current Velocity 
//		setCurrentVelocityLabel(String.valueOf(data[DataIndex.VELOCITY_INDEX.getOrder()]));
		setCurrentVelocityLabel(String.valueOf(data[DataIndex.ACCEL_X_INDEX.getOrder()]));
		
		//set Peak Acceleration
//		if (data[DataIndex.ACCELERATION_INDEX.getOrder()]>Double.parseDouble(peakAccelerationLabel.getText())) 
//		setPeakAccelerationLabel(String.valueOf(data[DataIndex.ACCELERATION_INDEX.getOrder()]));
		if (data[DataIndex.ACCEL_Y_INDEX.getOrder()]>Double.parseDouble(peakAccelerationLabel.getText())) 
			setPeakAccelerationLabel(String.valueOf(data[DataIndex.ACCEL_Z_INDEX.getOrder()]));
		
		//set Current Acceleration
		setCurrentAccelerationLabel(String.valueOf(data[DataIndex.ACCEL_Y_INDEX.getOrder()]));
		
		//setCurrentRSSI
		setCurrentRSSILabel(String.valueOf(data[DataIndex.RSSI_INDEX.getOrder()]));
	}
}
