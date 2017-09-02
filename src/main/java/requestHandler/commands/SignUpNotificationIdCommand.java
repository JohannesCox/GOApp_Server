package requestHandler.commands;

import com.google.gson.JsonObject;

import Database.UserHandler;

public class SignUpNotificationIdCommand extends Command {

	private String notificationId;
	private String username;
	private String userId;
	private UserHandler userHandler;
	
	public SignUpNotificationIdCommand(String uId, String userN, String notId) {
		setUserId(uId);
		setUsername(userN);
		setNotificationId(notId);
		setUserHandler(new UserHandler());
	}
	
	public String process() {
		boolean success = userHandler.addUser(userId, username, notificationId);
		
		JsonObject jo = new JsonObject();
		
		jo.addProperty(super.SUCCES_VAR, success);
		
		return jo.toString();
	}

	public UserHandler getUserHandler() {
		return userHandler;
	}

	public void setUserHandler(UserHandler userHandler) {
		this.userHandler = userHandler;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	
}
