

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import controller.Parser;
import controller.datastorage.DataStorage;
import controller.gui.DataIndex;
import controller.gui.GraphController;
import controller.gui.Gyro3dController;
import controller.gui.MainAppController;
import controller.gui.Mode;
import controller.gui.SceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * MainApp is used to create and present the main Scene as a GUI. The class loads the GUI from an fxml file, 
 * and presents it to the user. Features within the GUI can be accessed and controlled through the 
 * SceneController instance. 
 *
 * Depending on whether it is an old, live or simulated case, MainApp will run different blocks of code. 
 * 
 * If the case is OLD, the GUI will display past rocket data. 
 * If the case is LIVE, the GUI will display live data read though the serial port. 
 * 
 * Once thread execution stops, MainApp will call data storage methods to store live data. (If old data was used, it is not stored)
 * 
 */
public class MainApp extends Application {
	static StringBuffer rawDataConcatBuffer = new StringBuffer();
	static StringBuffer parsedDataConcatBuffer = new StringBuffer();
	static StringBuffer parsedPropDataConcatBuffer = new StringBuffer();
	static StringBuffer parsedFCDataConcatBuffer = new StringBuffer();
	static StringBuffer parsedXtendAckDataConcatBuffer = new StringBuffer();
	static StringBuffer parsedSradioAckDataConcatBuffer = new StringBuffer();

	private final Mode mode = Mode.LIVE;
	public final boolean flightComputer = true;
	private int SERIAL_PORT_NUMBER = 6;
//	private final String COM_PORT_DESC = "/dev/tty.usbmodem11101";
	private final String COM_PORT_DESC = "COM5";
	
	@FXML Button launchButton;
	private ScheduledExecutorService scheduledExecutorService;
	private SerialPort comPort;

