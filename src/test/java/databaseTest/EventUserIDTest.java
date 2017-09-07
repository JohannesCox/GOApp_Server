package databaseTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Database.EventUserID;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class EventUserIDTest {

	@Test
	public void testEqualsContract() {
		EqualsVerifier.forClass(EventUserID.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

}
