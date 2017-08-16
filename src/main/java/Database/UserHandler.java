
package Database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class UserHandler extends DataHandler {

	public UserHandler() {
	}
	/**
	 * Adds a user to the table "User"
	 * @param userID of the user to be added
	 * @param username of the user
	 * @param email of the user
	 * @return true, if the user could be added
	 */
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
	/**
	 * Adds a user to the table "User"
	 * @param userID of the user to be added
	 * @param username of the user
	 * @return true, if the user could be added 
	 */
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
	/**
	 * Removes a user from the table "User" and its EventUserRelations.
	 * @param userID of the user to be removed
	 * @return true, if the removal was successful
	 */
	public boolean deleteUser(String userID) {
		boolean success = true;
		User user = this.getUser(userID);
		if(user == null) return false;
		Session session = HibernateUtil.getFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EventUserHandler euh = new EventUserHandler();
			List<EventUserRelation> membership = euh.getRelations_byuserID(userID);
			for(EventUserRelation rel : membership) {
				if(euh.leaveEvent(userID, rel.getEventID())== false) success = false;
			}
			session.delete(user);
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		
		return success;
	}
	/**
	 * Checks whether the user exists in the database
	 * @param userID of the user
	 * @return true, if the user exists
	 */
	public boolean user_exists(String userID) {
		User user = this.getUser(userID);
		return user == null ? false : true;
			
	}
}

