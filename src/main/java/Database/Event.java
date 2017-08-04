package Database;

import java.util.Date;

class Event {
	private String eventID;
	private String eventname;
	private Date date;
	private String location; //TODO: data type might be changed!
	private String description;
	private int lastmodified;
	
	Event(String eventID, String eventname, Date date, String location, String description) {
		this.eventID = eventID;
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
	
	
}
