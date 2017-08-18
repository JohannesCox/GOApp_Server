package requestHandler.commands;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventUserHandler;

/**
 * The command to add a user to an event.
 */
public class JoinEventCommand extends Command {
	
	private String eventId;
	private String userId;
	private EventUserHandler eventUserHandler;
	
	/**
	 * Creates a new command to add the user to the event.
	 * @param eId The eventId of the event.
	 * @param uId The userId of the user.
	 */
	public JoinEventCommand(String eId, String uId) {
		eventId = eId;
		userId = uId;
		eventUserHandler = new EventUserHandler();
	}
	
	/**
	 * Adds the user to the event. The returned String specifies if the action was successful or not.
	 */
	public String process() {

		Event event = eventUserHandler.joinEvent(eventId, userId);
		
		JsonObject jo = new JsonObject();
		
		if (event == null) {
			jo.addProperty(super.SUCCES_VAR, false);
		} else {
			jo = event.serialize();
			jo.addProperty(super.SUCCES_VAR, true);
		}	
		
		return jo.toString();

	}
	
	public String getEventId() {
		return eventId;
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
