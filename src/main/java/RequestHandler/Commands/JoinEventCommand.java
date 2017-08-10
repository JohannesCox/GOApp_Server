package RequestHandler.Commands;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventUserHandler;

/**
 *Adds userId to the event in the database. If successful process() returns
 * the event otherwise an empty String.
 */
public class JoinEventCommand extends Command {
	
	private String eventId;
	private String userId;
	
	public JoinEventCommand(String eId, String uId) {
		eventId = eId;
		userId = uId;
	}
	
	public String process() {

		EventUserHandler euh = new EventUserHandler();
		Event event = euh.joinEvent(eventId, userId);
		
		if (event == null) {
			JsonObject jo = new JsonObject();
			jo.addProperty("successful", false);			
			return jo.toString();
		} else {
			JsonObject jo = event.serialize();
			jo.addProperty("successful", true);
			return jo.toString();
		}	

	}

}
