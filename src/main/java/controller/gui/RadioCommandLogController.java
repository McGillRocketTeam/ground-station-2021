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
	
	public final static Integer MAX_LOG_SIZE = 100;
	
	@FXML
	public CheckBox auto_scroll_checkbox;
	
	@FXML
	public ListView<String> command_log;
	
    private static ObservableList<String> commandList = FXCollections.observableArrayList();
    private static List<String> fullCommandList = new ArrayList<String>();
    
    public static void add_log(String str) {
    	fullCommandList.add(str);
    }
    
    public void update_log_display() {
    	
    	if (commandList.size() != fullCommandList.size()) {
    		
    		if (commandList.size() == 0) {
    			commandList.add(fullCommandList.get(0));
    		} else {
//    			for (int i = commandList.size() - 1; i < fullCommandList.size(); i++) {
//        			commandList.add(fullCommandList.get(i));
//        		}
    			commandList.add(fullCommandList.get(fullCommandList.size() - 1));
    		}
    	}
    	
    	if(commandList.size() > MAX_LOG_SIZE) {
    		commandList.remove(0);
    	}
    	
    	if(auto_scroll_checkbox.isSelected()) {
        	command_log.scrollTo(commandList.size());
    	}
    }
    
    public void clear_log() {
    	commandList.clear();
    }
    
    public void initialize(){
    	commandList.clear();
    	fullCommandList.clear();
        
        command_log.setItems(commandList);
    }
}
