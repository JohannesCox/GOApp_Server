package RequestHandler.Commands;

import com.google.gson.JsonObject;

import Database.EventHandler;
import Database.EventUserHandler;

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
}
