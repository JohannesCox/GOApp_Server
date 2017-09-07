package databaseTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import org.junit.*;


import Database.*;
/**
 * This class tests the methods of the EventUserHandler
 *
 */
public class EventUserHandlerTest extends DatabaseTest {
	private EventUserHandler handler;
	
	/**
	 * Initializes the EventUserHandler and sets up the configuration of the database.
	 */
	@Before
	public void setUp() throws Exception {
		handler = new EventUserHandler();
		super.setUp();
	}
	
	/**
	 * Tests the creation of the initial relation of an event. 
	 */
	@Test
	public void testCreateRelation() {
		String userID="1";
		String eventID = "eID6";
		EventUserRelation relation = new EventUserRelation(eventID, userID, true);
		boolean success = handler.createRelation(eventID, userID);
		assertTrue(success);
		assertEquals(relation, session.get(EventUserRelation.class, new EventUserID(eventID, userID)));
		
		
	}
	/**
	 * Tests method joinEvent.
	 */
	@Test
	public void testJoinEvent() {
		String userID1 = "1";
		String ID_alreadyMember = "5";
		String eventID1 = "eID3";
		String eventID2 = "notExistent";
		String notAUser = "xxx";
		
		Event event = handler.joinEvent(eventID1, userID1);
		EventUserRelation relation = new EventUserRelation(eventID1, userID1, false);
		assertEquals(event, session.get(Event.class, eventID1));
		assertEquals(relation, session.get(EventUserRelation.class, new EventUserID(eventID1, userID1)));
		
		event = handler.joinEvent(eventID1, ID_alreadyMember);
		assertEquals(event, session.get(Event.class, eventID1));
		
		event = handler.joinEvent(eventID2, userID1);
		assertNull(event);
		
		event = handler.joinEvent(eventID2,notAUser);
		assertNull(event);
		
		event = handler.joinEvent(eventID1, notAUser);
		assertNull(event);
			
	}
	
	/**
	 * Tests the method leaveEvent.
	 */
	@Test
	public void testLeaveEvent() {
		String eventID1 = "eID1";
		String userID1 = "2";
		//trigger only deletion of the corresponding relation
		assertTrue(handler.leaveEvent(userID1, eventID1));
		assertNull(session.get(EventUserRelation.class, new EventUserID(eventID1, userID1)));
		assertNotNull(session.get(Event.class, eventID1));
		
		//triggers deletion of corresponding relation and event
		String eventID2 = "eID2";
		String userID2 = "3";
		assertTrue(handler.leaveEvent(userID2, eventID2));
		assertNull(session.get(EventUserRelation.class, new EventUserID(eventID2, userID2)));
		assertNull(session.get(Event.class, eventID2));
		
		String notAnEvent = "event";
		String notAUser="xxx";
		assertFalse(handler.leaveEvent(notAUser, eventID1));
		assertFalse(handler.leaveEvent(userID1, notAnEvent));
		
	}
	/**
	 * Tests method isAdmin.
	 */
	@Test
	public void testIsAdmin() {
		String userID = "1";
		String userID2 = "2";
		String eventID = "eID1";
		assertTrue(handler.isAdmin(userID, eventID));
		assertFalse(handler.isAdmin(userID2, eventID));
	}
	
	/**
	 * Tests method isMember.
	 */
	@Test
	public void testIsMember() {
		String userID = "1";
		String userID2 = "5";
		String eventID = "eID1";
		assertTrue(handler.isMember(userID, eventID));
		assertFalse(handler.isMember(userID2, eventID));
	}
	
	/**
	 * Tests method getRelations_byUserID.
	 */
	@Test
	public void testRelations_byUserID() {
		String userID="1";
		EventUserRelation rel1 = new EventUserRelation("eID1",userID,true);
		EventUserRelation rel2 = new EventUserRelation("OneForAll",userID,false);
		List<EventUserRelation> byUserID = handler.getRelations_byuserID(userID);
		assertThat(byUserID, containsInAnyOrder(rel1, rel2));
	}
	
	/**
	 * Tests method getRelations_byEventID.
	 */
	@Test
	public void testRelations_byEventID() {
		String eventID = "OneForAll";
		EventUserRelation rel1 = new EventUserRelation(eventID,"1",false);
		EventUserRelation rel2 = new EventUserRelation(eventID,"2",true);
		EventUserRelation rel3 = new EventUserRelation(eventID,"3",false);
		EventUserRelation rel4 = new EventUserRelation(eventID,"5",false);
		List<EventUserRelation> byEventID = handler.getRelations_byeventID(eventID);
		assertThat(byEventID, containsInAnyOrder(rel1, rel2, rel3, rel4));
	}
	
	/**
	 * Tests method nominateAdmin.
	 */
	@Test
	public void testNominateAdmin() {
		EventUserRelation rel1 = new EventUserRelation("OneForAll","1",false);
		EventUserRelation rel2 = new EventUserRelation("OneForAll","3",false);
		EventUserRelation rel3 = new EventUserRelation("OneForAll","5",false);
		List<EventUserRelation> nomAdmin = new ArrayList<EventUserRelation>();
		nomAdmin.add(rel1);
		nomAdmin.add(rel2);
		nomAdmin.add(rel3);
		
		handler.nominateAdmin(nomAdmin);
		EventUserRelation newAdmin = null;
		
		for(EventUserRelation r : nomAdmin) {
			if(r.isAdmin()) newAdmin = r;
		}
		
		assertTrue(newAdmin.isAdmin());
		assertEquals(newAdmin, session.get(EventUserRelation.class, 
				new EventUserID(newAdmin.getEventID(), newAdmin.getUserID())));
	}
	
	/**
	 * Tests method getAllUserEvents.
	 */
	@Test
	public void testGetAllUserEvents() {
		Event event1 = session.get(Event.class, "eID1");
		Event event2 = session.get(Event.class, "OneForAll");
		String userID ="1";
		List<Event> userEvents = handler.getAllUserEvents(userID);
		assertThat(userEvents, containsInAnyOrder(event1, event2));
		assertTrue(handler.getAllUserEvents("4").isEmpty()); // user is not a member of any event
		
	}
	
	
	/**
	 * Tests method getMembers.
	 */
	@Test
	public void testGetMembers() {
		Map<String, Boolean> members = new HashMap<String, Boolean>();
		//insert all members of event "OneForAll" in the map "members"
		members.put("TestUser1", false);
		members.put("TestUser2", true);
		members.put("TestUser3", false);
		members.put("TestUser5", false);
		String userID = "1";
		String eventID = "OneForAll";
		assertEquals(members, handler.getMembers(userID, eventID));
		
		String notAMember = "xxx";
		assertTrue(handler.getMembers(notAMember, eventID).isEmpty());
	}
	
	@Test
	public void testGetMembersNotificationID() {
		String eventID = "eID1";
		String userID1 = "1";
		List<String> notificationIDs1 = handler.getMembersNotificationID(userID1, eventID);
		assertThat(notificationIDs1, containsInAnyOrder("nID1","nID2"));
		String userID2 = "5"; // not a member of event with id=eventID
		List<String> notificationIDs2 = handler.getMembersNotificationID(userID2, eventID);
		assertNull(notificationIDs2);
	}

}
