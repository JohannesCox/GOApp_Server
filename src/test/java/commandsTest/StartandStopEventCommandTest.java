package commandsTest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.junit.*;

import com.google.gson.JsonObject;

import Database.EventUserHandler;
import requestHandler.commands.Command;
import requestHandler.commands.StartEventCommand;
import requestHandler.commands.StopEventCommand;

/**
 * Tests the class StartEventCommand. It is checked if the response String is correct and if 
 * it is checked if the user is a member of the event before he can start the event. 
 * The behavior of the database access-class is simulated with a mock-object.
 */
public class StartandStopEventCommandTest {
	
	private String userId1 = "user1";
	private String userId2 = "user2";
	private String userId3 = "user3";
	private String eventId = "event";
	
	private DoublePoint dp1;
	private DoublePoint dp2;
	
	private StartEventCommand startTestClass1;
	private StartEventCommand startTestClass2;
	
	private StopEventCommand stopTestClass1;
	private StopEventCommand stopTestClass2;
	
	@Before
	public void setUpTests() {
		
		double[] p1 = {0,0};
		dp1 = new DoublePoint(p1);
		dp2 = new DoublePoint(p1);
		
		EventUserHandler euh = createMock(EventUserHandler.class);
		expect(euh.isMember(userId1, eventId)).andReturn(true);
		expect(euh.isMember(userId2, eventId)).andReturn(false);
		replay(euh);
		
		startTestClass1 = new StartEventCommand(userId1, eventId, dp1);
		startTestClass2 = new StartEventCommand(userId2, eventId, dp2);
		
		startTestClass1.setEventUserHandler(euh);
		startTestClass2.setEventUserHandler(euh);
		
		stopTestClass1 = new StopEventCommand(userId3, eventId);
		stopTestClass2 = new StopEventCommand(userId1, eventId);
		
	}

	@Test
	public void testSuccess() {
		
		/*
		 * It is not necessary to test the ClusteringAlgortihm itself as it is tested
		 * in a different class.
		 */
		
		assertEquals(startTestClass1.process(),"[]");
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, true);
		assertEquals(stopTestClass1.process(), jo.toString());
		
	}
	
	@Test
	public void testFailStart() {
		
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.ERROR_VAR, Command.No_Member_Error);
		assertEquals(startTestClass2.process(), jo.toString());
		
	}
	
	@Test
	public void testFailStop() {
		
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, false);
		assertEquals(jo.toString(), stopTestClass2.process());
	}
}
