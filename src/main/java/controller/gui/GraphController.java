package controller.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GraphController extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		initializeGraphs(primaryStage);
	}
	
	public void initializeGraphs(Stage primaryStage) {
		// Initialize javafx graphs
		
		HBox root = new HBox();
		Scene scene = new Scene(root, 500, 400);
		
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Time");
		
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Height");
		
		LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
		lineChart.setTitle("Altitude");
		
		XYChart.Series<String, Number> data = new XYChart.Series<>();
		
		/*
		 * Data format:
		 *                  S32.943012,-106.914963,3097894,4.71,371546.406250,C,8.63,36.80,0.000000,-79,E
		 *                  telemetry long data format:     Slat,long,time,alt,vel,sat,acc,temp,gyro_x,RSSI,E\n
		 *                  backup GPS data:                Slat,long,time,gps_alt,gps_speed,sat,RSSI,E\n
		 *
		*/
		
		//-------------------------Testing----------------------------------//
		data.getData().add(new XYChart.Data<String,Number>("1",2));
		data.getData().add(new XYChart.Data<String,Number>("2",20));
		data.getData().add(new XYChart.Data<String,Number>("3",200));
		data.getData().add(new XYChart.Data<String,Number>("4",2000));
		data.getData().add(new XYChart.Data<String,Number>("5",200));
		data.getData().add(new XYChart.Data<String,Number>("6",20));
		data.getData().add(new XYChart.Data<String,Number>("7",2));
		//------------------------------------------------------------------//
		
		lineChart.getData().add(data);
		root.getChildren().add(lineChart);
		
		primaryStage.setTitle("LineChart");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
