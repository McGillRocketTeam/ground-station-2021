package controller.gui;

/**
 * The Enum class that describes the ordering of data values in the received telemetry string.
 * This ordering is used throughout the program.
 * 
 * Example: To find out where the index of the Time value will be in the telemetry string,
 * 			use <code>DataIndex.TIME_INDEX.getOrder()</code>
 *  
 * @author jeremychow
 *
 */

public enum DataIndex {
	LATITUDE_INDEX(0),
	LONGITUDE_INDEX(1),
	TIME_INDEX(2),
	ALTITUDE_INDEX(3),
	VELOCITY_INDEX(4),
	ACCELERATION_INDEX(5),
	GYROX_INDEX(8),
	RSSI_INDEX(9);
	
	private Integer order;

	private DataIndex(final Integer order) {
		this.order = order;
	}
	
	public Integer getOrder() {
		return order;
	}
}



