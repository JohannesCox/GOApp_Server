package RequestHandler.Commands;

import com.google.gson.JsonObject;

import Database.UserHandler;

/**
 * The command to delete a user.
 */
public class DeleteUserCommand extends Command {
	
	private String userId;
	private UserHandler userHandler;
	
	/**
	 * Creates a Command which can delete the user with the given userId.
	 * @param uId The userId of the user that should be deleted.
	 */
	public DeleteUserCommand(String uId) {
		userId = uId;
		userHandler = new UserHandler();
	}
	
	/**
	 * Deletes the user. Returns if the deletion was successful or not.
	 */
	@Override
	public String process() {

		boolean success = userHandler.deleteUser(userId);
		
		JsonObject jo = new JsonObject();
		
		jo.addProperty(super.SUCCES_VAR, success);
		
		if(!success) {
			jo.addProperty(super.ERROR_VAR, super.INT_ERROR);
		}
		
		return jo.toString();

	}
	
	public String getUserId() {
		return userId;
	}
	
	public UserHandler getUserHandler() {
		return userHandler;
	}
	
	public void setUserHandler(UserHandler userHandler) {
		this.userHandler = userHandler;
	}
}

