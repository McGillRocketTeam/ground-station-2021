package controller.datastorage;

import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;


import java.io.File; // for creating directories
import controller.datastorage.*;

public class tests {
	static final String[] TEST_FORMATTED_DATES = {"1234-56-78--12-34-56", "1234-56-78 12-34-56-789"};	
	
		@Test
		public void testMakeFoldersWhenNoExist() {
			
		}
		
		@Test
		public void testMakeFoldersWhenAlreadyExist() {
			
		}
		
		@Test(expected=Exception.class)
		public void testWriteTelemetryHeaderCSV() throws Exception {
			int dataType = DataStorage.TELEMETRY;
			DataStorage.createHeader(TEST_FORMATTED_DATES, dataType);
			
			String filePath = DataStorage.DATA_TYPE[dataType] + TEST_FORMATTED_DATES[0] + DataStorage.DATA_FILENAME[dataType];
			
			String actual = "";
			try {
				actual = DataStorage.readLine(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// remove \n because readLine strips it
			String expected = DataStorage.TELEMETRY_HEADER.substring(0, DataStorage.TELEMETRY_HEADER.length()-1);
			
			Assert.assertEquals("failure - telemetry CSV header written wrong", expected, actual);
			System.out.println("test complete - telemetry CSV header");
		}
	
		@Test(expected=Exception.class)
		public void testWriteGPSHeaderCSV() throws Exception {
			int dataType = DataStorage.GPS;
			DataStorage.createHeader(TEST_FORMATTED_DATES, dataType);
			
			String filePath = DataStorage.DATA_TYPE[dataType] + TEST_FORMATTED_DATES[0] + DataStorage.DATA_FILENAME[dataType];
			
			String actual = "";
			try {
				actual = DataStorage.readLine(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// remove \n because readLine strips it
			String expected = DataStorage.GPS_HEADER.substring(0, DataStorage.GPS_HEADER.length()-1);
			
			Assert.assertEquals("failure - GPS CSV header written wrong", expected, actual);
			System.out.println("test complete - GPS CSV header");
		}
		
		@Test(expected=Exception.class)
		public void testWriteTelemetryHeaderRaw() throws Exception {
			int dataType = DataStorage.RAW_TELEMETRY;
			DataStorage.createHeader(TEST_FORMATTED_DATES, dataType);
			
			String filePath = DataStorage.DATA_TYPE[dataType] + TEST_FORMATTED_DATES[0] + DataStorage.DATA_FILENAME[dataType];
			
			String actual = "";
			try {
				actual = DataStorage.readLine(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//String expected = DataStorage.RAW_HEADER.substring(0, 9);
			String expected = "Raw Data:";
			
			Assert.assertEquals("failure - telemetry RAW header written wrong", expected, actual);
			System.out.println("test complete - telemetry RAW headers");
		}
		
		@Test(expected=Exception.class)
		public void testWriteGPSHeaderRaw() throws Exception {
			int dataType = DataStorage.RAW_GPS;
			DataStorage.createHeader(TEST_FORMATTED_DATES, dataType);
			
			String filePath = DataStorage.DATA_TYPE[dataType] + TEST_FORMATTED_DATES[0] + DataStorage.DATA_FILENAME[dataType];
			
			String actual = "";
			try {
				actual = DataStorage.readLine(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//String expected = DataStorage.RAW_HEADER.substring(0, 9);
			String expected = "Raw Data:";
			
			Assert.assertEquals("failure - GPS RAW header written wrong", expected, actual);
			System.out.println("test complete - GPS RAW headers");
		}
		
		@Test(expected=Exception.class)
		public void testWriteAntennaAnglesHeaderRaw() throws Exception {
			int dataType = DataStorage.ANTENNA_ANGLES;
			DataStorage.createHeader(TEST_FORMATTED_DATES, dataType);
			
			String filePath = DataStorage.DATA_TYPE[dataType] + TEST_FORMATTED_DATES[0] + DataStorage.DATA_FILENAME[dataType];
			
			String actual = "";
			try {
				actual = DataStorage.readLine(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//String expected = DataStorage.RAW_HEADER.substring(0, 9);
			String expected = "Raw Data:";
			
			Assert.assertEquals("failure - telemetry RAW header written wrong", expected, actual);
			System.out.println("test complete - telemetry RAW headers");
		}
		
		
	
	
	
//	
//	
//	/**
//	 * Runs basic tests to check whether the DataStorage
//	 * class methods run as expected. <p>
//	 * 
//	 * @throws Exception - In case of nonexistent files
//	 */
//	public static void runTests() {
//		
//		// test to check that folders were created successfully and properly
//		DataStorage.makeFolders(); // make folders
//		
//		boolean error = false;
//		
//		File[] folders = new File[7];
//		
//		folders[0] = new File("../storage");
//		folders[1] = new File("../storage/telemetry");
//		folders[2] = new File("../storage/gps");
//		folders[3] = new File("../storage/raw_telemetry");
//		folders[4] = new File("../storage/raw_gps");
//		folders[5] = new File("../storage/antenna_angles");
//		folders[6] = new File("../storage/serial");
//		
//		for (int i = 0; i < folders.length; i++) {
//			if (!folders[i].exists()) {
//				System.out.println("Error: Folder " + i);
//				error = true;
//			}
//		}
//		
//		if (!error) System.out.println("Folders created successfully\n");
//		
//		// assume that dateFormats() does its job properly
//		// since I don't know how to test it unless we just print it
//		
//		// test to check the headers were created properly
//		String[] formattedDates = DataStorage.dateFormats();
//		
//		// create headers
//		DataStorage.createTelemetryHeader(formattedDates);
//		DataStorage.createGPSHeader(formattedDates);
//		DataStorage.createRawTelemetry(formattedDates);
//		DataStorage.createRawGPS(formattedDates);
//		DataStorage.createRawAntenna(formattedDates);
//		
//		// test the headers were written correctly
//		String[] pathsToReadFrom = new String[5];
//		pathsToReadFrom[0] = "../storage/telemetry/" + formattedDates[0] + "_data_telemetry.csv";
//		pathsToReadFrom[1] = "../storage/gps/" + formattedDates[0] + "_data_gps.csv";
//		pathsToReadFrom[2] = "../storage/raw_telemetry/" + formattedDates[0] + "_raw_data.txt";
//		pathsToReadFrom[3] = "../storage/raw_gps/" + formattedDates[0] + "_raw_data.txt";
//		pathsToReadFrom[4] = "../storage/antenna_angles" + formattedDates[0] + "_antenna_angles.txt";
//		
//		// the correct headers:
//		String[] correctHeaders = new String[5];
//		correctHeaders[0] = "Current Time, Latitude, Longitude, Time, Altitude, "
//				+ "Velocity, Satelites, Acceleration, Temperature, GyroX";
//		correctHeaders[1] = "Current Time, Latitude, Longitude, Time, GPS_Altitude, GPS_Speed, Number of Satelites";
//		correctHeaders[2] = "Raw Data:";
//		correctHeaders[3] = "Raw Data:";
//		correctHeaders[4] = "Raw Data:\n";
//		
//		
//		
//		error = false;
//		
//		for (int j = 0; j < pathsToReadFrom.length-1; j++) {
//			String s1 = DataStorage.readLine(pathsToReadFrom[j]);
//			String s2 = correctHeaders[j];
//			if (!(s1.equals(s2))) {
//				System.out.println(s1);
//				System.out.println(s2);
//				System.out.println("Error: header not equal -- " + j + "\n");
//				error = true;
//			}
//			//System.out.println(error);
//		}
//		
//		if (!error) System.out.println("Headers created successfully");
//		//System.out.println("reached here");
//		
//		
//		// this test is incomplete
//		// check that data is saved properly
//		String[] textToReadFrom = new String[5];
//		textToReadFrom[0] = "../testReadingTelemetry.txt";
//		textToReadFrom[1] = "../testReadingGPS.txt";
//		textToReadFrom[2] = "../testReadingRawTelemetry.txt";
//		textToReadFrom[3] = "../testReadingRawGPS.txt";
//		textToReadFrom[4] = "../testReadingAntennas.txt";
//		
//		error = false;
//		
//		System.out.println("-----  End of testing  -----\n");
//	}
}

