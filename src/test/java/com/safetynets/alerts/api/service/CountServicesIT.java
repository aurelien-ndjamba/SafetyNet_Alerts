package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.safetynets.alerts.api.service.CountService;
import com.safetynets.alerts.api.service.MedicalRecordService;
import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.repository.FireStationRepository;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;

class CountServicesIT {

	@Autowired
	private MedicalRecordService medicalRecordService;
	@Autowired
	private FireStationRepository fireStationRepository;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
//	private CountService countService;

	@Test
	void testGetAge() throws ParseException {
//		MedicalRecordService medicalRecordServiceMock = new MedicalRecordService();
//		medicalRecordServiceMock = mock(MedicalRecordService.class);
//		MedicalRecordRepository medicalRecordRepositoryMock = mock(MedicalRecordRepository.class);
//		MedicalRecordModel medicalRecordModelMock = new MedicalRecordModel();
//		
//		String firstName = "Eric";
//		String lastName = "Cadigan";
//		String birthdate = "08/06/1945";
//
//		List<Long> listIdsEntitiesMedicalRecords = new ArrayList<Long>();
//		listIdsEntitiesMedicalRecords.add((long) 22);
//		when(medicalRecordServiceMock.getlistIdsEntitiesMedicalRecords()).thenReturn(listIdsEntitiesMedicalRecords);
//		
//		medicalRecordModelMock.setFirstName(firstName);
//		medicalRecordModelMock.setLastName(lastName);
//		medicalRecordModelMock.setBirthdate(birthdate);
//		when(medicalRecordRepositoryMock.getById(any(Long.class))).thenReturn(medicalRecordModelMock);
//		
//		List<MedicalRecordModel> listMedicalRecords = new ArrayList<MedicalRecordModel>();
//		when(medicalRecordRepositoryMock.findAll()).thenReturn(listMedicalRecords);

//		when(medicalRecordModelMock.getFirstName()).thenReturn(firstName);
//		when(medicalRecordModelMock.getLastName()).thenReturn(lastName);
//		when(medicalRecordModelMock.getBirthdate()).thenReturn(birthdate);
		CountService countService = new CountService();
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		MedicalRecordService medicalRecordService = new MedicalRecordService();
		String firstName = "Eric";
		String lastName = "Cadigan";
		assertThat(countService.getAge(firstName, lastName)).isEqualTo((int) 76);
	}
	
	@Test
	public void testGetMedicalRecordByFirstNameAndLastName() throws ParseException {
		String firstName = "Eric";
		String lastName = "Cadigan";
		MedicalRecordService medicalRecordService = new MedicalRecordService();
		assertThat(medicalRecordService.getMedicalRecordByFirstNameAndLastName(firstName, lastName).getBirthdate()).isEqualTo("08/06/1945");
	}

}
