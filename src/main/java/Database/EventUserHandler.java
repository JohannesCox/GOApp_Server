package Database;

import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

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
					session.load(EventUserRelation.class, new EventUserID(eventID, userID));
			session.delete(relation);
			//TODO: nominate admin if admin leaves event
			this.nominateAdmin(eventID);
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
			EventUserRelation relation = session.load(EventUserRelation.class, new EventUserID(eventID, userID));
			admin = relation.isAdmin() == true ? true : false;
			tx.commit();
			
		} finally {
			session.close();
		}
		return admin;
	}
	public List<EventUserRelation> getRelationbyeventID(String eventID) {
		Session session = factory.openSession();
		Criteria cr = session.createCriteria(EventUserRelation.class);
		cr.add(Restrictions.eq("eventID", eventID));
		List<EventUserRelation> members = cr.list();
		return members;
	}
	//TODO: implement nominateAdmin-method
	public void nominateAdmin(String eventID) {
		Session session = factory.openSession();
		List<EventUserRelation> members = this.getRelationbyeventID(eventID);
		EventUserRelation relation = (EventUserRelation) members.get(new Random().nextInt(members.size()));
		relation.setAdmin(true);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(relation);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		
	}
	
	
}
