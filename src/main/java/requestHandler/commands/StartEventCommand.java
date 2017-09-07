package requestHandler.commands;

import java.util.HashMap;

import org.apache.commons.math3.ml.clustering.DoublePoint;

import com.google.gson.JsonObject;

import Database.EventUserHandler;
import requestHandler.ClusteringAlgorithm;

/**
 * The command to start an event and to get the current group locations of an event.
 */
public class StartEventCommand extends Command {
	
	//This HashMap includes the Clustering-Algorithm for an event.
	private static HashMap<String,ClusteringAlgorithm> algorithms = new HashMap<String,ClusteringAlgorithm>();
	
	private String userId;
	private String eventId;
	private DoublePoint doublepoint;
	
	private EventUserHandler eventUserHandler;
	
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
		eventUserHandler = new EventUserHandler();
	}
	
	/**
	 * Adds the users location to the Clustering-Algorithm. Returns the group locations or an error
	 * if the user is not a member of the event.
	 */
	public String process() {
		
		//check if the user is a member of the event
		if (!eventUserHandler.isMember(userId, eventId)) {
			JsonObject jo = new JsonObject();
			jo.addProperty(Command.ERROR_VAR, Command.No_Member_Error);
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
	
	public void setEventUserHandler(EventUserHandler eventUserHandler) {
		this.eventUserHandler = eventUserHandler;
	}

}
