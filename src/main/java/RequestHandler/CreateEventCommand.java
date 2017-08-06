package RequestHandler;

import java.util.Date;

import com.google.gson.JsonObject;

import Database.EventHandler;

/**
 * Creates event. 
 */
public class CreateEventCommand extends Command {
	
	private String userId;
	private String title;
	private Date date;
	//TODO maybe String not correct here. maybe two doubles?
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
		
		//TODO
		
		/*
		EventHandler eh = new EventHandler();
		String eventId = eh.createEvent(userId, title, date, location, description);
		
		JsonObject jo = new JsonObject();
		jo.addProperty("eventId", eventId);
		
		return jo.toString();
		*/
		
		return null;
	}
	
}
