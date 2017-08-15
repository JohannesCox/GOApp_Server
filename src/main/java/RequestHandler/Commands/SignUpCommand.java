package RequestHandler.Commands;

import com.google.gson.JsonObject;

import Database.UserHandler;

/**
 * The command to add a User.
 */
public class SignUpCommand extends Command {
	
	private String userId;
	private String username;
	
	/**
	 * Creates a new command to add a user.
	 * @param uId The userId of the user.
	 * @param userN The username.
	 */
	public SignUpCommand(String uId, String userN) {
		userId = uId;
		username = userN;
	}
	
	/**
	 * Ads the user. If the user was already added this method has no effect. The returned String
	 * specifies if the action was successful.
	 */
	public String process() {
		UserHandler uh = new UserHandler();
		boolean success = uh.addUser(userId, username);
		
		JsonObject jo = new JsonObject();
		
		jo.addProperty(super.SUCCES_VAR, success);
		
		return jo.toString();
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}

}
