package controller.gui;

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



