package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.FireStationDataBaseModel;
import com.safetynets.alerts.api.model.MedicalRecordDataBaseModel;
import com.safetynets.alerts.api.model.PersonDataBaseModel;

@Service
public class CountService {

	@Autowired
	PersonService personService;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private MedicalRecordService medicalRecordService;

	// ----------------------------------------------------------------------------------------
	// GETAGE: Methode pour supprimer une personne à partir d'un
	// id=firstNamelastName
	// ----------------------------------------------------------------------------------------
	@SuppressWarnings("deprecation")
	public int getAge(String firstName, String lastName) throws ParseException {

		MedicalRecordDataBaseModel medicalRecord = new MedicalRecordDataBaseModel();
		medicalRecord = medicalRecordService.getMedicalRecordByFirstNameAndLastName(firstName, lastName);

		int age = 0;
		String birthdayInString;
		Date birthdayInDate;
		int currentYear;
		int birthdayYear;

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

		return age;
	}

	// ----------------------------------------------------------------------------------------
	// GETCOUNTADULTANDCHILDREN: Methode pour supprimer une personne à partir d'un
	// id=firstNamelastName
	// ----------------------------------------------------------------------------------------
	public int getCountAdult(long station) throws ParseException {

		int countAdult = 0;

		List<FireStationDataBaseModel> firestations = fireStationService.getFirestationsByStation(station);

		for (FireStationDataBaseModel fireStation : firestations) {
			List<PersonDataBaseModel> persons = personService.getPersonsByAddress(fireStation.getAddress());
			for (PersonDataBaseModel person : persons) {
				if (fireStation.getAddress().equals(person.getAddress())) {
					int agePerson = getAge(person.getFirstName(), person.getLastName());
					if (agePerson > 18)
						countAdult++;
				}
			}
		}
		return countAdult;
	}

	// ----------------------------------------------------------------------------------------
	// GETCOUNTADULTANDCHILDREN: Methode pour supprimer une personne à partir d'un
	// id=firstNamelastName
	// ----------------------------------------------------------------------------------------
	public int getCountChildren(long station) throws ParseException {

		int countChildren = 0;

		List<FireStationDataBaseModel> firestations = fireStationService.getFirestationsByStation(station);

		for (FireStationDataBaseModel fireStation : firestations) {
			List<PersonDataBaseModel> persons = personService.getPersonsByAddress(fireStation.getAddress());
			for (PersonDataBaseModel person : persons) {
				if (fireStation.getAddress().equals(person.getAddress())) {
					int agePerson = getAge(person.getFirstName(), person.getLastName());
					if (agePerson <= 18)
						countChildren++;
				}
			}
		}
		return countChildren;
	}

}