
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
	public Label runValveStatus;

	
	@FXML 
	public void setDrogueContinuityValue(String value) {
		this.drogueContinuity.setText(value);
		
	}
	@FXML 
	public void setMainContinuityValue(String value) {
		this.mainContinuity.setText(value);
		
	}
	
	@FXML 
	public void setRunValveStatusValue(String value) {
		this.runValveStatus.setText(value);
		
	}
	
	
	public void updateNumDisplay(double[] data) {
		
		// continuity and dump valve status are in different telemetry strings
		// prop string is shorter
		if (data.length >= DataIndex.CONTINUITY_INDEX.getOrder()) {
			
			int encodedContinuity = (int) data[DataIndex.CONTINUITY_INDEX.getOrder()];
			String decodedContinuity[] = decodeContinuity(encodedContinuity);
			
			// check if armed first; cannot have continuity if FC is not armed
			if (RadioCommandButtonsController.getArmRecoveryStatus().equals("Safed")) {
				if (encodedContinuity == 0) {
					setDrogueContinuityValue("safed");
					setMainContinuityValue("safed");
				} 
//					else {
//					setDrogueContinuityValue("not safed -- " + decodedContinuity[0]);
//					setMainContinuityValue("not safed -- " + decodedContinuity[1]);
//				}
			} else {
				setDrogueContinuityValue(decodedContinuity[0]);
				setMainContinuityValue(decodedContinuity[1]);
			}
			
			if (encodedContinuity >= 8) {
				setRunValveStatusValue("yes");
			} 
			else {
				setRunValveStatusValue("safed");
			}
			
		}
		
		else {
//			if (data[DataIndex.PROP_DUMP_VALVE_INDEX.getOrder()] > 0) {
//				setRunValveStatusValue("powered");
//			} else {
//				setRunValveStatusValue("unpowered");
//			}
			
		}

	}
	
	private String[] decodeContinuity(int encodedContinuity) {
		String drogueCont = "no";
		String mainCont = "no";
		
		if (encodedContinuity == 0) {
			drogueCont = "no";
			mainCont = "no";
		} else if (encodedContinuity == 1) {
			drogueCont = "yes";
			mainCont = "no";
		} else if (encodedContinuity == 2) {
			drogueCont = "no";
			mainCont = "yes";
		} else if (encodedContinuity == 3 || encodedContinuity == 7 || encodedContinuity == 15) {
			drogueCont = "yes";
			mainCont = "yes";
		}
		
		return (new String[] {drogueCont, mainCont});
	}

}


