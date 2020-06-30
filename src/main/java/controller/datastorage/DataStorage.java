package controller.datastorage;

import java.io.BufferedReader;
import java.io.File; // for creating directories
import java.io.FileInputStream;
import java.io.FileWriter; // for writing to existing files
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date; // getting current date

import controller.Parser;
import controller.Parser.*;

/**
 * This class is used to generate directories and files to be used for data
 * storage for the McGill Rocket Team Ground Station 2020.
 * <p>
 * 
 * Raw data is stored in <code>.txt</code> files. Parsed data will be added to
 * .CSV files.
 * <p>
 * 
 * To improve this class, a method which to write data to CSV or text files
 * needs to be written. This method would invoke other methods in this class;
 * doing so would hide the complexity of the data storage process, and simplify
 * a program that can be used to save data received from antennas.
 * <p>
 * 
 * @author Jasper Yun
 * 
 */

@SuppressWarnings("unused")
public class DataStorage {
	private static boolean PRINTEXIST = true; // if true, program prints message indicating if folders already exist
	private static final boolean TESTING = false; // if true, run tests from tests.java
	private static final int TelemetryLength = 9; // length of array (or string) for CSV GPS data to be written
	private static final int GPSLength = 7; // length of array (or string) for CSV telemetry data to be written

	private static final String[] DATA_TYPE = { "../storage/", "../storage/telemetry/", "../storage/gps/",
			"../storage/raw_telemetry/", "../storage/raw_gps/", "../storage/antenna_angles/", "../storage/serial/" };

	private static final String TELEMETRY_HEADER = "Current Time, Latitude, Longitude, Time, Altitude, "
			+ "Velocity, Satelites, Acceleration, Temperature, GyroX\n";
	private static final String GPS_HEADER = "Current Time, Latitude, Longitude, Time, GPS_Altitude, GPS_Speed, Number of Satelites\n";
	private static final String RAW_HEADER = "Raw Data:\n____________________\n";

	private static final String[] DATA_FILENAME = { "", "_data_telemetry.csv", "_data_gps.csv", "_raw_data.txt",
			"_raw_data.txt", "_antenna_angles.txt", "" };
	
	// different data types to be written
	private static final int STORAGE = 0;
	private static final int TELEMETRY = 1;
	private static final int GPS = 2;
	private static final int RAW_TELEMETRY = 3;
	private static final int RAW_GPS = 4;
	private static final int ANTENNA_ANGLES = 5;
	private static final int SERIAL = 6;
	
	
	/**
	 * enum to easily access the strings in <code>FILES_TO_STORE</code>.
	 */
	enum Telemetry_type {
		STORAGE, TELEMETRY, GPS, RAW_TELEMETRY, RAW_GPS, ANTENNA_ANGLES, SERIAL;
	}

	/**
	 * Creates a <b>single</b> directory with a specific input path determined by
	 * the programmer. Because McGill Rocket Team will consistently use the same
	 * hierarchy of directories for data storage, we will hardcode the paths in
	 * another method.
	 * <p>
	 * 
	 * This method also prints on the console to indicate whether the directory
	 * creation was successful.
	 * <p>
	 * 
	 * This code comes from <a href=
	 * "https://www.mkyong.com/java/how-to-create-directory-in-java/">Mkyong</a>.
	 * <p>
	 * 
	 * @param file - <code>File</code> object containing the path for the new
	 *             directory
	 */
	private static void make1Dir(File file) {
		// code origin: https://www.mkyong.com/java/how-to-create-directory-in-java/
		if (!file.exists()) {
			if (file.mkdir())
				System.out.println("Directory " + file.toString() + " created successfully.");
			else
				System.out.println("Failed to create directory.");
		} else {
			// choose whether we want to see this message by setting PRINTEXIST
			if (PRINTEXIST)
				System.out.println("Directory already exists");
		}
	}

	/**
	 * The parent folder is called "storage". It is created in the parent folder of
	 * the directory of this Java class.
	 * <p>
	 * 
	 * The subfolders of "storage" are "telemetry", "gps", "raw_telemetry",
	 * "raw_gps", "antenna_angles", "serial".
	 * <p>
	 * 
	 * If the folders already exist, then nothing happens.
	 * 
	 */
	protected static void makeFolders() {
		File storage = new File(DATA_TYPE[STORAGE]);
		File telemetry = new File(DATA_TYPE[TELEMETRY]);
		File gps = new File(DATA_TYPE[GPS]);
		File raw_telemetry = new File(DATA_TYPE[RAW_TELEMETRY]);
		File raw_gps = new File(DATA_TYPE[RAW_GPS]);
		File antenna_angles = new File(DATA_TYPE[ANTENNA_ANGLES]);
		File serial = new File(DATA_TYPE[SERIAL]);

		make1Dir(storage);
		make1Dir(telemetry);
		make1Dir(gps);
		make1Dir(raw_telemetry);
		make1Dir(raw_gps);
		make1Dir(antenna_angles);
		make1Dir(serial);

		// for testing:
		if (PRINTEXIST)
			System.out.println("Done making folders");
	}

