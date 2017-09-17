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
	
	//DummyPoints for TestEvent
	String eventIdDummy = "ITZlgC2aYCMvGLLGpHA";
	String userId1 = "u1";
	String userId2 = "u2";
	String userId3 = "u3";
	String userId4 = "u4";
	String userId5 = "u5";
	String userId6 = "u6";
	
	String userId7 = "u7";
	String userId8 = "u8";
	String userId9 = "u9";
	
	double lon1 = 49.013817;
	double lat1 = 8.416347656249982;	
	double lon11 = 49.013409;
	double lat11 = 8.418370;
	
	double lat2 = 49.006208;
	double lon2 = 8.431814;
	double lat22 = 49.009074;
	double lon22 = 8.416911;
	
	int counter = 0;
	
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
		
		//create DummyPoints
		if (eventId.equals(eventIdDummy)) {
			synchronized(StartEventCommand.algorithms) {
				if (!algorithms.containsKey("ITZlgC2aYCMvGLLGpHA")) {
					algorithms.put(eventIdDummy, new ClusteringAlgorithm());
					double[] gps1 = {lat1, lon1};
					DoublePoint dp1 = new DoublePoint(gps1);
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId1, dp1);
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId2, dp1);
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId3, dp1);
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId4, dp1);
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId5, dp1);
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId6, dp1);
					
					double[] gps2 = {lat2, lon2};
					DoublePoint dp2 = new DoublePoint(gps2);
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId7, dp2);
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId8, dp2);
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId9, dp2);
				}
			}
		}
		
	}
	
	/**
	 * Adds the users location to the Clustering-Algorithm. Returns the group locations or an error
	 * if the user is not a member of the event.
	 */
	public String process() {
		
		//update Dummy points
		if (eventId.equals(eventIdDummy)) {
			updateDummyPoints();
		}
		
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
	
	private void updateDummyPoints() {
		counter++;
		counter = counter % 20;
		double[] gps1 = {lat1 + ((lat1 - lat11) / 20) * counter, 
				lon1 + ((lon1 - lon11) / 20) * counter};
		double[] gps2 = {lat2 + ((lat2 - lat22) / 20) * counter, 
				lon2 + ((lon2 - lon22) / 20) * counter};
		
		DoublePoint dp1 = new DoublePoint(gps1);
		DoublePoint dp2 = new DoublePoint(gps2);
		
		synchronized(StartEventCommand.algorithms) {
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId1, dp1);
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId2, dp1);
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId3, dp1);
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId4, dp1);
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId5, dp1);
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId6, dp1);
			
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId7, dp2);
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId8, dp2);
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId9, dp2);
		}
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
