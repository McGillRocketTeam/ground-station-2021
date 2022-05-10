package controller.gui;

/**
 * The Enum class that describes the indices of commands from the radios, along with the
 * associated command as a string. The index is used to decode the acknowledge message
 * from the radio, in the form "xtend_ack_XX" where XX is some unsigned integer or 
 * "radio_ack_XX" for the sradio.
 *  
 * @author jasper yun
 *
 */

public enum RadioCommands {
	
	CMD_LAUNCH(1, "lr", "launch"), 
	CMD_ARM_PROP(2, "ap", "arm propulsion"),
	CMD_ARM_RCOV(3, "ar", "arm recovery"),
	CMD_DISARM_PROP(4, "dp", "disarm propulsion"),
	CMD_DISARM_RCOV(5, "dr", "disarm recovery"),
	CMD_DUMP_POWER_ON(6, "d1", "dump enable power"),
	CMD_DUMP_POWER_OFF(7, "d2", "dump disable power"),
	CMD_VR_POWER_ON(8, "v1", "VR power on"),
	CMD_VR_REC_START(9, "v2", "VR start rec"),
	CMD_VR_REC_STOP(10, "v3", "VR stop rec"),
	CMD_VR_POWER_OFF(11, "v4", "VR power off"),
	CMD_INVALID(12, "iv", "command INVALID");
	
	private final int command_index;
	private final byte[] command_code;
	private final String command_name;
	
	public static final int NUM_RADIO_COMMANDS = 12; // update this as needed! 
	
	public static final int command_length = 2;
	
	RadioCommands(int command_index, String command_code, String command_name) {
		this.command_index = command_index;
		this.command_code = command_code.getBytes();
		this.command_name = command_name;
	}
	
	public int getIndex() { return this.command_index; }
	public byte[] getCode() { return this.command_code; }
	public String getName() { return this.command_name; }
	
	public static byte[] getCodeByIndex(int index) {
		for (final RadioCommands cmd: values()) {
			if (cmd.command_index == index) {
				return cmd.command_code;
			}
		}
		return CMD_INVALID.command_code;
	}
	
	public static String getNameByIndex(int index) {
		for (final RadioCommands cmd: values()) {
			if (cmd.command_index == index) {
				return cmd.command_name;
			}
		}
		return CMD_INVALID.command_name;
	}
	
}
