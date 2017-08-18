package requestHandler.commands;

import java.util.Date;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventHandler;

/**
 * The Command to create an event.
 */
public class CreateEventCommand extends Command {
	
	private String userId;
	private String title;
	private Date date;
	private String location;
	private String description;
	
	private EventHandler eventHandler;
	
	/**
	 * Creates a command which can create an event with the following parameters.
	 * @param uId The userId of the user who sends the request.
	 * @param titleS The title of the event.
	 * @param d The date of the event.
	 * @param loc The location of the event.
	 * @param desc The description of the event.
	 */
	public CreateEventCommand(String uId, String titleS, Date d, String loc,
			String desc) {
		userId = uId;
		title = titleS;
		date = d;
		location = loc;
		description = desc;
		eventHandler = new EventHandler();
	}
	
	/**
	 * Creates a new Event. If the creation was successful the event will be returned as a String.
	 */
	@Override
	public String process() {
		
		Event event = eventHandler.createEvent(userId, title, date, location, description);
		
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
	
	public Date getDate() {
		return date;
	}
	
	public String getDescription() {
		return description;
	}
	public String getTitle() {
		return title;
	}
	public String getLocation() {
		return location;
	}
	public String getUserId() {
		return userId;
	}
	
	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
}
