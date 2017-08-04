package RequestHandler;

import java.util.Date;

public class UpdateEventCommand extends Command {
	
	private String userId;
	private String eventId;
	private String title;
	private Date date;
	//TODO maybe String not correct here. maybe two doubles?
	private String location;
	private String description;
	
	public UpdateEventCommand(String uId, String eId, String titleS, Date d, String loc,
			String desc) {
		userId = uId;
		eventId = eId;
		title = titleS;
		date = d;
		location = loc;
		description = desc;
	}
	
	public String process() {
		//TODO
		return null;
	}
}
