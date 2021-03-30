package controller.serial;

import java.util.ArrayList;



import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialComm {
	
	//SerialPort serialPort = null;
	//StringBuffer inputBuffer = new StringBuffer();
	String out;
	
	public static void main(String[] args) {
		
		String[] ports = SerialPortList.getPortNames();
		
		if (ports.length == 0) {
			System.out.println("no serial ports");
		} else {
			
		for (String name : ports) {
			System.out.println(name);
		}
		
		SerialPort serialPort = new SerialPort(ports[0]);
			
			try {
				serialPort.openPort();

				serialPort.setParams(
						SerialPort.BAUDRATE_9600,
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE
						);
				
				serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
				serialPort.addEventListener(new SerialPortReader(serialPort));
				//PortReader portReader = new PortReader();	
				//serialPort.addEventListener(portReader, SerialPort.MASK_RXCHAR);
				
				
			} catch (SerialPortException e) {
				e.printStackTrace();
			} 
		}
		
	}
	
//	public String serialCom() {
//		
//		String[] ports = SerialPortList.getPortNames();
//		
//		
//		if (ports.length == 0) {
//			System.out.println("no serial ports");
//		} else {
//			
//		for (String name : ports) {
//			System.out.println(name);
//		}
//			 serialPort = new SerialPort(ports[0]);
//			
//			try {
//				serialPort.openPort();
//				
//				serialPort.setParams(
//						SerialPort.BAUDRATE_9600,
//						SerialPort.DATABITS_8,
//						SerialPort.STOPBITS_1,
//						SerialPort.PARITY_NONE
//						);
//				
//				serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
//				serialPort.addEventListener(new SerialPortReader());
//				//PortReader portReader = new PortReader();	
//				//serialPort.addEventListener(portReader, SerialPort.MASK_RXCHAR);
//				
//			} catch (SerialPortException e) {
//				System.out.println("cannot open port");
//			} 
//		}
//		return out;
//	}
}
	
	 class SerialPortReader implements SerialPortEventListener {
		
		SerialPort serialPort;
		StringBuffer inputBuffer = new StringBuffer();
		
		public SerialPortReader(SerialPort serialPort) {
			this.serialPort = serialPort;
			//this.inputBuffer = inputBuffer;
		}
		
		public void serialEvent(SerialPortEvent event) {
			// TODO Auto-generated method stub
			if (event.isRXCHAR() && event.getEventValue() > 0) { 
				try {	
					//FIX ISSUE WITH SPLIT STRING INPUT
					byte[] buffer = serialPort.readBytes();
					if (buffer != null && buffer.length > 0) {
						String s = new String(buffer, 0, buffer.length);
						inputBuffer.append(s);
						//System.out.println(inputBuffer.toString());
						//check for line terminators 
						if (inputBuffer.toString().contains("E")) {
							//out = inputBuffer.toString();
							System.out.println(inputBuffer.toString());
							inputBuffer.setLength(0);
							
						} 
					}
			} catch (SerialPortException e) {
				System.out.println("unable to read string input");
			}
			
		}
	  
	}
}


