package requestHandler.commands;

import com.google.gson.JsonObject;

import Database.EventUserHandler;

public class IsEventAdminCommand extends Command {

	private String userId;
	private String eventId;
	private EventUserHandler euh;
	
	public IsEventAdminCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
		setEuh(new EventUserHandler());
	}

	public String process() {
		
		boolean isAdmin;
		if(euh.isAdmin(userId, eventId)) {
			isAdmin = true;
		} else {
			isAdmin = false;
		}
		
		JsonObject jo = new JsonObject();
		jo.addProperty(Command.SUCCES_VAR, true);
		jo.addProperty(ISADMIN, isAdmin);
		
		return jo.toString();
	}
	
	public String getUserId() {
		return userId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEuh(EventUserHandler euh) {
		this.euh = euh;
	}
}
