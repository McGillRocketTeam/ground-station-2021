package controller.gui;

public enum DataIndex {
	TIME_INDEX(2),
	ALTITUDE_INDEX(3),
	VELOCITY_INDEX(4),
	ACCELERATION_INDEX(5),
	RSSI_INDEX(9);
	
	private Integer order;

	private DataIndex(final Integer order) {
		this.order = order;
	}
	
	public Integer getOrder() {
		return order;
	}
}



