package com.accenture.robot;

import com.oracle.json.Json;
import com.oracle.json.JsonObject;

//import org.bson.types.ObjectId;

public class EventJson {

	// Id of the machine that generates de event
	String source;
	// Type of event (Turn on or turn off the machine)
	String eventType;
	// Date of the event
	Long date;

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	
	public JsonObject toJson(){
		 JsonObject value = Json.createObjectBuilder()
			     .add("source", this.source)
			     .add("eventType", this.eventType)
			     .add("date",this.date)
			     .build();
		 
		 return value;
	}
	
}
