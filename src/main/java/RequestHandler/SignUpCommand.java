package RequestHandler;

public class SignUpCommand extends Command {
	
	private String userId;
	private String username;
	private String mailAdresse;
	
	public SignUpCommand(String uId, String userN, String mailA) {
		userId = uId;
		username = userN;
		mailAdresse = mailA;
	}
	
	public String process() {
		//TODO
		return null;
	}
}
