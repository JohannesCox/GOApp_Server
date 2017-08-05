package Database;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EventUserHandler {
	
	private SessionFactory factory;
	
	public EventUserHandler() {
		try {
			Configuration configuration = new Configuration();
			factory = configuration.configure("hibernate.cfg.xml")
					.addAnnotatedClass(EventUserRelation.class)
					.buildSessionFactory();
		} catch(HibernateException he) {
			he.printStackTrace();
		}
	}
	//TODO: check whether createRelation and joinEvent can be combined in one method
	public boolean createRelation(String eventID, String userID) {
		boolean success = false;
		Transaction tx = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			EventUserRelation relation = new EventUserRelation(eventID, userID, true);
			session.save(relation);
			tx.commit();
			
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}
	
	public boolean joinEvent(String eventID, String userID) {
		boolean success = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EventUserRelation relation = new EventUserRelation(eventID, userID, false);
			session.save(relation);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}
	public void leaveEvent(String userID, String eventID) {
		
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EventUserRelation relation = 
					(EventUserRelation) session.load(EventUserRelation.class, new EventUserID(eventID, userID));
			session.delete(relation);
			//TODO: nominate admin if admin leaves event
			//TODO: delete event+ corresponding relations if all members left
			tx.commit();
			
			
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		
	}
	public boolean isAdmin(String userID, String eventID) {
		boolean admin = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EventUserRelation relation = (EventUserRelation) session.load(EventUserRelation.class, new EventUserID(eventID, userID));
			admin = relation.isAdmin() == true ? true : false;
			tx.commit();
			
		} finally {
			session.close();
		}
		return admin;
	}
	//TODO: implement nominateAdmin-method
	
	
}
