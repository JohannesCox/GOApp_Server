package databaseTest;

import org.junit.Test;

import Database.EventUserRelation;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Tests the class EventUserRelation. Since EventUserRelation is just a POJO-class there only the equals contract will be tested.
 * For testing the EqualsVerifier will be used.
 *
 */
public class EventUserRelationTest {

	@Test
	public void testEqualsContract() {
		EqualsVerifier.forClass(EventUserRelation.class).verify();
	}

}
