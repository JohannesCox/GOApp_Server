package RequestHandler.Commands;

import com.google.gson.JsonObject;

import Database.EventHandler;

public class DeleteEventCommand extends Command {
	
	private String userId;
	private String eventId;
	
	public DeleteEventCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
	}
	
	public String process() {
		EventHandler eh = new EventHandler();
		boolean success = eh.deleteEvent(userId, eventId);
		
		JsonObject jo = new JsonObject();
		
		if(success) {
			jo.addProperty("successfull", true);
		} else {
			jo.addProperty("successfull", false);
		}
		
		return jo.toString();
		
	}
}
