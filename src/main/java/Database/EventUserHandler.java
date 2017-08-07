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
	/**
	 * This method is invoked with the creation of an event. Creates the relation between the creator of the event and the event itself
	 * The creator of the event, will be automatically the administrator of the event 
	 * @param eventID of the event
	 * @param userID of the user creating the event
	 * @return true, if the relation, could be created
	 */
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

	public Event joinEvent(String eventID, String userID) {
		Event event = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			event = session.load(Event.class, eventID);
			if(event != null) ;
			EventUserRelation relation = new EventUserRelation(eventID, userID, false);
			EventUserID id = (EventUserID) session.save(relation);
			//if(id == null || !id.equals(new EventUserID(eventID, userID)));
			tx.commit();
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return event;
	}
	/**
	 * Deletes the relation with given eventID and userID. If there are no relations left,
	 * the corresponding event will be deleted. If the admin parameter of the relation was set, then there
	 * another admin from left members will be nominated 
	 * @param userID of the corresponding user
	 * @param eventID of the corresponding event
	 * @return true, if leaving the event was succesful
	 */
	public boolean leaveEvent(String userID, String eventID) {
		boolean success = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EventUserRelation relation = 
					session.load(EventUserRelation.class, new EventUserID(eventID, userID));
			if(relation != null) {
			session.delete(relation);
			List<EventUserRelation> members = this.getRelations_byeventID(eventID);
			if(relation.isAdmin()) {
				if(members.isEmpty()) {
					EventHandler eh = new EventHandler();
					eh.deleteEvent(eventID);
				} else {
					nominateAdmin(members);
				}
				}
			}
			tx.commit();
			success = true;
			
			
		} catch(HibernateException he) {
			if(tx != null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}
		/**
		 * Checks whether the user with corresponding ID is the administrator of the event
		 * @param userID of user
		 * @param eventID of event
		 * @return true, if the user is administrator of the event. 
		 */
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
	/**
	 * Creates a list of all relations to the corresponding ID. An empty list will be created if there is no such 
	 * relation
	 * @param eventID of the event
	 * @return list of the relations
	 */
	public List<EventUserRelation> getRelations_byeventID(String eventID) {
		Session session = factory.openSession();
		Criteria cr = session.createCriteria(EventUserRelation.class);
		cr.add(Restrictions.eq("eventID", eventID));
		List<EventUserRelation> relations = cr.list();
		session.close();
		return relations;
	}
	
	public List<EventUserRelation> getRelations_byuserID(String userID){
		Session session = factory.openSession();
		Criteria cr = session.createCriteria(EventUserRelation.class);
		cr.add(Restrictions.eq("userID", userID));
		List<EventUserRelation> relations = cr.list();
		return relations;
	}

	
	/**
	 * Picks randomly a user from the members list and nominates this user for administrator
	 * @param members of the event
	 */
	public void nominateAdmin(List<EventUserRelation> members) {
		Session session = factory.openSession();
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
	boolean deleteRelations(List<EventUserRelation> relations) {
		Session session = factory.openSession();
		boolean success = true;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for(EventUserRelation rel : relations) {
				
				session.delete(rel);
			}
			tx.commit();
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
