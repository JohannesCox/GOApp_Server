package databaseTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Database.EventUserRelation;
import nl.jqno.equalsverifier.EqualsVerifier;

public class EventUserRelationTest {

	@Test
	public void testEqualsContract() {
		EqualsVerifier.forClass(EventUserRelation.class).verify();
	}

}
