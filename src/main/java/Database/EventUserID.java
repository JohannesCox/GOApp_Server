
package Database;

import java.io.Serializable;


/**
 * This class models the composite primary key of the EventUserRelations
 *
 */
public class EventUserID implements Serializable {


/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
protected String eventID;
protected String userID;

/**
 * Creates an EventUserID
 */
public EventUserID(){}

/**
 * Creates an EventUserID
 * @param eventID of the event
 * @param userID of the user, who is a member of the event
 */
public EventUserID(String eventID, String userID) {
	this.eventID = eventID;
	this.userID = userID;
}

@Override
public final int hashCode() {
	final int prime = 31;
	int result = 1;
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
	if (!(obj instanceof EventUserID))
		return false;
	EventUserID other = (EventUserID) obj;
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
