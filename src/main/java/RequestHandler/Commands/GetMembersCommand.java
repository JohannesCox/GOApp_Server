package RequestHandler.Commands;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Database.EventUserHandler;

/**
 * The command to get all members of an event.
 */
public class GetMembersCommand extends Command {
	
	private String userId;
	private String eventId;
	private EventUserHandler eventUserHandler;
	
	/**
	 * Creates a new command to get all members of an event.
	 * @param uId The userId of the user who created the request.
	 * @param eId The eventId of the event.
	 */
	public GetMembersCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
		eventUserHandler = new EventUserHandler();
	}
	
	/**
	 *Returns a List of the user names of all members of an event. If the user is not a member
	 *of the event an empty list is returned.
	 */
	public String process() {
		
		Map<String, Boolean> usernamesAndAdmin = eventUserHandler.getMembers(userId, eventId);
		
		JsonArray ja = new JsonArray();
		
		for(String username: usernamesAndAdmin.keySet()) {
			JsonObject jo = new JsonObject();
			jo.addProperty("username", username);
			jo.addProperty("isAdmin", usernamesAndAdmin.get(username));
			ja.add(jo);
		}
		
		return ja.toString();
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public EventUserHandler getEventUserHandler() {
		return eventUserHandler;
	}
	
	public void setEventUserHandler(EventUserHandler eventUserHandler) {
		this.eventUserHandler = eventUserHandler;
	}
}
