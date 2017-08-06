package RequestHandler.Commands;

import Database.EventUserHandler;

/**
 *Adds userId to the event in the database. If successful process() returns
 * {"successful":"true"} otherwise {"successful":false}
 */
public class JoinEventCommand extends Command {
	
	private String eventId;
	private String userId;
	
	public JoinEventCommand(String eId, String uId) {
		eventId = eId;
		userId = uId;
	}
	
	public String process() {
		
		//TODO
		/*
		EventUserHandler euh = new EventUserHandler();
		Event event = euh.joinEvent(eventId, userId);
		
		return event.toJson();
		*/
		return null;
	}

}
