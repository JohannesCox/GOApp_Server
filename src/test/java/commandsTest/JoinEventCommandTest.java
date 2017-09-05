package commandsTest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.*;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventUserHandler;
import requestHandler.commands.Command;
import requestHandler.commands.JoinEventCommand;

/**
 * Tests the class JoinEventCommand. It is checked if the response String is correct
 * and if the right commands for adding a user to an event are called. 
 * The behavior of the database access-class is simulated with a mock-object.
 */
public class JoinEventCommandTest {

	private String eventId = "event";
	private String userId1 = "user1";
	private String userId2 = "user2";
	
	private JoinEventCommand testClass1;
	private JoinEventCommand testClass2;
	
	private Event event;
	private String eventname = "event tötle";
	private Date d = new Date();
	private String location = "loc";
	private String description = "descr";
	private int lastModified = 0;
	
	@Before
	public void setUpTest() {
		
		EventUserHandler euh = createMock(EventUserHandler.class);
		expect(euh.joinEvent(eventId, userId1)).andReturn(null);
	
		JsonObject jo = new JsonObject();
		jo.addProperty("eventId", eventId);
		jo.addProperty("title", eventname);
		jo.addProperty("date", d.getTime());
		jo.addProperty("location", location);
		jo.addProperty("description", description);
		jo.addProperty("lastModified", lastModified);
		event = createMock(Event.class);
		expect(event.serialize()).andReturn(jo).anyTimes();
		replay(event);
		
		expect(euh.joinEvent(eventId, userId2)).andReturn(event);
		replay(euh);
		
		testClass1 = new JoinEventCommand(eventId, userId1);
		testClass1.setEventUserHandler(euh);
		
		testClass2 = new JoinEventCommand(eventId, userId2);
		testClass2.setEventUserHandler(euh);
		
	}
	
	@Test
	public void testSuccess() {
		JsonObject jo = event.serialize();
		jo.addProperty(Command.SUCCES_VAR, true);
		
		assertEquals(jo.toString(), testClass2.process());
	}
	
	@Test
	public void testFailure() {
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, false);
		
		assertEquals(jo.toString(), testClass1.process());
	}
}
