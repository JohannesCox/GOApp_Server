package requestHandler.commands;

import com.google.gson.JsonObject;

import Database.EventHandler;

/**
 * The command to get the image of a certain event.
 */
public class DownloadEventImageCommand extends Command {

	private String userId;
	private String eventId;
	
	private EventHandler eventHandler;
	
	/**
	 * Creates a new instance with the given parameters.
	 * @param uId The userId of the user who sent the request.
	 * @param eId The eventId of the event.
	 */
	public DownloadEventImageCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
		eventHandler = new EventHandler();
	}
	
	/**
	 * Returns the image as a String if the operation was successful.
	 */
	public String process() {
		
		String img = eventHandler.getPicture(userId, eventId);
		JsonObject jo = new JsonObject();
		
		if (img == null) {
			jo.addProperty(SUCCES_VAR, false);
		} else {
			jo.addProperty(SUCCES_VAR, true);
			jo.addProperty("image", img);
		}
		
		return jo.toString();
	}
	
	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
}
