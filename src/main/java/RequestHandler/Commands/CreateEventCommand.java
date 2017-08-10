package RequestHandler.Commands;

import java.util.Date;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventHandler;

/**
 * Creates event. 
 */
public class CreateEventCommand extends Command {
	
	private String userId;
	private String title;
	private Date date;
	private String location;
	private String description;
	
	public CreateEventCommand(String uId, String titleS, Date d, String loc,
			String desc) {
		userId = uId;
		title = titleS;
		date = d;
		location = loc;
		description = desc;
	}
	
	@Override
	public String process() {
		
		EventHandler eh = new EventHandler();
		Event event = eh.createEvent(userId, title, date, location, description);
		
		JsonObject jo = new JsonObject();
		
		if (event == null) {
			jo.addProperty(super.SUCCES_VAR, false);
			jo.addProperty(super.ERROR_VAR, super.INT_ERROR);	
		} else {
			jo = event.serialize();
			jo.addProperty(super.SUCCES_VAR, true);
		}
		
		return jo.toString();


	}
	
}
