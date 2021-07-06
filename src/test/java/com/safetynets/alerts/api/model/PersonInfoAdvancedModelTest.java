package com.safetynets.alerts.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PersonInfoAdvancedModelTest {

	PersonInfoAdvancedModel personInfoAdvanced  = new PersonInfoAdvancedModel();
	PersonInfoAdvancedModel newPersonInfoAdvanced = new PersonInfoAdvancedModel();

	@Test
	public void testEquals() {
		assertThat(personInfoAdvanced.equals(newPersonInfoAdvanced)).isEqualTo(true);
	}
	
	@Test
	public void testToString() {
		String expected = "PersonInfoAdvancedModel(id=null, firstName=null, lastName=null, address=null, stationNumber=null, age=0, phone=null, medications=null, allergies=null)";
		assertThat(personInfoAdvanced.toString()).isEqualTo(expected);
	}
	
	@Test
	public void testCanEqual() {
		assertThat(personInfoAdvanced.canEqual(newPersonInfoAdvanced)).isEqualTo(true);
	}

	@Test
	public void testHashCode() {
		assertThat(personInfoAdvanced.hashCode()).isEqualTo(640434283);
	}

}
