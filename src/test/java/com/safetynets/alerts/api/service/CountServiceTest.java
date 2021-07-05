package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.model.PersonModel;

@SpringBootTest
public class CountServiceTest {

	@Autowired
	private CountService countService;
	@Mock
	private FireStationService fireStationServiceMock;
	@Mock
	private PersonService personServiceMock;
	@Mock
	private MedicalRecordService medicalRecordServiceMock;

	@Test
	public void testFindAge() throws ParseException {
		assertThat(countService.findAge("08/06/1945")).isEqualTo((int) 76);
	}

	@Test
	public void testFindCountAdult() throws ParseException {
		
		// GIVEN
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setAddress("Paris");
		fireStation.setStation((long)75);
		fireStations.add(fireStation);
		
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setAddress("Paris");
		persons.add(person);
		
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setBirthdate("28/01/1955");
		
		// WHEN
		when(fireStationServiceMock.findByStation(111)).thenReturn(fireStations);
		when(personServiceMock.findByAddress("Paris")).thenReturn(persons);
		when(medicalRecordServiceMock.findByFirstNameAndLastName("Nicolas", "Sarkozy")).thenReturn(medicalRecord);
		countService.setFireStationService(fireStationServiceMock);
		countService.setPersonService(personServiceMock);
		countService.setMedicalRecordService(medicalRecordServiceMock);
		
		// THEN
		assertThat(countService.findCountAdult(111)).isEqualTo(1);
		verify(medicalRecordServiceMock).findByFirstNameAndLastName("Nicolas", "Sarkozy");
		
	}

	@Test
	public void testFindCountChildren() throws ParseException {
		
		// GIVEN
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setAddress("Paris");
		fireStation.setStation((long)75);
		fireStations.add(fireStation);
		
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Nicolas");
		person.setLastName("Sarkozy");
		person.setAddress("Paris");
		persons.add(person);
		
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setBirthdate("24/05/2016");
		
		// WHEN
		when(fireStationServiceMock.findByStation(111)).thenReturn(fireStations);
		when(personServiceMock.findByAddress("Paris")).thenReturn(persons);
		when(medicalRecordServiceMock.findByFirstNameAndLastName("Nicolas", "Sarkozy")).thenReturn(medicalRecord);
		countService.setFireStationService(fireStationServiceMock);
		countService.setPersonService(personServiceMock);
		countService.setMedicalRecordService(medicalRecordServiceMock);
		
		// THEN
		assertThat(countService.findCountChildren(111)).isEqualTo(1);
		verify(medicalRecordServiceMock).findByFirstNameAndLastName("Nicolas", "Sarkozy");
	}
}