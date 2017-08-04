package Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "EventUserRelation")
class EventUserRelation {
	@Id @Column(name="eventID")
	private String eventID;
	
	@Id @Column(name="userID")
	private String userID;
	
	@Column(name="admin")
	private boolean admin;
	
	EventUserRelation(String eventID, String userID, boolean admin) {
		this.eventID = eventID;
		this.userID = userID;
		this.admin = admin;
	}

	String getEventID() {
		return eventID;
	}

	void setEventID(String eventID) {
		this.eventID = eventID;
	}

	String getUserID() {
		return userID;
	}

	void setUserID(String userID) {
		this.userID = userID;
	}

	boolean isAdmin() {
		return admin;
	}

	void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	
}
