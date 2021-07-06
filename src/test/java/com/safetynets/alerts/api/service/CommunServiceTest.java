package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;
import com.safetynets.alerts.api.repository.PersonRepository;

@SpringBootTest
public class CommunServiceTest {

	@Autowired
	private CommunService communService;
	@Mock
	private PersonService personServiceMock;
	@Mock
	private FireStationService fireStationServiceMock;
	@Mock
	private MedicalRecordService medicalRecordServiceMock;
	@Mock
	private CountService countServiceMock;
	@Mock
	private PersonRepository personRepositoryMock;
	@Mock
	private MedicalRecordRepository medicalRecordRepositoryMock;

	@Test
	public void testFindChildInfos() throws ParseException {

		// GIVEN
		List<PersonModel> persons = new ArrayList<PersonModel>();
		HashSet<PersonModel> relationShip = new HashSet<PersonModel>();

		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setAddress("Paris");
		person.setEmail("n.sarkozy@email.com");
		persons.add(person);

		PersonModel personFamily = new PersonModel();
		personFamily.setFirstName("Henry");
		personFamily.setLastName("Sarkozy");
		personFamily.setAddress("Paris");
		personFamily.setEmail("h.sarkozy@email.com");
		relationShip.add(personFamily);

		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		ArrayList<String> allergies = new ArrayList<String>();
		allergies.add("pollen");
		medicalRecord.setAllergies(allergies);
		ArrayList<String> medications = new ArrayList<String>();
		medications.add("pfizer");
		medicalRecord.setMedications(medications);
		medicalRecord.setBirthdate("24/05/2016");

		// WHEN
		when(personServiceMock.findByAddress("Paris")).thenReturn(persons);
		when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Nicolas", "Sarkozy")).thenReturn(medicalRecord);
		when(countServiceMock.findAge("24/05/2016")).thenReturn(2);
		when(personServiceMock.findRelationship("Paris", "Nicolas", "Sarkozy")).thenReturn(relationShip);
		communService.setPersonService(personServiceMock);
		communService.setMedicalRecordRepository(medicalRecordRepositoryMock);
		communService.setCountService(countServiceMock);

		// THEN
		assertThat(communService.findChildInfos("Paris").size()).isEqualTo(1);
	}

	@Test
	public void testFindPhoneAlert() throws ParseException {

		// GIVEN
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setAddress("Paris");
		fireStation.setStation((long) 75);
		fireStations.add(fireStation);

		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setAddress("Paris");
		person.setPhone("05555538686");
		persons.add(person);

		// WHEN
		when(fireStationServiceMock.findByStation(111)).thenReturn(fireStations);
		when(personServiceMock.findByAddress("Paris")).thenReturn(persons);
		communService.setFireStationService(fireStationServiceMock);
		communService.setPersonService(personServiceMock);

		// THEN
		assertThat(communService.findPhoneAlert((long) 111).size()).isEqualTo(1);

	}

	@Test
	public void testFindPersonsInfoAdvanced() throws ParseException {

		// GIVEN

		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Aurelia");
		person.setLastName("Florence");
		person.setAddress("Metz");
		person.setEmail("a.florence@email.com");
		persons.add(person);

		List<MedicalRecordModel> medicalRecords = new ArrayList<MedicalRecordModel>();
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		ArrayList<String> allergies = new ArrayList<String>();
		allergies.add("pollen");
		medicalRecord.setAllergies(allergies);
		ArrayList<String> medications = new ArrayList<String>();
		medications.add("pfizer");
		medicalRecord.setMedications(medications);
		medicalRecord.setBirthdate("24/05/2016");
		medicalRecords.add(medicalRecord);

		// WHEN
		when(personServiceMock.findByAddress("Metz")).thenReturn(persons);
		when(fireStationServiceMock.findStationByAddress("Metz")).thenReturn((long) 57);
		when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Aurelia", "Florence")).thenReturn(medicalRecord);
		when(countServiceMock.findAge("24/05/2016")).thenReturn(19);
		communService.setPersonService(personServiceMock);
		communService.setFireStationService(fireStationServiceMock);
		communService.setMedicalRecordRepository(medicalRecordRepositoryMock);
		communService.setCountService(countServiceMock);

		// THEN
		assertThat(communService.findPersonsInfoAdvanced("Metz").size()).isEqualTo(1);
	}

	@Test
	public void testFindPersonsByStations() throws ParseException {

		// GIVEN
		List<Long> stations = new ArrayList<Long>();
		stations.add((long) 7);
		stations.add((long) 8);
		stations.add((long) 9);

		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setAddress("Paris");
		fireStation.setStation((long) 75);
		fireStations.add(fireStation);

		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Aurelia");
		person.setLastName("Florence");
		person.setAddress("Metz");
		person.setEmail("a.florence@email.com");
		persons.add(person);

		// WHEN
		when(fireStationServiceMock.findFirestationsByManyStation(stations)).thenReturn(fireStations);
		when(personServiceMock.findByAddress("Paris")).thenReturn(persons);
		communService.setFireStationService(fireStationServiceMock);
		communService.setPersonService(personServiceMock);

		// THEN
		assertThat(communService.findPersonsByStations(stations).size()).isEqualTo(1);

	}

	@Test
	public void testFindPersonInfoGlobal() throws ParseException {

		// GIVEN

		PersonModel person = new PersonModel();
		person.setFirstName("Aurelia");
		person.setLastName("Florence");
		person.setAddress("Metz");
		person.setEmail("a.florence@email.com");

		List<MedicalRecordModel> medicalRecords = new ArrayList<MedicalRecordModel>();
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		ArrayList<String> allergies = new ArrayList<String>();
		allergies.add("pollen");
		medicalRecord.setAllergies(allergies);
		ArrayList<String> medications = new ArrayList<String>();
		medications.add("pfizer");
		medicalRecord.setMedications(medications);
		medicalRecord.setBirthdate("24/05/2016");
		medicalRecords.add(medicalRecord);

		// WHEN
		when(personRepositoryMock.findByFirstNameAndLastName("Aurelia", "Florence")).thenReturn(person);
		when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Aurelia", "Florence")).thenReturn(medicalRecord);
		when(medicalRecordServiceMock.findByLastName("Florence")).thenReturn(medicalRecords);
		communService.setPersonRepository(personRepositoryMock);
		communService.setMedicalRecordRepository(medicalRecordRepositoryMock);
		communService.setMedicalRecordService(medicalRecordServiceMock);

		// THEN
		assertThat(communService.findPersonInfoGlobal("Aurelia", "Florence").getAllergies()).contains("pollen");

	}

	@Test
	public void testFindCommunityEmail() throws ParseException {

		// GIVEN
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setAddress("Paris");
		person.setEmail("n.sarkozy@email.com");
		persons.add(person);

		// WHEN
		when(personServiceMock.findByCity("Paris")).thenReturn(persons);
		communService.setPersonService(personServiceMock);

		// THEN
		assertThat(communService.findCommunityEmail("Paris").get(0)).isEqualTo("n.sarkozy@email.com");
	}
}