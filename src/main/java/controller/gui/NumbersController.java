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

	public void updateNumDisplay(double[] data, EnumMap<DataIndex, Integer> DataFormat) {
		//TEST
		
		//read = serialComm.serialCom();
	//	System.out.println(read.get(0));
		
		
		//TEST 
		
		//System.out.println(myDataList.get(i)[5]);
		//set Peak Altitude 
		if (data[DataFormat.get(DataIndex.ALTITUDE_INDEX)] > Double.parseDouble(peakAltitudeLabel.getText())) 
			setPeakAltitudeLabel(String.valueOf(data[DataFormat.get(DataIndex.ALTITUDE_INDEX)]));
		//set Current Altitude 
		setCurrentAltitudeLabel(String.valueOf(data[DataFormat.get(DataIndex.ALTITUDE_INDEX)]));
		//set Peak Velocity 
		if (data[DataFormat.get(DataIndex.VELOCITY_INDEX)]>Double.parseDouble(peakVelocityLabel.getText())) 
			setPeakVelocityLabel(String.valueOf(data[DataFormat.get(DataIndex.VELOCITY_INDEX)]));
		//set Current Velocity 
		setCurrentVelocityLabel(String.valueOf(data[DataFormat.get(DataIndex.VELOCITY_INDEX)]));
		//set Peak Acceleration
		if (data[DataFormat.get(DataIndex.ACCELERATION_INDEX)]>Double.parseDouble(peakAccelerationLabel.getText())) 
		setPeakAccelerationLabel(String.valueOf(data[DataFormat.get(DataIndex.ACCELERATION_INDEX)]));
		//set Current Acceleration
		setCurrentAccelerationLabel(String.valueOf(data[DataFormat.get(DataIndex.ACCELERATION_INDEX)]));
		//setCurrentRSSI
		setCurrentRSSILabel(String.valueOf(data[DataFormat.get(DataIndex.RSSI_INDEX)]));
	}
}
