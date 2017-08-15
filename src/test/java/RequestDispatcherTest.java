import org.junit.*;

import RequestHandler.RequestDispatcher;
import RequestHandler.Commands.Command;
import RequestHandler.Commands.UpdateEventCommand;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 *This class is testing the public methods of the class "RequestDispatcher".
 * Depending on the command which should created, it is tested if the command was created and
 * if the behavior of the command is correct.
 */
public class RequestDispatcherTest {

	public final String USER1 = "TestUser1";
	public final String EVENTID = "qAs12AdfA12341";
	
	@Test
	public void updateEventFactoryTest() {
		
		String title = "TestEvent";
		long date = 856648800000L;
		String location = "Testlocation";
		String description = "Some description text";
		
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("event")).andReturn("{\"eventId\":\"" + EVENTID + "\",\"title\":\"" + title + "\",  \"date\":" + date + " ,"
				+ " \"location\": \"" + location + "\", \"description\": \"" + description + "\"}").atLeastOnce();
		expect(req.getParameter("request")).andReturn("updateEvent").atLeastOnce();
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		Command ce2 = new UpdateEventCommand(USER1, EVENTID, title, new Date(date), location, description);
		
		assertEquals(ce1.process(), ce2.process());
	}
	
	@Test
	public void startEventFactoryTest() {
		double[] gps = {1,0};
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("eventId")).andReturn(EVENTID);
		expect(req.getParameter("lat")).andReturn(gps[0] + "");
		expect(req.getParameter("lng")).andReturn(gps[1] + "");
		expect(req.getParameter("request")).andReturn("startEvent");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);	
	}
	
	@Test
	public void stopEventFactoryTest() {
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("eventId")).andReturn(EVENTID);
		expect(req.getParameter("request")).andReturn("stopEvent");
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
	}
}
