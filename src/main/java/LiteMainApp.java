

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class LiteMainApp extends Application {

	private final Mode mode = Mode.LIVE;
	private ScheduledExecutorService scheduledExecutorService;
	
	private SerialPort comPort;
	StringBuffer rawDataConcatBuffer = new StringBuffer();
	StringBuffer parsedDataConcatBuffer = new StringBuffer();
    @Override
    public void start(Stage stage) throws Exception {
    	
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    		if(mode == mode.OLD) scheduledExecutorService.shutdownNow();
    		else if (mode == mode.LIVE) {
    			MainApp.createRawDataFiles("storage/raw_fc/");
    			MainApp.createParsedDataFiles("storage/fc/");
    			try {
					comPort.getInputStream().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			comPort.closePort();
    		}
    	}));
   
    	DataStorage.makeFolders();
        
		Parser parser = new Parser(12);
		ArrayList<String> myData = new ArrayList<String>();
		ArrayList<double[]> myDataArrays = new ArrayList<double[]>();
		switch (mode) {
			case OLD:
				try {
					myData = (ArrayList<String>) Parser.storeData("src/main/resources/jenni1.txt");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (String str: myData) {
					try {
						myDataArrays.add(parser.parseFC((str)));
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid message. Message was thrown out.");
						System.out.println(e.toString());
					} catch (NullPointerException e) {
						System.out.println("Why you passing null to the parser dude");
					}
				}
				for (String s: myData) {
					System.out.println(s);
				}
				
				
			case SIMULATION:
				break;
			case LIVE:
				Queue<String> q = new ConcurrentLinkedQueue<String>();
				comPort = SerialPort.getCommPorts()[1];
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

								String s = buffer.readLine();

								q.add(s);

								

							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					});
				}catch (Exception e) {
					e.printStackTrace();
				}
				ExecutorService ex = Executors.newCachedThreadPool();
				ex.execute(() -> {
					while(true) {
						if(!q.isEmpty()) {
							String stringData = q.remove();
						try {

							double[] data = parser.parse(stringData);
							System.out.println(stringData);
							
							if(data != null) {
								parsedDataConcatBuffer.append(stringData + "\n");
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
    }

    public static void main(String[] args) {
        launch();
    }
    
	@Override
	public void stop() throws Exception{

	}

}
