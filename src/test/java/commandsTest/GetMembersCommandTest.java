package commandsTest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Database.EventUserHandler;
import requestHandler.commands.GetMembersCommand;

/**
 * Tests the class GetMembersCommand. It is checked if the response String is correct. 
 * The behavior of the database access-class is simulated with a mock-object.
 */
public class GetMembersCommandTest {

	HashMap<String, Boolean> users;
	
	String userId = "user";
	String eventId = "event";
	
	GetMembersCommand testClass;
	
	@Before
	public void setUpTest() {
		
		users = new HashMap<String, Boolean>();
		
		//add some random users to the event
		users.put("1", true);
		users.put("2", false);
		users.put("3", false);
		users.put("4", false);
		users.put("5", false);
		
		EventUserHandler euh = createMock(EventUserHandler.class);
		expect(euh.getMembers(userId, eventId)).andReturn(users);
		replay(euh);
		
		testClass = new GetMembersCommand(userId, eventId);
		testClass.setEventUserHandler(euh);
		
	}
	
	@Test
	public void testProcess() {
		
		JsonArray jaExpected = new JsonArray();
		
		for(String username: users.keySet()) {
			JsonObject jo = new JsonObject();
			jo.addProperty("username", username);
			jo.addProperty("isAdmin", users.get(username));
			jaExpected.add(jo);
		}
		
		assertEquals(jaExpected.toString(), testClass.process());
	}
}
