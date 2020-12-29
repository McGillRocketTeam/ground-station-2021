package controller.gui;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class NumbersController {
	
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

	int i = 0;
	public void initializeNumDisplay(ArrayList<double[]> myDataList) {
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO,event ->{
			//System.out.println(myDataList.get(i)[5]);
			if (myDataList.get(i)[3] > Double.parseDouble(peakAltitudeLabel.getText())) 
				setPeakAltitudeLabel(String.valueOf(myDataList.get(i)[3]));
			setCurrentAltitudeLabel(String.valueOf(myDataList.get(i)[3]));
			if (myDataList.get(i)[4]>Double.parseDouble(peakVelocityLabel.getText())) 
				setPeakVelocityLabel(String.valueOf(myDataList.get(i)[4]));
			setCurrentVelocityLabel(String.valueOf(myDataList.get(i)[4]));
			if (myDataList.get(i)[4]>Double.parseDouble(peakAccelerationLabel.getText())) 
			setPeakAccelerationLabel(String.valueOf(myDataList.get(i)[5]));
			setCurrentAccelerationLabel(String.valueOf(myDataList.get(i)[5]));
			setCurrentRSSILabel(String.valueOf(myDataList.get(i)[9]));
			if((i+1)<myDataList.size()) i++;
		}));
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
}
