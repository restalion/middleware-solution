package com.accenture.robot;

import java.io.IOException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.DeviceNotFoundException;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;

public class GPIOInput {

	private int switchPortID;
	private int switchPinID;	
	private GPIOPin pin;
	private boolean status;
	
	
	public GPIOInput(int switchPortID, int switchPinID) {
		super();
		this.setSwitchPortID(switchPortID);
		this.setSwitchPinID(switchPinID);
		this.setStatus(false);
	}
 
	
	public void start() throws IOException, DeviceNotFoundException {
		GPIOPinConfig config = null;
		
		config =  new GPIOPinConfig.Builder()
		           .setControllerNumber(this.switchPortID)
		           .setPinNumber(this.switchPinID)
		           .setDirection(GPIOPinConfig.DIR_INPUT_ONLY)
		           .setDriveMode(GPIOPinConfig.MODE_INPUT_PULL_UP)
		           .setTrigger(GPIOPinConfig.TRIGGER_BOTH_EDGES)
		           .setInitValue(true)
		           .build();
		
		pin = DeviceManager.open(config);
			
	}
	
	public int getSwitchPortID() {
		return switchPortID;
	}


	public void setSwitchPortID(int switchPortID) {
		this.switchPortID = switchPortID;
	}


	public int getSwitchPinID() {
		return switchPinID;
	}


	public void setSwitchPinID(int switchPinID) {
		this.switchPinID = switchPinID;
	}
	
	public GPIOPin getPin() {
		return pin;
	}


	public void setPin(GPIOPin pin) {
		this.pin = pin;
	}

	/**
	   * This method set the value for the LED
	   *
	   * @param value new value for the LED
	   * @throws IOException
	   */
	  public void setValue(boolean value) throws IOException {
	    pin.setValue(value);
	  }

	  /**
	   * This method set the value for the LED
	   *
	   * @return It returns the value of the LED
	   * @throws IOException
	   */
	  public boolean getValue() throws IOException {
	    return pin.getValue();
	  }
	
	
	public void close() throws IOException {
		if (pin != null) {
			pin.close();
		}
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}




}
