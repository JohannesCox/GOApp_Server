package databaseTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.*;

import Database.*;

public class EventUserTest extends DatabaseTest {
	private EventUserHandler handler;
	
	@Before
	public void setUp() throws Exception {
		handler = new EventUserHandler();
		super.setUp();
	}
	
	@Test
	public void testCreateRelation() {
		String userID="1";
		String eventID = "eID6";
		EventUserRelation relation = new EventUserRelation(eventID, userID, true);
		if(handler.createRelation(eventID, userID)) {
			assertEquals(relation, session.get(EventUserRelation.class, new EventUserID(eventID, userID)));
		} else {
			assertNull(session.get(EventUserRelation.class, new EventUserID(eventID, userID)));
		}
	}
	
	@Test
	public void testJoinEvent() {
		String userID = "1";
		String eventID = "eID3";
		Event event = handler.joinEvent(eventID, userID);
		EventUserRelation relation = new EventUserRelation(eventID, userID, false);
		assertEquals(event, session.get(Event.class, eventID));
		assertEquals(relation, session.get(EventUserRelation.class, new EventUserID(eventID, userID)));
	}
	
	@Test
	public void testLeaveEvent() {
		
	}
	
	@Test
	public void testIsAdmin() {
		String userID = "1";
		String userID2 = "2";
		String eventID = "eID1";
		assertTrue(handler.isAdmin(userID, eventID));
		assertFalse(handler.isAdmin(userID2, eventID));
	}
	
	@Test
	public void testIsMember() {
		String userID = "1";
		String userID2 = "5";
		String eventID = "eID1";
		assertTrue(handler.isMember(userID, eventID));
		assertFalse(handler.isMember(userID2, eventID));
	}
	
	@Test
	public void testRelations_byUserID() {
		String userID="1";
		List<EventUserRelation> reference = new ArrayList<EventUserRelation>();
		reference.add(new EventUserRelation("eID1",userID,true));
		reference.add(new EventUserRelation("OneForAll",userID,false));
		assertEquals(reference, handler.getRelations_byuserID(userID));
	}
	
	@Test
	public void testRelations_byEventID() {
		String eventID = "OneForAll";
		List<EventUserRelation> reference = new ArrayList<EventUserRelation>();
		reference.add(new EventUserRelation(eventID,"1",false));
		reference.add(new EventUserRelation(eventID,"2",true));
		reference.add(new EventUserRelation(eventID,"3",false));
		reference.add(new EventUserRelation(eventID,"5",false));
		assertEquals(reference, handler.getRelations_byeventID(eventID));
	}
	
	
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
	
	@Test
	public void testGetAllUserEvents() {
		List<Event> reference = new ArrayList<Event>();
		Event event1 = session.get(Event.class, "eID1");
		Event event2 = session.get(Event.class, "OneForAll");
		reference.add(event1);
		reference.add(event2);
		String userID ="1";
		
		assertEquals(reference, handler.getAllUserEvents(userID));
		assertTrue(handler.getAllUserEvents("4").isEmpty()); // user is not a member of any event
		
	}
	
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
	}

}
