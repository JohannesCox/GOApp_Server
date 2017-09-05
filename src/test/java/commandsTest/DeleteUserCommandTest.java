package commandsTest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import org.junit.*;

import com.google.gson.JsonObject;

import Database.UserHandler;
import requestHandler.commands.Command;
import requestHandler.commands.DeleteUserCommand;

/**
 * Tests the class DeleteUserCommand. It is checked if the response String is correct. 
 * The behavior of the database access class is simulated with a mock-object.
 */
public class DeleteUserCommandTest {

	String userId1 = "user1";
	String userId2 = "user2";
	
	DeleteUserCommand testClass1;
	DeleteUserCommand testClass2;
	
	@Before
	public void setUp() {
		UserHandler uh = createMock(UserHandler.class);
		expect(uh.deleteUser(userId1)).andReturn(true);
		expect(uh.deleteUser(userId2)).andReturn(false);
		replay(uh);
		
		testClass1 = new DeleteUserCommand(userId1);
		testClass2 = new DeleteUserCommand(userId2);
		
		testClass1.setUserHandler(uh);
		testClass2.setUserHandler(uh);
	}
	
	@Test
	public void succesTest() {
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, true);
		
		assertEquals(testClass1.process(), jo.toString());
	}
	
	@Test
	public void failureTest() {
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, false);
		jo.addProperty(Command.ERROR_VAR, Command.INT_ERROR);
		
		assertEquals(testClass2.process(), jo.toString());
	}
}
