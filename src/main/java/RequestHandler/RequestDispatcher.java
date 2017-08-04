package RequestHandler;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is the Command factory. 
 */
public class RequestDispatcher {
	
	private HttpServletRequest request;
	private String userId;
	
	/**
	 * Creates the command belonging to the HttpRequest. If the request is not valdi null is returned.
	 * Otherwise it returns the fitting Request-Handler (Command-Class).
	 * @param request
	 */
	public RequestDispatcher(HttpServletRequest req, String uId) {
		request = req;
		userId = uId;
	}
	
	public Command createHandler() {
		
		String requestString = request.getParameter("request");
		
		if(requestString == null) {
			return null;
		}
		
		switch(requestString) {
		
			case("CreateEvent"):
				return createEventFactory();
			case("DeleteEvent"):
				return deleteEventFactory();
			case("deleteUser"):
				return deleteUserFactory();
			case("getEvents"):
				return getEventsFactory();
			case("getMembers"):
				return getMembersFactory();
			case("joinEvent"):
				return joinEventFactory();
			case("leaveEvent"):
				return leaveEventFactory();
			case("signUp"):
				return signUpFactory();
			case("startEvent"):
				return startEventFactory();
			case("updateEvent"):
				return updateEventFactory();
			default: return null;
		
		}
	}
	
	private Command updateEventFactory() {
		String eventId = request.getParameter("eventId");
		String title = request.getParameter("title");
		Date date = (Date) request.getAttribute("date");
		//TODO maybe String not correct here. maybe two doubles?
		String location = request.getParameter("location");
		String description = request.getParameter("description");
		
		boolean notValidParameters = eventId == null || title == null || date == null || location == null || description == null;
		
		if(notValidParameters) {
			return null;
		}
		
		return new UpdateEventCommand(userId, eventId, title, date, location, description);
	}

	private Command startEventFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	private Command signUpFactory() {
		String username = request.getParameter("username");
		String mailAdresse = request.getParameter("mailAdresse");
		
		if (username == null || mailAdresse == null) {
			return null;
		} else {
			return new SignUpCommand(userId, username, mailAdresse);
		}
	}

	private Command joinEventFactory() {
		String eventId = request.getParameter("eventId");
		
		if (eventId == null) {
			return null;
		} else {
			return new JoinEventCommand(userId, eventId);
		}
	}

	private Command getMembersFactory() {
		String eventId = request.getParameter("eventId");
		
		if (eventId == null) {
			return null;
		} else {
			return new GetMembersCommand(userId, eventId);
		}
	}

	private Command getEventsFactory() {
		
		HashMap<String,Integer> eventList = (HashMap) request.getAttribute("eventList");
		
		if (eventList == null) {
			return null;
		} else {
			return new GetEventsCommand(userId, eventList);
		}
	}

	private Command deleteUserFactory() {
		return new DeleteUserCommand(userId);
	}

	private Command deleteEventFactory() {
		String eventId = request.getParameter("eventId");
		
		if (eventId == null) {
			return null;
		} else {
			return new DeleteEventCommand(userId, eventId);
		}
	}

	private Command createEventFactory() {
		//TODO
		return null;
	}
	
	private Command leaveEventFactory() {
		String eventId = request.getParameter("eventId");
		
		if (eventId == null) {
			return null;
		} else {
			return new LeaveEventCommand(userId, eventId);
		}
	}

}
