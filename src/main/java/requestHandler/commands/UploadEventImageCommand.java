package requestHandler.commands;

import com.google.gson.JsonObject;

import Database.EventHandler;

/**
 * The command to update the image of an event.
 */
public class UploadEventImageCommand extends Command {

	private String userId;
	private String eventId;
	private String image;
	
	private EventHandler eventHandler;
	
	/**
	 * Creates a new instance with the given parameters.
	 * @param uId The userId of the user who sent the request
	 * @param eId The eventId of the event 
	 * @param img The image as a String
	 */
	public UploadEventImageCommand(String uId, String eId, String img) {
		userId = uId;
		eventId = eId;
		image = img;
		eventHandler = new EventHandler();
	}
	
	/**
	 * Adds the image. The returned String specifies if the operation
	 * was successful or not.
	 */
	public String process() {
		
		boolean success = eventHandler.storePicture(userId, eventId, image);
		
		JsonObject jo = new JsonObject();
		jo.addProperty(SUCCES_VAR, success);
		return jo.toString();
		
	}
	
	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public String getImage() {
		return image;
	}
	
	public String getUserId() {
		return userId;
	}
}
