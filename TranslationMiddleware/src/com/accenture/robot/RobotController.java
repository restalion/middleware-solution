package com.accenture.robot;

import java.io.IOException;

import com.accenture.robot.constants.RobotConstants;
import com.accenture.robot.service.RestClient;

import jdk.dio.DeviceNotFoundException;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;

public class RobotController implements PinListener, AutoCloseable {

	private final GPIOInput buttonStart;
	private final GPIOInput buttonError;
	private final GPIOInput buttonStop;
	private final String deviceId;

	public RobotController(int buttonStartPin, int buttonStoptPin, int buttonErrorPin, String deviceId) {
		super();

		this.buttonStart = new GPIOInput(0, buttonStartPin);
		this.buttonError = new GPIOInput(0, buttonErrorPin);
		this.buttonStop = new GPIOInput(0, buttonStoptPin);
		this.deviceId = deviceId;

	}

	public void start() throws IOException, DeviceNotFoundException {
		buttonStart.start();
		buttonError.start();
		buttonStop.start();
		// Add this class as a pin listener to the buttons
		buttonStart.getPin().setInputListener(this);
		buttonError.getPin().setInputListener(this);
		buttonStop.getPin().setInputListener(this);

	}

	@Override
	public void close() throws IOException {
		buttonError.close();
		buttonStart.close();
		buttonStop.close();
	}

	@Override
	public void valueChanged(PinEvent event) {
		GPIOPin pin = event.getDevice();
		if (event.getValue()) {
			if (pin == buttonError.getPin() && !buttonError.isStatus()) {
				System.out.println("System is in ERROR");
				sendEvent(RobotConstants.ERROR);
			} else if (pin == buttonStart.getPin() && !buttonStart.isStatus()) {
				System.out.println("System is ON");
				buttonStart.setStatus(true);
				buttonStop.setStatus(false);
				sendEvent(RobotConstants.ON);
			} else if(pin == buttonStop.getPin() && !buttonStop.isStatus()) {
				System.out.println("System is OFF	");	
				buttonStart.setStatus(false);
				buttonStop.setStatus(true);
				sendEvent(RobotConstants.OFF);
			}
		}

	}
	
	public void sendEvent(String type){
		EventJson eventJ = new EventJson();
		eventJ.setDate(System.currentTimeMillis());
		eventJ.setEventType(type);
		eventJ.setSource(this.deviceId);		
		
		System.out.println(eventJ.toJson().toString());
		
		RestClient rest = new RestClient();
		try {			
			rest.post(RobotConstants.getURL_GET(),eventJ.toJson().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		    
	}
	

}
