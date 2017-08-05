/**
 * 
 */
package Database;

import java.io.Serializable;




public class EventUserID implements Serializable {


protected String eventID;
protected String userID;

public EventUserID() {
}

public EventUserID(String eventID, String userID) {
	this.eventID = eventID;
	this.userID = userID;
}
}
