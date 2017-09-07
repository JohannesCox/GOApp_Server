package databaseTest;


import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventHandler;
import nl.jqno.equalsverifier.EqualsVerifier;

 public class EventTest extends DatabaseTest {
	EventHandler handler;
	//names for the json properties
	String neventId;
	String ntitle;
	String ndate;
	String nlocation;
	String ndescription;
	String nlastmodified;
	@Before 
	public void setUp() throws Exception {
		handler = new EventHandler();
		neventId = "eventId";
		ntitle = "title";
		ndate = "date";
		nlocation = "location";
		ndescription = "description";
		nlastmodified = "lastModified";
		super.setUp();
	}
	
	@Test
	public void testEqualsContract() {
		EqualsVerifier.forClass(Event.class).verify();
	}
	
	@Test
	public void testSerialize() {
		//values for the json properties
		String veventID = "eID1";
		Event event = handler.getEvent(veventID);
		String vtitle = event.getEventname();
		Date vdate = event.getDate();
		String vlocation = event.getLocation();
		String vdescription = event.getDescription();
		int vlastmodified = event.getLastmodified();
		
		//set the reference object
		JsonObject reference = new JsonObject();
		reference.addProperty(neventId, veventID);
		reference.addProperty(ntitle, vtitle);
		reference.addProperty(ndate, vdate.getTime());
		reference.addProperty(nlocation, vlocation);
		reference.addProperty(ndescription, vdescription);
		reference.addProperty(nlastmodified, vlastmodified);
		
		//create the object to test
		JsonObject obj = event.serialize();
		
		assertEquals(reference, obj);
		
		
	}

}
