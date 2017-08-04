package RequestHandler;

/**
 *Adds userId to the event in the database. If successful process() returns
 * {"successful":"true"} otherwise {"successful":false}
 */
public class JoinEventCommand {
	
	private String eventId;
	private String userId;
	
	public JoinEventCommand(String eId, String uId) {
		eventId = eId;
		userId = uId;
	}
	
	public String process() {
		//TODO
		return null;
	}

}
