package commandsTest;

import org.junit.*;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventHandler;
import requestHandler.commands.Command;
import requestHandler.commands.CreateEventCommand;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Date;

/**
 * Tests the class CreateEventCommand. It is checked if the right commands for creating 
 * an event are made in if the response String is correct. The behavior of the database access
 * class is simulated with a mock-object.
 */
public class CreateEventCommandTest {

	private EventHandler eh;
	
	private String userId1 = "user1";
	private String userId2 = "user2";
	private String eventname = "event tötle";
	private Date d = new Date();
	private String location = "loc";
	private String description = "descr";
	private String eventId = "123as";
	private int lastModified = 0;
	JsonObject jo;
	CreateEventCommand testClass1;
	CreateEventCommand testClass2;
	
	@Before
	public void setUp() {
		
		eh = createMock(EventHandler.class);
		jo = new JsonObject();
		jo.addProperty("eventId", eventId);
		jo.addProperty("title", eventname);
		jo.addProperty("date", d.getTime());
		jo.addProperty("location", location);
		jo.addProperty("description", description);
		jo.addProperty("lastModified", lastModified);
		Event event = createMock(Event.class);
		expect(event.serialize()).andReturn(jo);
		replay(event);
		expect(eh.createEvent(userId1, eventname, d, location, description)).andReturn(event);
		expect(eh.createEvent(userId2, eventname, d, location, description)).andReturn(null);
		replay(eh);
		testClass1 = new CreateEventCommand(userId1, eventname, d, location, description);
		testClass1.setEventHandler(eh);
		
		testClass2 = new CreateEventCommand(userId2, eventname, d, location, description);
		testClass2.setEventHandler(eh);
		
		jo.addProperty(Command.SUCCES_VAR, true);
	}
	
	@Test
	public void testProcessSuccess() {
		
		String result = testClass1.process();
		assertEquals(result, jo.toString());

	}
	
	@Test
	public void testProcessFail() {
		JsonObject res = new JsonObject();
		res.addProperty(Command.SUCCES_VAR, false);
		res.addProperty(Command.ERROR_VAR, Command.INT_ERROR);
		
		assertEquals(res.toString(), testClass2.process());
	}
}
