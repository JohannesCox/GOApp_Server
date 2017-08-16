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
	
	private EventHandler eventHandler;
	private EventUserHandler eventUserHandler;
	
	/**
	 * Creates a command which can delete an event.
	 * @param uId The userId of the user who sent the request.
	 * @param eId The eventId of the event.
	 */
	public DeleteEventCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
		eventHandler = new EventHandler();
		eventUserHandler = new EventUserHandler();
	}
	
	/**
	 * Deletes the event if the user with userId is an admin of the event and if the
	 * event exists. The method returns if the deletion was done successfuly.
	 */
	@Override
	public String process() {
		
		boolean success = eventHandler.deleteEvent(userId, eventId);
		
		JsonObject jo = new JsonObject();
		jo.addProperty(super.SUCCES_VAR, success);
		
		if(!success) {
			if (eventUserHandler.isAdmin(userId, eventId)) {
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
	
	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	public EventUserHandler getEventUserHandler() {
		return eventUserHandler;
	}
	
	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
	
	public void setEventUserHandler(EventUserHandler eventUserHandler) {
		this.eventUserHandler = eventUserHandler;
	}
}
