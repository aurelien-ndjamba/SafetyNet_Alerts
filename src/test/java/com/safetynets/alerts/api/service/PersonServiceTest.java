package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.repository.PersonRepository;

@SpringBootTest
public class PersonServiceTest {

	@Autowired
	private PersonService personService;
	@Mock
	private PersonRepository personRepositoryMock;
	@Mock
	private FireStationService fireStationServiceMock;

	@Test
	public void testFindAll() throws ParseException {
		// GIVEN
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setCity("Paris");
		persons.add(person);
		// WHEN
		when(personRepositoryMock.findAll()).thenReturn(persons);
		personService.setPersonRepository(personRepositoryMock);
		// THEN
		assertThat(personService.findAll().size()).isEqualTo(1);
	}

	@Test
	public void testFindById() throws ParseException {
		// GIVEN
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setCity("Paris");
		persons.add(person);
		// WHEN
		when(personRepositoryMock.findById(1)).thenReturn(person);
		personService.setPersonRepository(personRepositoryMock);
		// THEN
		assertThat(personService.findById(1).getFirstName()).isEqualTo("Nicolas");
	}

	@Test
	public void testFindByAddress() throws ParseException {
		// GIVEN
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setCity("Paris");
		persons.add(person);
		// WHEN
		when(personRepositoryMock.findByAddress("748 Townings Dr")).thenReturn(persons);
		personService.setPersonRepository(personRepositoryMock);
		// THEN
		assertThat(personService.findByAddress("748 Townings Dr").size()).isEqualTo(1);
	}

	@Test
	public void testFindByCity() throws ParseException {
		// GIVEN
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setCity("Paris");
		persons.add(person);
		// WHEN
		when(personRepositoryMock.findByCity("Culver")).thenReturn(persons);
		personService.setPersonRepository(personRepositoryMock);
		// THEN
		assertThat(personService.findByCity("Culver").size()).isEqualTo(1);
	}

	@Test
	public void testFindByLastName() throws ParseException {
		// GIVEN
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setCity("Paris");
		persons.add(person);
		// WHEN
		when(personRepositoryMock.findByLastName("Boyd")).thenReturn(persons);
		personService.setPersonRepository(personRepositoryMock);
		// THEN
		assertThat(personService.findByLastName("Boyd").size()).isEqualTo(1);
	}

	@Test
	public void testFindPersonsByStation() throws ParseException {
		// GIVEN
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setCity("Paris");
		persons.add(person);

		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setId((long) 25);
		fireStation.setAddress("Paris");
		fireStation.setStation(75);
		fireStations.add(fireStation);

		// WHEN
		when(fireStationServiceMock.findByStation((long) 1)).thenReturn(fireStations);
		personService.setFireStationService(fireStationServiceMock);

		when(personRepositoryMock.findByAddress("Paris")).thenReturn(persons);
		personService.setPersonRepository(personRepositoryMock);

		// THEN
		assertThat(personService.findPersonsByStation((long) 1)).isEqualTo(persons);
	}

	@Test
	public void testFindRelationship() throws ParseException {
		// GIVEN
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setAddress("Paris");
		
		PersonModel personBrother = new PersonModel();
		personBrother.setFirstName("Nicolas");
		personBrother.setLastName("Thomas");
		personBrother.setAddress("Paris");
		
		persons.add(person);
		persons.add(personBrother);

		// WHEN
		when(personRepositoryMock.findByFirstNameAndLastName("Nicolas", "Sarkozy")).thenReturn(person);
		personService.setPersonRepository(personRepositoryMock);
		
		when(personRepositoryMock.findByLastName("Sarkozy")).thenReturn(persons);
		personService.setPersonRepository(personRepositoryMock);

		// THEN
		assertThat(personService.findRelationship("Paris", "Nicolas", "Sarkozy").size()).isEqualTo(1);
	}

	@Test
	public void testSave() throws ParseException {
		// GIVEN
		PersonModel person = new PersonModel();
		person.setId((long) 15);
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		// WHEN
		when(personRepositoryMock.save(person)).thenReturn(person);
		personService.setPersonRepository(personRepositoryMock);
		// THEN
		assertThat(personService.save(person).getId()).isEqualTo(null);
	}

	@Test
	public void testUpdate() throws ParseException {
		// GIVEN
		PersonModel person = new PersonModel();
		person.setId((long)11);
		person.setFirstName("Andre");
		person.setLastName("Eric");
		PersonModel personInDb = new PersonModel();
		personInDb.setId((long)1111);
		personInDb.setFirstName("Nicolas");
		personInDb.setLastName("Sarkozy");
		// WHEN
		when(personRepositoryMock.findByFirstNameAndLastName("Andre", "Eric")).thenReturn(personInDb);
		when(personRepositoryMock.save(person)).thenReturn(person);
		personService.setPersonRepository(personRepositoryMock);
		// THEN
		assertThat(personService.update(person).getId()).isEqualTo(1111);
	}

	@Test
	public void testDelete() throws ParseException {
		// GIVEN
		PersonModel person = new PersonModel();
		person.setId((long)1111);
		person.setFirstName("Andre");
		person.setLastName("Eric");
		// WHEN
		when(personRepositoryMock.count()).thenReturn((long) 1);
		when(personRepositoryMock.existsById((long) 0)).thenReturn(false);
		when(personRepositoryMock.existsById((long) 1)).thenReturn(true);
		when(personRepositoryMock.findById(1)).thenReturn(person);
		personService.setPersonRepository(personRepositoryMock);
		// THEN
		assertThat(personService.delete("AndreEric").getId()).isEqualTo(1111);
	}
}
