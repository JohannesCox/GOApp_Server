package databaseTest;

import java.util.Date;

import org.junit.*;

import Database.*;
/**
 * This class tests the methods of the EventHandler
 *
 */
public class EventTest extends DatabaseTest {
	private EventHandler handler;
	
	/**
	 * Initializes the EventHandler and sets up the configuration of the database
	 */
	@Before
	public void setUp() throws Exception {
		handler = new EventHandler();
		super.setUp();
	}
	
	/**
	 * Tests the creation of an event.
	 */
	@Test
	public void testCreateEvent() {
		String userID = "1";
		String eventname = "test";
		Date date = new Date();
		String location = "12.123,31.1231";
		String description = "test";
		
		Event event = handler.createEvent(userID, eventname, date, location, description);
		String eventID = event.getEventID();
		
		assertNotNull(event);
		assertEquals(eventname, event.getEventname());
		assertEquals(date, event.getDate());
		assertEquals(location, event.getLocation());
		assertEquals(description, event.getDescription());
		assertNull(event.getPicture());
		assertEquals(event, session.get(Event.class, eventID));
		
		Event received = session.get(Event.class, eventID);
		EventUserRelation relation = session.get(EventUserRelation.class, new EventUserID(eventID, userID));
		
		assertEquals(event, received);
		assertNotNull(relation);
		
		
	}
	
	/**
	 * Tests the creation of an event. As additional parameter a picture will be set.
	 */
	@Test
	public void testCreateEventwPicture() {
		String userID = "1";
		String eventname = "test";
		Date date = new Date();
		String location = "12.123,31.1231";
		String description = "test";
		String picture = "testpicture";
		
		Event event = handler.createEvent(userID, eventname, date, location, description, picture);
		String eventID = event.getEventID();
		
		assertNotNull(event);
		assertEquals(eventname, event.getEventname());
		assertEquals(date, event.getDate());
		assertEquals(location, event.getLocation());
		assertEquals(description, event.getDescription());
		assertEquals(picture, event.getPicture());
		assertEquals(0,event.getLastmodified());
		assertEquals(event, session.get(Event.class, eventID));
		
		Event received = session.get(Event.class, eventID);
		EventUserRelation relation = session.get(EventUserRelation.class, new EventUserID(eventID, userID));
		
		assertEquals(event, received);
		assertNotNull(relation);	
	}
	
	/**
	 * Tests whether a picture can be stored
	 */
	@Test
	public void test_storePicture() {
		String userID = "1";
		String eventID = "eID1";
		String picture = "testPicture";
		boolean success = handler.storePicture(userID, eventID, picture);
		assertTrue(success);
		assertEquals(picture, session.get(Event.class, eventID).getPicture());
		
		String eventID2 = "eID2"; //user is not a member of this event
		success = handler.storePicture(userID, eventID2, picture);
		assertFalse(success);
		assertNull(session.get(Event.class, eventID2).getPicture());
	}
	
	/**
	 * Tests whether a valid picture can be returned.
	 */
	@Test
	public void test_getPicture() {
		String userID_member = "5";
		String userID_notAMember = "1";
		String eventID ="eID3";
		String notExist = "...";
		String picture = "testPicture";
		assertEquals(picture, handler.getPicture(userID_member, eventID));
		assertNull(handler.getPicture(userID_notAMember, eventID));
		assertNull(handler.getPicture(userID_member, notExist));
	}
	
	/**
	 * Tests updating an event
	 */
	@Test
	public void testUpdateEvent() {
		String userID = "1";
		String eventID = "eID1";
		String eventname = "TestEvent1";
		Date date = new Date();
		String location = "12.121,1.12";
		String description = "bla";
		
		Event newParam = new Event(eventID, date, location, description);
		assertFalse(newParam.equals(session.get(Event.class, eventID)));
		
		Event event = handler.updateEvent(userID, eventID, eventname, date, location, description);
		assertEquals(1, event.getLastmodified());
		
	}
	/**
	 * Tests the deletion of an event
	 */
	@Test
	public void deleteEvent() {
		String userID1 = "1";
		String userID2 = "2";
		String eventID = "eID1";
		boolean success = handler.deleteEvent(userID2, eventID);
		assertFalse(success);
		assertNotNull(session.get(Event.class, eventID));
		
		success = handler.deleteEvent(userID1, eventID);
		assertTrue(success);
		assertNull(session.get(Event.class, eventID));
		assertNull(session.get(EventUserRelation.class, new EventUserID(eventID, userID1)));
		assertNull(session.get(EventUserRelation.class, new EventUserID(eventID, "2")));
		
		
	}
}
