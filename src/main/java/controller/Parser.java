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
    private final double EMPTY_ARRAY = -9999.99;

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
     * @param sIn the telemetry string to be parsed
     * Data format:
     *                  S-1.81;3.44;0.00;-0.28;-0.55;9.44;101372.60;96.49;454469358;-736939677;0:2:13E
     *                  
     *                  ???????
     *                  telemetry long data format:     Slat,long,time,alt,vel,sat,acc,temp,gyro_x,RSSI,E\n
     *                  backup GPS data:                Slat,long,time,gps_alt,gps_speed,sat,RSSI,E\n
     *
     * @return an array containing the the split values
     */
	
	// double[] myData = parse(string);
	// double altitude = myData[3];
    public double[] parse(String sIn) throws IllegalArgumentException {
        double[] out = new double[this.numberOfValues];
//        Arrays.fill(out, EMPTY_ARRAY);
        // Check if first and last characters are S and E respectively
//        if (sIn.charAt(0) != 'S' && sIn.charAt(sIn.length()-1) != 'E')
//            throw new IllegalArgumentException("First and Last characters are not S and E");
//        else if (sIn.charAt(0) != 'S') throw new IllegalArgumentException("First Character in input String is not S");
//        else if (sIn.charAt(sIn.length()-1) != 'E') throw new IllegalArgumentException("Last Character in input string is not E");
        
        //Remove S at start and , + E characters
        String subStr = sIn.substring(1, sIn.length()-1);

        //Split new string and convert to double
        String[] splitStr = subStr.split(";");
        if (splitStr.length != this.numberOfValues) throw new IllegalArgumentException("Incorrect number of values: found:" + splitStr.length + " expected:" + this.numberOfValues);
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
            else {

            	try {
            		// throws number format exception if string is invalid
            		
            		// Time value
            		if(i == DataIndex.TIME_INDEX.getOrder()) {
            			out[i] = Double.parseDouble(splitStr[i].replace(":", ""));
            			System.out.println(out[i]);
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
            	}
            	


            }
        }

        return out;
    }
    
    public double[] parseFC(String sIn) {
    	
    	// Format:
    	//	S,ACCx,ACCy,ACCz,MAGx,MAGy,MAGz,PRESSURE,LAT,LONG,HOUR,MIN,SEC,SUBSEC,E\n
    	//	S,3.2, 3.2, 3.2, 3.2, 3.2, 3.2, 4.2,    ,3.7,3.7, 2,   2,  2,  2,     E
    	
    	
    	double[] out = new double[this.numberOfValues];
//      Arrays.fill(out, EMPTY_ARRAY);
      // Check if first and last characters are S and E respectively
        if (sIn.charAt(0) != 'S' && sIn.charAt(sIn.length()-1) != 'E' && sIn.charAt(sIn.length()-2) != ',')
            throw new IllegalArgumentException("First and Last characters are not S and E");
        else if (sIn.charAt(0) != 'S') throw new IllegalArgumentException("First Character in input String is not S");
        else if (sIn.charAt(sIn.length()-1) != 'E') throw new IllegalArgumentException("Last Character in input string is not E");
        else if (sIn.charAt(sIn.length()-2) != ',') throw new IllegalArgumentException("Last Character in input string is not ,");
      //Remove S at start and , + E characters
      String subStr = sIn.substring(1, sIn.length()-1);

      //Split new string and convert to double
      String[] splitStr = subStr.split(";");
      if (splitStr.length != this.numberOfValues) throw new IllegalArgumentException("Incorrect number of values: found:" + splitStr.length + " expected:" + this.numberOfValues);
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
          else {
          	if(i == DataIndex.TIME_INDEX.getOrder()) {
          		out[i] = (double)LocalTime.parse(splitStr[i]).toSecondOfDay();
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
      }

      return out;
    }
}
