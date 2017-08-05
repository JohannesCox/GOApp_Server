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
	
	public boolean createEvent(String userID, String eventID, String eventname, 
			Date date, String location, String description) {
		boolean success = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			Event event = new Event(eventID, eventname, date, location, description);
			tx = session.beginTransaction();
			success = (boolean) session.save(event); //TODO: relation creation done, but ugly
			EventUserHandler handler = new EventUserHandler();
			handler.createRelation(eventID, userID);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}
	//TODO: check whether the user is admin
	public boolean updateEvent(String userID, String eventID, String eventname, 
			Date date, String location, String description) {
		EventUserHandler euh = new EventUserHandler();
		if(!euh.isAdmin(userID, eventID)) return false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Event event = session.load(Event.class, eventID);
			if(event == null) return false;//do i have to commit and close here?
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
		return true;
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
