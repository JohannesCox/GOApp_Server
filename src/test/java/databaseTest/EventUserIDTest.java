package databaseTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Database.EventUserID;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
/**
 * This class tests the EventUserID. 
 * The EventUserID is used to model the composite primary key of an EventUserRelation.
 * Since EventUserID is just a POJO-class there only the equals contract(correctness of equals() and hashCode) will be tested.
 * For testing EqualsVerifier will be used. 
 *
 */
public class EventUserIDTest {

	@Test
	public void testEqualsContract() {
		EqualsVerifier.forClass(EventUserID.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

}
