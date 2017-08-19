package databaseTest;

import org.junit.*;


import Database.*;

/**
 * This class tests the methods of the UserHandler.
 *
 */
public class UserTest extends DatabaseTest {
	
	private UserHandler handler;

	/**
	 *  Initializes the UserHandler and configures the database.
	 */
	@Before
	public void setUp() throws Exception {
		handler = new UserHandler();
		super.setUp();
	}
	/**
	 * Tests the creation of a user.
	 */
	@Test
	public void testAddUser() {
		String userID = "test1";
		String username = "Martin";
		handler.addUser(userID, username);
		User user = session.get(User.class, userID);
		assertNotNull(user);
		assertNull(user.getEmail());
		assertEquals(username,user.getUsername());
	}

	/**
	 * Tests the creation of a user. As additional parameter a mail-address will be set
	 */
	@Test
	public void testAddUserwMail() {
		String userID = "test2";
		String email = "testMail";
		String username = "Made";
		handler.addUser(userID, username, email);
		User user = session.get(User.class, userID);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertEquals(email, user.getEmail());
	}
	
	/**
	 * Tests method deleteUser. The user is not a member of any event.
	 * Therefore just the entry in the table "user" will be deleted.
	 */
	@Test
	public void testDeleteUser_default() {
		String userID = "4";
		if(handler.deleteUser(userID)) {
			assertNull(session.get(User.class, userID));
		}
		String eventID = "eID1";
		String userID2 = "2";
		if(handler.deleteUser(userID2)) {
			assertNull(session.get(EventUserRelation.class, new EventUserID(eventID, userID2)));
			assertNull(session.get(User.class, userID2));
			assertNotNull(session.get(Event.class, eventID));
		}
	}

	/**
	 * Tests the method deleteUser. The deletion of a member might trigger leaveEvent.
	 * The case that after deletion an event has no members left and it therefore will be deleted, 
	 * is covered here.
	 */
	@Test
	public void testDeleteUser_eDeleted() {
		String userID ="3";
		String eventID="eID2";
		if(handler.deleteUser(userID)) {
			assertNull(session.get(User.class, userID));
			assertNull(session.get(EventUserRelation.class, new EventUserID(eventID, userID)));
			assertNull(session.get(Event.class, eventID));	
		}
	}
	
	/**
	 * Tests method deleteUser. The deletion of a user triggers the deletion of all relations of the user.
	 * If the user was an administrator a new one has to be nominated. This case will be checked here. 
	 */
	@Test
	public void testDeleteUser_newAdmin() {
		String eventID= "eID1";
		String userID="1";
		String userID2="2";
		if(handler.deleteUser(userID)) {
			assertNull(session.get(User.class, userID));
			assertNull(session.get(EventUserRelation.class, new EventUserID(eventID,userID)));
			assertNotNull(session.get(Event.class, eventID));
			assertEquals(true, session.get(EventUserRelation.class,new EventUserID(eventID,userID2)).isAdmin());
		
		}
	}



}
