package controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controller.gui.DataIndex;
import controller.gui.RadioCommandLogController;
import controller.gui.RadioCommands;

public class Parser {
	private boolean showRangeError = false;
	
	private boolean containsHex;
	private int hexLocation;
	private final double EMPTY_ARRAY = 0;
	private final double LOCAL_PRESSURE = 1018; // pressure conversion to altitude
	
	public final int NUMBER_OF_VALUES_FC = 14; // make accessible to MainApp
	public final int NUMBER_OF_VALUES_PR = 7;
	
	private static double alt_ground = Double.MAX_VALUE; // init to known value
	
	//show error if state are not in range
	public void setRangeError(boolean showRangeError){this.showRangeError = showRangeError;}
	
	/**
	 *
	 */
	public Parser() {
		this(false, -1);
	}

	/**
	 * Constructor used when there is a hex value in the string
	 *
	 * @param containsHexValue boolean representing if the string contains a hex value
	 * @param locationOfHexValue index of the hex value (first index = 0)
	 */
	public Parser(boolean containsHexValue, int locationOfHexValue) {
		this.containsHex = containsHexValue;
		this.hexLocation = locationOfHexValue;
	}
	
	public double[] parse(String sIn) throws IllegalArgumentException {
		
		if (sIn.length() < 3) {
			System.out.println("Error string = " + sIn);
			throw new IllegalArgumentException("invalid string, doesn't start with S, P or an acknowledge format");
		}
		
		if (sIn.charAt(0)=='S' || sIn.charAt(0)=='J') return parseFC(sIn);
		else if (sIn.charAt(0)=='P') return parsePropulsion(sIn);
		//or check xtend and sradio
		else if (sIn.charAt(0)=='x') return parseAcknowledgeXtend(sIn);
		else if (sIn.charAt(0)=='r') return parseAcknowledgeSradio(sIn);
		
		System.out.println("invalid string = " + sIn);
		throw new IllegalArgumentException("invalid string, doesn't start with S, P or an acknowledge format");
		
	}

