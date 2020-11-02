package controller.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
	
	@FXML
	private Label peakAltitudeLabel;
	
	@FXML
	public void setPeakAltitudeLabel(){
		peakAltitudeLabel.setText("10");
	}
	public void initializeNumberDisplays() {
		
	}
}
