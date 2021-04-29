//
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import controller.datastorage.DataStorage;
//
//public class tests {
//	// date formats for files used for testing
//	static final String[] TEST_FORMATTED_DATES = {"1234-56-78--12-34-56", "1234-56-78 12-34-56-789"};
//	static final String TEST_FILE_TIME = TEST_FORMATTED_DATES[0];
//
//	@Test
//	public void testMakeFoldersWhenNoExist() {
//		DataStorage.setPRINTEXIST(false); // turn off debug printing
//		DataStorage.makeFolders(); 
//
//		// create File objects for each folder -- this does not create an actual folder
//		File[] folders = new File[DataStorage.DATA_TYPE.length];
//		for (int i = 0; i < folders.length; i++) {
//			folders[i] = new File(DataStorage.DATA_TYPE[i]);
//		}
//
//		// check that folders exist
//		for (int i = 0; i < folders.length; i++) {
//			Assert.assertTrue("Error -- " + DataStorage.DATA_TYPE[i] 
//					+ " -- folder", folders[i].exists());	
//		}
//		System.out.println("test complete - make folders");
//	}
//
//	@Test
//	public void testMakeFoldersWhenAlreadyExist() {
//		// do we need this?
//	}
//
//
//	// there is probably a way to combine a few of these tests into one single test 
//	// but it requires being able to pass a parameter into the test (dataType) to 
//	// know which data type (gps, telemetry, raw data) is being written.
//	// I think you can use parameterized JUnit tests, but I don't know how to do
//	// that while throwing Exceptions and expecting Exceptions.
//
//
//	@Test(expected=Exception.class)
//	public void testWriteTelemetryHeaderCSV() throws Exception {
//		int dataType = DataStorage.TELEMETRY;
//		DataStorage.createHeader(TEST_FORMATTED_DATES, dataType);
//
//		// check data written to file is correctly written
//		String filePath = DataStorage.DATA_TYPE[dataType] + TEST_FORMATTED_DATES[0] + DataStorage.DATA_FILENAME[dataType];
//		String actual = "";
//		try {
//			actual = DataStorage.readLine(filePath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// remove \n because readLine strips it
//		String expected = DataStorage.TELEMETRY_HEADER.substring(0, DataStorage.TELEMETRY_HEADER.length()-1);
//
//		Assert.assertEquals("failure - telemetry CSV header written wrong", expected, actual);
//		System.out.println("test complete - telemetry CSV header");
//	}
//
//	@Test(expected=Exception.class)
//	public void testWriteGPSHeaderCSV() throws Exception {
//		int dataType = DataStorage.GPS;
//		DataStorage.createHeader(TEST_FORMATTED_DATES, dataType);
//
//		// check data written to file is correctly written
//		String filePath = DataStorage.DATA_TYPE[dataType] + TEST_FORMATTED_DATES[0] + DataStorage.DATA_FILENAME[dataType];
//		String actual = "";
//		try {
//			actual = DataStorage.readLine(filePath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// remove \n because readLine strips it
//		String expected = DataStorage.GPS_HEADER.substring(0, DataStorage.GPS_HEADER.length()-1);
//
//		Assert.assertEquals("failure - GPS CSV header written wrong", expected, actual);
//		System.out.println("test complete - GPS CSV header");
//	}
//
//	// can probably combine the RAW header tests together somehow
//	@Test(expected=Exception.class)
//	public void testWriteTelemetryHeaderRaw() throws Exception {
//		int dataType = DataStorage.RAW_TELEMETRY;
//		DataStorage.createHeader(TEST_FORMATTED_DATES, dataType);
//
//		// check data written to file is correctly written
//		String filePath = DataStorage.DATA_TYPE[dataType] + TEST_FORMATTED_DATES[0] + DataStorage.DATA_FILENAME[dataType];
//		String actual = "";
//		try {
//			actual = DataStorage.readLine(filePath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		//String expected = DataStorage.RAW_HEADER.substring(0, 9);
//		String expected = "Raw Data:";
//
//		Assert.assertEquals("failure - telemetry RAW header written wrong", expected, actual);
//		System.out.println("test complete - telemetry RAW headers");
//	}
//
//	@Test(expected=Exception.class)
//	public void testWriteGPSHeaderRaw() throws Exception {
//		int dataType = DataStorage.RAW_GPS;
//		DataStorage.createHeader(TEST_FORMATTED_DATES, dataType);
//
//		// check data written to file is correctly written
//		String filePath = DataStorage.DATA_TYPE[dataType] + TEST_FORMATTED_DATES[0] + DataStorage.DATA_FILENAME[dataType];
//		String actual = "";
//		try {
//			actual = DataStorage.readLine(filePath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		//String expected = DataStorage.RAW_HEADER.substring(0, 9);
//		String expected = "Raw Data:";
//
//		Assert.assertEquals("failure - GPS RAW header written wrong", expected, actual);
//		System.out.println("test complete - GPS RAW headers");
//	}
//
//	@Test(expected=Exception.class)
//	public void testWriteAntennaAnglesHeaderRaw() throws Exception {
//		int dataType = DataStorage.ANTENNA_ANGLES;
//		DataStorage.createHeader(TEST_FORMATTED_DATES, dataType);
//
//		// check data written to file is correctly written
//		String filePath = DataStorage.DATA_TYPE[dataType] + TEST_FORMATTED_DATES[0] + DataStorage.DATA_FILENAME[dataType];
//		String actual = "";
//		try {
//			actual = DataStorage.readLine(filePath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		//String expected = DataStorage.RAW_HEADER.substring(0, 9);
//		String expected = "Raw Data:";
//
//		Assert.assertEquals("failure - telemetry RAW header written wrong", expected, actual);
//		System.out.println("test complete - telemetry RAW headers");
//	}
//	
//	@Test
//	public void testSaveTelemetryCSV() {
//		// properly parsed telemetry double[]
//		// example of raw telemetry data: S32.943012,-106.914963,3097894,4.71,371546.406250,C,8.63,36.80,0.000000,-79,E
//		double[] good_telemetry = {32.943008,-106.914925,1883089,1.26,0.000000,10,8.54,31.05,0.000000, -102};
//		
//		// improperly parsed or corrupted telemetry double[]
//		double[] bad_telemetry_1 = {32.943008,-106.914925,1883089,1.26,0.000000,8.54,31.05,0.000000};
//		double[] bad_telemetry_2 = {32.943008,1883089,1.26,0.000000,14,8.54,31.05,0.000000,-102,15,35,48};
//		double[] bad_telemetry_3 = {10, 12, 1.26};
//
//		int dataType = DataStorage.TELEMETRY;
//		boolean successful_saveData = DataStorage.saveDataCSV(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, good_telemetry);
//		
//		// to check that the line was properly written to the CSV file
//		File file = new File(DataStorage.DATA_TYPE[dataType] + TEST_FILE_TIME + DataStorage.DATA_FILENAME[dataType]);
//		FileReader fr = null;
//		String line = "";
//		try {
//			fr = new FileReader(file);
//			BufferedReader br = new BufferedReader(fr);
//			br.readLine();
//			line = br.readLine();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		String expected_line = "1234-56-78 12-34-56-789,32.943008,-106.914925,1883089.0,1.26,0.0,10.0,8.54,31.05,0.0,-102.0,";
//		Assert.assertTrue(successful_saveData && expected_line.equals(line));		
//		System.out.println("test complete - telemetry CSV good");
//		
//		Assert.assertFalse(DataStorage.saveDataCSV(TEST_FORMATTED_DATES, DataStorage.STORAGE, TEST_FILE_TIME, good_telemetry));
//		System.out.println("test complete - telemetry CSV bad (STORAGE)");
//		
//		Assert.assertFalse(DataStorage.saveDataCSV(TEST_FORMATTED_DATES, DataStorage.SERIAL, TEST_FILE_TIME, good_telemetry));
//		System.out.println("test complete - telemetry CSV bad (SERIAL)");
//		
//		Assert.assertFalse(DataStorage.saveDataCSV(TEST_FORMATTED_DATES, DataStorage.TELEMETRY, TEST_FILE_TIME, bad_telemetry_1));
//		System.out.println("test complete - telemetry CSV bad (data 1)");
//		
//		Assert.assertFalse(DataStorage.saveDataCSV(TEST_FORMATTED_DATES, DataStorage.TELEMETRY, TEST_FILE_TIME, bad_telemetry_2));
//		System.out.println("test complete - telemetry CSV bad (data 2)");
//		
//		Assert.assertFalse(DataStorage.saveDataCSV(TEST_FORMATTED_DATES, DataStorage.TELEMETRY, TEST_FILE_TIME, bad_telemetry_3));
//		System.out.println("test complete - telemetry CSV bad (data 3)");
//		
//	}
//	
//	@Test
//	public void testSaveGPSCSV() {
//		// example of raw GPS data: S46.004520,-72.732620,577962,40.700001,0.018520,A,E
//		double[] good_gps = {46.004520,-72.732620,577962,40.700001,0.018520,10};
//
//		// improperly parsed or corrupted telemetry double[]
//		double[] bad_gps_1 = {32.943008,-106.914925,1883089,1.26,0.000000,8.54,31.05,0.000000};
//		double[] bad_gps_2 = {46.004520,-72.732620,577962,40.700001,0.018520,10,11};
//		double[] bad_gps_3 = {10, 12, 1.26};
//
//		int dataType = DataStorage.GPS;
//		boolean successful_saveData = DataStorage.saveDataCSV(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, good_gps);
//
//		// to check that the line was properly written to the CSV file
//		File file = new File(DataStorage.DATA_TYPE[dataType] + TEST_FILE_TIME + DataStorage.DATA_FILENAME[dataType]);
//		FileReader fr = null;
//		String line = "";
//		try {
//			fr = new FileReader(file);
//			BufferedReader br = new BufferedReader(fr);
//			br.readLine();
//			line = br.readLine();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		String expected_line = "1234-56-78 12-34-56-789,46.00452,-72.73262,577962.0,40.700001,0.01852,10.0,";
//		Assert.assertTrue(successful_saveData && expected_line.equals(line));		
//		System.out.println("test complete - GPS CSV good");
//
//		Assert.assertFalse(DataStorage.saveDataCSV(TEST_FORMATTED_DATES, DataStorage.STORAGE, TEST_FILE_TIME, good_gps));
//		System.out.println("test complete - GPS CSV bad (STORAGE)");
//
//		Assert.assertFalse(DataStorage.saveDataCSV(TEST_FORMATTED_DATES, DataStorage.SERIAL, TEST_FILE_TIME, good_gps));
//		System.out.println("test complete - GPS CSV bad (SERIAL)");
//
//		Assert.assertFalse(DataStorage.saveDataCSV(TEST_FORMATTED_DATES, DataStorage.GPS, TEST_FILE_TIME, bad_gps_1));
//		System.out.println("test complete - GPS CSV bad (data 1)");
//
//		Assert.assertFalse(DataStorage.saveDataCSV(TEST_FORMATTED_DATES, DataStorage.GPS, TEST_FILE_TIME, bad_gps_2));
//		System.out.println("test complete - GPS CSV bad (data 2)");
//
//		Assert.assertFalse(DataStorage.saveDataCSV(TEST_FORMATTED_DATES, DataStorage.GPS, TEST_FILE_TIME, bad_gps_3));
//		System.out.println("test complete - GPS CSV bad (data 3)");
//	}
//	
//	@Test
//	public void testSaveTelemetryRaw() {
//		// example of raw telemetry data: S32.943012,-106.914963,3097894,4.71,371546.406250,C,8.63,36.80,0.000000,-79,E
//		String telemetry_1 = "S32.943012,-106.914963,3097894,4.71,371546.406250,C,8.63,36.80,0.000000,-79,E";
//		String telemetry_2 = "S32.943012,-106.914963,3097894,4.71,371546.406250,C,8.63,36.80,0.000000,E";
//		String telemetry_3 = "S32.943012,106.914963,3097894,C,E";
//
//		int dataType = DataStorage.RAW_TELEMETRY;
//		DataStorage.saveDataRaw(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, telemetry_1);
//		DataStorage.saveDataRaw(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, telemetry_2);
//		DataStorage.saveDataRaw(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, telemetry_3);
//
//		// to check that the line was properly written to the CSV file
//		File file = new File(DataStorage.DATA_TYPE[dataType] + TEST_FILE_TIME + DataStorage.DATA_FILENAME[dataType]);
//		FileReader fr = null;
//		String line = "";
//		try {
//			fr = new FileReader(file);
//			BufferedReader br = new BufferedReader(fr);
//			br.readLine();	// discard first line and second lines from the "Raw Data:" header
//			br.readLine();
//			int counter = 0;
//			while (counter <= 3 && (line=br.readLine()) != null) {
//				if (!(line.equals(""))) {
//					counter += 1;
//					switch (counter) {
//						case 1:  Assert.assertTrue(line.equals(telemetry_1));
//								 System.out.println("test complete - telemetry raw 1");
//								 break;
//						case 2:  Assert.assertTrue(line.equals(telemetry_2));
//								 System.out.println("test complete - telemetry raw 2");
//								 break;
//						case 3:  Assert.assertTrue(line.equals(telemetry_3));
//								 System.out.println("test complete - telemetry raw 3");
//								 break;
//						default: System.out.println("counter out of range");
//								 break;
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testSaveGPSRaw() {
//		// example of raw GPS data: S46.004520,-72.732620,577962,40.700001,0.018520,A,E
//		String gps_1 = "S32.943012,-106.914963,3097894,4.71,371546.406250,C,8.63,36.80,0.000000,-79,E";
//		String gps_2 = "S46.004520,-72.732620,577962,40.700001,0.018520,A,E";
//		String gps_3 = "F";
//
//		int dataType = DataStorage.RAW_GPS;
//		DataStorage.saveDataRaw(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, gps_1);
//		DataStorage.saveDataRaw(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, gps_2);
//		DataStorage.saveDataRaw(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, gps_3);
//
//		// to check that the line was properly written to the CSV file
//		File file = new File(DataStorage.DATA_TYPE[dataType] + TEST_FILE_TIME + DataStorage.DATA_FILENAME[dataType]);
//		FileReader fr = null;
//		String line = "";
//		try {
//			fr = new FileReader(file);
//			BufferedReader br = new BufferedReader(fr);
//			br.readLine();	// discard first line and second lines from the "Raw Data:" header
//			br.readLine();
//			int counter = 0;
//			while (counter <= 3 && (line=br.readLine()) != null) {
//				if (!(line.equals(""))) {
//					counter += 1;
//					switch (counter) {
//					case 1:  Assert.assertTrue(line.equals(gps_1));
//					System.out.println("test complete - GPS raw 1");
//					break;
//					case 2:  Assert.assertTrue(line.equals(gps_2));
//					System.out.println("test complete - GPS raw 2");
//					break;
//					case 3:  Assert.assertTrue(line.equals(gps_3));
//					System.out.println("test complete - GPS raw 3");
//					break;
//					default: System.out.println("counter out of range");
//					break;
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testSaveAntennaAnglesRaw() {
//		// I have no idea what example data looks like but the methd should be able to write any data
//		String aa_1 = "S32.943012,-106.914963,3097894,4.71,371546.406250,C,8.63,36.80,0.000000,-79,E";
//		String aa_2 = "S46.00, -46, deg";
//		String aa_3 = "F";
//
//		int dataType = DataStorage.ANTENNA_ANGLES;
//		DataStorage.saveDataRaw(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, aa_1);
//		DataStorage.saveDataRaw(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, aa_2);
//		DataStorage.saveDataRaw(TEST_FORMATTED_DATES, dataType, TEST_FILE_TIME, aa_3);
//
//		// to check that the line was properly written to the CSV file
//		File file = new File(DataStorage.DATA_TYPE[dataType] + TEST_FILE_TIME + DataStorage.DATA_FILENAME[dataType]);
//		FileReader fr = null;
//		String line = "";
//		try {
//			fr = new FileReader(file);
//			BufferedReader br = new BufferedReader(fr);
//			br.readLine();	// discard first line and second lines from the "Raw Data:" header
//			br.readLine();
//			int counter = 0;
//			while (counter <= 3 && (line=br.readLine()) != null) {
//				if (!(line.equals(""))) {
//					counter += 1;
//					switch (counter) {
//					case 1:  Assert.assertTrue(line.equals(aa_1));
//					System.out.println("test complete - Antenna Angles raw 1");
//					break;
//					case 2:  Assert.assertTrue(line.equals(aa_2));
//					System.out.println("test complete - Antenna Angles raw 2");
//					break;
//					case 3:  Assert.assertTrue(line.equals(aa_3));
//					System.out.println("test complete - Antenna Angles raw 3");
//					break;
//					default: System.out.println("counter out of range");
//					break;
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
