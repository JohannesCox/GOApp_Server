
package Database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class UserHandler {

	public UserHandler() {
	}
	
	public boolean addUser(String userID, String username, String email) {
		String id = null;
		Session session = HibernateUtil.getFactory().openSession();
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
		Session session = HibernateUtil.getFactory().openSession();
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
		Session session = HibernateUtil.getFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, userID);
			if(user == null) {
			} else {
			EventUserHandler euh = new EventUserHandler();
			List<EventUserRelation> membership = euh.getRelations_byuserID(userID);
			for(EventUserRelation rel : membership) {
				if(euh.leaveEvent(userID, rel.getEventID())== false) success = false;
			}
			session.delete(user);
			tx.commit();
			}
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		
		return success;
	}
	public boolean user_exists(String userID) {
		
		Session session = HibernateUtil.getFactory().openSession();
		User user = session.get(User.class, userID);
		session.close();
		return user == null ? false : true;
			
	}
}

