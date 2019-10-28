package controller;

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
    public int[] parse(String sIn, int numCommas) {
        int[] out = {};



        return out;
    }
}
