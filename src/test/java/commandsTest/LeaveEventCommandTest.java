package commandsTest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.google.gson.JsonObject;

import Database.EventHandler;
import Database.EventUserHandler;
import requestHandler.commands.Command;
import requestHandler.commands.LeaveEventCommand;

public class LeaveEventCommandTest {

	private String userId = "user";
	private String eventId = "event";
	
	private LeaveEventCommand testClass;
	
	@Before
	public void setUpTest() {
		EventUserHandler euh = createMock(EventUserHandler.class);
		
		expect(euh.leaveEvent(userId, eventId)).andReturn(true);
		replay(euh);
		
		testClass = new LeaveEventCommand(userId, eventId);
		testClass.setEventUserHandler(euh);
	}
	
	@Test
	public void testProcess() {
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, true);
		
		assertEquals(jo.toString(), testClass.process());
	}
}
