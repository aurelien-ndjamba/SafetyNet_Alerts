package com.safetynets.alerts.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InfoByStationNumberModelTest {

	InfoByStationNumberModel infoByStationNumber = new InfoByStationNumberModel();

	@BeforeEach
	public void setUp() {

		PersonModel person = new PersonModel();
		ArrayList<PersonModel> personsModel = new ArrayList<PersonModel>();
		personsModel.add(person);

		infoByStationNumber.setStation((long) 9);
		infoByStationNumber.setCountAdult(7);
		infoByStationNumber.setCountChildren(14);
		infoByStationNumber.setPersonsByStation(personsModel);
	}

	@Test
	public void testSetCountChildren() {

		// WHEN
		infoByStationNumber.setCountChildren(15);
		// THEN
		assertThat(infoByStationNumber.getCountChildren()).isEqualTo(15);
	}

	@Test
	public void testSetCountAdult() {

		// WHEN
		infoByStationNumber.setCountAdult(9);
		// THEN
		assertThat(infoByStationNumber.getCountAdult()).isEqualTo(9);
	}

	@Test
	public void testSetStation() {

		// WHEN
		infoByStationNumber.setStation(75);
		// THEN
		assertThat(infoByStationNumber.getStation()).isEqualTo(75);
	}

	@Test
	public void testSetPersonsByStation() {
		// GIVEN
		PersonModel newPerson = new PersonModel();
		newPerson.setZip(57000);
		ArrayList<PersonModel> persons = new ArrayList<PersonModel>();
		persons.add(newPerson);
		// WHEN
		infoByStationNumber.setPersonsByStation(persons);
		// THEN
		assertThat(infoByStationNumber.getPersonsByStation()).isEqualTo(persons);
	}
	
	@Test
	public void testEquals() {
		// GIVEN
		PersonModel newPerson = new PersonModel();
		ArrayList<PersonModel> persons = new ArrayList<PersonModel>();
		persons.add(newPerson);
		InfoByStationNumberModel newInfoByStationNumber = new InfoByStationNumberModel();
		newInfoByStationNumber.setStation((long) 9);
		newInfoByStationNumber.setCountAdult(7);
		newInfoByStationNumber.setCountChildren(14);
		newInfoByStationNumber.setPersonsByStation(persons);
		// THEN
		assertThat(infoByStationNumber).isEqualTo(newInfoByStationNumber);
	}
	
	@Test
	public void testToString() {
		
		String expected = "InfoByStationNumberModel(PersonsByStation=[PersonModel(id=null, firstName=null, lastName=null, address=null, city=null, zip=0, phone=null, email=null)], Station=9, CountAdult=7, CountChildren=14)";
		assertThat(infoByStationNumber.toString()).isEqualTo(expected);
	}
	
	@Test
	public void testHashCode() {
		assertThat(infoByStationNumber.hashCode()).isEqualTo(898398484);
	}
	
	@Test
	public void testCanEqual() {
		// GIVEN
		PersonModel newPerson = new PersonModel();
		ArrayList<PersonModel> persons = new ArrayList<PersonModel>();
		persons.add(newPerson);
		InfoByStationNumberModel newInfoByStationNumber = new InfoByStationNumberModel();
		newInfoByStationNumber.setStation((long) 19);
		newInfoByStationNumber.setCountAdult(17);
		newInfoByStationNumber.setCountChildren(15);
		newInfoByStationNumber.setPersonsByStation(persons);
		// THEN
		assertThat(infoByStationNumber.canEqual(newInfoByStationNumber)).isEqualTo(true);
	}

}
