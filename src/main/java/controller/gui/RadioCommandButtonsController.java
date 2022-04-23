package controller.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;


import org.gillius.jfxutils.JFXUtil;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.FixedFormatTickFormatter;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.StableTicksAxis;

import com.fazecast.jSerialComm.SerialPort;

import controller.Parser;
import javafx.fxml.FXMLLoader;

import javafx.scene.input.ScrollEvent;
import javafx.scene.Node;
import javafx.event.EventHandler;

import javafx.scene.paint.Color;


public class RadioCommandButtonsController {
	
	@FXML
	public ToggleGroup arm_recovery;
	
	@FXML
	public ToggleGroup arm_prop;
	
	@FXML
	public ToggleGroup dump_valve;
	
	@FXML
	public ToggleGroup vr_power;
	
	@FXML
	public ToggleGroup vr_recording;
	
	@FXML
	public Text dump_valve_status_text_value;
	
	@FXML
	public Button launch_button;
	
	private static SerialPort comPort = null; // stays null if we use mode.OLD
	
	public static void attachComPort(SerialPort port) {
		comPort = port;
	}
	
	
	public void initialize() {
		arm_recovery.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
        {
			@Override
            public void changed(ObservableValue<? extends Toggle> changed, 
                                                    Toggle oldVal, Toggle newVal)
            {
				RadioCommands cmd;
                RadioButton rb = (RadioButton)arm_recovery.getSelectedToggle();
                if (rb.getText().equals("Armed")) {
                	cmd = RadioCommands.CMD_ARM_RCOV;
                } else if (rb.getText().equals("Safed")){
                	cmd = RadioCommands.CMD_DISARM_RCOV;
                } else {
                	cmd = null;
                	System.out.println("Something went very wrong with recovery arming radio buttons");
                }
                
                writeComPort(cmd);
            }
        });
		
		arm_prop.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
        {
			@Override
            public void changed(ObservableValue<? extends Toggle> changed, 
                                                    Toggle oldVal, Toggle newVal)
            {
  
				RadioCommands cmd;
                RadioButton rb = (RadioButton)arm_prop.getSelectedToggle();
                if (rb.getText().equals("Armed")) {
                	cmd = RadioCommands.CMD_ARM_PROP;
                } else if (rb.getText().equals("Safed")){
                	cmd = RadioCommands.CMD_DISARM_PROP;
                } else {
                	cmd = null;
                	System.out.println("Something went very wrong with propulsion arming radio buttons");
                }
                
                writeComPort(cmd);
            }
        });
		
		
		vr_power.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
        {
			@Override
            public void changed(ObservableValue<? extends Toggle> changed, 
                                                    Toggle oldVal, Toggle newVal)
            {
  
				RadioCommands cmd;
                RadioButton rb = (RadioButton)vr_power.getSelectedToggle();
                if (rb.getText().equals("Off")) {
                	cmd = RadioCommands.CMD_VR_POWER_OFF;
                } else if (rb.getText().equals("On")){
                	cmd = RadioCommands.CMD_VR_POWER_ON;
                } else {
                	cmd = null;
                	System.out.println("Something went very wrong with propulsion arming radio buttons");
                }
                
                writeComPort(cmd);
            }
        });
		
		
		vr_recording.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
        {
			@Override
            public void changed(ObservableValue<? extends Toggle> changed, 
                                                    Toggle oldVal, Toggle newVal)
            {
  
				RadioCommands cmd;
                RadioButton rb = (RadioButton)vr_recording.getSelectedToggle();
                if (rb.getText().equals("Stopped")) {
                	cmd = RadioCommands.CMD_VR_REC_STOP;
                } else if (rb.getText().equals("Started")){
                	cmd = RadioCommands.CMD_VR_REC_START;
                } else {
                	cmd = null;
                	System.out.println("Something went very wrong with propulsion arming radio buttons");
                }
                
                writeComPort(cmd);
            }
        });
		
		
		dump_valve.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
        {
			@Override
            public void changed(ObservableValue<? extends Toggle> changed, 
                                                    Toggle oldVal, Toggle newVal)
            {
  
				RadioCommands cmd;
                RadioButton rb = (RadioButton)dump_valve.getSelectedToggle();
                if (rb.getText().equals("Unpowered")) {
                	cmd = RadioCommands.CMD_DUMP_POWER_OFF;
                } else if (rb.getText().equals("Powered")){
                	cmd = RadioCommands.CMD_DUMP_POWER_ON;
                } else {
                	cmd = null;
                	System.out.println("Something went very wrong with propulsion arming radio buttons");
                }
                
                writeComPort(cmd);
            }
        });
		
		
		launch_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				writeComPort(RadioCommands.CMD_LAUNCH);
			}
		});
	}
	
	public void updateDumpValveLabel(double data[]) {
		if (data[DataIndex.VALVE_STATUS.getOrder()] == 0) {
			dump_valve_status_text_value.setText("Unpowered");
		} else {
			dump_valve_status_text_value.setText("Powered");
		}
	}
	
	// writes data to com port and prints the command sent
	private void writeComPort(RadioCommands cmd) {
		if (comPort != null) { // would be null if running in mode.OLD
			comPort.writeBytes(cmd.code(), RadioCommands.command_length);
        } 
		System.out.println("Sending command = " + new String(cmd.code()));
	}
	
}
