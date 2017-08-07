package RequestHandler.Commands;

import java.util.HashMap;

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
		euh.getAllUserEvents
	}
}
