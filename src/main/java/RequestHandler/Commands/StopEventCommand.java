package RequestHandler.Commands;

import com.google.gson.JsonObject;

public class StopEventCommand extends Command {

	private String userId;
	private String eventId;
	
	public StopEventCommand(String uId, String eId){
		userId = uId;
		eventId = eId;
	}
	
	public String process() {
		
		boolean success = true;
		
		synchronized(StartEventCommand.algorithms) {
			boolean empty = false;
			if(StartEventCommand.algorithms.containsKey(eventId)) {
				empty = StartEventCommand.algorithms.get(eventId).stopEvent(userId);
			} else {
				success = false;
			}
			if (empty) {
					StartEventCommand.algorithms.remove(eventId);
			}	
		}
		
		JsonObject jo = new JsonObject();
		jo.addProperty(super.SUCCES_VAR, success);
		
		return jo.toString();
	}
}
