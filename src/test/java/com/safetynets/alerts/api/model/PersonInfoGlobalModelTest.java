package com.safetynets.alerts.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PersonInfoGlobalModelTest {

	PersonInfoGlobalModel personInfoGlobal = new PersonInfoGlobalModel();
	PersonInfoGlobalModel newPersonInfoGlobal = new PersonInfoGlobalModel();

	@Test
	public void testEquals() {
		assertThat(personInfoGlobal.equals(newPersonInfoGlobal)).isEqualTo(true);
	}
	
	@Test
	public void testToString() {
		String expected = "PersonInfoGlobalModel(firstName=null, lastName=null, address=null, age=0, email=null, medications=null, allergies=null)";
		assertThat(personInfoGlobal.toString()).isEqualTo(expected);
	}
	
	@Test
	public void testCanEqual() {
		assertThat(personInfoGlobal.canEqual(newPersonInfoGlobal)).isEqualTo(true);
	}

	@Test
	public void testHashCode() {
		assertThat(personInfoGlobal.hashCode()).isEqualTo(-1222542993);
	}

}
