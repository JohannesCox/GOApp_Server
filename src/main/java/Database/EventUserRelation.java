package Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(EventUserID.class)
@Table(name= "EventUserRelation")
public class EventUserRelation {
	@Id @Column(name="eventID")
	private String eventID;
	@Id @Column(name="userID")
	private String userID;
	
	
	@Column(name="admin")
	private boolean admin;
	EventUserRelation(){}
	
	public EventUserRelation(String eventID, String userID, boolean admin) {
		this.eventID = eventID;
		this.userID = userID;
		this.admin = admin;
	}

	public String getEventID() {
		return eventID;
	}

	void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getUserID() {
		return userID;
	}

	void setUserID(String userID) {
		this.userID = userID;
	}

	public boolean isAdmin() {
		return admin;
	}

	void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + ((eventID == null) ? 0 : eventID.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
		EventUserRelation other = (EventUserRelation) obj;
		if (admin != other.admin)
			return false;
		if (eventID == null) {
			if (other.eventID != null)
				return false;
		} else if (!eventID.equals(other.eventID))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}
	
	
	
}
