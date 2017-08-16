import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.junit.*;

import RequestHandler.RequestDispatcher;
import RequestHandler.Commands.Command;
import RequestHandler.Commands.CreateEventCommand;
import RequestHandler.Commands.DeleteEventCommand;
import RequestHandler.Commands.DeleteUserCommand;
import RequestHandler.Commands.GetEventsCommand;
import RequestHandler.Commands.GetMembersCommand;
import RequestHandler.Commands.JoinEventCommand;
import RequestHandler.Commands.LeaveEventCommand;
import RequestHandler.Commands.SignUpCommand;
import RequestHandler.Commands.StartEventCommand;
import RequestHandler.Commands.StopEventCommand;
import RequestHandler.Commands.UpdateEventCommand;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is testing the public methods of the class "RequestDispatcher".
 * Depending on the command which should be created, it is tested if the right instance of the command was created 
 * and if the state of the command is correct.
 */
public class RequestDispatcherTest {

	public final String USER1 = "TestUser1";
	public final String EVENTID = "qAs12AdfA12341";
	
	@Test
	public void updateEventFactoryTest() {
		
		String title = "TestEvent";
		long date = 856648800000L;
		String location = "Testlocation";
		String description = "Some description text";
		
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("event")).andReturn("{\"eventId\":\"" + EVENTID + "\",\"title\":\"" + title + "\",  \"date\":" + date + 
				" ,\"location\": \"" + location + "\", \"description\": \"" + description + "\"}").atLeastOnce();
		expect(req.getParameter("request")).andReturn("updateEvent").atLeastOnce();
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		assertTrue(ce1 instanceof UpdateEventCommand);
		
		UpdateEventCommand ce2 = (UpdateEventCommand) ce1;
		
		assertEquals(ce2.getDate() , new Date(date));
		assertEquals(ce2.getDescription(), description);
		assertEquals(ce2.getEventId(), EVENTID);
		assertEquals(ce2.getLocation(), location);
		assertEquals(ce2.getTitle(), title);
		assertEquals(ce2.getUserId(), USER1);
	}
	
	@Test
	public void startEventFactoryTest() {
		
		double[] gps = {1,0};
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("eventId")).andReturn(EVENTID);
		expect(req.getParameter("lat")).andReturn(gps[0] + "");
		expect(req.getParameter("lng")).andReturn(gps[1] + "");
		expect(req.getParameter("request")).andReturn("startEvent");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);	
		
		assertTrue(ce1 instanceof StartEventCommand);
		
		StartEventCommand ce2 = (StartEventCommand) ce1;
		
		assertEquals(ce2.getDoublepoint(), new DoublePoint(gps));
		assertEquals(ce2.getEventId(), EVENTID);
		assertEquals(ce2.getUserId(), USER1);
		
	}
	
	@Test
	public void stopEventFactoryTest() {
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("eventId")).andReturn(EVENTID);
		expect(req.getParameter("request")).andReturn("stopEvent");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		assertTrue(ce1 instanceof StopEventCommand);
		
		StopEventCommand ce2 = (StopEventCommand) ce1;
		
		assertEquals(ce2.getEventId(), EVENTID);
		assertEquals(ce2.getUserId(), USER1);
		
	}
	
	@Test
	public void signUpFactoryTest() {
		
		String username = "MaxMustermann";
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("username")).andReturn(username);
		expect(req.getParameter("request")).andReturn("signUp");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		assertTrue(ce1 instanceof SignUpCommand);
		
		SignUpCommand ce2 = (SignUpCommand) ce1;
		
		assertEquals(ce2.getUsername(), username);
		assertEquals(ce2.getUserId(), USER1);
	}
	
	@Test
	public void joinEventFactoryTest() {

		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("eventId")).andReturn(EVENTID);
		expect(req.getParameter("request")).andReturn("joinEvent");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		assertTrue(ce1 instanceof JoinEventCommand);
		
		JoinEventCommand ce2 = (JoinEventCommand) ce1;
		
		assertEquals(ce2.getEventId(), EVENTID);
		assertEquals(ce2.getUserId(), USER1);
		
	}
	
	@Test
	public void getMembersFactoryTest() {
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("eventId")).andReturn(EVENTID);
		expect(req.getParameter("request")).andReturn("getMembers");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		assertTrue(ce1 instanceof GetMembersCommand);
		
		GetMembersCommand ce2 = (GetMembersCommand) ce1;
		
		assertEquals(ce2.getEventId(), EVENTID);
		assertEquals(ce2.getUserId(), USER1);
	}
	
	@Test 
	public void getEventsFactoryTest() {
		
		String eventId1 = "event1";
		String eventId2 = "event2";
		int lastMod1 = 0;
		int lastMod2 = 2;
		HashMap<String, Integer> el = new HashMap<String, Integer>();
		el.put(eventId1, lastMod1);
		el.put(eventId2, lastMod2);
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("request")).andReturn("getEvents");
		expect(req.getParameter("event")).andReturn("[{\"eventId\":\"" + eventId1 + "\", \"lastModified\":" + lastMod1 +
				"}, {\"eventId\":\"" + eventId2 + "\", \"lastModified\":" + lastMod2 + "}]");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		assertTrue(ce1 instanceof GetEventsCommand);
		
		GetEventsCommand ce2 = (GetEventsCommand) ce1;
		
		assertEquals(ce2.getEventsList(), el);
		assertEquals(ce2.getUserId(), USER1);
	}
	
	@Test
	public void deleteUserFactoryTest() {
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("request")).andReturn("deleteUser");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		assertTrue(ce1 instanceof DeleteUserCommand);
		
		DeleteUserCommand ce2 = (DeleteUserCommand) ce1;
		
		assertEquals(ce2.getUserId(), USER1);
	}
	
	@Test
	public void deleteEventFactoryTest() {
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("eventId")).andReturn(EVENTID);
		expect(req.getParameter("request")).andReturn("deleteEvent");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		assertTrue(ce1 instanceof DeleteEventCommand);
		
		DeleteEventCommand ce2 = (DeleteEventCommand) ce1;
		
		assertEquals(ce2.getEventId(), EVENTID);
		assertEquals(ce2.getUserId(), USER1);
	}
	
	@Test
	public void createEventFactoryTest() {
		
		String title = "NewTitle";
		long date = 856648801110L;
		String location = "NewLoc";
		String description = "Some new description text";
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("event")).andReturn("{\"title\":\"" + title + "\",  \"date\":" + date + 
				" ,\"location\": \"" + location + "\", \"description\": \"" + description + "\"}");
		expect(req.getParameter("request")).andReturn("createEvent");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		assertTrue(ce1 instanceof CreateEventCommand);
		
		CreateEventCommand ce2 = (CreateEventCommand) ce1;
		
		assertEquals(ce2.getDate() , new Date(date));
		assertEquals(ce2.getDescription(), description);
		assertEquals(ce2.getLocation(), location);
		assertEquals(ce2.getTitle(), title);
		assertEquals(ce2.getUserId(), USER1);
			
	}
	
	@Test
	public void leaveEventCommand() {
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("eventId")).andReturn(EVENTID);
		expect(req.getParameter("request")).andReturn("leaveEvent");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		assertTrue(ce1 instanceof LeaveEventCommand);
		
		LeaveEventCommand ce2 = (LeaveEventCommand) ce1;
		
		assertEquals(ce2.getEventId(), EVENTID);
		assertEquals(ce2.getUserId(), USER1);
		
	}
}
