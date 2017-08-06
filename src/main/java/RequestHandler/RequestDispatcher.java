package RequestHandler;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;

import RequestHandler.Commands.Command;
import RequestHandler.Commands.CreateEventCommand;
import RequestHandler.Commands.DeleteEventCommand;
import RequestHandler.Commands.DeleteUserCommand;
import RequestHandler.Commands.GetEventsCommand;
import RequestHandler.Commands.GetMembersCommand;
import RequestHandler.Commands.JoinEventCommand;
import RequestHandler.Commands.LeaveEventCommand;
import RequestHandler.Commands.SignUpCommand;
import RequestHandler.Commands.UpdateEventCommand;

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
		
		JsonObject jo = (JsonObject) request.getAttribute("event");
		
		String eventId;
		String title;
		long dateL;
		Date date;
		String location;
		String description;
		try {
			eventId = jo.get("eventid").getAsString();
			title = jo.get("title").getAsString();
			dateL = jo.get("date").getAsLong();
			date = new Date(dateL);
			location = jo.get("location").getAsString();
			description = jo.get("description").getAsString();
		} catch(NullPointerException e) {
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
		
		if (username == null) {
			return null;
		} else {
			return new SignUpCommand(userId, username);
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
		
		HashMap<String,Integer> eventList = (HashMap<String, Integer>) request.getAttribute("eventList");
		
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
		JsonObject jo = (JsonObject) request.getAttribute("event");
		
		String title;
		long dateL;
		Date date;
		String location;
		String description;
		try {
			title = jo.get("title").getAsString();
			dateL = jo.get("date").getAsLong();
			date = new Date(dateL);
			location = jo.get("location").getAsString();
			description = jo.get("description").getAsString();
		} catch(NullPointerException e) {
			return null;
		}
		
		return new CreateEventCommand(userId, title, date, description, location);
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
