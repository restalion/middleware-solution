package com.accenture.robot;

import java.io.IOException;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import jdk.dio.DeviceNotFoundException;

public class Robot extends MIDlet {
	
	/** Robot controller */
	private RobotController controller;
	
	/** Empty constructor */
	public Robot() {
	}

	
	/**
	 * Destroy app method.
	 */
	@Override
	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
		System.out.println("Stop Roboot");
		try {
			controller.close();
		} catch (IOException ex) {
			System.out.println("IOException closing DoorSensor: " + ex);
		}
	}

	/**
	 * Start app method.
	 */
	@Override
	protected void startApp() throws MIDletStateChangeException {
		
		System.out.println("Start Robot");
		
		// create new controller with specific pins
		controller = new RobotController(6, 7, 8, 17, 22);
		
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
