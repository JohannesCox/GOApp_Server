package RequestHandler.Commands;

import java.util.HashMap;

/**
 * Gets a List of eventIds with their lastModified value. Returns a list of
 * all events with a higher lastModified value.
 */
public class GetEventsCommand extends Command {
	
	private String userId;
	//First entry is the eventID, second entry is the lastModified value
	private HashMap<String,Integer> eventsList;
	
	public GetEventsCommand(String uID, HashMap<String,Integer> eventsL) {
		userId = uID;
		eventsList = eventsL;
		
	}
	
	public String process() {
		//TODO
		return null;
	}
}
