package com.accenture.robot;

import java.io.IOException;

import com.accenture.robot.GPIOLED;

import jdk.dio.DeviceNotFoundException;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;

public class RobotController implements PinListener, AutoCloseable  {

	/** Shut down LED. */
	private final GPIOLED redLED;
	/** Running LED. */
	private final GPIOLED greenLED;
	/** Error LED. */
	private final GPIOLED errorLED;
	/** Start/Stop button. */
	private final GPIOButton buttonStart;
	/** Error button. */
	private final GPIOButton buttonError;	

	/**
	 * Consutructor.
	 * 
	 * @param redLEDPin 
	 * @param greenLEDPin
	 * @param errorLEDPin
	 * @param buttonStartPin
	 * @param buttonErrorPin
	 */
	public RobotController(int redLEDPin, int greenLEDPin, int errorLEDPin, int buttonStartPin, int buttonErrorPin) {
		super();
		this.redLED = new GPIOLED(redLEDPin);
		this.greenLED = new GPIOLED(greenLEDPin);
		this.errorLED = new GPIOLED(errorLEDPin);
		this.buttonStart = new GPIOButton(0, buttonStartPin);
		this.buttonError = new GPIOButton(0, buttonErrorPin);

	}

	
	public void start() throws IOException, DeviceNotFoundException {
	    
	   buttonStart.start();
	   buttonError.start();

	    //Create the connection to the LEDs
	    redLED.start();
	    greenLED.start();
	    errorLED.start();
	 
	    redLED.setValue(true);
	    greenLED.setValue(false);
	    errorLED.setValue(false);

	    // Add this class as a pin listener to the buttons
	    buttonStart.getPin().setInputListener(this);
	    buttonError.getPin().setInputListener(this);

	  }
	
	
	@Override
	public void close() throws IOException {
		 redLED.close();
		 greenLED.close();
		 buttonError.close();
		 buttonStart.close();
	}


	@Override
	public void valueChanged(PinEvent event) {
		GPIOPin pin = event.getDevice();
		try {
			if (event.getValue()) {
				if (pin == buttonStart.getPin()) {
					System.out.println("Boton Start pulsado: pin = " + buttonStart.getSwitchPinID());
				}
				if (pin == buttonError.getPin()) {
					System.out.println("Boton Error pulsado: pin = " + buttonError.getSwitchPinID());
				}
				System.out.println("Event:" + event.getValue());

				if (pin == buttonError.getPin() && greenLED.getValue() && event.getValue()) {
					System.out.println("El Sistema ha producido un error");
//					errorLED.setStopBlink(false);
//					errorLED.blink(3);
//					errorLED.blink(3);
					errorLED.setValue(true);
	                Thread.sleep(1000);
	                errorLED.setValue(false);
				} else if (pin == buttonStart.getPin() && event.getValue()) {
					greenLED.setValue(!greenLED.getValue());
					redLED.setValue(!redLED.getValue());
				}
			}
		} catch (IOException ex) {
			System.out.println("DoorSensor: IOException: " + ex);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	
	
}
