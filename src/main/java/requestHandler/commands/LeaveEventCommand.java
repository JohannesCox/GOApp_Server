package requestHandler.commands;

import com.google.gson.JsonObject;

import Database.EventUserHandler;

/**
 * The command to remove a user from an event.
 */
public class LeaveEventCommand extends Command {
	
	private String userId;
	private String eventId;
	private EventUserHandler eventUserHandler;
	
	/**
	 * Creates a new command which removes a user from an event.
	 * @param uId The userId of the user.
	 * @param eId The eventId of the event.
	 */
	public LeaveEventCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
		eventUserHandler = new EventUserHandler();
	}
	
	/**
	 * Removes the user from the event. Returns if the action was successful or not.
	 */
	public String process() {

		boolean success = eventUserHandler.leaveEvent(userId, eventId);
		
		JsonObject jo = new JsonObject();
		
		jo.addProperty(super.SUCCES_VAR, success);
		
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
