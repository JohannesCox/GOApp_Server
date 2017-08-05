package RequestHandler;

import com.google.gson.JsonObject;

import Database.UserHandler;

public class SignUpCommand extends Command {
	
	private String userId;
	private String username;
	
	public SignUpCommand(String uId, String userN) {
		userId = uId;
		username = userN;
	}
	
	public String process() {
		UserHandler uh = new UserHandler();
		boolean success = uh.addUser(userId, username);
		
		JsonObject jo = new JsonObject();
		
		jo.addProperty("successful", success);
		
		return jo.toString();
	}

}
