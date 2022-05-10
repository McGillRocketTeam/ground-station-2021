package controller.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CoordinatesController {
	@FXML
	public Label currentLatitudeValue;
	public Label currentLongitudeValue;
	
	@FXML 
	public void setCurrentLatitudeValue(String value) {
		this.currentLatitudeValue.setText(value);
	}
	@FXML 
	public void setCurrentLongitudeValue(String value) {
		this.currentLongitudeValue.setText(value);
	}
	
	public void updateCoordinatesDisplay(double[] data) {
		setCurrentLatitudeValue(String.format("%.7f", data[DataIndex.LATITUDE_INDEX.getOrder()]));
		setCurrentLongitudeValue(String.format("%.7f", data[DataIndex.LONGITUDE_INDEX.getOrder()]));
	}
}
