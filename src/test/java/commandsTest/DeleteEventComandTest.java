package commandsTest;

import org.junit.*;

import com.google.gson.JsonObject;

import Database.EventHandler;
import Database.EventUserHandler;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import requestHandler.commands.Command;
import requestHandler.commands.DeleteEventCommand;

/**
 * This class includes tests for the class DeleteEventCommand. The behavior of the database access
 * class is simulated with a mock-object.
 */
public class DeleteEventComandTest {

	String eId = "einEvent";
	String uId1 = "user1";
	String uId2 = "user2";
	String uId3 = "user3";
	
	DeleteEventCommand testClass1;
	DeleteEventCommand testClass2;
	DeleteEventCommand testClass3;
	
	@Before
	/*
	 * User1 is an admin and should be able to delete the event. User2 is not an
	 * admin and shouldn´t be able to delete it.
	 */
	public void setUp() {
		EventHandler eh = createMock(EventHandler.class);
		expect(eh.deleteEvent(uId1, eId)).andReturn(true);
		expect(eh.deleteEvent(uId2, eId)).andReturn(false);
		expect(eh.deleteEvent(uId3, eId)).andReturn(false);
		replay(eh);
		
		EventUserHandler euh = createMock(EventUserHandler.class);
		expect(euh.isAdmin(uId1, eId)).andReturn(true);
		expect(euh.isAdmin(uId2, eId)).andReturn(false);
		expect(euh.isAdmin(uId3, eId)).andReturn(true);
		replay(euh);
		
		testClass1 = new DeleteEventCommand(uId1, eId);
		testClass1.setEventHandler(eh);
		testClass1.setEventUserHandler(euh);
		
		testClass2 = new DeleteEventCommand(uId2, eId);
		testClass2.setEventHandler(eh);
		testClass2.setEventUserHandler(euh);
		
		testClass3 = new DeleteEventCommand(uId3, eId);
		testClass3.setEventHandler(eh);
		testClass3.setEventUserHandler(euh);
	}
	
	@Test
	public void adminErrorTest() {
		String result = testClass2.process();
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, false);
		jo.addProperty(Command.ERROR_VAR, Command.ADMIN_ERROR);
		assertEquals(result, jo.toString());
	}
	
	@Test
	public void successTest() {
		String result = testClass1.process();
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, true);
		assertEquals(result, jo.toString());
	}
	
	@Test
	public void intErrorTest() {
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, false);
		jo.addProperty(Command.ERROR_VAR, Command.INT_ERROR);
		
		assertEquals(testClass3.process(), jo.toString());
	}
}
