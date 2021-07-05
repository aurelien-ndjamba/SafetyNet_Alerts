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

import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;

@SpringBootTest
public class MedicalRecordTest {

	@Autowired
	private MedicalRecordService medicalRecordService;
	@Mock
	private MedicalRecordRepository medicalRecordRepositoryMock;

	@Test
	public void testFindAll() throws ParseException {
		// GIVEN
		List<MedicalRecordModel> medicalRecords = new ArrayList<MedicalRecordModel>();
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setFirstName("Nicolas");
		medicalRecord.setLastName("Sarkozy");
		medicalRecord.setBirthdate("24/05/2016");
		medicalRecords.add(medicalRecord);
		// WHEN
		when(medicalRecordRepositoryMock.findAll()).thenReturn(medicalRecords);
		medicalRecordService.setMedicalRecordRepository(medicalRecordRepositoryMock);
		// THEN
		assertThat(medicalRecordService.findAll().size()).isEqualTo(1);
	}

	@Test
	public void testFindById() throws ParseException {
		// GIVEN
		List<MedicalRecordModel> medicalRecords = new ArrayList<MedicalRecordModel>();
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setFirstName("Nicolas");
		medicalRecord.setLastName("Sarkozy");
		medicalRecord.setBirthdate("24/05/2016");
		medicalRecords.add(medicalRecord);
		// WHEN
		when(medicalRecordRepositoryMock.findById(1)).thenReturn(medicalRecord);
		medicalRecordService.setMedicalRecordRepository(medicalRecordRepositoryMock);
		// THEN
		assertThat(medicalRecordService.findById(1).getFirstName()).isEqualTo("Nicolas");
	}

	@Test
	public void testFindByLastName() throws ParseException {
		// GIVEN
		List<MedicalRecordModel> medicalRecords = new ArrayList<MedicalRecordModel>();
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setFirstName("Nicolas");
		medicalRecord.setLastName("Sarkozy");
		medicalRecord.setBirthdate("24/05/2016");
		medicalRecords.add(medicalRecord);
		// WHEN
		when(medicalRecordRepositoryMock.findByLastName("Sarkozy")).thenReturn(medicalRecords);
		medicalRecordService.setMedicalRecordRepository(medicalRecordRepositoryMock);
		// THEN
		assertThat(medicalRecordService.findByLastName("Sarkozy").size()).isEqualTo(1);
	}

	@Test
	public void testFindByFirstNameAndLastName() throws ParseException {
		// GIVEN
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setFirstName("Nicolas");
		medicalRecord.setLastName("Sarkozy");
		medicalRecord.setBirthdate("24/05/2016");
		// WHEN
		when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Nicolas", "Sarkozy")).thenReturn(medicalRecord);
		medicalRecordService.setMedicalRecordRepository(medicalRecordRepositoryMock);
		// THEN
		assertThat(medicalRecordService.findByFirstNameAndLastName("Nicolas", "Sarkozy")).isEqualTo(medicalRecord);
	}

	@Test
	public void testSave() throws ParseException {
		// GIVEN
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setId((long) 15);
		medicalRecord.setFirstName("Nicolas");
		medicalRecord.setLastName("Sarkozy");
		medicalRecord.setBirthdate("24/05/2016");
		// WHEN
		when(medicalRecordRepositoryMock.save(medicalRecord)).thenReturn(medicalRecord);
		medicalRecordService.setMedicalRecordRepository(medicalRecordRepositoryMock);
		// THEN
		assertThat(medicalRecordService.save(medicalRecord).getId()).isEqualTo(null);
	}

	@Test
	public void testUpdate() throws ParseException {
		// GIVEN
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setId((long) 11);
		medicalRecord.setFirstName("Nicolas");
		medicalRecord.setLastName("Sarkozy");
		medicalRecord.setBirthdate("24/05/2016");
		MedicalRecordModel medicalRecordInDB = new MedicalRecordModel();
		medicalRecordInDB.setId((long) 1111);
		medicalRecordInDB.setBirthdate("10/02/1955");
		// WHEN
		when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Nicolas", "Sarkozy"))
				.thenReturn(medicalRecordInDB);
		when(medicalRecordRepositoryMock.save(medicalRecord)).thenReturn(medicalRecord);
		medicalRecordService.setMedicalRecordRepository(medicalRecordRepositoryMock);
		// THEN
		assertThat(medicalRecordService.update(medicalRecord).getId()).isEqualTo(1111);
	}

	@Test
	public void testDelete() throws ParseException {
		// GIVEN
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setFirstName("Nicolas");
		medicalRecord.setLastName("Sarkozy");
		medicalRecord.setBirthdate("24/05/2016");
		// WHEN
		when(medicalRecordRepositoryMock.count()).thenReturn((long) 1);
		when(medicalRecordRepositoryMock.existsById((long) 0)).thenReturn(false);
		when(medicalRecordRepositoryMock.existsById((long) 1)).thenReturn(true);
		when(medicalRecordRepositoryMock.findById(1)).thenReturn(medicalRecord);
		medicalRecordService.setMedicalRecordRepository(medicalRecordRepositoryMock);
		// THEN
		assertThat(medicalRecordService.delete("NicolasSarkozy").getBirthdate()).isEqualTo("24/05/2016");
	}
}
