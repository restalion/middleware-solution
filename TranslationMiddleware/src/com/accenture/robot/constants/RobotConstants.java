package com.accenture.robot.constants;

public class RobotConstants {

	// Inputs pins
	public static String INPUT_ERROR = "input_error";
	public static String INPUT_STOP = "input_stop";
	public static String INPUT_START = "input_start";
	
	// connections
	public static String URL_GET = "url_service_get";


	public static String URL_POST = "url_service_post";
	
	// Event type
	public static String ON = "ON";
	public static String OFF = "OFF";
	public static String ERROR = "ERROR";
	
	public static String DEVICE_ID = "device_id";
	
	public static String getURL_GET() {
		return URL_GET;
	}

	public static void setURL_GET(String uRL_GET) {
		URL_GET = uRL_GET;
	}
	
}
