package RequestHandler.Commands;

public class UploadEventImageCommand extends Command {

	private String userId;
	private String eventId;
	private String image;
	
	public UploadEventImageCommand(String uId, String eId, String img) {
		userId = uId;
		eventId = eId;
		image = img;
	}
	
	public String process() {
		//TODO
		return null;
	}
}
