package RequestHandler;

import com.google.gson.JsonObject;

import Database.EventUserHandler;

public class LeaveEventCommand extends Command {
	
	private String userId;
	private String eventId;
	
	public LeaveEventCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
	}
	
	public String process() {
		EventUserHandler euh = new EventUserHandler();
		boolean success = euh.leaveEvent(userId, eventId);
		
		JsonObject jo = new JsonObject();
		
		jo.addProperty("succesfull", success);
		
		return jo.toString();
	}
}
