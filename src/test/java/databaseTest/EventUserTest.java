package databaseTest;

import org.junit.*;

import Database.*;
import Database.EventUserHandler;

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
		
	}
	
	@Test
	public void testIsMember() {
		
	}
	
	@Test
	public void testRelations_byUserID() {
		
	}
	
	@Test
	public void testRelations_byEventID() {
		
	}
	
	@Test
	public void testNominateAdmin() {
		
	}
	
	@Test
	public void testGetAllUserEvents() {
		
	}
	
	@Test
	public void testGetMembers() {
		
	}

}
