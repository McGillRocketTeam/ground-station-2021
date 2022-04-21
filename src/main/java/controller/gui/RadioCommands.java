package controller.gui;

/**
 * The Enum class that describes the indices of commands from the radios, along with the
 * associated command as a string. The index is used to decode the acknowledge message
 * from the radio, in the form "xtend_ack_XX" where XX is some unsigned integer. 
 *  
 * @author jasper yun
 *
 */

public enum RadioCommands {
	CMD_LAUNCH(1, "lr"), 
	CMD_ARM_PROP(2, "ap"),
	CMD_ARM_RCOV(3, "ar"),
	CMD_DISARM_PROP(4, "dp"),
	CMD_DISARM_RCOV(5, "dr"),
	CMD_DUMP_POWER_ON(6, "d1"),
	CMD_DUMP_POWER_OFF(7, "d2"),
	CMD_VR_POWER_ON(8, "v1"),
	CMD_VR_REC_START(9, "v2"),
	CMD_VR_REC_STOP(10, "v3"),
	CMD_VR_POWER_OFF(11, "v4"),
	CMD_INVALID(12, "iv");
	
	private final int command_index;
	private final byte[] command_code;
	
	public static final int command_length = 2;
	
	RadioCommands(int command_index, String command_code) {
		this.command_index = command_index;
		this.command_code = command_code.getBytes();
	}
	
	public int index() { return this.command_index; }
	public byte[] code() { return this.command_code; }
	
	public static byte[] getByInt(int index) {
		for (final RadioCommands cmd: values()) {
			if (cmd.command_index == index) {
				return cmd.command_code;
			}
		}
		return CMD_INVALID.command_code;
	}
	
}
