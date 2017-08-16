package Database;

import org.hibernate.Session;
/**
 * Manages accesses to the database
 *
 */

public class DataHandler {
	/**
	 * Gets an User-object from the database
	 * @param userID of the user
	 * @return User, if the user corresponding to userID exists. Else null will be returned
	 */
	User getUser(String userID) {
		Session session = HibernateUtil.getFactory().openSession();
		User user = session.get(User.class, userID);
		return user;
		
	}
	/**
	 * Gets an Event-object from the database 
	 * @param eventID of the event
	 * @return Event, if the event corresponding to eventID exists. Else null will be returned
	 */
	Event getEvent(String eventID) {
		Session session = HibernateUtil.getFactory().openSession();
		Event event = session.get(Event.class, eventID);
		return event;
	}
	/**
	 * Gets an EventUserRelation-object from the database
	 * @param eventID of the relation
	 * @param userID of the relation
	 * @return EventUserRelation,if the event corresponding to EventUserID exists. Else null will be returned.
	 */
	EventUserRelation getRelation(String eventID, String userID) {
		EventUserID id = new EventUserID(eventID, userID);
		Session session = HibernateUtil.getFactory().openSession();
		EventUserRelation relation = session.get(EventUserRelation.class, id);
		return relation;
	}
}
