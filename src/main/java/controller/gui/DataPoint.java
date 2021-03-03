package controller.gui;

import javafx.beans.property.SimpleIntegerProperty;

public class DataPoint {
//	private SimpleIntegerProperty time;
//	private SimpleIntegerProperty data;
//	
//	public DataPoint(Integer time, Integer data) {
//		this.setTime(new SimpleIntegerProperty(time));
//		this.setData(new SimpleIntegerProperty(data));
//	}
//
//	public SimpleIntegerProperty getTime() {
//		return time;
//	}
//
//	public void setTime(SimpleIntegerProperty time) {
//		this.time = time;
//	}
//
//	public SimpleIntegerProperty getData() {
//		return data;
//	}
//
//	public void setData(SimpleIntegerProperty data) {
//		this.data = data;
//	}
	
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
	

