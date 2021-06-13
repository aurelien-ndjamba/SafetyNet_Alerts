package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynets.alerts.api.model.MedicalRecordsModel;
import com.safetynets.alerts.api.model.PersonsModel;
import com.safetynets.alerts.api.repository.MedicalrecordsRepository;
import com.safetynets.alerts.api.repository.PersonsRepository;

@Component
public class AgeService {

	@Autowired
	private MedicalRecordsModel medicalRecord;
	@Autowired
	private MedicalrecordsRepository medicalRecordsRepository;
	@Autowired
	private MedicalRecordsService medicalRecordsService;

	public int getAge(String firstName, String lastName) throws ParseException {

		int age;
		String birthdayInString;
		Date birthdayInDate;
		Calendar calendar;
		int currentYear;
		int YearOfbirth;

		List<Long> listIdsEntitiesMedicalRecords = medicalRecordsService.getlistIdsEntitiesMedicalRecords();

		for (Long i : listIdsEntitiesMedicalRecords) {

			medicalRecord = medicalRecordsRepository.getById(i);

			if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {

				birthdayInString = medicalRecord.getBirthdate();
				birthdayInDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthdayInString);
				YearOfbirth = 
				
				calendar = Calendar.getInstance();	
		        currentYear = calendar.get(Calendar.YEAR);

				age = currentYear - ;
			}
		}
		return age;
	}
}