package RequestHandler;

public class SignUpCommand extends Command {
	
	private String userId;
	private String username;
	
	public SignUpCommand(String uId, String userN) {
		userId = uId;
		username = userN;
	}
	
	public String process() {
		//TODO
		return null;
	}

}
