package CommandsTest;
import static org.junit.Assert.*;

import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.junit.*;

import RequestHandler.ClusteringAlgorithm;

/**
 * This class includes tests for the ClusteringAlgorithm class. 
 * It is checked if the clustering works correctly and that no single GPS-Points outside a Cluster
 * are returned.
 */
public class ClusteringAlgorithmTest {

	private DoublePoint loc1;
	private DoublePoint loc2;
	private DoublePoint loc3;
	private DoublePoint loc4;
	private DoublePoint loc5;
	
	String user1 = "user1";
	String user2 = "user2";
	String user3 = "user3";
	String user4 = "user4";
	String user5 = "user5";
	
	private ClusteringAlgorithm ca;
	
	@Before
	public void setUpGPS() {	
		
		double[] gps1 = {49.009428, 8.404064};	
		double[] gps2 = {49.009428,8.404257}; //Point1 and 2 are close
		double[] gps3 = {49.011729,8.404064};	
		double[] gps4 = {49.011680, 8.403946}; //Point 3 and 4 are close
		double[] gps5 = {49.011603, 8.397154}; //Point 5 is an outsider
		loc1 = new DoublePoint(gps1);
		loc2 = new DoublePoint(gps2);
		loc3 = new DoublePoint(gps3);
		loc4 = new DoublePoint(gps4);
		loc5 = new DoublePoint(gps5);	
		ca = new ClusteringAlgorithm();
		
	}
	
	@Test
	public void clusteringAlgorithmTest() {
		String result1 = ca.updateGPS(user1, loc1).toString();
		assertEquals(result1, "[]"); //No groups locations exist yet

		String result2 = ca.updateGPS(user2, loc2).toString();
		assertEquals(result2, "[{\"lat\":49.009428,\"lng\":8.4041605}]"); //the middle between Point1 and Point2

		String result3 = ca.updateGPS(user3, loc3).toString();
		//As Point 3 is not in the same cluster as point 1 and point 2 nothing changes
		assertEquals(result3, "[{\"lat\":49.009428,\"lng\":8.4041605}]"); 
		System.out.println(result3);
		
		String result4 = ca.updateGPS(user4, loc4).toString();
		//Now there are 2 clusters
		assertEquals(result4, "[{\"lat\":49.009428,\"lng\":8.4041605},{\"lat\":49.0117045,\"lng\":8.404005}]"); 
		
		String result5 = ca.updateGPS(user5, loc5).toString();
		//Nothing changes as the 5th point is an outsider
		assertEquals(result5, "[{\"lat\":49.009428,\"lng\":8.4041605},{\"lat\":49.0117045,\"lng\":8.404005}]"); 
		
		//user1 updates his position
		double[] gps1 = new double[2];
		gps1[0] = 49.009400;
		gps1[1] = 8.404012;	
		loc1 = new DoublePoint(gps1);
		String result6 = ca.updateGPS(user1, loc1).toString();
		//The first cluster location changed
		assertEquals(result6, "[{\"lat\":49.009414,\"lng\":8.4041345},{\"lat\":49.0117045,\"lng\":8.404005}]"); 
		
		//user3 stops the event
		boolean empty = ca.stopEvent(user3);
		assertFalse(empty);
		
		String result7 = ca.updateGPS(user5, loc5).toString();
		//There is no more second cluster as user3 stopped the event
		//the other points did not change
		assertEquals(result7, "[{\"lat\":49.009414,\"lng\":8.4041345}]"); 
	}

}
