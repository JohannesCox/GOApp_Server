
package Database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UserHandler {
	private SessionFactory factory;
	public UserHandler() {
		try {
			Configuration configuration = new Configuration();
			this.factory = configuration.configure("hibernate.cfg.xml")
					.addAnnotatedClass(User.class)
					.buildSessionFactory();
		} catch(Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
	}
	
	public boolean addUser(String userID, String username, String email) {
		String id = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = new User(userID, username, email);
			id = (String) session.save(user);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return id.equals(userID) ? true : false;
	}
	
	public boolean addUser(String userID, String username) {
		String id = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = new User(userID, username);
			id = (String) session.save(user);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return id.equals(userID) ? true : false;
	}
	
	public boolean deleteUser(String userID) {
		boolean success = true;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, userID);
			if(user != null) {
			EventUserHandler euh = new EventUserHandler();
			List<EventUserRelation> membership = euh.getRelations_byuserID(userID);
			if(!membership.isEmpty()) euh.deleteRelations(membership);
			session.delete(user);
			tx.commit();
			}
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
			success = false;
		} finally {
			session.close();
		}
		
		return success;
	}
}

