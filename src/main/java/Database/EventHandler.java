package Database;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EventHandler {
	private SessionFactory factory;
	public EventHandler() {
		try {
			Configuration configuration = new Configuration();
			factory = configuration.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Event.class)
					.buildSessionFactory();
		} catch(Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
	}
	
	Event getEvent(String eventID) {
		Session session = factory.openSession();
		Event event = session.get(Event.class, eventID);
		return event;
	}
	/**
	 * Creates an event in the database. But first the relation (eventID, userID, admin=true) will be created.
	 * If the creation of the relation was successful the database-entry can be set.
	 * @param userID of the user creating the event
	 * @param eventID of the event 
	 * @param eventname of the event
	 * @param date of the event
	 * @param location of the event
	 * @param description of the event
	 * @return event, if the entry could be created. Otherwise return null
	 */
	public Event createEvent(String userID, String eventname, 
			Date date, String location, String description) {
		String id = null;
		Event event = new Event(eventname, date, location, description);
		String eventID = event.getEventID();
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			
			tx = session.beginTransaction();
			EventUserHandler handler = new EventUserHandler();
			if(handler.createRelation(eventID, userID) == false) return null;
			id = (String) session.save(event);
			
			tx.commit();
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
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			event = session.get(Event.class, eventID);
			event.setEventname(eventname);
			event.setDate(date);
			event.setLocation(location);
			event.setDescription(description);
			event.setLastmodified(event.getLastmodified()+1); // increments the lastmodified parameter to guarantee synchronization with client
			session.update(event);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		}
		return event;
	}
	/**
	 * Deletes an event and all relations connected to the event
	 * @param userID 
	 * @param eventID of the event
	 * @return true, if the event could be deleted
	 */
	public boolean deleteEvent(String userID, String eventID) {
		EventUserHandler euh = new EventUserHandler();
		if(euh.isAdmin(userID, eventID)) {
			return false;
		} else {
			Session session = factory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				Event event = session.get(Event.class, eventID);
				List<EventUserRelation> relations = euh.getRelations_byeventID(eventID);
				if(event == null || euh.deleteRelations(relations) == false) return false;
				session.delete(event);
				tx.commit();
				
			} catch(HibernateException he) {
				if(tx != null) tx.rollback();
				he.printStackTrace();
			} finally {
				session.close();
			}
			
		}
		return true;
	}
	/**
	 * Deletes an event with given eventID. This method is only invoked, if EventUserHandler.leaveEvent(...) leads to an empty members list
	 * @param eventID of the event
	 * @return true, if the event could be deleted
	 */
	boolean deleteEvent(String eventID) {
		boolean success = false;
		Session session = factory.openSession();
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
