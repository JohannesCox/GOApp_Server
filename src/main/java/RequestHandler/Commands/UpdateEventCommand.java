package RequestHandler.Commands;

import java.util.Date;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventHandler;
import Database.EventUserHandler;

/**
 * The command to update an event.
 */
public class UpdateEventCommand extends Command {
	
	private String userId;
	private String eventId;
	private String title;
	private Date date;
	private String location;
	private String description;
	
	/**
	 * Creates a new command to update an already existing event.
	 * @param uId The userId of the user.
	 * @param eId The eventId of the event.
	 * @param titleS The new title of the event.
	 * @param d The new Date of the event.
	 * @param loc The new location of the event.
	 * @param desc The new description of the event.
	 */
	public UpdateEventCommand(String uId, String eId, String titleS, Date d, String loc,
			String desc) {
		userId = uId;
		eventId = eId;
		title = titleS;
		date = d;
		location = loc;
		description = desc;
	}
	
	/**
	 * Updates the event. Checks if the user is an admin of the event. The returned String specifies if the operation
	 * was successful or not.
	 */
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
	
	public Date getDate() {
		return date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getUserId() {
		return userId;
	}
	
	
}
