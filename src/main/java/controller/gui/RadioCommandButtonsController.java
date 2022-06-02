package controller.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import com.fazecast.jSerialComm.SerialPort;

import javafx.event.EventHandler;


public class RadioCommandButtonsController {
	
	@FXML
	public RadioButton dump_unpowered_rb;
	
	@FXML
	public RadioButton dump_powered_rb;

	@FXML
	public RadioButton arm_prop_safed_rb;
	
	@FXML
	public RadioButton arm_prop_armed_rb;
	
	@FXML
	public RadioButton arm_rcov_safed_rb;
	
	@FXML
	public RadioButton arm_rcov_armed_rb;
	
	@FXML
	public ToggleGroup arm_recovery;
	
	@FXML
	public ToggleGroup arm_prop;
	
	@FXML
	public ToggleGroup dump_valve;
	
	@FXML
	public ToggleGroup vr_power;
	
	@FXML
	public ToggleGroup vr_recording;
	
	@FXML
	public Text dump_valve_status_text_value;
	
	@FXML
	public Button launch_button;
	
	
	private static SerialPort comPort = null; // stays null if we use mode.OLD
	private static String armRecoveryStatus = "Safed";
	private static String armPropulsionStatus = "Safed";
	
	public static void attachComPort(SerialPort port) {
		comPort = port;
	}
	
	public static String getArmRecoveryStatus() {
		return armRecoveryStatus;
	}
	
	public static String getArmPropulsionStatus() {
		return armPropulsionStatus;
	}
	
	// listeners as objects so that they can be removed and added
	private ChangeListener<Toggle> armRecoveryChangeListener = new ChangeListener<Toggle>() {
		@Override
        public void changed(ObservableValue<? extends Toggle> changed, 
                                                Toggle oldVal, Toggle newVal)
        {
			RadioCommands cmd;
            RadioButton rb = (RadioButton)arm_recovery.getSelectedToggle();
            armRecoveryStatus = rb.getText();
            if (rb.getText().equals("Armed")) {
            	cmd = RadioCommands.CMD_ARM_RCOV;
            } else if (rb.getText().equals("Safed")){
            	cmd = RadioCommands.CMD_DISARM_RCOV;
            } else {
            	cmd = null;
            	System.out.println("Something went very wrong with recovery arming radio buttons");
            }
            
            if (armRecoveryStatus.equals("Armed") && armPropulsionStatus.equals("Armed")) {
            	launch_button.setDisable(false);
            } else {
            	launch_button.setDisable(true);
            }
            
            writeComPort(cmd);
        }
	};
	
	private ChangeListener<Toggle> armPropChangeListener = new ChangeListener<Toggle>() {
		@Override
        public void changed(ObservableValue<? extends Toggle> changed, 
                                                Toggle oldVal, Toggle newVal)
        {
			RadioCommands cmd;
            RadioButton rb = (RadioButton)arm_prop.getSelectedToggle();
            armPropulsionStatus = rb.getText();
            if (rb.getText().equals("Armed")) {
            	cmd = RadioCommands.CMD_ARM_PROP;
            } else if (rb.getText().equals("Safed")){
            	cmd = RadioCommands.CMD_DISARM_PROP;
            } else {
            	cmd = null;
            	System.out.println("Something went very wrong with propulsion arming radio buttons");
            }
            
            if (armRecoveryStatus.equals("Armed") && armPropulsionStatus.equals("Armed")) {
            	launch_button.setDisable(false);
            } else {
            	launch_button.setDisable(true);
            }
            
            writeComPort(cmd);
        }
	};
	
	private ChangeListener<Toggle> vrPowerChangeListener = new ChangeListener<Toggle>() {
		@Override
        public void changed(ObservableValue<? extends Toggle> changed, 
                                                Toggle oldVal, Toggle newVal)
        {

			RadioCommands cmd;
            RadioButton rb = (RadioButton)vr_power.getSelectedToggle();
            if (rb.getText().equals("Off")) {
            	cmd = RadioCommands.CMD_VR_POWER_OFF;
            } else if (rb.getText().equals("On")){
            	cmd = RadioCommands.CMD_VR_POWER_ON;
            } else {
            	cmd = null;
            	System.out.println("Something went very wrong with propulsion arming radio buttons");
            }
            
            writeComPort(cmd);
        }
	};
	
	private ChangeListener<Toggle> vrRecordingChangeListener = new ChangeListener<Toggle>() {
		@Override
        public void changed(ObservableValue<? extends Toggle> changed, 
                                                Toggle oldVal, Toggle newVal)
        {

			RadioCommands cmd;
            RadioButton rb = (RadioButton)vr_recording.getSelectedToggle();
            if (rb.getText().equals("Stopped")) {
            	cmd = RadioCommands.CMD_VR_REC_STOP;
            } else if (rb.getText().equals("Started")){
            	cmd = RadioCommands.CMD_VR_REC_START;
            } else {
            	cmd = null;
            	System.out.println("Something went very wrong with propulsion arming radio buttons");
            }
            
            writeComPort(cmd);
        }
	};
	
