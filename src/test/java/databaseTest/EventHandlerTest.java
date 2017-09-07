package databaseTest;

import java.util.Date;

import org.junit.*;

import Database.*;
/**
 * This class tests the methods of the EventHandler
 *
 */
public class EventHandlerTest extends DatabaseTest {
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
		String notAUser = "xxx";
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
		
		assertNull(handler.createEvent(notAUser, eventname, date, location, description));
		
		
	}
	
	/**
	 * Tests the creation of an event. As additional parameter a picture will be set.
	 */
	@Test
	public void testCreateEventwPicture() {
		String userID = "1";
		String notAUser = "xxx";
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
		
		assertNull(handler.createEvent(notAUser, eventname, date, location, description, picture));
	}
	
	/**
	 * Tests whether a picture can be stored
	 */
	@Test
	public void test_storePicture() {
		String userID1 = "1";
		String userID2 = "2";//admin
		String eventID = "eID1";
		String notAnEvent = "event";
		String picture = "testPicture";
		boolean success = handler.storePicture(userID1, eventID, picture);
		assertTrue(success);
		assertEquals(picture, session.get(Event.class, eventID).getPicture());
		
		String eventID2 = "eID2"; //user is not a member of this event
		success = handler.storePicture(userID1, eventID2, picture);
		assertFalse(success);
		assertNull(session.get(Event.class, eventID2).getPicture());
		
		success = handler.storePicture(userID2, eventID, picture);
		assertFalse(success);
		
		success = handler.storePicture(userID1, notAnEvent, picture);
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
		String regularMember = "2";
		String eventID = "eID1";
		String eventname = "TestEvent1";
		Date date = new Date();
		String location = "12.121,1.12";
		String description = "bla";
		
		Event newParam = new Event(eventID, date, location, description);
		assertFalse(newParam.equals(session.get(Event.class, eventID)));
		
		Event event = handler.updateEvent(userID, eventID, eventname, date, location, description);
		assertEquals(1, event.getLastmodified());
		
		assertNull(handler.updateEvent(regularMember, eventID, eventname, date, location, description));
		
	}
	/**
	 * Tests the deletion of an event
	 */
	@Test
	public void testDeleteEvent() {
		String userID1 = "1"; //admin of the event
		String userID2 = "2"; // not an admin of the event
		String eventID = "eID1";
		String notAnEvent = "notAnEvent";
		boolean success = handler.deleteEvent(userID2, eventID);
		assertFalse(success);
		assertNotNull(handler.getEvent(eventID));
		
		success = handler.deleteEvent(userID1, eventID); 
		assertTrue(success);
		assertNull(handler.getEvent(eventID));
		assertNull(handler.getRelation(eventID, userID1));
		assertNull(handler.getRelation(eventID, userID2));
		
		success = handler.deleteEvent(userID1, notAnEvent);
		assertFalse(success);
		assertNull(handler.getEvent(notAnEvent));
		
		
	}
}
