package Database;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Table;

import com.google.gson.JsonObject;


@Entity
@Table(appliesTo = "Event")
public class Event {
	
	@Id 
	@Column(name="eventID")
	private String eventID;
	
	@Column(name="eventname")
	private String eventname;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="location")
	private String location;
	
	@Column(name="description")
	private String description;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="picture")
	private String picture;
	
	@Column(name="lastmodified")
	private int lastmodified;
	
	public Event(){	
	}
	
	public Event(String eventname, Date date, String location, String description, String picture) {
		new IdGenerator();
		this.eventID = IdGenerator.uuid();
		this.eventname = eventname;
		this.date = date;
		this.location = location;
		this.description = description;
		this.picture = picture;
		this.lastmodified = 0;
		
	}
	public Event(String eventname, Date date, String location, String description) {
		new IdGenerator();
		this.eventID = IdGenerator.uuid();
		this.eventname = eventname;
		this.date = date;
		this.location = location;
		this.description = description;
		this.picture = null;
		this.lastmodified = 0;
		
	}

	public String getEventID() {
		return eventID;
	}

	void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getEventname() {
		return eventname;
	}

	void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public Date getDate() {
		return date;
	}

	void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}
	
	void setPicture(String picture) {
		this.picture = picture;
	}
	
	public int getLastmodified() {
		return lastmodified;
	}

	void setLastmodified(int lastmodified) {
		this.lastmodified = lastmodified;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eventID == null) ? 0 : eventID.hashCode());
		result = prime * result + ((eventname == null) ? 0 : eventname.hashCode());
		result = prime * result + lastmodified;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (eventID == null) {
			if (other.eventID != null)
				return false;
		} else if (!eventID.equals(other.eventID))
			return false;
		if (eventname == null) {
			if (other.eventname != null)
				return false;
		} else if (!eventname.equals(other.eventname))
			return false;
		if (lastmodified != other.lastmodified)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		return true;
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
