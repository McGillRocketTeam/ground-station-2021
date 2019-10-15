package dataStorage;

import java.io.File;					// for creating directories
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;					// getting current date


/**
 * This class is used to generate directories and files to be used for data
 * storage for the McGill Rocket Team Ground Station 2020. <p>
 * 
 * Raw data is stored in <code>.txt</code> files. Parsed data will be added
 * to .CSV files. <p>
 * 
 * @author Jasper Yun
 * @param args
 */

public class DataStorage 
{
	private static boolean PRINTEXIST = false;	// when true, program will print message if folders already exist
	
	/**
	 * Creates a single directory given an input path. Prints on the console to 
	 * indicate whether the directory creation was successful. <p>
	 * 
	 * This code comes from <a href="https://www.mkyong.com/java/how-to-create-directory-in-java/">Mkyong</a>. <p>
	 * 
	 * @param file
	 */
	
	private static void DirMaker1(File file) 
	{
		// code origin: https://www.mkyong.com/java/how-to-create-directory-in-java/
		if (!file.exists())
		{
			if (file.mkdir()) System.out.println("Directory " + file.toString() + " created successfully.");
			else System.out.println("Failed to created directory.");
		}
		else 
		{
			//choose whether we want to see this message by setting PRINTEXIST
			if (PRINTEXIST) System.out.println("Directory already exists");
		}
	}
	
	
	/**
	 * Creates multiple directories given an input path. Prints on the console to 
	 * indicate whether the directory creation was successful. <p>
	 * 
	 * This code comes from <a href="https://www.mkyong.com/java/how-to-create-directory-in-java/">Mkyong</a>. <p>
	 * 
	 * @param file
	 */
	
	private static void DirMaker2(File file) 
	{
		// code origin: https://www.mkyong.com/java/how-to-create-directory-in-java/
		if (!file.exists())
		{
			if (file.mkdirs()) System.out.println("Directory " + file.toString() + " created successfully.");
			else System.out.println("Failed to created directory.");
		}
		else
		{
			//choose whether we want to see this message by setting PRINTEXIST
			if (PRINTEXIST) System.out.println("Directory already exists");		
		}
			
	}
	
	
	
	/**
	 * Creates directories for data storage by invoking DirMaker1. <p>
	 * 
	 *  The parent folder is called "storage". It is created 
	 *  in the parent folder of the directory of this Java class. <p>
	 *  
	 *  The subfolders of "storage" are "telemetry", "gps", "raw_telemetry",
	 *  "raw_gps", "antenna_angles", "serial". <p>
	 *  
	 *  If the folders already exist, then nothing happens. <p> 
	 *  
	 *  @param void
	 *  @return void
	 */
	
	private static void makeFolders() 
	{
		File storage = new File("../storage");
		File telemetry = new File("../storage/telemetry");
		File gps = new File("../storage/gps");
		File raw_telemetry = new File("../storage/raw_telemetry");
		File raw_gps = new File("../storage/raw_gps");
		File antenna_angles = new File("../storage/antenna_angles");
		File serial = new File("../storage/serial");
		
		
		// I will make this nicer later but for initial implementation, this works
		// due to the way that I am creating the folders, then we may not need the
		// DirMaker2 method
		DirMaker1(storage);
		DirMaker1(telemetry);
		DirMaker1(gps);
		DirMaker1(raw_telemetry);
		DirMaker1(raw_gps);
		DirMaker1(antenna_angles);
		DirMaker1(serial);	
		
		// for testing:
		System.out.println("Done");
	}
	
	
	
	/**
	 * Formats the current time and date into a format for filename use
	 * or for use in the column entries of CSV files. <p>
	 * 
	 * The <i>filename</i> format is "yyyy-MM-dd--HH-mm-ss".<br>
	 * The <i>column entry</i> format is "yyyy-MM-dd HH-mm-ss". <p>
	 * 
	 * @param void
	 * @return String[]	String array with two strings of the formatted dates.
	 */
	
	private static String[] dateFormats()
	{
		String[] formatted = new String[2];
		Date date = new Date();
		SimpleDateFormat dateFormatFileNames = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss"); // format for dates part of file names
		SimpleDateFormat dateFormatColumns = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");	 // format for dates in csv columns
		
		formatted[0] = dateFormatFileNames.format(date);		// add formatted date strings to string array
		formatted[1] = dateFormatColumns.format(date);
		
		return formatted;
	}
	
	
	/**
	 * Takes in an argument of a String array containing the current date in
	 * a specific format. The method uses the first string to create a file
	 * with the current time in the filename. The method then creates a header
	 * row in a CSV file with column names pertinent to <b>telemetry</b> data
	 * storage. <p>
	 * 
	 * 
	 * @param formattedDates
	 * @return void
	 */
	// inspiration for this code: 
	// https://stackoverflow.com/questions/30073980/java-writing-strings-to-a-csv-file answer by Prashant Ghimire
	
