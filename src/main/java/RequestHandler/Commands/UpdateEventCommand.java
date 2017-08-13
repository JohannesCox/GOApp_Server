package RequestHandler.Commands;

import java.util.Date;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventHandler;
import Database.EventUserHandler;

public class UpdateEventCommand extends Command {
	
	private String userId;
	private String eventId;
	private String title;
	private Date date;
	private String location;
	private String description;
	
	public UpdateEventCommand(String uId, String eId, String titleS, Date d, String loc,
			String desc) {
		userId = uId;
		eventId = eId;
		title = titleS;
		date = d;
		location = loc;
		description = desc;
	}
	
	public String process() {
		EventHandler eh = new EventHandler();
		Event event = eh.updateEvent(userId, eventId, title, date, location, description);
		
		JsonObject jo = new JsonObject();
		
		if (event == null) {
			jo.addProperty(super.SUCCES_VAR, false);
			EventUserHandler euh = new EventUserHandler();
			if (euh.isAdmin(userId, eventId)) {
				jo.addProperty(super.ERROR_VAR, super.INT_ERROR);
			} else {
				jo.addProperty(super.ERROR_VAR, super.ADMIN_ERROR);
			}
		} else {
			jo = event.serialize();
			jo.addProperty(super.SUCCES_VAR, true);
		}
		
		return jo.toString();
	}
}
