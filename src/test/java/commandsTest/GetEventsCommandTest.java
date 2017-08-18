package commandsTest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.*;

import com.google.gson.JsonObject;

import Database.Event;
import Database.EventUserHandler;
import requestHandler.commands.GetEventsCommand;

public class GetEventsCommandTest {

	private HashMap<String, Integer> input1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> input2 = new HashMap<String, Integer>();
	
	private List<Event> handlerOutput1 = new ArrayList<Event>();
	private List<Event> handlerOutput2 = new ArrayList<Event>();
	
	private String  eventId1 = "e1";
	private String  eventId2 = "e2";
	private String  eventId3 = "e3";
	
	private int lastMod1 = 0;
	private int lastMod2 = 0;
	private int lastMod3 = 0;
	
	private int newLM2 = 1;
	private int newLM3 = 1;
	
	private long date = 1503072074059L;
	
	private Event ev1;
	private Event ev2;
	private Event ev3;
	private Event changedEv2;
	private Event changedEv3;
	
	private String userId = "uId";
	
	private EventUserHandler euh1;
	private EventUserHandler euh2;
	
	private GetEventsCommand testGEC1;
	private GetEventsCommand testGEC2;
	
	@Before
	public void setUp() {
		
		input1.put(eventId1, lastMod1);
		input1.put(eventId2, lastMod2);
		input1.put(eventId3, lastMod3);
		
		input2.put(eventId1, lastMod1);
		input2.put(eventId2, lastMod2);
		input2.put(eventId3, lastMod3);
		
		ev1 = createTestE(eventId1, lastMod1);
		ev2 = createTestE(eventId2, lastMod2);
		ev3 = createTestE(eventId3, lastMod3);
		
		handlerOutput1.add(ev1);
		handlerOutput1.add(ev2);
		handlerOutput1.add(ev3);
		
		changedEv2 = createTestE(eventId2, newLM2);
		changedEv3 = createTestE(eventId3, newLM3);
		
		handlerOutput2.add(ev1);
		handlerOutput2.add(changedEv2);
		handlerOutput2.add(changedEv3);
		
		euh1 = createMock(EventUserHandler.class);
		expect(euh1.getAllUserEvents(userId)).andReturn(handlerOutput1);
		expect(euh1.isAdmin(userId, eventId1)).andReturn(false);
		expect(euh1.isAdmin(userId, eventId2)).andReturn(false);
		expect(euh1.isAdmin(userId, eventId3)).andReturn(true);
		replay(euh1);
		
		euh2 = createMock(EventUserHandler.class);
		expect(euh2.getAllUserEvents(userId)).andReturn(handlerOutput2);
		expect(euh2.isAdmin(userId, eventId1)).andReturn(false);
		expect(euh2.isAdmin(userId, eventId2)).andReturn(false);
		expect(euh2.isAdmin(userId, eventId3)).andReturn(true);
		replay(euh2);
		
		testGEC1 = new GetEventsCommand(userId, input1);
		testGEC2 = new GetEventsCommand(userId, input2);
		
		testGEC1.setEventUserHandler(euh1);
		testGEC2.setEventUserHandler(euh2);
		
	}
	
	@Test
	public void testNoChangeInEvents() {
		String result = testGEC1.process();
		//no event has changed
		assertEquals(result, "[]");
	}
	
	@Test
	public void testTwoChangesInEvents() {
		String result = testGEC2.process();
		
		String expectedRes = "[{\"eventId\":\"" + eventId2 + "\",\"title\":\"name\",\"date\":" + date + ",\"location\":\"location\","
				+ "\"description\":\"description\",\"lastModified\":" + newLM2 + ",\"isAdmin\":false,\"isDeleted\":false},{\"eventId\""
				+ ":\"" + eventId3 + "\",\"title\":\"name\",\"date\":" + date + ",\"location\":\"location\",\"description\":\"description\","
				+ "\"lastModified\":" + newLM3 + ",\"isAdmin\":true,\"isDeleted\":false}]";
		assertEquals(result, expectedRes);
	}
	
	private Event createTestE(String eId, int lastMod) {
		JsonObject jo = new JsonObject();
		jo.addProperty("eventId", eId);
		jo.addProperty("title", "name");
		jo.addProperty("date", date);
		jo.addProperty("location", "location");
		jo.addProperty("description", "description");
		jo.addProperty("lastModified", lastMod);
		Event event = createMock(Event.class);
		expect(event.serialize()).andReturn(jo);
		expect(event.getEventID()).andReturn(eId).anyTimes();
		expect(event.getLastmodified()).andReturn(lastMod);
		replay(event);
		return event;
	}
	
}
