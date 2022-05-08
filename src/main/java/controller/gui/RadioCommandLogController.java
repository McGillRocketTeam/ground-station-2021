package controller.gui;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;




public class RadioCommandLogController {
	
	public final Integer MAX_LOG_SIZE = 100;
	
	@FXML
	public CheckBox auto_scroll_checkbox;
	
	@FXML
	public ListView command_log;
	
    private ObservableList<String> commandList = FXCollections.observableArrayList();
    private List<String> fullCommandList = new ArrayList<String>();

    public void add_log(String str) {
    	
    	fullCommandList.add(str);

    	if(commandList.size() > MAX_LOG_SIZE) {
    		commandList.remove(0);
    	}
    	commandList.add(str);
    	
    	if(auto_scroll_checkbox.isSelected()) {
        	command_log.scrollTo(commandList.size());
    	}


    	
    }
    
    public void clear_log() {
    	commandList.clear();
    }
    
    public void initialize(){
        command_log.setItems(commandList);
    }
}
