package RequestHandler.Commands;

import com.google.gson.JsonObject;

/**
 * The command to stop an event.
 */
public class StopEventCommand extends Command {

	private String userId;
	private String eventId;
	
	/**
	 * Creates a new command to stop an event.
	 * @param uId The userId of the user.
	 * @param eId The eventId of the event.
	 */
	public StopEventCommand(String uId, String eId){
		userId = uId;
		eventId = eId;
	}
	
	/**
	 * Stops the event and deletes the location of the user from the Clustering-Algorithm. The returned String specifies
	 * if the action was completed successfully or not.
	 */
	public String process() {
		
		boolean success = true;
		
		synchronized(StartEventCommand.getAlgorithms()) {
			boolean empty = false;
			if(StartEventCommand.getAlgorithms().containsKey(eventId)) {
				empty = StartEventCommand.getAlgorithms().get(eventId).stopEvent(userId);
			} else {
				success = false;
			}
			if (empty) {
					StartEventCommand.getAlgorithms().remove(eventId);
			}	
		}
		
		JsonObject jo = new JsonObject();
		jo.addProperty(super.SUCCES_VAR, success);
		
		return jo.toString();
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	
}
