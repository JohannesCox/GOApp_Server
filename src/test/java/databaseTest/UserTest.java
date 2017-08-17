package databaseTest;

import org.junit.*;


import Database.*;

public class UserTest extends DatabaseTest {
private UserHandler handler;
@Before
public void setUp() {
	handler = new UserHandler();
}
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
@Test
public void testDeleteUser_eDeleted() {
	String userID ="3";
	String eventID="eID2";
	if(handler.deleteUser(userID)) {
		assertNull(session.get(EventUserRelation.class, new EventUserID(eventID, userID)));
		assertNull(session.get(Event.class, eventID));
	}
}
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
