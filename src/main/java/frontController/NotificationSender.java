package frontController;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonObject;

public class NotificationSender {

	private final String serverUrl = "";
	private final String serverKey = "";
	
	private ArrayList<String> notificationIds;
	private String eventId;
	
	public NotificationSender(ArrayList<String> notIds, String eId) {
		notificationIds = notIds;
		eventId = eId;
	}
	
	public void sendNotifications() {
		JsonObject message = new JsonObject();
	    message.addProperty("message", eventId);
	    
	    for(String nId: notificationIds) {
	    	JsonObject protocol = new JsonObject();
	    	protocol.addProperty("to", nId);
	    	protocol.addProperty("data", message.toString());
	    	executePost(protocol.toString());
	    }
	    
	}
	
	private void executePost(String parameter) {
		
		Thread th = new Thread() {
			public void run() {
				HttpURLConnection connection = null;

				  try {
				    //Create connection
				    URL url = new URL(serverUrl);
				    connection = (HttpURLConnection) url.openConnection();
				    connection.setRequestMethod("POST");
				    connection.setRequestProperty("Content-Type", 
				        "application/json");  
				    connection.setRequestProperty("Authorization", serverKey);

				    //Send request
				    DataOutputStream wr = new DataOutputStream (
				        connection.getOutputStream());
				    wr.writeBytes(parameter);
				    wr.close();

				  } catch (Exception e) {
				    e.printStackTrace();

				  } finally {
				    if (connection != null) {
				      connection.disconnect();
				    }
				  }
			}
		};
		
		th.start();
		
		}
	
}
