package Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class EventUserHandler extends DataHandler {
	
	public EventUserHandler() {

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
		Session session = HibernateUtil.getFactory().openSession();
		try {
			tx = session.beginTransaction();
			EventUserRelation relation = new EventUserRelation(eventID, userID, true);
			EventUserID id = (EventUserID) session.save(relation);
			if(id != null) success = true;
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
	 * This method models a join to an event. Creates a new relation (eventID, userID, admin), with admin set to false
	 * @param eventID of the event to join
	 * @param userID of the user
	 * @return Event to be joined
	 */
	public Event joinEvent(String eventID, String userID) {
		Event event = this.getEvent(eventID);
		User user = this.getUser(userID);
		if(event == null || user == null) return null;
		Session session = HibernateUtil.getFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if(!this.isMember(userID, eventID)) {
			EventUserRelation relation = new EventUserRelation(eventID, userID, false);
			EventUserID id = (EventUserID) session.save(relation);
			if(id == null || !id.equals(new EventUserID(eventID, userID))) {
			tx.rollback();
			event = null;
			}
			}
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
		User user = this.getUser(userID);
		EventUserRelation relation = this.getRelation(eventID, userID);
		if(user == null || relation == null) return success;
		Session session = HibernateUtil.getFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(relation);
			List<EventUserRelation> members = this.getRelations_byeventID(eventID);
			if(relation.isAdmin()) {
				if(members.isEmpty()) {
					EventHandler eh = new EventHandler();
					success = eh.deleteEvent(eventID);
					tx.commit();
				} else {
					nominateAdmin(members);
					success = true;
					tx.commit();
				}
				}
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
		EventUserRelation relation = this.getRelation(eventID, userID);
		return relation.isAdmin();
	}
	/**
	 * Checks whether the user with given userID is member of the event with given eventID
	 * @param userID of the user
	 * @param eventID of the event
	 * @return true, if the user is member of the event
	 */
	public boolean isMember(String userID, String eventID) {
		EventUserRelation relation = this.getRelation(eventID, userID);
		return relation == null ? false : true;
	}
	/**
	 * Creates a list of all relations to the corresponding eventID. An empty list will be created if there is no such 
	 * relation
	 * @param eventID of the event
	 * @return list of the relations
	 */
	public List<EventUserRelation> getRelations_byeventID(String eventID) {
		Session session = HibernateUtil.getFactory().openSession();
		Criteria cr = session.createCriteria(EventUserRelation.class);
		cr.add(Restrictions.eq("eventID", eventID));
		List<EventUserRelation> relations = cr.list();
		session.close();
		return relations;
	}
	/**
	 * Creates a list of all relations to the corresponding userID. An empty list will be created if there is no such relation
	 * @param userID of the user
	 * @return list of the relations
	 */
	public List<EventUserRelation> getRelations_byuserID(String userID){
		Session session = HibernateUtil.getFactory().openSession();
		Criteria cr = session.createCriteria(EventUserRelation.class);
		cr.add(Restrictions.eq("userID", userID));
		List<EventUserRelation> relations = cr.list();
		session.close();
		return relations;
	}

	
	/**
	 * Picks randomly a user from the members list and nominates this user for administrator
	 * @param members of the event
	 */
	public void nominateAdmin(List<EventUserRelation> members) {
		Session session = HibernateUtil.getFactory().openSession();
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
	/**
	 * Deletes a list of relations. This method is invoked, if an event or a user is deleted
	 * @param relations to delete
	 * @return true, if the relations could be deleted
	 */
	boolean deleteRelations(List<EventUserRelation> relations) {
		Session session = HibernateUtil.getFactory().openSession();
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
	
	/**
	 * Creates a list of all events the user with given userID is a member. The created list is empty, if the user is not member of any events
	 * @param userID of the user
	 * @return list of events
	 */
	public List<Event> getAllUserEvents(String userID) {
		List<EventUserRelation> relations = this.getRelations_byuserID(userID);
		List<Event> list = new ArrayList<Event>();
		for(EventUserRelation rel : relations) {
			list.add(new EventHandler().getEvent(rel.getEventID()));
		}
		return list;
	}
	
	/**
	 * Returns a mapping (userID, isAdmin) of all members of an event.
	 * @param eventID
	 * @return Map of all members of the event 
	 */
	public Map<String,Boolean> getMembers(String userID,String eventID) {
		Map<String, Boolean> members= new HashMap<String,Boolean>();
		if(this.isMember(userID, eventID)) {
		List<EventUserRelation> relations = this.getRelations_byeventID(eventID);
		for(EventUserRelation rel : relations) {
			User user = this.getUser(rel.getUserID());
			members.put(user.getUsername(), rel.isAdmin());
		}
		}
		return members;
	}
}
