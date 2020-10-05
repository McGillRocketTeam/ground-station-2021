package controller;
import java.security.InvalidParameterException;
import java.util.Arrays;

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
     * @param sIn the telemetry string to be parsed
     * Data format:
     *                  S32.943012,-106.914963,3097894,4.71,371546.406250,C,8.63,36.80,0.000000,-79,E
     *                  telemetry long data format:     Slat,long,time,alt,vel,sat,acc,temp,gyro_x,RSSI,E\n
     *                  backup GPS data:                Slat,long,time,gps_alt,gps_speed,sat,RSSI,E\n
     *
     * @return an array containing the the split values
     */
    public double[] parse(String sIn) throws IllegalArgumentException {
        double[] out = new double[this.numberOfValues];
//        Arrays.fill(out, EMPTY_ARRAY);
        // Check if first and last characters are S and E respectively
        if (sIn.charAt(0) != 'S' && sIn.charAt(sIn.length()-1) != 'E' && sIn.charAt(sIn.length()-2) != ',')
            throw new IllegalArgumentException("First and Last characters are not S and E");
        else if (sIn.charAt(0) != 'S') throw new IllegalArgumentException("First Character in input String is not S");
        else if (sIn.charAt(sIn.length()-1) != 'E') throw new IllegalArgumentException("Last Character in input string is not E");
        else if (sIn.charAt(sIn.length()-2) != ',') throw new IllegalArgumentException("Last Character in input string is not ,");

        //Remove S at start and , + E characters
        String subStr = sIn.substring(1, sIn.length()-2);

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
