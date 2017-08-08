package RequestHandler.Commands;

import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Database.Event;
import Database.EventUserHandler;

/**
 * Gets a List of eventIds with their lastModified value. Returns a list of
 * all events with a higher lastModified value.
 */
public class GetEventsCommand extends Command {
	
	private String userId;
	private HashMap<String,Integer> eventsList;
	
	public GetEventsCommand(String uID, HashMap<String,Integer> eventsL) {
		userId = uID;
		eventsList = eventsL;
		
	}
	
	public String process() {
		EventUserHandler euh = new EventUserHandler();
		List<Event> events = euh.getAllUserEvents(userId);
		
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
			JsonObject jo = new JsonObject();
			jo.addProperty("event", e.serialize().toString());
			ja.add(jo);
		}
		
		//if an event was deleted
		for (String eId: eventsList.keySet()) {	
			if (!newEvents.containsKey(eId)) {
				JsonObject jo = new JsonObject();
				jo.addProperty("DeletedEvent", eId);
				ja.add(jo);
			}
		}
		
		return ja.toString();
		
	}
}
