package dataStorage;

import java.io.File;

public class DataStorage 
{

	/**
	 * javadoc for whole class
	 * class is for data storage for MRT
	 * 
	 * @author Jasper Yun
	 * @param args
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
		DirMaker1(storage);
		DirMaker1(telemetry);
		DirMaker1(gps);
		DirMaker1(raw_telemetry);
		DirMaker1(raw_gps);
		DirMaker1(antenna_angles);
		DirMaker1(serial);
		
		
	}
	
	// code origin: https://www.mkyong.com/java/how-to-create-directory-in-java/
	
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
		if (!file.exists())
		{
			if (file.mkdir()) System.out.println("Directory " + file.toString() + " created successfully.");
			else System.out.println("Failed to created directory.");
		}
		else System.out.println("Directory already exists");
	}
	
	
	/**
	 * Creates a multiple directory given an input path. Prints on the console to 
	 * indicate whether the directory creation was successful. <p>
	 * 
	 * This code comes from <a href="https://www.mkyong.com/java/how-to-create-directory-in-java/">Mkyong</a>. <p>
	 * 
	 * @param file
	 */
	
	private static void DirMaker2(File file) 
	{
		if (!file.exists())
		{
			if (file.mkdirs()) System.out.println("Directory " + file.toString() + " created successfully.");
			else System.out.println("Failed to created directory.");
		}
		else System.out.println("Directory already exists");
	}
	
	
	/**
	 * Main method of the program. It begins by checking whether
	 * the necessary folders have been created for data storage. If not,
	 * the directories will be created with the parent folder name "storage". <p>
	 * 
	 * For now, there's nothing else, but the program should write data to csv
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// code for main method
		makeFolders();
	}

}
