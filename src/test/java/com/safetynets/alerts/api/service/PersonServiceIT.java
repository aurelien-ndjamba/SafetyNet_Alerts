package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynets.alerts.api.model.PersonModel;

@SpringBootTest
public class PersonServiceIT {

	@Autowired
	private PersonService personService;

	@Test
	public void testFindAll() throws ParseException {
		assertThat(personService.findAll().size()).isEqualTo(23);
	}

	@Test
	public void testFindById() throws ParseException {
		assertThat(personService.findById(1).getFirstName()).isEqualTo("John");
	}

	@Test
	public void testFindByAddress() throws ParseException {
		assertThat(personService.findByAddress("748 Townings Dr").size()).isEqualTo(2);
	}

	@Test
	public void testFindByCity() throws ParseException {
		assertThat(personService.findByCity("Culver").size()).isEqualTo(23);
	}

	@Test
	public void testFindByLastName() throws ParseException {
		assertThat(personService.findByLastName("Boyd").size()).isEqualTo(6);
	}

	@Test
	public void testFindPersonsByStation() throws ParseException {
		assertThat(personService.findPersonsByStation((long) 4).size()).isEqualTo(1);
	}

	@Test
	public void testFindRelationship() throws ParseException {
		assertThat(personService.findRelationship("1509 Culver St", "John", "Boyd").size()).isEqualTo(4);
	}

	@Test
	public void testSave() throws ParseException {
		PersonModel person = new PersonModel();
		person.setId((long)1);
		person.setFirstName("Aurelien");
		person.setLastName("David");
		assertThat(personService.save(person).getId()).isEqualTo(24);
	}

	@Test
	public void testUpdate() throws ParseException {
		PersonModel person = new PersonModel();
		person.setFirstName("John");
		person.setLastName("Boyd");
		person.setPhone("0687870778");
		assertThat(personService.update(person).getPhone()).isEqualTo("0687870778");
	}

	@Test
	public void testDeletePersonByLastNameFirstname() throws ParseException {
		assertThat(personService.delete("EricCadigan").getId()).isEqualTo(23);
	}
}
