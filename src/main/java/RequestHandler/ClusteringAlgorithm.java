package RequestHandler;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.ml.clustering.*;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ClusteringAlgorithm {
	
	private final double EPS = 0.001;
	private final int MINPNTS = 2;
	
	private HashMap<String, DoublePoint> gpsData;
	EuclideanDistance measurement;
	DBSCANClusterer<DoublePoint> clusterer;
	
	public ClusteringAlgorithm() {
		gpsData = new HashMap<String, DoublePoint>();
		measurement = new EuclideanDistance();
		clusterer = new DBSCANClusterer<DoublePoint>(EPS, MINPNTS, measurement);
	}
	
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
			
			int i = 0;
			for(DoublePoint dp: c.getPoints()) {
				jo.addProperty("Point" + i + "-lat", dp.getPoint()[0]);
				jo.addProperty("Point" + i + "-lng", dp.getPoint()[1]);
				i++;
			}
			
			ja.add(jo);
			
		}
		
		return ja;
	}
}
