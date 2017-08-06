package RequestHandler;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.ml.clustering.*;

public class ClusteringAlgorithm {
	
	private HashMap<String, DoublePoint> gpsData;
	
	public ClusteringAlgorithm(String uId, DoublePoint dp) {
		gpsData = new HashMap<String, DoublePoint>();
	}
	
	public List<Cluster<DoublePoint>> updateGPS(String uId, DoublePoint dp) {
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
	
	private List<Cluster<DoublePoint>> calculate() {
		return null;
	}
}
