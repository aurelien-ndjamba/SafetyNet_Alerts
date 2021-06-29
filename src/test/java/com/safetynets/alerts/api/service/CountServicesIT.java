package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.safetynets.alerts.api.model.MedicalRecordModel;

//@WebMvcTest(controllers = CountService.class)
class CountServicesIT {

//	@Autowired
//	private MedicalRecordService medicalRecordService;
//	@Autowired
//	private FireStationRepository fireStationRepository;
//	@Autowired
//	private FireStationService fireStationService;
//	@Autowired
//	private CountService countService;
	@Autowired
	
	@Mock
	private MedicalRecordService medicalRecordService;
	
	@MockBean
	private ReaderJsonService readerJsonService;
	
	@MockBean
	private CountService countService;
	
	@Test
	public void testGetAge() throws ParseException {
		
		// GIVEN
		String firstName = "Eric";
		String lastName = "Cadigan";
		String birthdate = "08/06/1945";
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setFirstName(firstName);
		medicalRecord.setLastName(lastName);
		medicalRecord.setBirthdate(birthdate);
//		CountService countService = new CountService();
		
		// WHEN
		when(medicalRecordService.getMedicalRecordByFirstNameAndLastName("Eric", "Cadigan")).thenReturn(medicalRecord);
		
		// THEN
		CountService countService = new CountService();
		assertThat(countService.getAge("Eric", "Cadigan")).isEqualTo((int) 76);
	}
	
	@Test
	public void testGetMedicalRecordByFirstNameAndLastName() throws ParseException {
		String firstName = "Eric";
		String lastName = "Cadigan";
		MedicalRecordService medicalRecordService = new MedicalRecordService();
		assertThat(medicalRecordService.getMedicalRecordByFirstNameAndLastName(firstName, lastName).getBirthdate()).isEqualTo("08/06/1945");
	}

}
