package RequestHandler.Commands;

public class DownloadEventImageCommand extends Command {

	private String userId;
	private String eventId;
	private String image;
	
	public DownloadEventImageCommand(String uId, String eId, String img) {
		userId = uId;
		eventId = eId;
		image = img;
	}
	
	public String process() {
		//TODO
		return null;
	}
}
