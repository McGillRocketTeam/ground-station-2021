package controller.gui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * 
 * Controller class for number display on GUI. Responsible for displaying and 
 * updating numerical values received from the AV Bay. 
 * 
 * @author miiyuf
 *
 */

public class NumbertableController {
	//SerialComm serialComm = new SerialComm();
	ArrayList<String> read;
	
	@FXML
	public Label peakAltitudeValue;
	public Label currentAltitudeValue;
	public Label peakXAccelValue;
	public Label currentXAccelValue;
	public Label peakYAccelValue;
	public Label currentYAccelValue;
	public Label peakZAccelValue;
	public Label currentZAccelValue;
	public Label currentRSSIValue;
	public Label currentVelocityValue;
	
	@FXML
	public void setPeakAltitudeValue(String value){
		this.peakAltitudeValue.setText(value);
	}
	
	@FXML
	public void setCurrentAltitudeValue(String value){
		this.currentAltitudeValue.setText(value);
	}
	@FXML
	public void setPeakXAccelValue(String value) {
		this.peakXAccelValue.setText(value);
	}
	@FXML 
	public void setCurrentXAccelValue(String value) {
		this.currentXAccelValue.setText(value);
	}
	@FXML 
	public void setPeakYAccelValue(String value) {
		this.peakYAccelValue.setText(value);
	}
	@FXML 
	public void setCurrentYAccelValue(String value) {
		this.currentYAccelValue.setText(value);
	}
	@FXML 
	public void setPeakZAccelValue(String value) {
		this.peakZAccelValue.setText(value);
	}
	@FXML 
	public void setCurrentZAccelValue(String value) {
		this.currentZAccelValue.setText(value);
	}
	@FXML 
	public void setCurrentRSSIValue(String value) {
		this.currentRSSIValue.setText(value);
	}
	@FXML 
	public void setCurrentVelocityValue(String value) {
		this.currentVelocityValue.setText(value);
	}
	
/**
 * updateNumberDisplay method updates number table display on GUI 
 * @param data to display on the GUI 
 */

	public void updateNumberDisplay(double[] data) {
		// *** ROUND ALL TO TWO DECIMAL PLACES *** using the String.format(...) method
		
		//System.out.println(myDataList.get(i)[5]);
		
		//set Peak Altitude 
		if (data[DataIndex.ALTITUDE_INDEX.getOrder()] > Double.parseDouble(peakAltitudeValue.getText())) 
			setPeakAltitudeValue(String.format("%.2f", data[DataIndex.ALTITUDE_INDEX.getOrder()]));
		
		//set Current Altitude 
		setCurrentAltitudeValue(String.format("%.2f", data[DataIndex.ALTITUDE_INDEX.getOrder()]));
		
		//set Peak X Acceleration
		if (data[DataIndex.ACCEL_X_INDEX.getOrder()] > Double.parseDouble(peakXAccelValue.getText())) 
			setPeakXAccelValue(String.format("%.2f", data[DataIndex.ACCEL_X_INDEX.getOrder()]));
		
		//set Current X Acceleration
		setCurrentXAccelValue(String.format("%.2f", data[DataIndex.ACCEL_X_INDEX.getOrder()]));
		
		//set Peak Y Acceleration
		if (data[DataIndex.ACCEL_Y_INDEX.getOrder()] > Double.parseDouble(peakYAccelValue.getText())) 
			setPeakYAccelValue(String.format("%.2f", data[DataIndex.ACCEL_Y_INDEX.getOrder()]));
		
		//set Current Y Acceleration
		setCurrentYAccelValue(String.format("%.2f", data[DataIndex.ACCEL_Y_INDEX.getOrder()]));
		
		//set Peak Z Acceleration
		if (data[DataIndex.ACCEL_Z_INDEX.getOrder()] > Double.parseDouble(peakZAccelValue.getText())) 
			setPeakZAccelValue(String.format("%.2f", data[DataIndex.ACCEL_Z_INDEX.getOrder()]));
		
		//set Current Z Acceleration
		setCurrentZAccelValue(String.format("%.2f", data[DataIndex.ACCEL_Z_INDEX.getOrder()]));
		
		//setCurrentRSSI
//		setCurrentRSSIValue(String.format("%.2f", data[DataIndex.RSSI_INDEX.getOrder()]));
		
		//set Current Velocity (TODO: fix or remove velocity)
		setCurrentVelocityValue("Which Index");

		//set Peak Acceleration
//		if (data[DataIndex.ACCELERATION_INDEX.getOrder()]>Double.parseDouble(peakAccelerationLabel.getText())) 
//		setPeakAccelerationLabel(String.format("%.2f", data[DataIndex.ACCELERATION_INDEX.getOrder()]));

		

	}
}
