package controller.datastorage;

import java.io.BufferedReader;
import java.io.File;					// for creating directories
import java.io.FileInputStream;
import java.io.FileWriter;				// for writing to existing files
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;					// getting current date


/**
 * This class is used to generate directories and files to be used for data
 * storage for the McGill Rocket Team Ground Station 2020. <p>
 * 
 * Raw data is stored in <code>.txt</code> files. Parsed data will be added
 * to .CSV files. <p>
 * 
 * To improve this class, a method which to write data to CSV or text files 
 * needs to be written. This method would invoke other methods in this class;
 * doing so would hide the complexity of the data storage process, and simplify
 * a program that can be used to save data received from antennas. <p>
 * 
 * @author Jasper Yun
 * 
 */

@SuppressWarnings("unused")
public class DataStorage 
{
	
	
	// change methods from private to package private so
	// that we can store all the methods in one class
	// and have another class that contains methods which 
	// use the methods in this class to save data.
	// Then, we would not need a main() method in this class
	
	private static boolean PRINTEXIST = false;		// if true, program prints message indicating if folders already exist
	private static final boolean TESTING = true;	// if true, run tests from tests.java
	private static final int TelemetryLength = 10;  // length of array (or string) for CSV GPS data to be written
	private static final int GPSLength = 7;			// length of array (or string) for CSV telemetry data to be written
	
	/**
	 * Creates a <b>single</b> directory with a specific input path
	 * determined by the programmer. Because McGill Rocket Team
	 * will consistently use the same hierarchy of directories for
	 * data storage, we will hardcode the paths in another method. <p>
	 * 
	 * This method also prints on the console to 
	 * indicate whether the directory creation was successful. <p>
	 * 
	 * This code comes from <a href="https://www.mkyong.com/java/how-to-create-directory-in-java/">Mkyong</a>. <p>
	 * 
	 * @param file - <code>File</code> object containing the path for the new directory
	 * 
	 */
	
	private static void make1Dir(File file) 
	{
		// code origin: https://www.mkyong.com/java/how-to-create-directory-in-java/
		if (!file.exists())
		{
			if (file.mkdir()) 
				System.out.println("Directory " + file.toString() + " created successfully.");
			else 
				System.out.println("Failed to create directory.");
		}
		else 
		{
			//choose whether we want to see this message by setting PRINTEXIST
			if (PRINTEXIST) System.out.println("Directory already exists");
		}
	}
	
	
	/**
	 * 
	 * Creates <b>multiple</b> directories given an input path. Prints on the console to 
	 * indicate whether the directory creation was successful. <p>
	 * 
	 * This code comes from <a href="https://www.mkyong.com/java/how-to-create-directory-in-java/">Mkyong</a>. <p>
	 * 
	 * @param file - <code>File</code> object containing the path for the new directories
	 * 
	 */
	
	
	private static void make2Dirs(File file) 
	{
		// code origin: https://www.mkyong.com/java/how-to-create-directory-in-java/
		if (!file.exists())
		{
			if (file.mkdirs()) 
				System.out.println("Directory " + file.toString() + "\t created successfully.");
			else 
				System.out.println("Failed to created directory.");
		}
		else
		{
			//choose whether we want to see this message by setting PRINTEXIST
			if (PRINTEXIST) System.out.println("Directory already exists");		
		}
			
	}
	
	
	
	/**
	 * 
	 *  The parent folder is called "storage". It is created 
	 *  in the parent folder of the directory of this Java class. <p>
	 *  
	 *  The subfolders of "storage" are "telemetry", "gps", "raw_telemetry",
	 *  "raw_gps", "antenna_angles", "serial". <p>
	 *  
	 *  If the folders already exist, then nothing happens.
	 *  
	 */
	
