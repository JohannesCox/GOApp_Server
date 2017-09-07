package commandsTest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.google.gson.JsonObject;

import Database.EventUserHandler;
import requestHandler.commands.Command;
import requestHandler.commands.IsEventAdminCommand;

public class IsEventAdminCommandTest {

	private String userId1 = "user1";
	private String userId2 = "user2";
	private String eventId = "event";
	
	private IsEventAdminCommand testClass1;
	private IsEventAdminCommand testClass2;
	
	@Before
	public void setUpTests() {
		
		EventUserHandler euh = createMock(EventUserHandler.class);
		expect(euh.isAdmin(userId1, eventId)).andReturn(true);
		expect(euh.isAdmin(userId2, eventId)).andReturn(false);
		replay(euh);
		
		testClass1 = new IsEventAdminCommand(userId1, eventId);
		testClass2 = new IsEventAdminCommand(userId2, eventId);
		
		testClass1.setEuh(euh);
		testClass2.setEuh(euh);
	}
	
	@Test
	public void testisAdminProcess() {
		
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, true);
		jo.addProperty(Command.ISADMIN, true);
		assertEquals(jo.toString(), testClass1.process());
		
	}
	
	@Test
	public void testNoAdminProcess() {
		
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, true);
		jo.addProperty(Command.ISADMIN, false);
		assertEquals(jo.toString(), testClass2.process());
		
	}
}