	@Override
	public void start(Stage stage) throws Exception {
	//	Button launchButton = this.launchButton;

//		DataStorage.makeFolders();
//

		Label l = new Label("McGill Rocket Team Ground Station");
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml_21_22/Scene.fxml"));
	//	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MainApp.fxml"));
		Parent root = fxmlLoader.load();
		Scene mainApp = new Scene(root, 1000,700);
		
		SceneController sceneController = (SceneController)fxmlLoader.getController();
		sceneController.initializeScene();
		sceneController.sceneInitializeGyro();
		sceneController.sceneInitializeGraphs();
		sceneController.sceneInitializePropulsionGraphs();
		
//		MainAppController mainAppController = (MainAppController)fxmlLoader.getController();
//		mainAppController.mainAppInitializeGraphs();
//		mainAppController.mainAppInitializeMap();
//		mainAppController.mainAppIntitializeRawData();     
//		mainAppController.mainAppInitializeGyro();
//		//        ((Pane)mainApp.getRoot()).getChildren().add(gyroController.initializeGyro().getRoot());
//
//
//		stage.setTitle("McGill Rocket Team Ground Station");
//
//
		Parser parser = new Parser();
		
		ArrayList<String> myData = new ArrayList<String>();
		ArrayList<double[]> myDataArrays = new ArrayList<double[]>();
		ArrayList<double[]> myDataArraysProp = new ArrayList<double[]>();

		switch (mode) {
		case OLD:
			try {
//				myData = (ArrayList<String>) Parser.storeData("test_data/2020-10-10-serial-2378-flight-0021_av_only.csv");
//				myData = (ArrayList<String>) Parser.storeData("test_data/2020-10-10-serial-2378-flight-0021_av_only_subsec.csv");
				myData = (ArrayList<String>) Parser.storeData("oldd.csv");
				System.out.println("found file");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (String str: myData) {
				try {
					myDataArraysProp.add(parser.parse(str));
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid message. Message was thrown out.");
					System.out.println(e.toString());
				} catch (NullPointerException e) {
					System.out.println("Why you passing null to the parser");
				}
			}

			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			Iterator<double[]> dataItr = myDataArrays.iterator();
			Iterator<double[]> dataItrProp = myDataArraysProp.iterator();

			scheduledExecutorService.scheduleAtFixedRate(() -> {
				double[] data = dataItr.next();
				double[] dataProp = dataItrProp.next();

				Platform.runLater(()-> {

					//	System.out.println(data[3]);
					Date now = new Date();
//
//					mainAppController.mainAppAddGraphData(data);
//					mainAppController.mainAppAddMapData(data);
//					mainAppController.mainAppAddRawData(data);
					sceneController.sceneAddGraphData(data);
					sceneController.startTimer(data);
					
					sceneController.sceneAddGyroData(data);
//					mainAppController.mainAppAddGyroData(data);
					sceneController.startPropulsionTimer(dataProp);
					sceneController.sceneAddPropulsionGraphData(dataProp);
//
//
//
				});
			}, 0, 100, TimeUnit.MILLISECONDS);
//
		case SIMULATION:
			break;
		case LIVE:


			Queue<String> q = new ConcurrentLinkedQueue<String>();
			SerialPort[] t = SerialPort.getCommPorts();

			for (SerialPort x : t ) {
				System.out.println(x.getPortDescription());
			}

			System.out.println(SerialPort.getCommPorts());
			System.out.println(SerialPort.getCommPorts().length);
			//	comPort = SerialPort.getCommPorts()[SERIAL_PORT_NUMBER];
			//comPort = SerialPort.getCommPort("/dev/tty.usbserial-1420");
			comPort = SerialPort.getCommPort(COM_PORT_DESC);
			//comPort = SerialPort.getCommPort("/dev/tty.usbserial-1420");
			comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);


			try {
				System.out.println("Port open: " + comPort.openPort());
				comPort.setComPortParameters(9600,8,1,0);
				comPort.addDataListener(new SerialPortDataListener() {

					public int getListeningEvents() {
						return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
					}

					public void serialEvent(SerialPortEvent event) {
						try {
							BufferedReader buffer = new BufferedReader(
									new InputStreamReader(comPort.getInputStream()));
							//											System.out.println(buffer.readLine());
							//	System.out.println(comPort.bytesAvailable());
							String s = buffer.readLine();
							
							q.add(s);
							
							//	System.out.println(buffer.read());
							//					System.out.println(s);
							//	System.out.println(comPort.bytesAvailable());
//							q.add(s);
							//System.out.println(buffer.readLine()); //test connection
							//double[] data = parser_fc.parse(buffer.readLine());
							//	mainAppController.startTimer(data, DataFormat); //update GUI
							//	in.close();


						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
			ExecutorService ex = Executors.newCachedThreadPool();
			ex.execute(() -> {
				while(true) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if(!q.isEmpty()) {
						String stringData = q.remove();
						try {
							System.out.println(stringData);
							double[] data;
							data = parser.parse(stringData);
							if(data != null) {
								parsedDataConcatBuffer.append(stringData + "\n");
								//put in method
								if (stringData.charAt(0)=='S' || stringData.charAt(0)=='J') parsedFCDataConcatBuffer.append(stringData + "\n");
								if (stringData.charAt(0)=='P') parsedPropDataConcatBuffer.append(stringData + "\n");
								if (stringData.charAt(0)=='x') parsedXtendAckDataConcatBuffer.append(stringData + "\n");
								if (stringData.charAt(0)=='s') parsedSradioAckDataConcatBuffer.append(stringData + "\n");
								//pw.println(stringData + "\n");
								if(data[0] != -10000) {
	
									Platform.runLater(()-> {
										if(data.length == 14) { // get numbers from the class
											sceneController.sceneAddGraphData(data);
											sceneController.sceneAddGyroData(data);
											sceneController.startTimer(data);
											//is this ok?
//											parsedFCDataConcatBuffer.append(stringData + "\n");
										}
										else if (data.length == 6) {
											sceneController.startPropulsionTimer(data);
											sceneController.sceneAddPropulsionGraphData(data);
//											parsedPropDataConcatBuffer.append(stringData + "\n");
										}
									});
								}
							}

						} catch (IllegalArgumentException e) {
							System.out.println("Invalid message. Message was thrown out.");
						} catch (NullPointerException e) {
							System.out.println("Why you passing null to the parser");
						} finally {
							rawDataConcatBuffer.append(stringData + "\n");
						}
					}
				}
			});

		}

		stage.setScene(mainApp);
		stage.show();

	}
/**
 * createRawDataFiles method saves the raw live data to specified path
 * @param path to which raw live data is saved
 */
	public static void createRawDataFiles(String path) {
		if(rawDataConcatBuffer.length() == 0) {
			System.out.println("No raw data to save. Skipping data log creation.");
		} else {
			try (PrintWriter writer = new PrintWriter(new File(path + DataStorage.dateFormats()[0] + "_data.txt"))){
				writer.write(rawDataConcatBuffer.toString());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
/**
 * createParsedDataFiles saves parsed live data to specified path
 * @param path to which parsed data is saved
 */
	public static void createParsedDataFiles(String path) {
		if(parsedDataConcatBuffer.length() == 0) {
			System.out.println("No parsed data to save. Skipping data log creation.");
		} else {
			try (PrintWriter writer = new PrintWriter(new File(path + DataStorage.dateFormats()[0] + "_data.txt"))){
				writer.write(parsedDataConcatBuffer.toString());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public static void createParsedPropFile(String path) {
		if(parsedPropDataConcatBuffer.length() == 0) {
			System.out.println("No parsed propulsion data to save. Skipping data log creation.");
		} else {
			try (PrintWriter writer = new PrintWriter(new File(path + DataStorage.dateFormats()[0] + "_prop_data.txt"))){
				writer.write(parsedPropDataConcatBuffer.toString());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void createParsedFCFile(String path) {
		if(parsedFCDataConcatBuffer.length() == 0) {
			System.out.println("No parsed flight computer data to save. Skipping data log creation.");
		} else {
			try (PrintWriter writer = new PrintWriter(new File(path + DataStorage.dateFormats()[0] + "_FC_data.txt"))){
				writer.write(parsedFCDataConcatBuffer.toString());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void createParsedSradioAckFile(String path) {
		if(parsedSradioAckDataConcatBuffer.length() == 0) {
			System.out.println("No parsed flight computer data to save. Skipping data log creation.");
		} else {
			try (PrintWriter writer = new PrintWriter(new File(path + DataStorage.dateFormats()[0] + "sradio_ack_data.txt"))){
				writer.write(parsedSradioAckDataConcatBuffer.toString());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void createParsedXtendAckFile(String path) {
		if(parsedXtendAckDataConcatBuffer.length() == 0) {
			System.out.println("No parsed flight computer data to save. Skipping data log creation.");
		} else {
			try (PrintWriter writer = new PrintWriter(new File(path + DataStorage.dateFormats()[0] + "xtend_ack_data.txt"))){
				writer.write(parsedXtendAckDataConcatBuffer.toString());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void stop() throws Exception{
		super.stop();
		if(mode == mode.OLD) scheduledExecutorService.shutdownNow();
		else if (mode == mode.LIVE) {
			if(!flightComputer) {
				createRawDataFiles("storage/raw_telemetry/");
				createParsedDataFiles("storage/telemetry/");
			} else {
				//for testing
				rawDataConcatBuffer.append("line to create file\n");
				parsedDataConcatBuffer.append("line to create file\n");
				parsedPropDataConcatBuffer.append("line to create file\n");
				parsedFCDataConcatBuffer.append("line to create file\n");
				parsedXtendAckDataConcatBuffer.append("line to create file\n");
				parsedSradioAckDataConcatBuffer.append("line to create file\n");
				System.out.println("---------------------------creating files!---------------------------");

				createRawDataFiles("storage/raw_fc/");
				createParsedDataFiles("storage/fc/");
				//change dir
				createParsedPropFile("storage/fc/");
				createParsedFCFile("storage/fc/");
				createParsedSradioAckFile("storage/fc/");
				createParsedXtendAckFile("storage/fc/");
			}

			comPort.getInputStream().close();
			comPort.closePort();
		}

	}

}
