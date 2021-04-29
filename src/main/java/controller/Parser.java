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
        if (sIn.isEmpty() || (sIn.charAt(0) != 's' && sIn.charAt(sIn.length()-1) != 'e'))
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
            			System.out.println(splitStr[i]);
            			String[] time = splitStr[i].split(":");
            			out[i] = (Double.parseDouble(time[0])*3600 + Double.parseDouble(time[1])*60 + Double.parseDouble(time[2]));
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
    	
    	
/*
 * S,ACCx,ACCy,ACCz,MAGx,MAGy,MAGz,PRESSURE,LAT,LONG,HOUR,MIN,SEC,E
 * S,0.85,-128.71,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,E
 */
    	
    	double[] out = new double[this.numberOfValues];
//      Arrays.fill(out, EMPTY_ARRAY);
      // Check if first and last characters are S and E respectively
        if (sIn.isEmpty() || (sIn.charAt(0) != 'S' && sIn.charAt(sIn.length()) != 'E' && sIn.charAt(sIn.length()-1) != ','))
            throw new IllegalArgumentException("First and Last characters are not S and E");
        else if (sIn.charAt(0) != 'S') throw new IllegalArgumentException("First Character in input String is not S");
        else if (sIn.charAt(sIn.length()-1) != 'E') throw new IllegalArgumentException("Last Character in input string is not E");
        else if (sIn.charAt(sIn.length()-2) != ',') throw new IllegalArgumentException("Last Character in input string is not ,");
      //Remove S at start and , + E characters
      String subStr = sIn.substring(2, sIn.length()-2);

      //Split new string and convert to double
      String[] splitStr = subStr.split(",");
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
}
