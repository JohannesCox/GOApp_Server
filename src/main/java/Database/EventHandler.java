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
		} catch(HibernateException he) {
			he.printStackTrace();
		}
	}
	
	public Event createEvent(String userID, String eventID, String eventname, 
			Date date, String location, String description) {
		Event event = new Event(eventname, date, location, description);
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			
			tx = session.beginTransaction();
			event = (Event) session.save(event); //TODO: relation creation done, but ugly
			EventUserHandler handler = new EventUserHandler();
			handler.createRelation(eventID, userID);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return event;
	}

	public Event updateEvent(String userID, String eventID, String eventname, 
			Date date, String location, String description) {
		Event event = null;
		EventUserHandler euh = new EventUserHandler();
		if(!euh.isAdmin(userID, eventID)) return null; //user has no permission to update event
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			event = session.load(Event.class, eventID);
			event.setEventname(eventname);
			event.setDate(date);
			event.setLocation(location);
			event.setDescription(description);
			event.setLastmodified(event.getLastmodified()+1);
			session.update(event);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		}
		return event;
	}
//TODO: check whether user is admin
	public boolean deleteEvent(String userID, String eventID) {
		EventUserHandler euh = new EventUserHandler();
		if(euh.isAdmin(userID, eventID)) {
			return false;
		} else {
			Session session = factory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				Event event = session.load(Event.class, eventID);
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
	boolean deleteEvent(String eventID) {
		boolean success = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Event event = (Event) session.load(Event.class, eventID);
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
