import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.junit.*;

import RequestHandler.RequestDispatcher;
import RequestHandler.Commands.Command;
import RequestHandler.Commands.CreateEventCommand;
import RequestHandler.Commands.StartEventCommand;
import RequestHandler.Commands.StopEventCommand;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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
		
		Command ce2 = new StopEventCommand(USER1, EVENTID);
		
		assertEquals(ce1.process(), ce2.process());
	}
}
