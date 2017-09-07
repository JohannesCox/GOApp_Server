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

/**
 * This class models an Event in the database
 *
 */
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
	
	/**
	 * Creates an event
	 */
	public Event(){	
	}
	
	/**
	 * Creates an event.
	 * @param eventname of the event
	 * @param date of the event
	 * @param location of the event
	 * @param description of the event
	 * @param picture of the event
	 */
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
	
	/**
	 * Creates an event.
	 * @param eventname of the event
	 * @param date of the event
	 * @param location of the event
	 * @param description of the event
	 */
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

	/**
	 * @return eventID of the event.
	 */
	public String getEventID() {
		return eventID;
	}

	/**
	 * @param eventID of the event to set.
	 */
	void setEventID(String eventID) {
		this.eventID = eventID;
	}

	/**
	 * @return eventname of the event.
	 */
	public String getEventname() {
		return eventname;
	}

	/**
	 * @param eventname of the event to set.
	 */
	void setEventname(String eventname) {
		this.eventname = eventname;
	}

	/**
	 * @return date of the event
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date of the event to set.
	 */
	void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return location of the event.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location of the event to set.
	 */
	void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return description of the event.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description of the event to set.
	 */
	void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return picture of the event.
	 */
	public String getPicture() {
		return picture;
	}
	
	/**
	 * @param picture of the event to set.
	 */
	void setPicture(String picture) {
		this.picture = picture;
	}
	
	/**
	 * @return lastmodified of the event.
	 */
	public int getLastmodified() {
		return lastmodified;
	}

	/**
	 * @param lastmodified of the event to set.
	 */
	void setLastmodified(int lastmodified) {
		this.lastmodified = lastmodified;
	}
	
	@Override
	public final int hashCode() {
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
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Event))
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
	
	/**
	 * @return JsonObject of the event.
	 */
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
