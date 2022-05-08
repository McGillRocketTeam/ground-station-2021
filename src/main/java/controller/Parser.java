package controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controller.gui.DataIndex;

public class Parser {
	private boolean showRangeError = false;
	//private int numberOfValues;
	private boolean containsHex;
	private int hexLocation;
	private final double EMPTY_ARRAY = 0;
	private final double LOCAL_PRESSURE = 1031; // pressure conversion to altitude
	
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
		
		if (sIn.trim().charAt(0)=='S' || sIn.trim().charAt(0)=='J') return parseFC(sIn);
		if (sIn.trim().charAt(0)=='P') return parsePropulsion(sIn);
		//or check xtend and sradio
		if (sIn.trim().charAt(0)=='x') return parseAcknowledgeXtend(sIn);
		if (sIn.trim().charAt(0)=='s') return parseAcknowledgeSradio(sIn);
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

	/**
	 *
	 * @param sIn the telemetry string to be parsed (telemetry 2021)
	 * Data format:
	 * 					12 values. 13 if temperature is included (the example string has 12 but format has 13 so double check)
	 *                  s-004.38;-003.69;0000.00;-00.72;000.58;009.41;022.31;101237.30;00107.73;454469476;-736939739;20:04:29e
	 *                  
	 *                  sprintf(packet, "s%07.2f;%07.2f;%07.2f;%06.2f;%06.2f;%06.2f;%06.2f;%09.2f;%08.2f;%09ld;%09ld;%02hu:%02hu:%02hue",     
	 *                  pitch, roll, yaw, accel_x, accel_y, accel_z, temp, pressure, real_altitude, latitude, longitude, hour, minutes, sec);
	 *
	 * @return an array containing the the split values
	 */

//	public double[] parse(String sIn) throws IllegalArgumentException {
//		int numberOfValues =13;
//		double[] out = new double[numberOfValues]; //not sure?
//
//		Arrays.fill(out, EMPTY_ARRAY);
//		//         Check if first and last characters are S and E respectively
//		if (sIn.isEmpty()) throw new IllegalArgumentException("Input string is empty");
//		else if (sIn.charAt(0) != 's' && sIn.charAt(sIn.length()-1) != 'e')
//			throw new IllegalArgumentException("First and Last characters are not s and e");
//		else if (sIn.charAt(0) != 's') throw new IllegalArgumentException("First Character in input String is not s");
//		else if (sIn.charAt(sIn.length()-1) != 'e') throw new IllegalArgumentException("Last Character in input string is not e");
//
//		//Remove s at start and e at end characters
//		String subStr = sIn.substring(1, sIn.length()-1);
//
//		//Split new string and convert to double
//		String[] splitStr = subStr.split(";");
//		if (splitStr.length != numberOfValues) throw new IllegalArgumentException("Incorrect number of values: found:" + splitStr.length + " expected:" + numberOfValues);
//		for (int i = 0; i < splitStr.length; i++) {
//			if (i == this.hexLocation) {
//				if (this.containsHex) {
//					try {
//						out[i] = Integer.parseInt(splitStr[i], 16);
//					}
//					catch (Exception e) {
//						throw new InvalidParameterException(
//								"String: \"" + out[i] + " \" at index:"
//										+ i + " cannot be converted from hexidecimal string to integer \n"
//										+ "Message from original exception follows:\n"
//										+ e.getMessage()
//								);
//					}
//				} else {
//					try {
//						out[i] = Integer.parseInt(splitStr[i], 10);
//					}
//					catch (Exception e) {
//						throw new InvalidParameterException(
//								"String: \"" + out[i] + " \" at index:"
//										+ i + " cannot be converted from integer to integer \n"
//										+ "Message from original exception follows:\n"
//										+ e.getMessage()
//								);
//					}
//				}
//			}
//			else {
//
//				try {
//					// throws number format exception if string is invalid
//
//					// Time value
//					//if(i == DataIndex.TIME_INDEX.getOrder()) {
//					
//					//is this ok?
//					if(i == out.length-1) {
//					String[] time = splitStr[i].split(":");
//						out[i] = (Double.parseDouble(time[0])*3600 + Double.parseDouble(time[1])*60 + Double.parseDouble(time[2]));
//					}
//					else {
//						out[i] = Double.parseDouble(splitStr[i]);
//					}
//
//
//				}
//				catch (Exception e) {
//					throw new InvalidParameterException(
//							"String: \"" + out[i] + " \" at index:"
//									+ i + " cannot be converted to a Double \n Message from original exception follows:\n"
//									+ e.getMessage()
//							);
//					//e.printStackTrace();
//				}
//
//
//
//			}
//		}
//
//		return out;
//	}

	public double[] parseFC(String sIn) throws IllegalArgumentException{

		// Format:
		//new:
		// S,ACCx,ACCy,ACCz,GYROx,GYROy,GYROz,PRESSURE,LAT,LONG,MIN,SEC,SUBSEC,STATE,CONT,E
		//numbberOfValue: 14
		
		int numberOfValues =14;
		//use ones in DataIndex
		int state=12;
		int stateLRange=0;
		int stateURange=4;
		int cont=13;
		int contLRange=0;
		int contURange=3;
		int pressureIndex=6;
			
		double[] out = new double[numberOfValues];
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
		if (splitStr.length != numberOfValues) throw new IllegalArgumentException("Incorrect number of values: found: " + splitStr.length + " expected: " + numberOfValues);
			
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
			if (out[0]<0 || out[0]>12) throw new IllegalArgumentException("Invalid acknowledge message");
			return out;
		} catch(NumberFormatException e){
			throw new IllegalArgumentException("Acknowledge message is missing the number");
		}
	}

	public double[] parseAcknowledgeSradio(String sIn) throws IllegalArgumentException {
		double[] out = new double[1]; //get from class
		if (!sIn.startsWith("sradio_ack_")) throw new IllegalArgumentException("Input string does not start with 'sradio_ack_'");
		String numberString = sIn.replace("sradio_ack_", "");
		try {
			out[0]=Integer.parseInt(numberString);
			if (out[0]<0 || out[0]>12) throw new IllegalArgumentException("Invalid acknowledge message");
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
		
		int numberOfValues = 6;
		//use ones in DataIndex
		int pressureLocation = 0;
		int temperatureLocation = 1;
		int valveStatusLocation = 2;
		int valveLRange=0;
		int valveURange=1;
		
		double[] out = new double[numberOfValues];
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
		
		if (splitStr.length != numberOfValues) throw new IllegalArgumentException("Incorrect number of values: found:" + splitStr.length + " expected:" + numberOfValues);
		
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
