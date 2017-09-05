package commandsTest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.google.gson.JsonObject;

import Database.UserHandler;
import requestHandler.commands.Command;
import requestHandler.commands.SignUpCommand;

public class SignUpCommandTest {

	private String userId = "user";
	private String username = "name";
	
	private SignUpCommand testClass;
	
	@Before
	public void setUpTest() {
		UserHandler uh = createMock(UserHandler.class);
		
		expect(uh.addUser(userId, username)).andReturn(true);
		replay(uh);
		
		testClass = new SignUpCommand(userId, username);
		testClass.setUserHandler(uh);
	}
	
	@Test
	public void testProcess() {
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, true);
		
		assertEquals(jo.toString(), testClass.process());
	}
}
