package controller.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RawDataController implements Initializable{
	@FXML 
	private TableView<DataPoint> tbData;
	@FXML
	public TableColumn<DataPoint, Integer> time;
	@FXML
	public TableColumn<DataPoint, Integer> data;
	

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //make sure the property value factory should be exactly same as the e.g getStudentId from your model class
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        //add your data to the table here.
        tbData.setItems(dataPoints);
    }
    
    private ObservableList<DataPoint> dataPoints = FXCollections.observableArrayList(
            new DataPoint(1,4),
            new DataPoint(2,3),
            new DataPoint(2,3)
    );

}
