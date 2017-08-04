package RequestHandler;

public class DeleteEventCommand extends Command {
	
	private String userId;
	private String eventId;
	
	public DeleteEventCommand(String uId, String eId){
		userId = uId;
		eventId = eId;
	}
	
	public String process() {
		//TODO
		return null;
	}

}
