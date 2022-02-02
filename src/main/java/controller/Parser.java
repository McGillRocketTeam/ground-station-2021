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
	private int numberOfValues;
	private boolean containsHex;
	private int hexLocation;
	private final double EMPTY_ARRAY = 0;
	private final double LOCAL_PRESSURE = 96754; // pressure conversion to altitude

	/**
	 *
	 * @param numberOfValues
	 */
	public Parser(int numberOfValues) {
		this(numberOfValues, false, -1);
	}

	/**
	 * Constructor used when there is a hex value in the string
	 *
	 * @param numberOfValuesInput number of values in the data string
	 * @param containsHexValue boolean representing if the string contains a hex value
	 * @param locationOfHexValue index of the hex value (first index = 0)
	 */
	public Parser(int numberOfValuesInput, boolean containsHexValue, int locationOfHexValue) {
		this.numberOfValues = numberOfValuesInput;
		this.containsHex = containsHexValue;
		this.hexLocation = locationOfHexValue;
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

	public double[] parse(String sIn) throws IllegalArgumentException {
		double[] out = new double[this.numberOfValues];

		Arrays.fill(out, EMPTY_ARRAY);
		//         Check if first and last characters are S and E respectively
		if (sIn.isEmpty()) throw new IllegalArgumentException("Input string is empty");
		else if (sIn.charAt(0) != 's' && sIn.charAt(sIn.length()-1) != 'e')
			throw new IllegalArgumentException("First and Last characters are not s and e");
		else if (sIn.charAt(0) != 's') throw new IllegalArgumentException("First Character in input String is not s");
		else if (sIn.charAt(sIn.length()-1) != 'e') throw new IllegalArgumentException("Last Character in input string is not e");

		//Remove s at start and e at end characters
		String subStr = sIn.substring(1, sIn.length()-1);

		//Split new string and convert to double
		String[] splitStr = subStr.split(";");
		if (splitStr.length != this.numberOfValues) throw new IllegalArgumentException("Incorrect number of values: found:" + splitStr.length + " expected:" + this.numberOfValues);
		for (int i = 0; i < splitStr.length; i++) {
			if (i == this.hexLocation) {
				if (this.containsHex) {
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
				} else {
					try {
						out[i] = Integer.parseInt(splitStr[i], 10);
					}
					catch (Exception e) {
						throw new InvalidParameterException(
								"String: \"" + out[i] + " \" at index:"
										+ i + " cannot be converted from integer to integer \n"
										+ "Message from original exception follows:\n"
										+ e.getMessage()
								);
					}
				}
			}
			else {

				try {
					// throws number format exception if string is invalid

					// Time value
					//if(i == DataIndex.TIME_INDEX.getOrder()) {
					
					//is this ok?
					if(i == out.length-1) {
					String[] time = splitStr[i].split(":");
						out[i] = (Double.parseDouble(time[0])*3600 + Double.parseDouble(time[1])*60 + Double.parseDouble(time[2]));
					}
					else {
						out[i] = Double.parseDouble(splitStr[i]);
					}


				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to a Double \n Message from original exception follows:\n"
									+ e.getMessage()
							);
					//e.printStackTrace();
				}



			}
		}

		return out;
	}

	public double[] parseFC(String sIn) throws IllegalArgumentException{

		// Format:
		//old:
		//	S,ACCx,ACCy,ACCz,MAGx,MAGy,MAGz,PRESSURE,LAT,LONG,HOUR,MIN,SEC,SUBSEC,E\n
		//	S,3.2, 3.2, 3.2, 3.2, 3.2, 3.2, 4.2,    ,3.7,3.7, 2,   2,  2,  2,     E
		/*
		 * S,ACCx,ACCy,ACCz,MAGx,MAGy,MAGz,PRESSURE,LAT,LONG,HOUR,MIN,SEC,E
		 * S,0.85,-128.71,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,E
		 */
		//new:
		// S,ACCx,ACCy,ACCz,GYROx,GYROy,GYROz,PRESSURE,LAT,LONG,HOUR,MIN,SEC,STATE,CONT,E
		//numberOfValue: 14
		
		int state=12;
		int stateLRange=0;
		int stateURange=4;
		int cont=13;
		int contLRange=0;
		int contURange=3;
		
		double[] out = new double[this.numberOfValues];
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
		if (splitStr.length != this.numberOfValues) throw new IllegalArgumentException("Incorrect number of values: found: " + splitStr.length + " expected: " + this.numberOfValues);
			
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
				if (stateValue>=stateLRange && stateValue<=stateURange) {
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
				if (contValue>=contLRange && contValue<=contURange) {
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
	
	public double[] parsePropulsion(String sIn) throws IllegalArgumentException {
		
		// Format:
		// P,PRESSURE,TEMPERATURE,VALVE_STATUS,E
		boolean throwErrors=true;
		
		int pressureLocation = 0;
		double pressureLRange = 0;
		//double pressureURange = 750;
		double pressureURange = 760;
		int temperatureLocation = 1;
		double temperatureLRange = 0;
		//double temperatureURange = 25;
		double temperatureURange = 30;
		int valveStatusLocation = 2;
		double[] out = new double[this.numberOfValues];
		Arrays.fill(out, EMPTY_ARRAY);

		// Check if first and last characters are S and E respectively
		if (sIn.isEmpty() || sIn.length() <= 2) throw new IllegalArgumentException("Input string is empty or size 1 or size 2");
		else if (sIn.charAt(0) != 'P' && sIn.charAt(sIn.length() - 1) != 'E' && sIn.charAt(sIn.length()-2) != ',')
			throw new IllegalArgumentException("First and Last characters are not S and E");
		else if (sIn.charAt(0) != 'P') throw new IllegalArgumentException("First Character in input String is not P");
		else if (sIn.charAt(sIn.length()-1) != 'E') throw new IllegalArgumentException("Last Character in input string is not E");
		else if (sIn.charAt(sIn.length()-2) != ',') throw new IllegalArgumentException("Last Character in input string is not ,");
		//Remove S at start and , + E characters
		String subStr = sIn.substring(2, sIn.length()-2);

		//Split new string and convert to double
		String[] splitStr = subStr.split(",");
		if (splitStr.length != this.numberOfValues) throw new IllegalArgumentException("Incorrect number of values: found:" + splitStr.length + " expected:" + this.numberOfValues);
		
		for (int i = 0; i < splitStr.length; i++) {

			if (i == pressureLocation) {
				double pressure;
				try {
					pressure=Double.parseDouble(splitStr[i]);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to a double \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
//				if (throwErrors && pressure>=pressureLRange && pressure<=pressureURange) {
//					out[i] = pressure;
//				}
//				else {
//					throw new IllegalArgumentException("PRESSURE should be between " + pressureLRange + " and " + pressureURange);
//				}
			}
			
			else if (i == temperatureLocation) {
				double temperature;
				try {
					temperature=Double.parseDouble(splitStr[i]);
				}
				catch (Exception e) {
					throw new InvalidParameterException(
							"String: \"" + out[i] + " \" at index:"
									+ i + " cannot be converted to a double \n Message from original exception follows:\n"
									+ e.getMessage()
							);
				}
//				if (throwErrors && temperature>=temperatureLRange && temperature<=temperatureURange) {
//					out[i] = temperature;
//				}
//				else {
//					throw new IllegalArgumentException("TEMPERATURE should be between " + pressureLRange + " and " + pressureURange);
//				}
			}
			
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
				if (throwErrors && (valveStatus==0 || valveStatus==1)) {
					out[i] = valveStatus;
				}
				else {
					throw new IllegalArgumentException("VALVE_STATUS should be either 1 or 0");
				}
			}
			
		}
		
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
