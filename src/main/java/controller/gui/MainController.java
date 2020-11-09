package controller.gui;

import java.util.Arrays;
import java.util.ArrayList;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


import javafx.scene.control.Label;

public class MainController {
	ArrayList<String> strings = new ArrayList<String>(
			Arrays.asList("1","2","3","4","5","1","2","3","4","5","1","2","3","4","30","1","2","3","4","5"));
	
	
	@FXML
	public Label peakAltitudeLabel;
	
	@FXML
	public void setPeakAltitudeLabel(String value){
		this.peakAltitudeLabel.setText(value);
	}
	
    int i = 0;
	
	public void startTimer() {
	    Timeline timeline = new Timeline();
	    timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, event -> {

	        if (Integer.parseInt(strings.get(i)) > Integer.parseInt(peakAltitudeLabel.getText())) setPeakAltitudeLabel(strings.get(i).toString());
	        i++;
	    }));
	    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1)));
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.play();
	    
	 }
	
	public void initializeNumberDisplays() {
		
	}

}
	