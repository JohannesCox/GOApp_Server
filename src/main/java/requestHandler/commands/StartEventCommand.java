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
	
	double lat1 = 49.013513;
	double lon1 = 8.404435;	
	double lat11 = 49.01129249999996;
	double lon11 = 8.416347656249982;
	
	double lat2 = 49.006208;
	double lon2 = 8.431814;
	double lat22 = 49.01129249999996;
	double lon22 = 8.416347656249982;
	
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
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId1, createDP(lat1, lon1));
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId2, createDP(lat1, lon1));
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId3, createDP(lat1, lon1));
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId4, createDP(lat1, lon1));
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId5, createDP(lat1, lon1));
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId6, createDP(lat1, lon1));
					
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId7, createDP(lat2, lon2));
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId8, createDP(lat2, lon2));
					algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId9, createDP(lat2, lon2));
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
	
	private DoublePoint createDP(double lat, double lon) {
		lat = lat + Math.random() * 0.0001;
		lon = lon + Math.random() * 0.0001;
		double[] gps = {lat, lon};
		return new DoublePoint(gps);
	}
	
	private void updateDummyPoints() {
		counter++;
		counter = counter % 8;
		double lat1N = lat1 + ((-lat1 + lat11) / 7) * counter;
		double lon1N =lon1 + ((-lon1 + lon11) / 7) * counter;
		double lat2N = lat2 + ((-lat2 + lat22) / 7) * counter; 
		double lon2N = lon2 + ((-lon2 + lon22) / 7) * counter;
		
		synchronized(StartEventCommand.algorithms) {
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId1, createDP(lat1N, lon1N));
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId2, createDP(lat1N, lon1N));
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId3, createDP(lat1N, lon1N));
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId4, createDP(lat1N, lon1N));
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId5, createDP(lat1N, lon1N));
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId6, createDP(lat1N, lon1N));
			
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId7, createDP(lat2N, lon2N));
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId8, createDP(lat2N, lon2N));
			algorithms.get("ITZlgC2aYCMvGLLGpHA").updateGPS(userId9, createDP(lat2N, lon2N));
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
