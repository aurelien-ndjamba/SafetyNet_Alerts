package com.safetynets.alerts.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonsByAddressModelTest {

	PersonsByAddressModel personsByAddress = new PersonsByAddressModel();

	@BeforeEach
	public void setUp() {
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		persons.add(person);
		personsByAddress.setAddress("Paris");
		personsByAddress.setPersonsByAddress(persons);
	}

	@Test
	public void testEquals() {
		PersonsByAddressModel newPersonsByAddress = new PersonsByAddressModel();
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		persons.add(person);
		newPersonsByAddress.setAddress("Paris");
		newPersonsByAddress.setPersonsByAddress(persons);
		
		assertThat(personsByAddress.equals(newPersonsByAddress)).isEqualTo(true);
	}
	
	@Test
	public void testToString() {
		String expected = "PersonsByAddressModel(address=Paris, PersonsByAddress=[PersonModel(id=null, firstName=null, lastName=null, address=null, city=null, zip=0, phone=null, email=null)])";
		assertThat(personsByAddress.toString()).isEqualTo(expected);
	}
	
	@Test
	public void testHashcode() {
		assertThat(personsByAddress.hashCode()).isEqualTo(1125619233);
	}
	
	@Test
	public void testCanEqual() {

		// WHEN
		PersonsByAddressModel newPersonsByAddress = new PersonsByAddressModel();
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		persons.add(person);
		newPersonsByAddress.setAddress("Berlin");
		newPersonsByAddress.setPersonsByAddress(persons);
		// THEN
		assertThat(personsByAddress.canEqual(newPersonsByAddress)).isEqualTo(true);
	}



}
