package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynets.alerts.api.model.MedicalRecordModel;

@SpringBootTest
public class MedicalRecordIT {

	@Autowired
	private MedicalRecordService medicalRecordService;

	@Test
	public void testFindAll() throws ParseException {
		assertThat(medicalRecordService.findAll().size()).isEqualTo(23);
	}

	@Test
	public void testFindById() throws ParseException {
		assertThat(medicalRecordService.findById(1).getFirstName()).isEqualTo("John");
	}
	
	@Test
	public void testFindByLastName() throws ParseException {
		assertThat(medicalRecordService.findByLastName("Boyd").size()).isEqualTo(6);
	}
	
	@Test
	public void testFindByFirstNameAndLastName() throws ParseException {
		assertThat(medicalRecordService.findByFirstNameAndLastName("John", "Boyd").getBirthdate()).isEqualTo("03/06/1984");
	}

	@Test
	public void testSave() throws ParseException {
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setFirstName("Aurelien");
		medicalRecord.setLastName("David");
		assertThat(medicalRecordService.save(medicalRecord)).isEqualTo(medicalRecord);
	}

	@Test
	public void testUpdate() throws ParseException {
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Boyd");
		medicalRecord.setBirthdate("25/12/2000");
		assertThat(medicalRecordService.update(medicalRecord).getBirthdate()).isEqualTo("25/12/2000");
	}
	
}