	/**
	 *
	 * @param path of the text file
	 *
	 * @return a list containing each of the lines of the file
	 */
	public static List<String> storeData(String path) throws FileNotFoundException
	{
		List<String> list = new ArrayList<String>();
		File file = new File(path);
		Scanner scan = new Scanner(file);
		String str;
		while(scan.hasNextLine()) {
			str = scan.nextLine();
			list.add(str);
		}
		//System.out.println(list);
		//System.out.println(list.get(5371));
		return list;

	}

	
	public double[] parseFC(String sIn) throws IllegalArgumentException{

		// Format:
		//new:
		// S,ACCx,ACCy,ACCz,GYROx,GYROy,GYROz,PRESSURE,LAT,LONG,MIN,SEC,SUBSEC,STATE,CONT,E
		// numberOfValue: 14
		
		//use ones in DataIndex
		int state=12;
		int stateLRange=0;
		int stateURange=4;
		int cont=13;
		int contLRange=0;
		int contURange=3;
		int pressureIndex=6;
			
		double[] out = new double[NUMBER_OF_VALUES_FC];
		Arrays.fill(out, EMPTY_ARRAY);
		// Check if first and last characters are S and E respectively
		if (sIn.isEmpty() || sIn.length() <= 2) throw new IllegalArgumentException("Input string is empty or size 1 or size 2");
		
		/* Special FC Event Message */
		else if ((sIn.charAt(0) == 'J') && sIn.charAt(sIn.length()-1) == 'E' && sIn.charAt(sIn.length()-2) == ','){
			
			System.out.println("Event Message Received");
			//Remove J at start and , + E characters
			String subStr = sIn.substring(2, sIn.length()-2);

			//Split new string and convert to double
			String[] splitStr = subStr.split(",");
			out[0] = -1000; // TODO: why is this here?
			for (int i = 1; i < splitStr.length + 1; i++) {

				try {
					// throws number format exception if string is invalid
					out[i] = Double.parseDouble(splitStr[i]);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to a Double \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
			}
			System.out.println("Returning event message");
			return out;
		}
		
		else if (sIn.charAt(0) != 'S' && sIn.charAt(sIn.length() - 1) != 'E' && sIn.charAt(sIn.length()-2) != ',')
			throw new IllegalArgumentException("First and Last characters are not S and E");
		else if (sIn.charAt(0) != 'S') throw new IllegalArgumentException("First Character in input String is not S");
		else if (sIn.charAt(sIn.length()-1) != 'E') throw new IllegalArgumentException("Last Character in input string is not E");
		else if (sIn.charAt(sIn.length()-2) != ',') throw new IllegalArgumentException("Last Character in input string is not ,");
		//Remove S at start and , + E characters
		String subStr = sIn.substring(2, sIn.length()-2);

		//Split new string and convert to double
		String[] splitStr = subStr.split(",");
		if (splitStr.length != NUMBER_OF_VALUES_FC) throw new IllegalArgumentException("Incorrect number of values: found: " + splitStr.length + " expected: " + NUMBER_OF_VALUES_FC);
			
		for (int i = 0; i < splitStr.length; i++) {
			if (i == this.hexLocation) {
				try {
					out[i] = Integer.parseInt(splitStr[i], 16);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted from hexidecimal string to integer \n"
									+ "Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
			}
			
			// for the STATE
			else if (i == state) {
				int stateValue;
				try {
					stateValue=Integer.parseInt(splitStr[i]);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to an int \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
				if ((stateValue>=stateLRange && stateValue<=stateURange) || !showRangeError) {
					out[i] = stateValue;
				}
				else {
					throw new IllegalArgumentException("STATE should be between " + stateLRange + " and " + stateURange);
				}
			}
			
			// for the CONT
			else if (i == cont) {
				int contValue;
				try {
					contValue=Integer.parseInt(splitStr[i]);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to an int \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
				if ((contValue>=contLRange && contValue<=contURange)  || !showRangeError) {
					out[i] = contValue;
				}
				else {
					throw new IllegalArgumentException("CONT should be between " + contLRange + " and " + contURange);
				}
			}
			
			// convert pressure to altitude because we want alt
			else if (i == DataIndex.PRESSURE_INDEX.getOrder()) {
				double pressure = 0;
				try {
					pressure = Double.parseDouble(splitStr[i]);
					out[i] = getAltitude(pressure);
					
					if (alt_ground == Double.MAX_VALUE) {
						alt_ground = out[i];
					}
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to an int \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
			}
			
			else {
				try {
					// throws number format exception if string is invalid
					out[i] = Double.parseDouble(splitStr[i]);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to a Double \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
			}
		}

		return out;
	}
	public double[] parseAcknowledgeXtend(String sIn) throws IllegalArgumentException {
		//xtend_ack_ in var
		double[] out = new double[1]; //get from class
		if (!sIn.startsWith("xtend_ack_")) throw new IllegalArgumentException("Input string does not start with 'xtend_ack_'");
		String numberString = sIn.replace("xtend_ack_", "");
		try {
			out[0]=Integer.parseInt(numberString);
			if (out[0]<0 || out[0]>RadioCommands.NUM_RADIO_COMMANDS) throw new IllegalArgumentException("Invalid acknowledge message");
			RadioCommandLogController.add_log("XTend: " + RadioCommands.getNameByIndex((int) out[0]) + "\n");
			return out;
		} catch(NumberFormatException e){
			throw new IllegalArgumentException("Acknowledge message is missing the number");
		}
	}

	public double[] parseAcknowledgeSradio(String sIn) throws IllegalArgumentException {
		double[] out = new double[1]; //get from class
		if (!sIn.startsWith("radio_ack_")) throw new IllegalArgumentException("Input string does not start with 'radio_ack_'");
		String numberString = sIn.replace("radio_ack_", "");
		try {
			out[0]=Integer.parseInt(numberString);
			if (out[0]<0 || out[0]>RadioCommands.NUM_RADIO_COMMANDS) throw new IllegalArgumentException("Invalid acknowledge message");
			RadioCommandLogController.add_log("SRADio: " + RadioCommands.getNameByIndex((int) out[0]) + "\n");
			return out;
		} catch(NumberFormatException e){
			throw new IllegalArgumentException("Acknowledge message is missing the number");
		}
	}
	
	public double[] parsePropulsion(String sIn) throws IllegalArgumentException {
		
		// Format:
		// P,PRESSURE,TEMPERATURE,VALVE_STATUS,E
		
//		System.out.println("Parser in: str = " + sIn);
		
		boolean throwErrors=true;
		// P,PRESSURE,TEMPERATURE,VALVE_STATUS,MIN,SEC,SUBSEC,E
		
//		int numberOfValues = 6;
		//use ones in DataIndex
		int pressureLocation = 0;
		int temperatureLocation = 1;
		int valveStatusLocation = 2;
		int valveLRange=0;
		int valveURange=1;
		
		double[] out = new double[NUMBER_OF_VALUES_PR];
		Arrays.fill(out, EMPTY_ARRAY);

		// Check if first and last characters are P and E respectively
		if (sIn.isEmpty() || sIn.length() <= 2) throw new IllegalArgumentException("Input string is empty or size 1 or size 2");
		else if (sIn.charAt(0) != 'P' && sIn.charAt(sIn.length() - 1) != 'E' && sIn.charAt(sIn.length()-2) != ',')
			throw new IllegalArgumentException("First and Last characters are not P and E");
		else if (sIn.charAt(0) != 'P') throw new IllegalArgumentException("First Character in input String is not P");
		else if (sIn.charAt(sIn.length()-1) != 'E') throw new IllegalArgumentException("Last Character in input string is not E");
		else if (sIn.charAt(sIn.length()-2) != ',') throw new IllegalArgumentException("Last Character in input string is not ,");
		//Remove P at start and , + E characters
		String subStr = sIn.substring(2, sIn.length()-2);

		//Split new string and convert to double
		String[] splitStr = subStr.split(",");
		
		if (splitStr.length != NUMBER_OF_VALUES_PR) throw new IllegalArgumentException("Incorrect number of values: found:" + splitStr.length + " expected:" + NUMBER_OF_VALUES_PR);
		
		for (int i = 0; i < splitStr.length; i++) {

			if (i == pressureLocation) {
				try {
//					double pressure = Double.parseDouble(splitStr[i]);
//					out[i] = getAltitude(pressure);
					out[i] = Double.parseDouble(splitStr[i]);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to a double \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
			}
			
			else if (i == temperatureLocation) {
				try {
					out[i] = Double.parseDouble(splitStr[i]);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to a double \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
			}
			
			//for the STATE
			else if (i == valveStatusLocation) {
				int valveStatus;
				try {
					valveStatus=Integer.parseInt(splitStr[i]);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to an int \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
				if ((valveStatus>=valveLRange && valveStatus<=valveURange) || !showRangeError) {
					out[i] = valveStatus;
				}
				else {
					throw new IllegalArgumentException("STATE should be between " + valveLRange + " and " + valveURange);
				}
			}
			
			else {
				try {
					// throws number format exception if string is invalid
					out[i] = Double.parseDouble(splitStr[i]);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to an int \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
			}
			
		}
		
		// debug -- print the array
//		System.out.print("ParserProp out: ");
//		for (int i = 0; i < out.length; i++) System.out.print(out[i] + " ");
//		System.out.println(""); // newline
		
		return out;
		
		
	}
	
	/**
	 * convert pressure to altitude
	 * 
	 */
	private double getAltitude(double pressure) {
		double altitude = 145442.1609 * (1.0 - Math.pow(pressure / LOCAL_PRESSURE, 0.190266436));
		return altitude;
	}
	
	public static double getAltGround() {
		return alt_ground;
	}
	
//	public static void main(String[] args) {
//		//System.out.println(Integer.parseInt("0011", 2));
////		String test = "S,9.99,-0.45,-0.31,-0.15,-0.02,0.05,96754.01,37.1677142,-97.7362292,00,00,00,2,3,E";
////    	Parser testP = new Parser(14);
////    	double[] data =  testP.parseFC(test);
////    	for (int i=0; i<data.length; i++) {
////    		System.out.println(data[i]);
////    	}
//		
//		String test = "P,750.65,25.65,0,E";
//    	Parser testP = new Parser(3);
//    	double[] data =  testP.parsePropulsion(test);
//    	for (int i=0; i<data.length; i++) {
//    		System.out.println(data[i]);
//    	}
//    	
//        
//    }
}
