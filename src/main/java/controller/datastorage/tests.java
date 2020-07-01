package controller.datastorage;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class tests {
	static final String[] TEST_FORMATTED_DATES = {"1234-56-78--12-34-56", "1234-56-78 12-34-56-789"};	
	
		@Test
		public void testMakeFoldersWhenNoExist() {
			DataStorage.setPRINTEXIST(false);
			DataStorage.makeFolders();
			
			File[] folders = new File[DataStorage.DATA_TYPE.length];
			for (int i = 0; i < folders.length; i++) {
				folders[i] = new File(DataStorage.DATA_TYPE[i]);
			}
			
			for (int i = 0; i < folders.length; i++) {
				Assert.assertTrue("Error -- " + DataStorage.DATA_TYPE[i] 
						+ " -- folder", folders[i].exists());	
			}
			System.out.println("test complete - make folders");
			
		}
		
		@Test
		public void testMakeFoldersWhenAlreadyExist() {
			
		}
		
		
		// there is probably a way to combine a few of these tests into one single test 
		// but it requires being able to pass a parameter into the test (dataType) to 
		// know which data type (gps, telemetry, raw data) is being written.
		// I think you can use parameterized JUnit tests, but I don't know how to do
		// that while throwing Exceptions and expecting Exceptions.
		
		
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
		
		// can probably combine the RAW header tests together somehow
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
//		
//	}
}

