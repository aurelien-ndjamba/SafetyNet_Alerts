package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.model.SpecificInfoPersonsModel;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;

@Service
public class CountService {

	@Autowired
	private PersonService personsService;
	@Autowired
	private MedicalRecordModel medicalRecord;
	@Autowired
	private MedicalRecordService medicalRecordService;
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	@Autowired
	private CountService countService;

	@SuppressWarnings("deprecation")
	public int getAge(String firstName, String lastName) throws ParseException {

		int age = 0;
		String birthdayInString;
		Date birthdayInDate;
		int currentYear;
		int birthdayYear;

		List<Long> listIdsEntitiesMedicalRecords = medicalRecordService.getlistIdsEntitiesMedicalRecords();

		for (Long i : listIdsEntitiesMedicalRecords) {
			medicalRecord = medicalRecordRepository.getById(i);

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

	public int getCountAdult(long stationNumber) throws ParseException {

		ArrayList<SpecificInfoPersonsModel> listSpecificPersonImpacted = personsService
				.getListSpecificPersonImpacted(stationNumber);

		int numberOfSpecificPersonImpacted = listSpecificPersonImpacted.size();
		int ageOfMajority = 18;
		int countAdult = 0;

		for (int i = 0; i < numberOfSpecificPersonImpacted; i++) {
			SpecificInfoPersonsModel specificPersonImpacted = listSpecificPersonImpacted.get(i);
			if (countService.getAge(specificPersonImpacted.getFirstName(),
					specificPersonImpacted.getLastName()) > ageOfMajority) {
				countAdult++;
			}
		}
		return countAdult;
	}

	public int getCountChildren(long stationNumber) throws ParseException {

		ArrayList<SpecificInfoPersonsModel> listSpecificPersonImpacted = personsService
				.getListSpecificPersonImpacted(stationNumber);

		int numberOfSpecificPersonImpacted = listSpecificPersonImpacted.size();
		int countAdult = countService.getCountAdult(stationNumber);
		int countChildren = numberOfSpecificPersonImpacted - countAdult;

		return countChildren;
	}
}