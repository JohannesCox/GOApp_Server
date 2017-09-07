package requestHandler.commands;

import com.google.gson.JsonObject;

import Database.UserHandler;

/**
 * The command to add a User.
 */
public class SignUpCommand extends Command {
	
	private String userId;
	private String username;
	private UserHandler userHandler;
	
	/**
	 * Creates a new command to add a user.
	 * @param uId The userId of the user.
	 * @param userN The username.
	 */
	public SignUpCommand(String uId, String userN) {
		userId = uId;
		username = userN;
		userHandler = new UserHandler();
	}
	
	/**
	 * Ads the user. If the user was already added this method has no effect. The returned String
	 * specifies if the action was successful.
	 */
	public String process() {

		boolean success = userHandler.addUser(userId, username);
		
		JsonObject jo = new JsonObject();
		
		jo.addProperty(Command.SUCCES_VAR, success);
		
		return jo.toString();
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUserHandler(UserHandler userHandler) {
		this.userHandler = userHandler;
	}

}
