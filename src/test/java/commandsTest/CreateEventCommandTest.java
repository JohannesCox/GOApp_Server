package commandsTest;

import org.junit.*;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventHandler;
import requestHandler.commands.CreateEventCommand;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Date;

public class CreateEventCommandTest {

	private EventHandler eh;
	
	private String userId = "user1";
	private String eventname = "event title";
	private Date d = new Date();
	private String location = "loc";
	private String description = "descr";
	private String eventId = "123as";
	private int lastModified = 0;
	JsonObject jo;
	CreateEventCommand toTestCEC;
	
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
		expect(eh.createEvent(userId, eventname, d, location, description)).andReturn(event);
		replay(eh);
		toTestCEC = new CreateEventCommand(userId, eventname, d, location, description);
		toTestCEC.setEventHandler(eh);
		
		jo.addProperty("successful", true);
	}
	
	@Test
	public void testProcess() {
		
		String result = toTestCEC.process();
		
		assertEquals(result, jo.toString());
		
	}
}
