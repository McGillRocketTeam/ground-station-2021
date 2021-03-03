package controller.gui;

import java.net.URL;
import java.util.EnumMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class RawDataController{ // implements initializable?
	@FXML 
	private TableView<DataPoint> tbAltData;
	@FXML
	public TableColumn<DataPoint, Double> altTime;
	@FXML
	public TableColumn<DataPoint, Double> altitude;
	
    private ObservableList<DataPoint> altDataPoints = FXCollections.observableArrayList(
    );
    
    @FXML
    private TableView<DataPoint> tbVelData;
    @FXML
    public TableColumn<DataPoint, Double> velTime;
    @FXML
    public TableColumn<DataPoint, Double> velocity;
    
    private ObservableList<DataPoint> velDataPoints = FXCollections.observableArrayList(
    	    );
    
    @FXML
    private TableView<DataPoint> tbAccelData;
    @FXML
    public TableColumn<DataPoint, Double> accelTime;
    @FXML
    public TableColumn<DataPoint, Double> acceleration;
    
    private ObservableList<DataPoint> accelDataPoints = FXCollections.observableArrayList(
    	    );
    
    @FXML
    private TableView<DataPoint> tbRssiData;
    @FXML
    public TableColumn<DataPoint, Double> rssiTime;
    @FXML
    public TableColumn<DataPoint, Double> rssi;
    
    private ObservableList<DataPoint> rssiDataPoints = FXCollections.observableArrayList(
    	    );
    
    public void addAltDataPoint(Double data1, Double data2) {
    	altDataPoints.add(new DataPoint(data1,data2));
    }
    public void addVelDataPoint(double data1, Double data2) {
    	velDataPoints.add(new DataPoint(data1,data2));
    }
    public void addAccelDataPoint(double data1, Double data2) {
    	accelDataPoints.add(new DataPoint(data1,data2));
    }
    public void addRssiDataPoint(double data1, Double data2) {
    	rssiDataPoints.add(new DataPoint(data1,data2));
    }
    
    // Method that just adds all rawdata in a batch instead of having to call addDataPoint individually
	public void addRawData(double[] data) {
		addAltDataPoint(data[DataIndex.TIME_INDEX.getOrder()],data[DataIndex.ALTITUDE_INDEX.getOrder()]);
		addVelDataPoint(data[DataIndex.TIME_INDEX.getOrder()],data[DataIndex.VELOCITY_INDEX.getOrder()]);
		addAccelDataPoint(data[DataIndex.TIME_INDEX.getOrder()],data[DataIndex.ACCELERATION_INDEX.getOrder()]);
		addRssiDataPoint(data[DataIndex.TIME_INDEX.getOrder()],data[DataIndex.RSSI_INDEX.getOrder()]);
	}
    

    
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//        //make sure the property value factory should be exactly same as the name from model class
//        time.setCellValueFactory(new PropertyValueFactory<>("time"));
//        data.setCellValueFactory(new PropertyValueFactory<>("data"));
//        //add your data to the table here.
//        tbData.setItems(dataPoints);
//    }
    public void initializeRawDataController() {
        //make sure the property value factory should be exactly same as the name from model class
        altTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        altitude.setCellValueFactory(new PropertyValueFactory<>("data"));
        //add your data to the table here.
        tbAltData.setItems(altDataPoints);
        
        //make sure the property value factory should be exactly same as the name from model class
        velTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        velocity.setCellValueFactory(new PropertyValueFactory<>("data"));
        //add your data to the table here.
        tbVelData.setItems(velDataPoints);
        
        //make sure the property value factory should be exactly same as the name from model class
        accelTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        acceleration.setCellValueFactory(new PropertyValueFactory<>("data"));
        //add your data to the table here.
        tbAccelData.setItems(accelDataPoints);
        
        //make sure the property value factory should be exactly same as the name from model class
        rssiTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        rssi.setCellValueFactory(new PropertyValueFactory<>("data"));
        //add your data to the table here.
        tbRssiData.setItems(rssiDataPoints);
    }




    


}
