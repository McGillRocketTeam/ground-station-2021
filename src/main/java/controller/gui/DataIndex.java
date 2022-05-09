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
	 * propulsion
	 */
	PROP_PRESSURE_INDEX(0),
	PROP_TEMP_INDEX(1),
	PROP_DUMP_VALVE_INDEX(2),
	PROP_RUN_VALVE_INDEX(3),
	
	/*
	 * Flight Computer
	 */
	ACCEL_X_INDEX(0),
	ACCEL_Y_INDEX(1),
	ACCEL_Z_INDEX(2),
	
	GYROX_INDEX(3),
	GYROY_INDEX(4),
	GYROZ_INDEX(5),
	
	PITCH_INDEX(3),	// exists so rocket model doesn't crash
	ROLL_INDEX(4),
	YAW_INDEX(5),
	
	PRESSURE_INDEX(6),
	ALTITUDE_INDEX(6),
	
	LATITUDE_INDEX(7),
	LONGITUDE_INDEX(8),
	
	TIME_INDEX(9), // minutes, seconds, subsec
	
	STATE_INDEX(12),
	CONTINUITY_INDEX(13);
	
	
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



