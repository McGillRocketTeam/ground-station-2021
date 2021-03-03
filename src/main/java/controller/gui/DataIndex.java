package controller.gui;

public enum DataIndex {
	TIME_INDEX(0),
	ALTITUDE_INDEX(1),
	VELOCITY_INDEX(2),
	ACCELERATION_INDEX(3),
	RSSI_INDEX(4);
	
	private Integer order;

	private DataIndex(final Integer order) {
		this.order = order;
	}
	
	public Integer getOrder() {
		return order;
	}
}



