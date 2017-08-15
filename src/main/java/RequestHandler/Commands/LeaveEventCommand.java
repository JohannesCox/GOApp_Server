package RequestHandler.Commands;

import com.google.gson.JsonObject;

import Database.EventUserHandler;

/**
 * The command to remove a user from an event.
 */
public class LeaveEventCommand extends Command {
	
	private String userId;
	private String eventId;
	
	/**
	 * Creates a new command which removes a user from an event.
	 * @param uId The userId of the user.
	 * @param eId The eventId of the event.
	 */
	public LeaveEventCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
	}
	
	/**
	 * Removes the user from the event. Returns if the action was successful or not.
	 */
	public String process() {
		EventUserHandler euh = new EventUserHandler();
		boolean success = euh.leaveEvent(userId, eventId);
		
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
}
