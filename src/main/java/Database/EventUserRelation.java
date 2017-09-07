package Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Models an EventUserRelation in the database. Such relation exists, if a user is member of an event. 
 * An EventUserRelation consists of the parameters eventID, userID and admin.
 * Admin shows wheter a user is administrator of an event, or not. 
 */
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
	
	/**
	 * Creates a EventUserRelation.
	 */
	EventUserRelation(){}
	
	/**
	 * Creates a EventUserRelation.
	 * @param eventID of the event.
	 * @param userID of the user, who is a member of the event
	 * @param admin
	 */
	public EventUserRelation(String eventID, String userID, boolean admin) {
		this.eventID = eventID;
		this.userID = userID;
		this.admin = admin;
	}
	/**
	 * @return eventID of the relation.
	 */
	public String getEventID() {
		return eventID;
	}


	/**
	 * @return userID of the relation.
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @return true, if the user is administrator of the event. Else return false.
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin of relation to set.
	 */
	void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + ((eventID == null) ? 0 : eventID.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EventUserRelation))
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
