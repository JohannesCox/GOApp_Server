package RequestHandler.Commands;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Database.EventUserHandler;

/**
 *Returns a List of all members of an event. The username and if the user is an admin
 *of the event is returned.
 */
public class GetMembersCommand extends Command {
	
	private String userId;
	private String eventId;
	
	public GetMembersCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
	}
	
	public String process() {

		EventUserHandler euh = new EventUserHandler();
		
		Map<String, Boolean> usernamesAndAdmin = euh.getMembers(userId, eventId);
		
		JsonArray ja = new JsonArray();
		
		for(String username: usernamesAndAdmin.keySet()) {
			JsonObject jo = new JsonObject();
			jo.addProperty("username", username);
			jo.addProperty("isAdmin", usernamesAndAdmin.get(username));
			ja.add(jo);
		}
		
		return ja.toString();
	}
}
