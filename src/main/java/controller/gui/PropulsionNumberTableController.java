
package controller.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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


import controller.Parser;
import javafx.fxml.FXMLLoader;

import javafx.scene.input.ScrollEvent;
import javafx.scene.Node;
import javafx.event.EventHandler;

/**

 *  ---------------INFO------------------
 * 
 *  PRESSURE = pressure of the oxidizer tank (starts at 750 and drops to 0)
 *  TEMPERATURE = temperature of the oxidizer tank (starts at 25 and drops to 0)
 *  VALVE_STATUS: 1 when open, 0 when closed
 *
 */

public class PropulsionNumberTableController {
	ArrayList<String> read;
	
	

	
	
	@FXML
	public Label currentTempValue;
	public Label currentPressureValue;
	public Label valveStatus;
	
	
	
	@FXML 
	public void setCurrentTempValue(String value) {
		this.currentTempValue.setText(value);
	}
	@FXML 
	public void setCurrentPressureValue(String value) {
		this.currentPressureValue.setText(value);
	}
	
	@FXML 
	public void setValveStatus(String value) {
		this.valveStatus.setText(value);
		
	}
	
	public void updateNumDisplay(double[] data) {

		int temp_index = 1, pressure_index = 0, time_index = 3, valve_status_index = 2;
		double x_val = data[time_index]*60 + data[time_index+1] + data[time_index+2]/100.0;
		
		System.out.printf("updateNumDisplay: time = %f, pressure = %f, temp = %f, valve = %f\n", x_val, data[pressure_index], data[temp_index], data[valve_status_index]);
		setCurrentTempValue(String.valueOf((int) Math.round(data[temp_index])));
		
		setCurrentPressureValue(String.valueOf((int) Math.round(data[pressure_index])));

		if (data[valve_status_index] > 0) {
		setValveStatus("open");
		} else {
			setValveStatus("closed");
		}

	}
	
	
	

}


