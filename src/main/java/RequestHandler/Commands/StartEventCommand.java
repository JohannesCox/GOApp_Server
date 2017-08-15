package RequestHandler.Commands;

import java.util.HashMap;

import org.apache.commons.math3.ml.clustering.DoublePoint;

import com.google.gson.JsonObject;

import Database.EventUserHandler;
import RequestHandler.ClusteringAlgorithm;

/**
 * The command to start an event and to get the current group locations of an event.
 */
public class StartEventCommand extends Command {
	
	//This HashMap includes the Clustering-Algorithm for an event.
	private static HashMap<String,ClusteringAlgorithm> algorithms = new HashMap<String,ClusteringAlgorithm>();
	
	private String userId;
	private String eventId;
	private DoublePoint doublepoint;
	
	/**
	 * The command to start an event.
	 * @param uId The userId of the user.
	 * @param eId The eventId of the event.
	 * @param dp The location of the user as a 2-dimensional DoublePoint. The first value should be the latitude and
	 * the second one the longitude in degrees.
	 */
	public StartEventCommand(String uId, String eId, DoublePoint dp) {
		userId = uId;
		eventId = eId;
		doublepoint = dp;
		
		//TODO Delete! For Testing only
		//Start creating dummy points
		if(!algorithms.containsKey("TestEvent")) {
			ClusteringAlgorithm ca = new ClusteringAlgorithm();
			String user1 = "user1";
			double[] gps1 = {49.009428, 8.404064};
			String user2 = "user2";
			double[] gps2 = {49.009428,8.404257}; //user1 and user2 are in one group
			String user3 = "user3";
			double[] gps3 = {49.011729,8.404064};
			String user4 = "user4";
			double[] gps4 = {49.011680, 8.403946}; //user3 and user4 are in one group
			String user5 = "user5";
			double[] gps5 = {49.011603, 8.397154}; //user5 is alone
			DoublePoint loc1 = new DoublePoint(gps1);
			DoublePoint loc2 = new DoublePoint(gps2);
			DoublePoint loc3 = new DoublePoint(gps3);
			DoublePoint loc4 = new DoublePoint(gps4);
			DoublePoint loc5 = new DoublePoint(gps5);
			ca.updateGPS(user1, loc1);
			ca.updateGPS(user2, loc2);
			ca.updateGPS(user3, loc3);
			ca.updateGPS(user4, loc4);
			ca.updateGPS(user5, loc5);
			algorithms.put("TestEvent", ca);
		}
		//end creating dummy points
		
		
	}
	
	/**
	 * Adds the users location to the Clustering-Algorithm. Returns the group locations or an error
	 * if the user is not a member of the event.
	 */
	public String process() {
		
		EventUserHandler euh = new EventUserHandler();
		
		//check if the user is a member of the event
		if (!euh.isMember(userId, eventId)) {
			JsonObject jo = new JsonObject();
			jo.addProperty("error", "You are not a member of the event!");
			return jo.toString();
		}
		
		synchronized(StartEventCommand.algorithms) {
			if (!algorithms.containsKey(eventId)) {				
				algorithms.put(eventId, new ClusteringAlgorithm());
			}
		}
		
		return algorithms.get(eventId).updateGPS(userId, doublepoint).toString();

	}
	
	public DoublePoint getDoublepoint() {
		return doublepoint;
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Returns a HashMap of the Clustering-Algorithms for the different events.
	 * @return A HashMap<eventId, ClusteringAlgorithm> where for each event which was started by at least one user the
	 * belonging Clustering-Algorithm is saved.
	 */
	public static HashMap<String, ClusteringAlgorithm> getAlgorithms() {
		return algorithms;
	}

}
