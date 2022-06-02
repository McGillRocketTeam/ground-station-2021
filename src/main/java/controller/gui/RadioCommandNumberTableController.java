
package controller.gui;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
/**

 *  ---------------INFO------------------
 * 
 *  DROGUE CONTINUITY = status of e-match for drogue parachute (ejection) (yes/no)
 *  MAIN CONTINUITY = status of e-match for main parachute
 *  RUN VALVE STATUS = 1 when powered, 0 when unpowered
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
	
	public void initializeDisplay() {
		setDrogueContinuityValue("safed");
		setMainContinuityValue("safed");
		setRunValveStatusValue("Open");
	}
	
	public void updateNumDisplay(double[] data) {
			
		int encodedContinuity = (int) data[DataIndex.CONTINUITY_INDEX.getOrder()];
		String decodedContinuity[] = decodeContinuity(encodedContinuity);
		
		// check if armed first; cannot have continuity if FC is not armed
		if (RadioCommandButtonsController.getArmRecoveryStatus().equals("Safed")) {
			if (encodedContinuity == 0 || encodedContinuity == 4 || encodedContinuity == 8 || encodedContinuity == 12) { 
				// prop channels can be armed independently of recovery
				
				setDrogueContinuityValue("safed");
				setMainContinuityValue("safed");
			} 
			else {
				setDrogueContinuityValue("not safed -- " + decodedContinuity[0]);
				setMainContinuityValue("not safed -- " + decodedContinuity[1]);
			}
		} else {
			setDrogueContinuityValue(decodedContinuity[0]);
			setMainContinuityValue(decodedContinuity[1]);
		}
		
		if (RadioCommandButtonsController.getArmPropulsionStatus().equals("Safed")) {
			if (encodedContinuity >= 4) {
				setRunValveStatusValue("not safed! -- yes");
			} else {
				setRunValveStatusValue("safed");
			}
		} else {
			if (encodedContinuity > 15) {
				setRunValveStatusValue("launched!");
			}
			else if (encodedContinuity == 12 || encodedContinuity == 15) {
				setRunValveStatusValue("yes");
			}
			else if (encodedContinuity < 12) {
				setRunValveStatusValue("uh...idk");
			}
		}
		
		
	}
	
	private String[] decodeContinuity(int encodedContinuity) {
		String drogueCont = "no";
		String mainCont = "no";
		
		if (encodedContinuity == 0) {
			drogueCont = "no";
			mainCont = "no";
		} else if (encodedContinuity == 3 || encodedContinuity == 15 || encodedContinuity == 27) {
			drogueCont = "yes";
			mainCont = "yes";
		}
		
		return (new String[] {drogueCont, mainCont});
	}

}


