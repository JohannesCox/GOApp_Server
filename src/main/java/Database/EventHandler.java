package Database;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 * This class manages the accesses to the table "Event" in the database
 *
 */
public class EventHandler extends DataHandler {
	
	public EventHandler() {

	}
	
	/**
	 * Creates an event in the database. But first the relation (eventID, userID, admin=true) will be created.
	 * If the creation of the relation was successful the database-entry can be set.
	 * @param userID of the user creating the event 
	 * @param eventname of the event
	 * @param date of the event
	 * @param location of the event
	 * @param description of the event
	 * @return event, if the entry could be created. Otherwise return null
	 */
	public Event createEvent(String userID, String eventname, 
			Date date, String location, String description) {
		if(this.getUser(userID) == null) return null;
		String id = null;
		Event event = new Event(eventname, date, location, description);
		String eventID = event.getEventID();
		Session session = HibernateUtil.getFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EventUserHandler handler = new EventUserHandler();
			if(handler.createRelation(eventID, userID) == true) {
				id = (String) session.save(event);
				tx.commit();
			}
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		if(id == null || !id.equals(eventID)) return null;
		return event;
	}
	/**
	 * Creates an event in the database. But first the relation (eventID, userID, admin=true) will be created.
	 * If the creation of the relation was successful the database-entry can be set.
	 * @param userID of the user creating the event
	 * @param eventname of the event
	 * @param date of the event
	 * @param location of the event
	 * @param description of the event
	 * @param picture of the event
	 * @return event, if the entry could be created. Otherwise return null.
	 */
	public Event createEvent(String userID, String eventname, 
			Date date, String location, String description, String picture) {
		if(this.getUser(userID) == null) return null;
		String id = null;
		Event event = new Event(eventname, date, location, description, picture);
		String eventID = event.getEventID();
		Session session = HibernateUtil.getFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EventUserHandler handler = new EventUserHandler();
			if(handler.createRelation(eventID, userID) == true) {
				id = (String) session.save(event);
				tx.commit();
			}
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		if(id == null || !id.equals(eventID)) return null;
		return event;
	}
	
	public boolean storePicture(String userID, String eventID, String picture) {
		boolean success = false;
		Event event = this.getEvent(eventID);
		EventUserRelation relation = this.getRelation(eventID, userID);
		if(event == null || relation == null || !relation.isAdmin()) {
			return success;
		} else {
			Session session = HibernateUtil.getFactory().openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				event.setPicture(picture);
				event.setLastmodified(event.getLastmodified() +1);
				session.saveOrUpdate(event);
				tx.commit();
				success = true;
			} catch(HibernateException he) {
				if(tx != null) tx.rollback();
				he.printStackTrace();
			} finally {
				session.close();
			}
		}
		
		return success;
	}
	
	public String getPicture(String userID, String eventID) {
		Event event = this.getEvent(eventID);
		EventUserRelation relation = this.getRelation(eventID, userID);
		if(event == null || relation == null) {
			return null;
		} else {
			return event.getPicture();
		}
	}
	/**
	 * Updates an event, if the user has the permission.
	 * @param userID of the user, trying to update the event
	 * @param eventID of the event
	 * @param eventname of the event
	 * @param date of the event
	 * @param location of the event
	 * @param description of the event
	 * @return event, if the event could be updated. Otherwise return null
	 */
	public Event updateEvent(String userID, String eventID, String eventname, 
			Date date, String location, String description) {
		Event event = null;
		EventUserHandler euh = new EventUserHandler();
		if(!euh.isAdmin(userID, eventID)) return null; //user has no permission to update event
		Session session = HibernateUtil.getFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			event = session.get(Event.class, eventID);
			event.setEventname(eventname);
			event.setDate(date);
			event.setLocation(location);
			event.setDescription(description);
			// increment the lastmodified parameter to guarantee synchronization with client
			event.setLastmodified(event.getLastmodified()+1); 
			session.update(event);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return event;
	}
	/**
	 * Deletes an event and all relations connected to the event
	 * @param userID 
	 * @param eventID of the event
	 * @return true, if the event could be deleted
	 */
	public synchronized boolean deleteEvent(String userID, String eventID) {
		EventUserHandler euh = new EventUserHandler();
		boolean success = false;
		Event event = this.getEvent(eventID);
		if(event == null || !euh.isAdmin(userID, eventID) ) { 
			return success;
		} else {
			Session session = HibernateUtil.getFactory().openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction(); 
				List<EventUserRelation> relations = euh.getRelations_byeventID(eventID);
				if(euh.deleteRelations(relations) == true) {	
					session.delete(event);
					tx.commit();
					success = true;
				}	
			} catch(HibernateException he) {
				if(tx != null) tx.rollback();
				he.printStackTrace();
			} finally {
				session.close();
			}
			return success;
		}
	}
	/**
	 * Deletes an event with given eventID. This method is only invoked, if EventUserHandler.leaveEvent(...) leads to an empty members list
	 * @param eventID of the event
	 * @return true, if the event could be deleted
	 */
	boolean deleteEvent(String eventID) {
		boolean success = false;
		Session session = HibernateUtil.getFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Event event = (Event) session.get(Event.class, eventID);
			if(event != null) {
				session.delete(event);
				success = true;
			}
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}
}
