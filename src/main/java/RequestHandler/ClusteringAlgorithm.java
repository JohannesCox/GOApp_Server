package RequestHandler;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.ml.clustering.*;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class ClusteringAlgorithm {
	
	//the maximum distance for 2 points to be in the same cluster. Is equal to about 60m.
	private final double EPS = 0.0007;
	
	//The minimum of points which can build a cluster. The value 1 means that a cluster contains at least 2 points.
	private final int MINPNTS = 1;
	
	private HashMap<String, DoublePoint> gpsData;
	EuclideanDistance measurement;
	DBSCANClusterer<DoublePoint> clusterer;
	
	public ClusteringAlgorithm() {
		gpsData = new HashMap<String, DoublePoint>();
		measurement = new EuclideanDistance();
		clusterer = new DBSCANClusterer<DoublePoint>(EPS, MINPNTS, measurement);
	}
	
	/**
	 *  Updates the GPS and returns the current cluster of the group locations.
	 * @param uId	the userId
	 * @param dp	The location of the user as DoublePoint: {lat,lng}
	 * @return	Returns a Cluster saved in a JsonObject.
	 */
	public JsonArray updateGPS(String uId, DoublePoint dp) {
		gpsData.put(uId, dp);
		return calculate();
	}
	
	/**
	 * returns true if all user stopped the event
	 */
	public boolean stopEvent(String uId) {
		
		synchronized(gpsData) {
			gpsData.remove(uId);
			return (gpsData.isEmpty());
		}
		
	}
	
	private JsonArray calculate() {
		
		//TODO gps veraltet?
		
		List<Cluster<DoublePoint>> clusters = clusterer.cluster(gpsData.values());
		JsonArray ja = new JsonArray();
		
		for(Cluster<DoublePoint> c: clusters) {
			JsonObject jo = new JsonObject();
			DoublePoint centerOfCluster = getCenter(c);
			jo.addProperty("lat", centerOfCluster.getPoint()[0]);
			jo.addProperty("lng", centerOfCluster.getPoint()[1]);
			ja.add(jo);
		}
		
		return ja;
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
