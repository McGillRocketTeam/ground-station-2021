package controller.gui;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * The class used to represent data points that are meant to be displayed on the Raw Data page of the GUI.
 * The class is necessary to conform to the TableView implementation used in RawDataController
 * @author Jeremy Chow
 *
 */

public class DataPoint {
	
	/**
	 * The x axis which will almost always be with respect to time
	 */
	private Double time;
	
	/**
	 * The y axis which can represent any form of data such as altitude, velocity, acceleration, in any unit
	 */
	private Double data;
	
	/**
	 * Constructor for data point
	 * @param time the x value of the data point
	 * @param data the y value of the data point
	 */
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
	

