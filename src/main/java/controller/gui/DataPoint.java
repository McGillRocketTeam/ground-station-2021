package controller.gui;

import javafx.beans.property.SimpleIntegerProperty;

public class DataPoint {
	
	private Double time;
	private Double data;
	
	public DataPoint(Double  time, Double data) {
		this.setTime(time);
		this.setData(data);
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Double getData() {
		return data;
	}

	public void setData(Double data) {
		this.data = data;
	}
	
}
	

