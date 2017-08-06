package Database;

import java.util.Date;

import com.google.gson.JsonObject;

public class Event {
	private String eventID;
	private String eventname;
	private Date date;
	private String location; //TODO: data type might be changed!
	private String description;
	private int lastmodified;
	
	Event(String eventname, Date date, String location, String description) {
		new IdGenerator();
		this.eventID = IdGenerator.uuid();
		this.eventname = eventname;
		this.date = date;
		this.location = location;
		this.description = description;
		this.lastmodified = 0;
		
	}

	 String getEventID() {
		return eventID;
	}

	void setEventID(String eventID) {
		this.eventID = eventID;
	}

	String getEventname() {
		return eventname;
	}

	void setEventname(String eventname) {
		this.eventname = eventname;
	}

	Date getDate() {
		return date;
	}

	void setDate(Date date) {
		this.date = date;
	}

	String getLocation() {
		return location;
	}

	void setLocation(String location) {
		this.location = location;
	}

	String getDescription() {
		return description;
	}

	void setDescription(String description) {
		this.description = description;
	}

	int getLastmodified() {
		return lastmodified;
	}

	void setLastmodified(int lastmodified) {
		this.lastmodified = lastmodified;
	}
	
	public JsonObject serialize() {
		JsonObject jo = new JsonObject();
		jo.addProperty("eventId", eventID);
		jo.addProperty("title", eventname);
		jo.addProperty("date", date.getTime());
		jo.addProperty("location", location);
		jo.addProperty("description", description);
		jo.addProperty("lastModified", lastmodified);
		return jo;
	}
	
	
}
