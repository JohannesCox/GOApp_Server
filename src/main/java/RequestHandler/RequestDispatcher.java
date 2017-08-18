package RequestHandler;

import java.util.Date;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.math3.ml.clustering.DoublePoint;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import RequestHandler.Commands.*;

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
	private final String IMAGE = "image";
	
	private HttpServletRequest request;
	private String userId;
	
	/**
	 * Creates a new RequestDispatcher with a HttpRequest and an userId.
	 * @param req The HttpRequest which should be dispatched.
	 * @param uId The UserId of the user who sent the request.
	 */
	public RequestDispatcher(HttpServletRequest req, String uId) {
		request = req;
		userId = uId;
	}
	
	/**
	 * Creates the command belonging to the HttpRequest saved in the class during creation. If the request is not valid null
	 *  is returned. Otherwise it returns the fitting Request-Handler (Command-Class).
	 * @return Type Command if the request was valid, otherwise null.
	 */
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
			case("uploadEventImage"):
				return uploadEventImageFactory();
			case("downloadEventImage"):
				return downloadEventImageFactory();
			default: return null;
		
		}
	}
	
	private Command downloadEventImageFactory() {
		String eventId = request.getParameter(EVENT_ID);
		
		if(eventId == null) {
			return null;
		} else {
			return (new DownloadEventImageCommand(userId, eventId));
		}
	}

	private Command uploadEventImageFactory() {
		String eventId = request.getParameter(EVENT_ID);
		String image = request.getParameter(IMAGE);
		
		if(image == null || eventId == null) {
			return null;
		} else {
			return (new UploadEventImageCommand(userId, eventId, image));
		}
	}

	private Command updateEventFactory() {
		
		String eventS = request.getParameter(EVENT);
		
		if (eventS == null) {
			return null;
		}
		
		JsonParser jp = new JsonParser();
		JsonObject jo = null;
		try {
			jo = (JsonObject) jp.parse(eventS);
		} catch(JsonSyntaxException e) {
			return null;
		}
		
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
			return new JoinEventCommand(eventId, userId);
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
		if(eventS == null) {
			return null;
		}
		
		JsonParser jp = new JsonParser();
		JsonArray ja = null;
		try {
			ja = (JsonArray) jp.parse(eventS);
		} catch(JsonSyntaxException e) {
			return null;
		}
		
		HashMap<String,Integer> eventList = new HashMap<String,Integer>();
		
		for (int i = 0; i < ja.size(); i++) {
		    JsonObject rec = (JsonObject) ja.get(i);
		    try {
		    	eventList.put(rec.get(EVENT_ID).getAsString(), rec.get(LASTMODIFIED).getAsInt());
		    } catch(NullPointerException e) {
		    	return null;
		    } catch(ClassCastException e) {
		    	return null;
		    }
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
		if (eventS == null) {
			return null;
		}
		
		JsonParser jp = new JsonParser();
		JsonObject jo = null;
		try {
			jo = (JsonObject) jp.parse(eventS);
		} catch(JsonSyntaxException e) {
			return null;
		}
		
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
