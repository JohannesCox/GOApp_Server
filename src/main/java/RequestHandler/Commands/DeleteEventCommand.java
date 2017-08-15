package RequestHandler.Commands;

import com.google.gson.JsonObject;

import Database.EventHandler;
import Database.EventUserHandler;

/**
 * The command to delete an event.
 */
public class DeleteEventCommand extends Command {
	
	private String userId;
	private String eventId;
	
	/**
	 * Creates a command which can delete an event.
	 * @param uId The userId of the user who sent the request.
	 * @param eId
	 */
	public DeleteEventCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
	}
	
	/**
	 * Deletes the event if the user with userId is an admin of the event and if the
	 * event exists. The method returns if the deletion was done successfuly.
	 */
	@Override
	public String process() {
		EventHandler eh = new EventHandler();
		boolean success = eh.deleteEvent(userId, eventId);
		
		JsonObject jo = new JsonObject();
		jo.addProperty(super.SUCCES_VAR, success);
		
		if(!success) {
			EventUserHandler euh = new EventUserHandler();
			if (euh.isAdmin(userId, eventId)) {
				jo.addProperty(super.ERROR_VAR, super.INT_ERROR);
			} else {
				jo.addProperty(super.ERROR_VAR, super.ADMIN_ERROR);
			}
		}
		
		return jo.toString();
		
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public String getUserId() {
		return userId;
	}
}