	/**
	 * Formats the current time and date into a format for filename use or for use
	 * in the column entries of CSV files.
	 * <p>
	 * 
	 * The <i>filename</i> format is "yyyy-MM-dd--HH-mm-ss".<br>
	 * The <i>column entry</i> format is "yyyy-MM-dd HH:mm:ss:SSS", where SSS
	 * represents milliseconds to three digits.
	 * <p>
	 * 
	 * @return formatted - <code>String[]</code> String array containing the
	 *         formatted dates.
	 */
	protected static String[] dateFormats() {
		String[] formatted = new String[2];
		Date date = new Date(); // current date and time
		
		// date/time for filenames
		SimpleDateFormat dateFormatFileNames = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
		
		// date/time for csv column headers
		SimpleDateFormat dateFormatColumns = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

		formatted[0] = dateFormatFileNames.format(date); // add formatted date strings to string array
		formatted[1] = dateFormatColumns.format(date);

		return formatted;
	}

	/**
	 * Creates the first row in a .txt or .csv file based on the input.
	 * 
	 * @param formattedDates String[] formatted dates for the filename
	 * @param dataType enum type of telemetry data to store
	 */
	// inspiration for this code:
	// https://stackoverflow.com/questions/30073980/java-writing-strings-to-a-csv-file
	// answer by Prashant Ghimire
	
