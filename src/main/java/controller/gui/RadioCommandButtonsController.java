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
	
	
	// EXAMPLE 1 with a listener. Would need access to commport from here.
	public void initialize() {
		arm_recovery.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
        {
			@Override
            public void changed(ObservableValue<? extends Toggle> changed, 
                                                    Toggle oldVal, Toggle newVal)
            {
  
                RadioButton rb = (RadioButton)arm_recovery.getSelectedToggle();
                System.out.println(rb.getText());
                if (rb.getText().equals("Armed")) {
                	System.out.println("hello");
                	// Execute method that does commport communication somehow
                } else if (rb.getText().equals("Safed")){
                	System.out.println("hi");
                	
                }
            }
        });
		
		
		dump_valve.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
        {
			@Override
            public void changed(ObservableValue<? extends Toggle> changed, 
                                                    Toggle oldVal, Toggle newVal)
            {
  
                RadioButton rb = (RadioButton)dump_valve.getSelectedToggle();
                dump_valve_status_text_value.setText(rb.getText());
               
            }
        });
		
		
		
		launch_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
	
				// LAUNCH BUTTON PRESSED ACTIONS
				System.out.println("LAUNCH");
				
			}
		});
	}
	
	// EXAMPLE 2 Just return the value i.e. how launch button on other prop page is implemented currently.
	public String getRecoveryValue() {
		RadioButton rb = (RadioButton)arm_recovery.getSelectedToggle();
		return rb.getText();
	}
	
}
