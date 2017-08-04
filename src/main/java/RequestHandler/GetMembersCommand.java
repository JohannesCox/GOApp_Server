package RequestHandler;

/**
 *Returns a List of all members of an event. The username and if the user is an admin
 *of the event is returned.
 */
public class GetMembersCommand extends Command{
	
	private String userId;
	private String eventId;
	
	public GetMembersCommand(String uId, String eId) {
		userId = uId;
		eventId = eId;
	}
	
	public String process() {
		//TODO
		return null;
	}
}
