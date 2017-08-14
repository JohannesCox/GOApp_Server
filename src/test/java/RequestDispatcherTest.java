import org.junit.*;

import RequestHandler.RequestDispatcher;
import RequestHandler.Commands.Command;
import RequestHandler.Commands.CreateEventCommand;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class RequestDispatcherTest {

	public final String USER1 = "TestUser1";
	
	
	@Test
	public void updateEventFactoryTest() {
		
		String title = "TestEvent";
		long date = 856648800000L;
		String location = "Testlocation";
		String description = "Some description text";
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getParameter("event")).andReturn("{\"title\":\"" + title + "\",  \"date\":" + date + " ,"
				+ " \"location\": \"" + location + "\", \"description\": \"" + description + "\"}").atLeastOnce();
		expect(req.getParameter("request")).andReturn("createEvent").atLeastOnce();
		replay(req);
		
		RequestDispatcher rd = new RequestDispatcher(req, USER1);
		Command ce1 = rd.createHandler();
		
		assertNotNull(ce1);
		
		Command ce2 = new CreateEventCommand(USER1, title, new Date(date), location, description);
		
		assertEquals(ce1, ce2);
	}
}
