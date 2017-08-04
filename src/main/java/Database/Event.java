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

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(int lastmodified) {
		this.lastmodified = lastmodified;
	}
	
	
}
