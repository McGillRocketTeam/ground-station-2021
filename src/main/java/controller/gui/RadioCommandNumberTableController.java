
package controller.gui;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
/**

 *  ---------------INFO------------------
 * 
 *  DROGUE CONTINUITY = status of e-match for drogue parachute (ejection) (yes/no)
 *  MAIN CONTINUITY = status of e-match for main parachute
 *  DUMP VALVE STATUS = 1 when powered (closed), 0 when unpowered (open)
 *
 */

public class RadioCommandNumberTableController {
	ArrayList<String> read;
	
	
	@FXML
	public Label drogueContinuity;
	public Label mainContinuity;
	public Label dumpValveStatus;

	
	@FXML 
	public void setDrogueContinuityValue(String value) {
		this.drogueContinuity.setText(value);
		
	}
	@FXML 
	public void setMainContinuityValue(String value) {
		this.mainContinuity.setText(value);
		
	}
	
	@FXML 
	public void setDumpValveStatusValue(String value) {
		this.dumpValveStatus.setText(value);
		
	}
	
	
	public void updateNumDisplay(double[] data) {
		
		// continuity and dump valve status are in different telemetry strings
		// prop string is shorter
		if (data.length >= DataIndex.CONTINUITY_INDEX.getOrder()) {
			
			// check if armed first; cannot have continuity if FC is not armed
			System.out.println("getArmRcovStatus = " + RadioCommandButtonsController.getArmRecoveryStatus());
			if (RadioCommandButtonsController.getArmRecoveryStatus().equals("Safed")) {
				setDrogueContinuityValue("safed");
				setMainContinuityValue("safed");
				return;
			}
			
			int encodedContinuity = (int) data[DataIndex.CONTINUITY_INDEX.getOrder()];
			
			if (encodedContinuity == 0) {
				setDrogueContinuityValue("no");
				setMainContinuityValue("no");
			} else if (encodedContinuity == 1) {
				setDrogueContinuityValue("yes");
				setMainContinuityValue("no");
			} else if (encodedContinuity == 2) {
				setDrogueContinuityValue("no");
				setMainContinuityValue("yes");
			} else if (encodedContinuity == 3) {
				setDrogueContinuityValue("yes");
				setMainContinuityValue("yes");
			}
			
		}
		
		else {
			if (data[DataIndex.VALVE_STATUS.getOrder()] > 0) {
				setDumpValveStatusValue("powered");
			} else {
				setDumpValveStatusValue("unpowered");
			}
		}

	}

}


