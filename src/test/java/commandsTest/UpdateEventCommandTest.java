package commandsTest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

import org.junit.*;

import java.util.Date;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventHandler;
import requestHandler.commands.UpdateEventCommand;

/**
 * Tests the class UpdateEventCommand. It is checked if the right commands for updating 
 * an event are made in if the response String is correct. The behavior of the database access
 * classes is simulated with mock-objects.
 */
public class UpdateEventCommandTest {

private EventHandler eh;
	
	private String userId = "user1";
	private String eventname = "event tötle";
	private Date d = new Date();
	private String location = "loc";
	private String description = "descr";
	private String eventId = "123as";
	private int lastModified = 0;
	JsonObject jo;
	UpdateEventCommand testClass;
	
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
		expect(eh.updateEvent(userId, eventId, eventname, d, location, description))
		.andReturn(event);
		replay(eh);
		testClass = new UpdateEventCommand(userId, eventId, eventname, d,
				location, description);
		testClass.setEventHandler(eh);
		
		jo.addProperty("successful", true);
	}
	
	@Test
	public void testProcess() {
		
		String result = testClass.process();
		
		assertEquals(result, jo.toString());
		
	}
}
