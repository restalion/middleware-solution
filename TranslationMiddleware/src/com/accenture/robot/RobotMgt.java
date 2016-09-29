package com.accenture.robot;

import java.io.IOException;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.accenture.robot.constants.RobotConstants;

import jdk.dio.DeviceNotFoundException;


public class RobotMgt extends MIDlet {
	private RobotController controller;
	public RobotMgt() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
		System.out.println("Stop Robot");
		try {
			controller.close();
		} catch (IOException ex) {
			System.out.println("IOException closing DoorSensor: " + ex);
		}

	}

	@Override
	protected void startApp() throws MIDletStateChangeException {
		System.out.println("Start App...");
		System.out.println("- Input Error PIN: " + this.getAppProperty(RobotConstants.INPUT_ERROR));
		System.out.println("- Input Start PIN: " + this.getAppProperty(RobotConstants.INPUT_START));
		System.out.println("- Input Stop PIN: " + this.getAppProperty(RobotConstants.INPUT_STOP));
		
		int inputError = Integer.valueOf(this.getAppProperty(RobotConstants.INPUT_ERROR));
		int inputStart = Integer.valueOf(this.getAppProperty(RobotConstants.INPUT_START));
		int inputStop = Integer.valueOf(this.getAppProperty(RobotConstants.INPUT_STOP));
		controller = new RobotController(inputStart,inputStop,inputError, this.getAppProperty(RobotConstants.DEVICE_ID));
		
		RobotConstants.setURL_GET(this.getAppProperty(RobotConstants.URL_POST));
		
		try {
			controller.start();
		} catch (DeviceNotFoundException ex) {
			System.out.println("DeviceException: " + ex.getMessage());
			notifyDestroyed();
		} catch (IOException ex) {
			System.out.println("IOException: " + ex);
			notifyDestroyed();
		}
	}

}