	protected static void makeFolders() 
	{
		File storage = new File("../storage");
		File telemetry = new File("../storage/telemetry");
		File gps = new File("../storage/gps");
		File raw_telemetry = new File("../storage/raw_telemetry");
		File raw_gps = new File("../storage/raw_gps");
		File antenna_angles = new File("../storage/antenna_angles");
		File serial = new File("../storage/serial");
		
		
		// I will make this nicer later but for initial implementation, this works
		// because of the way that I am creating the folders, so we may not need the
		// DirMaker2 method
		
		make1Dir(storage);
		make1Dir(telemetry);
		make1Dir(gps);
		make1Dir(raw_telemetry);
		make1Dir(raw_gps);
		make1Dir(antenna_angles);
		make1Dir(serial);	
		
		// for testing:
		if (PRINTEXIST) System.out.println("Done making folders");
	}
	
	
	
	/**
	 * 
	 * Formats the current time and date into a format for filename use
	 * or for use in the column entries of CSV files. <p>
	 * 
	 * The <i>filename</i> format is "yyyy-MM-dd--HH-mm-ss".<br>
	 * The <i>column entry</i> format is "yyyy-MM-dd HH:mm:ss:SSS", where 
	 * SSS represents milliseconds to three digits. <p>
	 * 
	 * 
	 * @return formatted - <code>String[]</code> String array containing the formatted dates.
	 */
	
