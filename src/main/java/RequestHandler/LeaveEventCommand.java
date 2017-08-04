package RequestHandler;

public class LeaveEventCommand {
	
	private String userId;
	private String eventId;
	
	public LeaveEventCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
	}
	
	public String process() {
		//TODO
		return null;
	}
}
