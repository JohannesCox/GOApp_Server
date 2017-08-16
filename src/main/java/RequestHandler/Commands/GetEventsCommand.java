package RequestHandler.Commands;

import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Database.Event;
import Database.EventUserHandler;

/**
 * The command to get all events of a user.
 */
public class GetEventsCommand extends Command {
	
	private String userId;
	private HashMap<String,Integer> eventsList;
	private EventUserHandler eventUserHandler;
	
	/**
	 * Creates a new command to get all events of a user.
	 * @param uID The userId of the user whose events should be get
	 * @param eventsL A HashMap<eventId,lastModified> of all events that are already known to the user.
	 */
	public GetEventsCommand(String uID, HashMap<String,Integer> eventsL) {
		userId = uID;
		eventsList = eventsL;
		eventUserHandler = new EventUserHandler();
	}
	
	/**
	 * Gets a List of all changed events of a user.
	 */
	public String process() {
		
		List<Event> events = eventUserHandler.getAllUserEvents(userId);
		
		HashMap<String, Event> newEvents = new HashMap<String, Event>();
		
		for(Event e: events) {
			newEvents.put(e.getEventID(), e);
		}
		
		HashMap<String, Event> changedEvents = new HashMap<String, Event>(newEvents);
		
		for (Event e: newEvents.values()) {
			
			if (eventsList.containsKey(e.getEventID()) && eventsList.get(e.getEventID()) == e.getLastmodified()) {
				changedEvents.remove(e.getEventID());
			}
		}
		
		JsonArray ja = new JsonArray();
		
		for (Event e: changedEvents.values()) {
			JsonObject jo = e.serialize();
			
			//check if the user is an admin of the event
			jo.addProperty("isAdmin", eventUserHandler.isAdmin(userId, e.getEventID()));
			jo.addProperty("isDeleted", false);
			ja.add(jo);
		}
		
		//if an event was deleted
		for (String eId: eventsList.keySet()) {	
			if (!newEvents.containsKey(eId)) {
				JsonObject jo = new JsonObject();
				jo.addProperty("deletedEvent", eId);
				jo.addProperty("isDeleted", true);
				ja.add(jo);
			}
		}
		
		return ja.toString();
		
	}
	
	public HashMap<String, Integer> getEventsList() {
		return eventsList;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public EventUserHandler getEventUserHandler() {
		return eventUserHandler;
	}
	
	public void setEventUserHandler(EventUserHandler eventUserHandler) {
		this.eventUserHandler = eventUserHandler;
	}
}
