package RequestHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Database.UserHandler;

public class DeleteUserCommand extends Command {
	
	private String userId;
	
	public DeleteUserCommand(String uId) {
		userId = uId;
	}
	
	public String process() {
		UserHandler uh = new UserHandler();
		boolean succesfull = uh.deleteUser(userId);
		
		JsonObject jsonObj = new JsonObject();
		
		jsonObj.addProperty("successfull", succesfull);
		
		JsonArray ja = new JsonArray();
		ja.add(jsonObj);
		return ja.toString();
		
	}
}
