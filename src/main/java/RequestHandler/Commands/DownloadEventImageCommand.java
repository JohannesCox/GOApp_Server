package RequestHandler.Commands;

import com.google.gson.JsonObject;

import Database.EventHandler;

public class DownloadEventImageCommand extends Command {

	private String userId;
	private String eventId;
	
	private EventHandler eventHandler;
	
	public DownloadEventImageCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
		eventHandler = new EventHandler();
	}
	
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
