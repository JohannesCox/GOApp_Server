package requestHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.ml.clustering.*;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * This class includes the clustering algorithm for the event classes. 
 * One instance of this class should belong to one event.
 */
public class ClusteringAlgorithm {
	
	//the maximum distance for 2 points to be in the same cluster in degrees. Is equal to about 60m.
	private final double EPS = 0.0007;
	
	//The minimum of points which can build a cluster. 
	//The value 1 means that a cluster contains at least 2 points.
	private final int MINPNTS = 1;
	
	//Time in seconds until the gps data of a user is deleted (if not updated)
	private final long maxTime = 1200;
	
	private HashMap<String, DoublePoint> gpsData;
	private HashMap<String, Long> gpsAddingTime;
	
	EuclideanDistance measurement;
	DBSCANClusterer<DoublePoint> clusterer;
	
	/**
	 * Creates a new empty instance of ClusteringAlgorithm.
	 */
	public ClusteringAlgorithm() {
		gpsData = new HashMap<String, DoublePoint>();
		measurement = new EuclideanDistance();
		clusterer = new DBSCANClusterer<DoublePoint>(EPS, MINPNTS, measurement);
		gpsAddingTime = new HashMap<String, Long>();
	}
	
	/**
	 *  Updates the GPS and returns the current cluster of the group locations.
	 * @param uId	the userId
	 * @param dp	The location of the user as DoublePoint: {lat,lng}
	 * @return	Returns a Cluster saved in a JsonObject.
	 */
	public JsonArray updateGPS(String uId, DoublePoint dp) {
		gpsData.put(uId, dp);
		gpsAddingTime.put(uId, (new Date()).getTime());
		return calculate();
	}
	
	/**
	 * Stops the event for that specific user and deletes his gps data.
	 * @return returns true if all user stopped the event otherwise false.
	 */
	public boolean stopEvent(String uId) {
		
		gpsAddingTime.remove(uId);
		
		synchronized(gpsData) {
			gpsData.remove(uId);
			return (gpsData.isEmpty());
		}
		
	}
	
	private JsonArray calculate() {
		
		checkForExpiredGps();
		
		List<Cluster<DoublePoint>> clusters = clusterer.cluster(gpsData.values());
		JsonArray ja = new JsonArray();
		
		for(Cluster<DoublePoint> c: clusters) {
			JsonObject jo = new JsonObject();
			DoublePoint centerOfCluster = getCenter(c);
			jo.addProperty("lat", centerOfCluster.getPoint()[0]);
			jo.addProperty("lng", centerOfCluster.getPoint()[1]);
			jo.addProperty("amountOfPoints", c.getPoints().size());
			ja.add(jo);
		}
		
		return ja;
	}
	
	//Deletes all the gps data of users which havent´t updated their gps for a certain period of time
	private void checkForExpiredGps() {
		
		long date = (new Date()).getTime();
		List<String> usersToDel = new ArrayList<String>();
		
		synchronized(gpsAddingTime) {
			for(String user: gpsAddingTime.keySet()) {
				if (Math.abs(date - gpsAddingTime.get(user)) > maxTime) {
					usersToDel.add(user);
				}
			}
		}
		synchronized(gpsData) {
			for (String user: usersToDel) {
				if (gpsData.containsKey(user)) {
					gpsData.remove(user);
				}
			}
		}
	}
	
	private DoublePoint getCenter(Cluster<DoublePoint> c) {	
		double[] start = {0,0};
		for(DoublePoint dp: c.getPoints()) {
			start[0] += dp.getPoint()[0];
			start[1] += dp.getPoint()[1];
		}
		int size = c.getPoints().size();
		start[0] = start[0] / size;
		start[1] = start[1] / size;
		return new DoublePoint(start);
	}

}
