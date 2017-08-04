package Database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UserHandler {
	private static SessionFactory factory;
	public UserHandler() {
		try {
			Configuration configuration = new Configuration();
			factory = configuration.configure("hibernate.cfg.xml")
					.addAnnotatedClass(User.class)
					.buildSessionFactory();
		} catch(HibernateException he) {
			he.printStackTrace();
		}
	}
	
	public boolean addUser(String userID, String username) {
		boolean success = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = new User(userID, username);
			success = (boolean) session.save(user);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}
	
	public boolean deleteUser(String userID) {
		boolean success = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			User user = (User) session.get(User.class, userID);
			if(user != null) {
			//TODO: Delete all event entries with given userID
			session.delete(user);
			success = true;
			}
		} catch(HibernateException he) {
			he.printStackTrace();
		} finally {
			session.close();
		}
		
		return success;
	}
}
