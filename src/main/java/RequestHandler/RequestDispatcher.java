package RequestHandler;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.math3.ml.clustering.DoublePoint;

import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
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
import RequestHandler.Commands.StartEventCommand;
import RequestHandler.Commands.StopEventCommand;
import RequestHandler.Commands.UpdateEventCommand;

/**
 * This class is the Command factory. 
 */
public class RequestDispatcher {
	
	private final String EVENT_ID = "eventId";
	private final String EVENT = "event";
	private final String USERNAME = "username";
	private final String REQUEST = "request";
	private final String TITLE = "title";
	private final String DATE = "date";
	private final String LOCATION = "location";
	private final String DESCRIPTION = "description";
	private final String LASTMODIFIED = "lastModified";
	
	private HttpServletRequest request;
	private String userId;
	
	/**
	 * Creates the command belonging to the HttpRequest. If the request is not valid null is returned.
	 * Otherwise it returns the fitting Request-Handler (Command-Class).
	 * @param request
	 */
	public RequestDispatcher(HttpServletRequest req, String uId) {
		request = req;
		userId = uId;
	}
	
	public Command createHandler() {
		
		String requestString = request.getParameter(REQUEST);
		
		if(requestString == null) {
			return null;
		}
		
		switch(requestString) {
		
			case("createEvent"):
				return createEventFactory();
			case("deleteEvent"):
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
			case("stopEvent"):
				return stopEventFactory();
			default: return null;
		
		}
	}
	
	private Command updateEventFactory() {
		
		String eventS = request.getParameter(EVENT);
		
		JsonParser jp = new JsonParser();
		JsonObject jo = (JsonObject) jp.parse(eventS);
		
		String eventId;
		String title;
		long dateL;
		Date date;
		String location;
		String description;
		try {
			eventId = jo.get(EVENT_ID).getAsString();
			title = jo.get(TITLE).getAsString();
			dateL = jo.get(DATE).getAsLong();
			date = new Date(dateL);
			location = jo.get(LOCATION).getAsString();
			description = jo.get(DESCRIPTION).getAsString();
		} catch(NullPointerException e) {
			return null;
		}
		
		return new UpdateEventCommand(userId, eventId, title, date, location, description);
		
	}

	private Command startEventFactory() {
		
		String eventId = request.getParameter(EVENT_ID);
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		
		if (eventId == null || lat == null || lng == null) {
			return null;
		}
		
		double latD,lngD;
		
		try {
			latD =  Double.parseDouble(lat);
			lngD = Double.parseDouble(lng);
		} catch (NumberFormatException e) {
			return null;
		}
		
		double[] ds = {latD, lngD};
		
		DoublePoint dp = new DoublePoint(ds);
		
		return new StartEventCommand(userId, eventId, dp);
	}
	
	private Command stopEventFactory() {
		String eventId = request.getParameter(EVENT_ID);
		
		if (eventId == null) {
			return null;
		} else {
			return new StopEventCommand(userId, eventId);
		}
	}

	private Command signUpFactory() {
		String username = request.getParameter(USERNAME);
		
		if (username == null) {
			return null;
		} else {
			return new SignUpCommand(userId, username);
		}
	}

	private Command joinEventFactory() {
		String eventId = request.getParameter(EVENT_ID);
		
		if (eventId == null) {
			return null;
		} else {
			return new JoinEventCommand(userId, eventId);
		}
	}

	private Command getMembersFactory() {
		String eventId = request.getParameter(EVENT_ID);
		
		if (eventId == null) {
			return null;
		} else {
			return new GetMembersCommand(userId, eventId);
		}
	}

	private Command getEventsFactory() {
		
		String eventS = request.getParameter(EVENT);
		
		JsonParser jp = new JsonParser();
		JsonArray ja = (JsonArray) jp.parse(eventS);
		
		HashMap<String,Integer> eventList = new HashMap<String,Integer>();
		
		for (int i = 0; i < ja.size(); i++) {
		    JsonObject rec = (JsonObject) ja.get(i);
		    eventList.put(rec.get(EVENT_ID).getAsString(), rec.get(LASTMODIFIED).getAsInt());
		}
		
		return new GetEventsCommand(userId, eventList);

	}

	private Command deleteUserFactory() {
		return new DeleteUserCommand(userId);
	}

	private Command deleteEventFactory() {
		String eventId = request.getParameter(EVENT_ID);
		
		if (eventId == null) {
			return null;
		} else {
			return new DeleteEventCommand(userId, eventId);
		}
	}

	private Command createEventFactory() {
		
		String eventS = request.getParameter(EVENT);
		
		JsonParser jp = new JsonParser();
		JsonObject jo = (JsonObject) jp.parse(eventS);
		
		String title;
		long dateL;
		Date date;
		String location;
		String description;
		try {
			title = jo.get(TITLE).getAsString();
			dateL = jo.get(DATE).getAsLong();
			date = new Date(dateL);
			location = jo.get(LOCATION).getAsString();
			description = jo.get(DESCRIPTION).getAsString();
		} catch(NullPointerException e) {
			return null;
		}
		
		return new CreateEventCommand(userId, title, date, location, description);
	}
	
	private Command leaveEventFactory() {
		String eventId = request.getParameter(EVENT_ID);
		
		if (eventId == null) {
			return null;
		} else {
			return new LeaveEventCommand(userId, eventId);
		}
	}

}
