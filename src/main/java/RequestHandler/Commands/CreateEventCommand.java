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
		
		EventHandler eh = new EventHandler();
		Event event = eh.createEvent(userId, title, date, location, description);
		
		return event.serialize().toString();

	}
	
}
