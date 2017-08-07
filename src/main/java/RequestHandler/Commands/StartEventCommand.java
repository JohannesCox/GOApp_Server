package RequestHandler.Commands;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DoublePoint;

import com.google.gson.JsonObject;

import Database.EventUserHandler;
import RequestHandler.ClusteringAlgorithm;

public class StartEventCommand extends Command {
	
	public static HashMap<String,ClusteringAlgorithm> algorithms = new HashMap<String,ClusteringAlgorithm>();
	
	private String userId;
	private String eventId;
	private DoublePoint doublepoint;
	
	public StartEventCommand(String uId, String eId, DoublePoint dp) {
		userId = uId;
		eventId = eId;
		doublepoint = dp;
	}
	
	public String process() {
		
		EventUserHandler euh = new EventUserHandler();
		
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

}
