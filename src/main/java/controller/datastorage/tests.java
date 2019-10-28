package controller.datastorage;

import java.io.File;					// for creating directories

public class tests {

	private static final int SLEEP = 30; 		// choose how long the thread sleeps for in ms
	
	/**
	 * 
	 * Runs basic tests to check whether the DataStorage
	 * class methods run as expected. <p>
	 * 
	 * 
	 * @throws Exception - In case of nonexistent files
	 */
	public static void runTests() throws Exception
	{
		
		// test to check that folders were created successfully and properly
		DataStorage.makeFolders(); // make folders
		
		boolean error = false;
		
		File[] folders = new File[7];
		
		folders[0] = new File("../storage");
		folders[1] = new File("../storage/telemetry");
		folders[2] = new File("../storage/gps");
		folders[3] = new File("../storage/raw_telemetry");
		folders[4] = new File("../storage/raw_gps");
		folders[5] = new File("../storage/antenna_angles");
		folders[6] = new File("../storage/serial");
		
		for (int i = 0; i < folders.length; i++)
		{
			if (!folders[i].exists()) 
			{
				System.out.println("Error: Folder " + i);
				error = true;
			}
		}
		
		if (!error) System.out.println("Folders created successfully\n");
		
		// assume that dateFormats() does its job properly
		// since I don't know how to test it unless we just print it
		
		// test to check the headers were created properly
		String[] formattedDates = DataStorage.dateFormats();
		
		// create headers
		DataStorage.createTelemetryHeader(formattedDates);
		DataStorage.createGPSHeader(formattedDates);
		DataStorage.createRawTelemetry(formattedDates);
		DataStorage.createRawGPS(formattedDates);
		DataStorage.createRawAntenna(formattedDates);
		
		// test the headers were written correctly
		String[] pathsToReadFrom = new String[5];
		pathsToReadFrom[0] = "../storage/telemetry/" + formattedDates[0] + "_data_telemetry.csv";
		pathsToReadFrom[1] = "../storage/gps/" + formattedDates[0] + "_data_gps.csv";
		pathsToReadFrom[2] = "../storage/raw_telemetry/" + formattedDates[0] + "_raw_data.txt";
		pathsToReadFrom[3] = "../storage/raw_gps/" + formattedDates[0] + "_raw_data.txt";
		pathsToReadFrom[4] = "../storage/antenna_angles" + formattedDates[0] + "_antenna_angles.txt";
		
		// the correct headers:
		String[] correctHeaders = new String[5];
		correctHeaders[0] = "Current Time, Latitude, Longitude, Time, Altitude, "
				+ "Velocity, Satelites, Acceleration, Temperature, GyroX";
		correctHeaders[1] = "Current Time, Latitude, Longitude, Time, GPS_Altitude, GPS_Speed, Number of Satelites";
		correctHeaders[2] = "Raw Data:";
		correctHeaders[3] = "Raw Data:";
		correctHeaders[4] = "Raw Data:\n";
		
		
		
		error = false;
		
		for (int j = 0; j < pathsToReadFrom.length-1; j++)
		{
			String s1 = DataStorage.readLine(pathsToReadFrom[j]);
			String s2 = correctHeaders[j];
			if (!(s1.equals(s2)))
			{
				System.out.println(s1);
				System.out.println(s2);
				System.out.println("Error: header not equal -- " + j + "\n");
				error = true;
			}
			//System.out.println(error);
		}
		
		if (!error) System.out.println("Headers created successfully");
		//System.out.println("reached here");
		
		
		// this test is incomplete
		// check that data is saved properly
		String[] textToReadFrom = new String[5];
		textToReadFrom[0] = "../testReadingTelemetry.txt";
		textToReadFrom[1] = "../testReadingGPS.txt";
		textToReadFrom[2] = "../testReadingRawTelemetry.txt";
		textToReadFrom[3] = "../testReadingRawGPS.txt";
		textToReadFrom[4] = "../testReadingAntennas.txt";
		
		error = false;
		
		System.out.println("-----  End of testing  -----\n");
	}

}