	static void createHeader(String[] formattedDates, int dataType) throws IllegalArgumentException {
		if (dataType != STORAGE && dataType != SERIAL) {
			try (PrintWriter writer = new PrintWriter(
					new File(DATA_TYPE[dataType] + formattedDates[0] + DATA_FILENAME[dataType]))) {

				// write the appropriate header based on the dataType
				if (dataType == TELEMETRY) {
					writer.write(TELEMETRY_HEADER);
				} else if (dataType == GPS) {
					writer.write(GPS_HEADER);
				} else if (dataType == RAW_TELEMETRY || dataType == RAW_GPS || dataType == ANTENNA_ANGLES) {
					writer.write(RAW_HEADER);
				}
			} catch(FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		} else {
			throw new IllegalArgumentException("Input type cannot be STORAGE or SERIAL.");
		}
	}

	// --- BELOW ARE METHODS FOR WRITING DATA --- //

	/**
	 * Writes <b>telemetry or GPS</b> data to an existing CSV file. The data is passed into
	 * the method by a <code>double</code> array; the data is written onto a single line in the
	 * CSV file. The end of the writing process adds a newline. Then the CSV file is
	 * saved.
	 * <p>
	 * 
	 * If the length of the input double is incorrect, the data is assumed to
	 * be corrupted and will not be written to the CSV.
	 * <p>
	 * 
	 * @param formattedDates - <code>String[]</code> 2-entry string array with
	 *                       formatted date strings
	 * @param dataType       - <code>int</code> type of data to be saved (telemetry or GPS)
	 * @param fileTime       - <code>String</code> Date and Time that is in the file
	 *                       name that the data should be written to
	 * @param data           - <code>double[]</code> Double array with telemetry
	 *                       data
	 * @return success - <code>boolean</code> Flag indicating whether the save
	 *         operation failed at any point
	 */
	static boolean saveDataCSV(String[] formattedDates, int dataType, String fileTime, double[] data) {
		boolean success = true; // flag for whether data gets written or not
		
		if (dataType == TELEMETRY || dataType == GPS) {
			try (FileWriter file = new FileWriter(DATA_TYPE[dataType] + fileTime + DATA_FILENAME[dataType], true)) {
				boolean data_uncorrupted = true;
				
				while (data_uncorrupted) { // check that we have uncorrupted data
					if (dataType == TELEMETRY && data.length == TelemetryLength) break;
					else if (dataType == GPS && data.length == GPSLength) break;
					else data_uncorrupted = false;
				}
				//System.out.println(data.length);
				
				if (data_uncorrupted) {
					file.write(formattedDates[1]); // writes the current time to the first column of the row
					// String currentTime = formattedDates[1]; // current date precise to ms
	
					file.write(','); // for the CSV to be written to properly formatted
					for (int i = 0; i < data.length - 1; i++) { // data.length-1 means 'E' at the end is not written
						file.write(String.valueOf(data[i]));
						file.write(',');
					}
					
					file.append('\n');
				
				} else System.out.println("corrupt data"); // if data is "corrupt", don't do anything and move to next line to save
			} catch (FileNotFoundException e) {
				System.out.println("exception :" + e.getMessage());
				success = false;
			} catch (IOException e) {
				System.out.println("exception :" + e.getMessage());
			}
		}
		return success; // method breaks as soon as failed is true (if file not found)
	}
	
	/**
	 * Saves raw data received in the form of a string to a text file.
	 * <p>
	 * 
	 * @param formattedDates - <code>String[]</code> Contains the current date and
	 *                       time formatted appropriately
	 * @param dataType       - <code>int</code> Type of data to save (telemetry, gps, antenna angles)
	 * @param fileTime       - <code>String</code> Date/Time contained in the name
	 *                       of the file to save the raw data to
	 * @param data           - <code>String</code> Data to be saved
	 * 
	 */
	static void saveDataRaw(String[] formattedDates, int dataType, String fileTime, String data) {

		if (dataType == RAW_TELEMETRY || dataType == RAW_GPS || dataType == ANTENNA_ANGLES) {
			try (FileWriter file = new FileWriter(DATA_TYPE[dataType] + fileTime + DATA_FILENAME[dataType], true)) {
				if (dataType == ANTENNA_ANGLES) file.write(formattedDates[1] + "\n"); // testing: see which version I am using
				file.write(data); // write the raw telemetry data string to file
				file.append("\n\n"); // add newline
			} catch (FileNotFoundException e) {
				System.out.println("exception :" + e.getMessage());
			} catch (IOException e) {
				System.out.println("exception :" + e.getMessage());
			}
		}
	}

	/**
	 * Reads a single line from a file and returns a string with the data that is
	 * read. The method will be used to read raw data from text files and pass the
	 * data as strings to the methods to save telemetry or GPS data into CSVs.
	 * <p>
	 * 
	 * @param filePath - <code>String</code> path to the file to be read from
	 * @return line - <code>String</code> single line of data read from a file to be
	 *         written to the CSV
	 * @throws Exception - for any errors related to nonexisting files
	 */
	static String readLine(String filePath) throws Exception {
		BufferedReader read = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(filePath)), Charset.forName("UTF-8")));
		try {
			String line = read.readLine();
			return line;
		} finally { // to close the bufferedReader
			try {
				if (read != null)
					read.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Main method of the program. It begins by checking whether the necessary
	 * folders have been created for data storage. If not, the directories will be
	 * created with the parent folder name "storage".
	 * <p>
	 * 
	 * The method will then create .CSV files for telemetry and GPS data storage,
	 * and <code>.txt</code> files for telemetry, GPS, and antenna angle raw data
	 * storage.
	 * <p>
	 * 
	 * Currently, more code is written to test some of the methods for saving data
	 * to CSV and/or text files. However, more tests <b><i>need</i></b> to be done,
	 * and the tests must be more rigorous.
	 * <p>
	 * 
	 * The file <code>tests.java</code> in the same package contains a method
	 * <code>runTests()</code> that tests the methods in this class for creating
	 * directories and creating new CSV and text files.
	 * <p>
	 * 
	 * @param args - arguments as required by syntax
	 * 
	 * @throws Exception - for any errors related to non-existing files
	 */

	public static void main(String[] args) throws Exception {
		
		final Parser parsley = new Parser(TelemetryLength);

		String[] formattedDates = dateFormats();
		makeFolders();
		
		for (int i = TELEMETRY; i <= ANTENNA_ANGLES; i++) {
			createHeader(formattedDates, i);
		}


		// testing: saving GPS data to CSV
		for (int testing = 0; testing < 100; testing++) {
			
			String telem_raw = readLine("../test_1.txt"); // get the string from the test Reader text file
			System.out.println(telem_raw);
			
//			String[] convToStringArray = (telem_raw.split(",")); // split data from string into string array
//			double[] data = new double[convToStringArray.length]; // array to store data for writing to CSV
//			for (int i = 0; i < data.length - 1; i++) // populate the array
//			{
//				data[i] = Double.valueOf(convToStringArray[i]);
//			}
			
			double[] parseyed = parsley.parse(telem_raw);
			
			String fileTime = dateFormats()[0]; // record the current time
			
			
			saveDataCSV(dateFormats(), TELEMETRY, fileTime, parseyed);
			saveDataRaw(dateFormats(), RAW_TELEMETRY, fileTime, telem_raw);
			saveDataRaw(dateFormats(), RAW_GPS, fileTime, telem_raw);
			
//			String telemetryData = readLine("../testReadingTelemetry.txt"); // get the string from the test Reader text
//																			// file
//
//			String[] convToStringArray2 = (telemetryData.split(",")); // split data from string into string array
//			double[] data2 = new double[convToStringArray2.length]; // array to store data for writing to CSV
//			for (int i = 0; i < data2.length - 1; i++) // populate the array
//			{
//				data2[i] = Double.valueOf(convToStringArray2[i]);
//			}
//
//			saveTelemetryCSV(dateFormats(), fileTime, data2);
//			saveTelemetryRaw(dateFormats(), fileTime, telemetryData);

		}

		System.out.println("program terminated");
	}

}