	protected static String[] dateFormats()
	{
		String[] formatted = new String[2];
		Date date = new Date();		// current date and time
		SimpleDateFormat dateFormatFileNames = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss"); 	// format for dates part of file names
		SimpleDateFormat dateFormatColumns = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");	// format for dates in csv columns
		
		formatted[0] = dateFormatFileNames.format(date);		// add formatted date strings to string array
		formatted[1] = dateFormatColumns.format(date);
		
		return formatted;
	}
	
	
	/**
	 * 
	 * Takes in an argument of a String array containing the current date in
	 * a specific format. The method uses the first string to create a file
	 * with the current time in the filename. The method then creates a header
	 * row in a CSV file with column names pertinent to <b>telemetry</b> data
	 * storage. <p>
	 * 
	 * 
	 * @param formattedDates - <code>String[]</code> Contains current time and date formatted appropriately
	 * 
	 */
	// inspiration for this code: 
	// https://stackoverflow.com/questions/30073980/java-writing-strings-to-a-csv-file answer by Prashant Ghimire
	
	
	static void createTelemetryHeader(String[] formattedDates) 
	{	
		
		try (PrintWriter writer = new PrintWriter(new File("../storage/telemetry/" + formattedDates[0] + "_data_telemetry.csv")))
		{
			// column labels for CSV file -- first row in the file
			String labels = "Current Time, Latitude, Longitude, Time, Altitude, "
									+ "Velocity, Satelites, Acceleration, Temperature, GyroX\n";
			
			// write the labels into the CSV
			writer.write(labels);
		}
		catch (FileNotFoundException e) 
		{
		      System.out.println(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * Takes in argument of a String array containing the current date in
	 * a specific format. The method uses the first string to create a file
	 * with the current time in the filename. The method then creates a header
	 * row in a CSV file with column names pertinent to <b>GPS</b> data storage. <p>
	 * 
	 * 
	 * @param formattedDates - <code>String[]</code> Contains current time and date formatted appropriately
	 * 
	 */
	// inspiration for this code: 
	// https://stackoverflow.com/questions/30073980/java-writing-strings-to-a-csv-file answer by Prashant Ghimire
	
	static void createGPSHeader(String[] formattedDates) 
	{	
		
		try (PrintWriter writer = new PrintWriter(new File("../storage/gps/" + formattedDates[0] + "_data_gps.csv")))
		{
			// column labels for CSV files -- first row in the file
			String labels = "Current Time, Latitude, Longitude, Time, GPS_Altitude, GPS_Speed, Number of Satelites\n";
			
			// write the column labels into CSV
			writer.write(labels);
		}
		catch (FileNotFoundException e) 
		{
		      System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * Creates a <code>.txt</code> file for storing raw telemetry data.<p>
	 * 
	 * @param formattedDates - <code>String[]</code> Contains current time and date formatted appropriately 
	 * 
	 */
	
	static void createRawTelemetry(String[] formattedDates)
	{
		try (PrintWriter writer = new PrintWriter(new File("../storage/raw_telemetry/" + formattedDates[0] + "_raw_data.txt")))
		{
			String initialRow = "Raw Data:\n____________________\n";
			writer.write(initialRow);
		}
		catch (FileNotFoundException e)
		{
		      System.out.println(e.getMessage());
		}
	}
	
	

	
	/**
	 * 
	 * Creates a <code>.txt</code> file for storing raw GPS data.<p>
	 * 
	 * @param formattedDates - <code>String[]</code> Contains current time and date formatted appropriately 
	 * 
	 */
	
	static void createRawGPS(String[] formattedDates)
	{
		try (PrintWriter writer = new PrintWriter(new File("../storage/raw_gps/" + formattedDates[0] + "_raw_data.txt")))
		{
			String initialRow = "Raw Data:\n____________________\n";
			writer.write(initialRow);
		}
		catch (FileNotFoundException e)
		{
		      System.out.println(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * Creates a <code>.txt</code> file for storing raw antenna angles data.<p>
	 * 
	 * @param formattedDates - <code>String[]</code> Contains current time and date formatted appropriately 
	 * 
	 */
	
	static void createRawAntenna(String[] formattedDates)
	{
		try (PrintWriter writer = new PrintWriter(new File("../storage/antenna_angles/" + formattedDates[0] + "_antenna_angles.txt")))
		{
			String initialRow = "Raw Data:\n____________________\n";
			writer.write(initialRow);
		}
		catch (FileNotFoundException e)
		{
		      System.out.println(e.getMessage());
		}
	}
	
	// ----------------------------------  BELOW ARE METHODS FOR WRITING DATA --------------------------------- //
	
	
	/**
	 * Writes <b>telemetry</b> data to an existing CSV file. The data is passed into the
	 * method by a double array; the data is written onto a single line in the CSV file. 
	 * The end of the writing process adds a newline. Then the CSV file is saved. <p>
	 * 
	 * If the length of the input double is not exactly 10, the data is assumed to be 
	 * corrupted and will not be written to the CSV. <p>
	 * 
	 * @param formattedDates - <code>String[]</code> 2-entry string array with formatted date strings
	 * @param fileTime - <code>String</code> Date and Time that is in the file name that the data should be written to
	 * @param data - <code>double[]</code> Double array with telemetry data
	 * @return success - <code>boolean</code> Flag indicating whether the save operation failed at any point
	 */
	
	static boolean saveTelemetryCSV(String[] formattedDates, String fileTime, double[] data)
	{
		boolean success = true;
		// set true so that we append
		try (FileWriter file = new FileWriter("../storage/telemetry/" + fileTime + "_data_telemetry.csv", true))	
		{
			
			if (data.length == TelemetryLength)					// ensure we write the clean data to the CSV only
			{
				file.write(formattedDates[1]);					// writes the current time to the first column of the row
				//String currentTime = formattedDates[1];		// current date precise to ms
				
				
				file.write(',');								// for the CSV to be written to properly formatted
				for (int i = 0; i < data.length - 1; i++)		// data.length-1 means 'E' at the end is not written
				{
					file.write(String.valueOf(data[i]));
					file.write(',');
				}
				file.append('\n');
			}
			else {};		// if data is "corrupt", don't do anything and move to next line to save
		
			
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("exception :" + e.getMessage());
			success = false;
		}
		
		catch (IOException e) {
            System.out.println("exception :" + e.getMessage());
        }
		return success;		// method breaks as soon as failed is true (if file not found)
		
	}
	
	
	/**
	 * 
	 * Writes <b>GPS</b> data to an existing CSV file. The data is passed into the
	 * method by a double array with <b>6 entries</b>; the data is written onto a single 
	 * line in the CSV file. The end of the writing process adds a newline. Then the
	 * CSV file is saved. <p>
	 * 
	 * 
	 * 
	 * @param formattedDates - <code>String[]</code> 2-entry string array with formatted date strings
	 * @param fileTime - <code>String</code> Date and Time that is in the file name that the data should be written to
	 * @param data - <code>double[]</code> 6-entry double array with GPS data
	 * @return success - <code>boolean</code> Flag indicating whether the save operation failed at any point
	 */
	
	static boolean saveGPSCSV(String[] formattedDates, String fileTime, double[] data)
	{
		boolean success = true;
		// set true so that we append
		
			try (FileWriter file = new FileWriter("../storage/gps/" + fileTime + "_data_gps.csv", true))	
			{
				// change to global variable so that we can change it easily
				if (data.length == GPSLength)					// ensure we write the clean data to the CSV only
				{
					file.write(formattedDates[1]);				// writes the current time to the first column of the row
					//String currentTime = formattedDates[1];	// current date precise to ms
					
					file.write(',');							// for the CSV to be written to properly formatted
					
					for (int i = 0; i < data.length - 1; i++)	// data.length-1 means 'E' at the end is not written
					{
						file.write(String.valueOf(data[i]));
						file.write(',');
					}
					file.append('\n');
				}
				else {};		// if data is "corrupt", don't do anything and move to next line to save
			
				
			}
			
			catch (FileNotFoundException e)
			{
				System.out.println("exception :" + e.getMessage());
				success = false;
			}
			
			catch (IOException e) {
	            System.out.println("exception :" + e.getMessage());
	        }
			return success;				// method breaks as soon as failed is true (if file not found)
			
	}
	
	
	/**
	 * 
	 * Saves raw telemetry data received in the form of a string to a text file.<p>
	 * 
	 * @param formattedDates - <code>String[]</code> Contains the current date and time formatted appropriately
	 * @param fileTime - <code>String</code> Date/Time contained in the name of the file to save the raw data to
	 * @param data - <code>String</code> Data to be saved
	 * 
	 */
	static void saveTelemetryRaw(String[] formattedDates, String fileTime, String data)
	{
		
		// set true so that we append
		try (FileWriter file = new FileWriter("../storage/raw_telemetry/" + fileTime + "_raw_data.txt", true))	
		{
			//pw.write(formattedDates[1] + "\n");		// while testing, indicate which version of file i am viewing
			
			file.write(data);		// write the raw telemetry data string to file
			file.append("\n\n");	// add newline
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("exception :" + e.getMessage());
		}
		
		catch (IOException e) {
            System.out.println("exception :" + e.getMessage());
        }
		
	}
	
	/**
	 * 
	 * Saves raw GPS data received in the form of a string to a text file.<p>
	 * 
	 * @param formattedDates - <code>String[]</code> Contains the current date and time formatted appropriately
	 * @param fileTime - <code>String</code> Date/Time contained in the name of the file to save the raw data to
	 * @param data - <code>String</code> Data to be saved
	 * 
	 */
	static void saveGPSRaw(String[] formattedDates, String fileTime, String data)
	{
		
		// set true so that we append
		try (FileWriter file = new FileWriter("../storage/raw_gps/" + fileTime + "_raw_data.txt", true))	
		{
			//file.write(formattedDates[1] + "\n");		// while testing, indicate which version of file i am viewing
			file.write(data);							// write the raw gps data string to file
			file.append("\n\n");						// add newline
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("exception :" + e.getMessage());
		}
		
		catch (IOException e) {
            System.out.println("exception :" + e.getMessage());
        }
		
	}
	
	
	/**
	 * 
	 * Saves raw antenna angle data received in the form of a string to a text file.<p>
	 * 
	 * @param formattedDates - <code>String[]</code> Contains the current date and time formatted appropriately
	 * @param fileTime - <code>String</code> Date/Time contained in the name of the file to save the raw data to
	 * @param data - <code>String</code> Data to be saved
	 * 
	 */
	static void saveAntennasRaw(String[] formattedDates, String fileTime, String data)
	{
		
		// set true so that we append
		try (FileWriter file = new FileWriter("../storage/antenna_angles/" + fileTime + "_antenna_angles.txt", true))	
		{
			file.write(formattedDates[1] + "\n");		// while testing, indicate which version of file i am viewing
			file.write(data);							// write the raw antenna angle data string to file
			file.append('\n');							// add newline
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("exception :" + e.getMessage());
		}
		
		catch (IOException e) {
            System.out.println("exception :" + e.getMessage());
        }
		
	}
	
	
	/**
	 * 
	 * Reads a single line from a file and returns a string with the data that is read. The 
	 * method will be used to read raw data from text files and pass the data as strings
	 * to the methods to save telemetry or GPS data into CSVs. <p>
	 * 
	 * Currently, the path to the file being read from is a <code>testReading.txt</code> file
	 * which contains a single line of comma-separated values. This path is hardcoded for testing. <p>
	 * 
	 * @param filePath - <code>String</code> path to the file to be read from
	 * @return line - <code>String</code> single line of data read from a file to be written to the CSV
	 * @throws Exception - for any errors related to nonexisting files
	 */
	static String readLine(String filePath) throws Exception
	{
		BufferedReader read = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(filePath)), Charset.forName("UTF-8")));
		
		try 
		{
			String line = read.readLine();
			return line;
		}
		
		finally // to close the bufferedReader
		{
	        try {if (read != null) read.close();} 
	        
	        catch(IOException e) {System.out.println(e);}
	    }
	}
	
	
	
	
	/**
	 * 
	 * Main method of the program. It begins by checking whether
	 * the necessary folders have been created for data storage. If not,
	 * the directories will be created with the parent folder name "storage". <p>
	 * 
	 * The method will then create .CSV files for telemetry and GPS data storage,
	 * and <code>.txt</code> files for telemetry, GPS, and antenna angle raw
	 * data storage. <p> 
	 * 
	 * Currently, more code is written to test some of the methods for saving
	 * data to CSV and/or text files. However, more tests <b><i>need</i></b> to 
	 * be done, and the tests must be more rigorous. <p>
	 * 
	 * The file <code>tests.java</code> in the same package contains a method
	 * <code>runTests()</code> that tests the methods in this class for creating
	 * directories and creating new CSV and text files. <p>
	 * 
	 * @param args - arguments as required by syntax
	 * 
	 * @throws Exception - for any errors related to non-existing files
	 */
	
	public static void main(String[] args) throws Exception 
	{
		if (TESTING)
		{
			tests.runTests();
		}
		
		/*
		// generate formatted strings for current time and date
		String[] formattedDates = dateFormats();
		
		// code for main method
		makeFolders();
	
		
		// create empty files with the correct column labels
		createTelemetryHeader(formattedDates);
		createGPSHeader(formattedDates);
		createRawTelemetry(formattedDates);
		createRawGPS(formattedDates);
		createRawAntenna(formattedDates);
		*/
		
		// writing data to CSV files
		
		String telData = readLine("../testReadingTelemetry.txt"); 			// get the string from the test Reader text file
		//System.out.println(telData + "\n");
		String[] formattedDatesInit = dateFormats();
		
		createGPSHeader(formattedDatesInit);
		createTelemetryHeader(formattedDatesInit);
		
		createRawGPS(formattedDatesInit);
		createRawTelemetry(formattedDatesInit);
		
		// testing: saving GPS data to CSV
		for (int testing = 0; testing < 100; testing++)
		{
			String gpsData = readLine("../testReadingGPS.txt"); // get the string from the test Reader text file
			
			
			String[] convToStringArray = (gpsData.split(",")); // split data from string into string array
			double[] data = new double[convToStringArray.length];    // array to store data for writing to CSV
			for (int i = 0; i < data.length-1; i++) 					 // populate the array
			{
				data[i] = Double.valueOf(convToStringArray[i]);
			}
			//double[] data = {1,2,3,4,5,6,7,8,9,10};
			
			String fileTime = formattedDatesInit[0];		// record the current time
			
			
			
			
			saveGPSCSV(dateFormats(), fileTime, data);		// save data to CSV
			saveGPSRaw(dateFormats(), fileTime, gpsData);
			//Thread.sleep(12);								// add small pause between writes
			
			String telemetryData = readLine("../testReadingTelemetry.txt"); // get the string from the test Reader text file
			
			
			String[] convToStringArray2 = (telemetryData.split(",")); // split data from string into string array
			double[] data2 = new double[convToStringArray2.length];    // array to store data for writing to CSV
			for (int i = 0; i < data2.length-1; i++) 					 // populate the array
			{
				data2[i] = Double.valueOf(convToStringArray2[i]);
			}

			saveTelemetryCSV(dateFormats(), fileTime, data2);
			saveTelemetryRaw(dateFormats(), fileTime, telemetryData);
		}
		
		
		
		System.out.println("program terminated");
	}
	
}
