package RequestHandler.Commands;

import com.google.gson.JsonObject;

import Database.EventHandler;

public class UploadEventImageCommand extends Command {

	private String userId;
	private String eventId;
	private String image;
	
	private EventHandler eventHandler;
	
	public UploadEventImageCommand(String uId, String eId, String img) {
		userId = uId;
		eventId = eId;
		image = img;
		eventHandler = new EventHandler();
	}
	
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
