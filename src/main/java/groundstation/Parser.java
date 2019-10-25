package main.java.groundstation;
import java.util.regex.*;

public class Parser {
    public Parser() {

    }

    /**
     *
     * @param sIn the telemetry string to be parsed
     * @param numCommas the number of commas in the telemetry string
     *                     (data format can vary so this needs to be flexible)
     * Data format:
     *                  S32.943012,-106.914963,3097894,4.71,371546.406250,C,8.63,36.80,0.000000,-79,E
     *                  telemetry long data format:     Slat,long,time,alt,vel,sat,acc,temp,gyro_x,RSSI,E\n
     *                  backup GPS data:                Slat,long,time,gps_alt,gps_speed,sat,RSSI,E\n
     *
     * @return an array containing the the split values
     */

    private Pattern regexPattern = Pattern.compile("^S{0,1}(\\d+\\.\\d+$|\\d+$)"); // Matches S + Integer, S + Double, or just Double.

    public double[] parse(String sIn, int numCommas) {
        int index = 0;
        double[] out = new double[numCommas + 1];

        String[] parsedListOfStrings = sIn.split(","); // Call Java's built-in method to split the string by the "," symbol.

        if (parsedListOfStrings.length != numCommas + 1) {
            return out; // Maybe apply some magic regex filter to savage some data?
        }

        for (String entry: parsedListOfStrings) {
            Matcher matcher = regexPattern.matcher(entry);
            if (matcher.find()) {
                out[index] = Double.parseDouble(matcher.group(0));
            }
            else {
                out[index] = -1;
            }
            index += 1;
        }





        return out;
    }
}
