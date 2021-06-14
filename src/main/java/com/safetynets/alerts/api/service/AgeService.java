package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.MedicalRecordsModel;
import com.safetynets.alerts.api.repository.MedicalrecordsRepository;

@Service
public class AgeService {

	@Autowired
	private MedicalRecordsModel medicalRecord;
	@Autowired
	private MedicalrecordsRepository medicalRecordsRepository;
	@Autowired
	private MedicalRecordsService medicalRecordsService;

	@SuppressWarnings("deprecation")
	public int getAge(String firstName, String lastName) throws ParseException {

		int age = 0;
		String birthdayInString;
		Date birthdayInDate;
		int currentYear;
		int birthdayYear;
		

		List<Long> listIdsEntitiesMedicalRecords = medicalRecordsService.getlistIdsEntitiesMedicalRecords();

		for (Long i : listIdsEntitiesMedicalRecords) {
			medicalRecord = medicalRecordsRepository.getById(i);

			if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {

				// Date de naissance sous le type String
				birthdayInString = medicalRecord.getBirthdate();
				// Date de naissance sous le type Date
				birthdayInDate = new SimpleDateFormat("MM/dd/yyyy").parse(birthdayInString);
				// Année de naissance sous le type Date
				birthdayYear = 1900 + birthdayInDate.getYear();
				// Année courante
				currentYear = Calendar.getInstance().get(Calendar.YEAR);
				// Calcul de l'age
				age = currentYear - birthdayYear;
			}
		}
		
		return age;
	}
}