	private ChangeListener<Toggle> dumpValveChangeListener = new ChangeListener<Toggle>() {
		@Override
        public void changed(ObservableValue<? extends Toggle> changed, 
                                                Toggle oldVal, Toggle newVal)
        {

			RadioCommands cmd;
            RadioButton rb = (RadioButton)dump_valve.getSelectedToggle();
            if (rb.getText().equals("Open")) {
            	cmd = RadioCommands.CMD_DUMP_POWER_OFF;
            } else if (rb.getText().equals("Closed")){
            	cmd = RadioCommands.CMD_DUMP_POWER_ON;
            } else {
            	cmd = null;
            	System.out.println("Something went very wrong with dump valve radio buttons");
            }
            
            writeComPort(cmd);
        }
	};
	
	
	public void initialize() {
		
		launch_button.setDisable(true);
		
		arm_recovery.selectedToggleProperty().addListener(armRecoveryChangeListener);
		arm_prop.selectedToggleProperty().addListener(armPropChangeListener);
		vr_power.selectedToggleProperty().addListener(vrPowerChangeListener);
		vr_recording.selectedToggleProperty().addListener(vrRecordingChangeListener);
		dump_valve.selectedToggleProperty().addListener(dumpValveChangeListener);
		
		launch_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				writeComPort(RadioCommands.CMD_LAUNCH);
			}
		});
	}
	
	public void updateDumpValveLabel(double data[]) {
		if (data[DataIndex.PROP_DUMP_VALVE_INDEX.getOrder()] == 0) {
			dump_valve_status_text_value.setText("Open");
		} else {
			dump_valve_status_text_value.setText("Closed");
		}
	}
	
	// writes data to com port and prints the command sent
	private void writeComPort(RadioCommands cmd) {
		if (comPort != null) { // would be null if running in mode.OLD
			comPort.writeBytes(cmd.getCode(), RadioCommands.command_length);
        } 
		System.out.println("Sending command = " + new String(cmd.getCode()));
	}
	
	public void updateButtonsSelectedStatus(double data[]) {
		if (data.length > 9) { // avionics data, update arming status
			int encodedContinuity = (int) data[DataIndex.CONTINUITY_INDEX.getOrder()];
			
			// update recovery arming radio button based on continuity data received
			
			if (encodedContinuity == 0) { // all channels are disarmed
				arm_recovery.selectedToggleProperty().removeListener(armRecoveryChangeListener);
				arm_prop.selectedToggleProperty().removeListener(armPropChangeListener);
				
				arm_rcov_armed_rb.setSelected(false);
				arm_rcov_safed_rb.setSelected(true);
				
				arm_prop_armed_rb.setSelected(false);
				arm_prop_safed_rb.setSelected(true);
				
				armRecoveryStatus = "Safed";
				armPropulsionStatus = "Safed";
				
				arm_prop.selectedToggleProperty().addListener(armPropChangeListener);
				arm_recovery.selectedToggleProperty().addListener(armRecoveryChangeListener);
			} else if (encodedContinuity == 3 || encodedContinuity == 15 || encodedContinuity == 27) {
				//  3: only rcov armed
				// 15: rcov and prop armed
				// 27: rcov armed, prop launched
				arm_recovery.selectedToggleProperty().removeListener(armRecoveryChangeListener);
				arm_rcov_armed_rb.setSelected(true);
				arm_rcov_safed_rb.setSelected(false);
				armRecoveryStatus = "Armed";
				
				arm_recovery.selectedToggleProperty().addListener(armRecoveryChangeListener);
			}
			
			// update propulsion arming radio button based on continuity data received
			if (encodedContinuity == 12 || encodedContinuity == 15 || encodedContinuity == 24 || encodedContinuity == 27) {
				
				arm_prop.selectedToggleProperty().removeListener(armPropChangeListener);
				arm_prop_armed_rb.setSelected(true);
				arm_prop_safed_rb.setSelected(false);
				armPropulsionStatus = "Armed";
				arm_prop.selectedToggleProperty().addListener(armPropChangeListener);
				
			} else if (encodedContinuity == 3) { // recovery armed, prop disarmed
				arm_prop.selectedToggleProperty().removeListener(armPropChangeListener);
				arm_prop_armed_rb.setSelected(false);
				arm_prop_safed_rb.setSelected(true);
				armPropulsionStatus = "Safed";
				arm_prop.selectedToggleProperty().addListener(armPropChangeListener);
			}
			
		} else { // prop data, update dump status
			if (data[DataIndex.PROP_DUMP_VALVE_INDEX.getOrder()] == 1) {
				dump_valve.selectedToggleProperty().removeListener(dumpValveChangeListener);
				dump_powered_rb.setSelected(true);
				dump_unpowered_rb.setSelected(false);
				dump_valve.selectedToggleProperty().addListener(dumpValveChangeListener);
				
			} else {
				dump_valve.selectedToggleProperty().removeListener(dumpValveChangeListener);
				dump_powered_rb.setSelected(false);
				dump_unpowered_rb.setSelected(true);
				dump_valve.selectedToggleProperty().addListener(dumpValveChangeListener);
			}
		}
		
//		if (armRecoveryStatus.equals("Armed") && armPropulsionStatus.equals("Armed")) {
//        	launch_button.setDisable(false);
//        } else {
//        	launch_button.setDisable(true);
//        }
		
	}
	
}
