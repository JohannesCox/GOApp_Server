package Database;

import org.hibernate.Session;


public class DataHandler {
	User getUser(String userID) {
		Session session = HibernateUtil.getFactory().openSession();
		User user = session.get(User.class, userID);
		return user;
		
	}
	Event getEvent(String eventID) {
		Session session = HibernateUtil.getFactory().openSession();
		Event event = session.get(Event.class, eventID);
		return event;
	}
	EventUserRelation getRelation(String eventID, String userID) {
		EventUserID id = new EventUserID(eventID, userID);
		Session session = HibernateUtil.getFactory().openSession();
		EventUserRelation relation = session.get(EventUserRelation.class, id);
		return relation;
	}
}