	private static void writeTelemetryHeader(String[] formattedDates) 
	{	
		
		try (PrintWriter writer = new PrintWriter(new File("../storage/telemetry/" + formattedDates[0] + "_data_telemetry.csv")))
		{
			String sb = "Current Time, Latitude, Longitude, Time, Altitude, Velocity, Satelites, Acceleration, Temperature, GyroX";
			writer.write(sb);
		}
		catch (FileNotFoundException e) 
		{
		      System.out.println(e.getMessage());
		}
	}
	
	
	
	/**
	 * Takes in argument of a String array containing the current date in
	 * a specific format. The method uses the first string to create a file
	 * with the current time in the filename. The method then creates a header
	 * row in a CSV file with column names pertinent to <b>GPS</b> data storage. <p>
	 * 
	 * 
	 * @param String[] formattedDates
	 * @return void
	 */
	// inspiration for this code: 
	// https://stackoverflow.com/questions/30073980/java-writing-strings-to-a-csv-file answer by Prashant Ghimire
	
	private static void writeGPSHeader(String[] formattedDates) 
	{	
		
		try (PrintWriter writer = new PrintWriter(new File("../storage/gps/" + formattedDates[0] + "_data_gps.csv")))
		{
			String sb = "Current Time, Latitude, Longitude, Time, GPS_Altitude, GPS_Speed, Number of Satelites\n";
			writer.write(sb);
		}
		catch (FileNotFoundException e) 
		{
		      System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * Creates a <code>.txt</code> file for storing raw telemetry data.<p>
	 * 
	 * @param String[] formattedDates
	 * @return void
	 */
	
	private static void writeRawTelemetry(String[] formattedDates)
	{
		try (PrintWriter writer = new PrintWriter(new File("../storage/raw_telemetry/" + formattedDates[0] + "_raw_data.txt")))
		{
			String sb = "Raw Data:\n____________________\n\n\n" + "____________________\n";
			writer.write(sb);
		}
		catch (FileNotFoundException e)
		{
		      System.out.println(e.getMessage());
		}
	}
	
	

	
	/**
	 * Creates a <code>.txt</code> file for storing raw antenna angles data.<p>
	 * 
	 * @param String[] formattedDates
	 * @return void
	 */
	
	private static void writeRawGPS(String[] formattedDates)
	{
		try (PrintWriter writer = new PrintWriter(new File("../storage/raw_gps/" + formattedDates[0] + "_raw_data.txt")))
		{
			String sb = "Raw Data:\n____________________\n\n\n" + "____________________\n";
			writer.write(sb);
		}
		catch (FileNotFoundException e)
		{
		      System.out.println(e.getMessage());
		}
	}
	
	
	
	/**
	 * Creates a <code>.txt</code> file for storing raw GPS data.<p>
	 * 
	 * @param String[] formattedDates
	 * @return void
	 */
	
	private static void writeRawAntenna(String[] formattedDates)
	{
		try (PrintWriter writer = new PrintWriter(new File("../storage/antenna_angles/" + formattedDates[0] + "_antenna_angles.txt")))
		{
			String sb = "Raw Data:\n____________________\n\n\n" + "____________________\n";
			writer.write(sb);
		}
		catch (FileNotFoundException e)
		{
		      System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * Main method of the program. It begins by checking whether
	 * the necessary folders have been created for data storage. If not,
	 * the directories will be created with the parent folder name "storage". <p>
	 * 
	 * The method will then create .CSV files for telemetry and GPS data storage,
	 * and <code>.txt</code> files for telemetry, GPS, and antenna angle raw
	 * data storage. <p> 
	 * 
	 * @param args
	 * @return void
	 */
	
	public static void main(String[] args) 
	{
		// code for main method
		makeFolders();
	
		// generate formatted strings for current time and date
		String[] formattedDates = dateFormats();
		
		// create empty files
		writeTelemetryHeader(formattedDates);
		writeGPSHeader(formattedDates);
		writeRawTelemetry(formattedDates);
		writeRawGPS(formattedDates);
		writeRawAntenna(formattedDates);
		
		// testing things:
		//System.out.println("../something/" + dateFormatFileNames.format(date) + "/something/else");
		//System.out.println(dateFormatFileNames());
	}
}
