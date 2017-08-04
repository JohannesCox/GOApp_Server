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
	@Id @Column(name="admin")
	private boolean admin;
	
	EventUserRelation() {
		
	}
	
	
}
