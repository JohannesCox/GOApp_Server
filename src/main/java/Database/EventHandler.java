package Database;

import java.util.Date;

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
			success = (boolean) session.save(event); //TODO: create an event-user relation
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}
	
	public boolean updateEvent(String userID, String eventID, String eventname, 
			Date date, String location, String description) {
		boolean success = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Event event = session.load(Event.class, eventID);
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
		return success;
	}

	public boolean deleteEvent(String userID) {
		boolean success = false;
		return success;
	}
}
