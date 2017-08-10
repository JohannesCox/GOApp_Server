package RequestHandler.Commands;

import com.google.gson.JsonObject;

import Database.UserHandler;

public class DeleteUserCommand extends Command {
	
	private String userId;
	
	public DeleteUserCommand(String uId) {
		userId = uId;
	}
	
	public String process() {

		UserHandler uh = new UserHandler();
		boolean success = uh.deleteUser(userId);
		
		JsonObject jo = new JsonObject();
		
		jo.addProperty(super.SUCCES_VAR, success);
		
		if(!success) {
			jo.addProperty(super.ERROR_VAR, super.INT_ERROR);
		}
		
		return jo.toString();

	}
}
