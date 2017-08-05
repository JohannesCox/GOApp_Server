package RequestHandler;

public class DeleteUserCommand extends Command {
	
	private String userId;
	
	public DeleteUserCommand(String uId) {
		userId = uId;
	}
	
	public String process() {
		//TODO
		return null;
	}
}
