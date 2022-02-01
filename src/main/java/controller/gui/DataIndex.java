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
	

	/*
	 * Telemetry
	 */
	
//	PRESSURE_INDEX(0),
//	TEMP_INDEX(1),
	VALVE_STATUS(2),
//	TIME_INDEX(3),
	
	
	
	
	
	
	
//	TIME_INDEX(11),
//	GYROX_INDEX(0),
//	RSSI_INDEX(0),
//	PITCH_INDEX(0),
//	ROLL_INDEX(1),
//	YAW_INDEX(2),
//	ACCEL_X_INDEX(3),
//	ACCEL_Y_INDEX(4),
//	ACCEL_Z_INDEX(5),
//	TEMP_INDEX(6),
//	PRESSURE_INDEX(7),
//	ALTITUDE_INDEX(8),
//	LATITUDE_INDEX(9),
//	LONGITUDE_INDEX(10);
	
	/*
	 * Flight Computer
	 */
	

	TIME_INDEX(9),

	GYROX_INDEX(0),
	RSSI_INDEX(0),
	
	PITCH_INDEX(4),
	ROLL_INDEX(5),
	YAW_INDEX(6),
	ACCEL_X_INDEX(1),
	ACCEL_Y_INDEX(2),
	ACCEL_Z_INDEX(3),
	
	
	TEMP_INDEX(2),
	PRESSURE_INDEX(3),
	
	ALTITUDE_INDEX(7),
	LATITUDE_INDEX(7),
	LONGITUDE_INDEX(8);
	/** 
	 * The index with which the specified value will be found in the telemetry string
	 */
	private Integer order;

	/**
	 * Private constructor for creating new data value to be expected in telemetry string
	 * @param order The index with which the specified value will be found in the telemetry string
	 */
	private DataIndex(final Integer order) {
		this.order = order;
	}
	
	
	/**
	 * Getter for telemetry data index
	 * @return the index with which the specified value will be found in the telemetry string
	 */
	public Integer getOrder() {
		return order;
	}
}



