package controller.gui;

import javafx.beans.property.SimpleIntegerProperty;

public class DataPoint {
	private SimpleIntegerProperty time;
	private SimpleIntegerProperty data;
	
	public DataPoint(Integer time, Integer data) {
		this.setTime(new SimpleIntegerProperty(time));
		this.setData(new SimpleIntegerProperty(data));
	}

	public SimpleIntegerProperty getTime() {
		return time;
	}

	public void setTime(SimpleIntegerProperty time) {
		this.time = time;
	}

	public SimpleIntegerProperty getData() {
		return data;
	}

	public void setData(SimpleIntegerProperty data) {
		this.data = data;
	}
	
	
}
