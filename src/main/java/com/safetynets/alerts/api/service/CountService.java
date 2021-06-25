package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.model.PersonModel;

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

		MedicalRecordModel medicalRecord = new MedicalRecordModel();
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

		List<FireStationModel> firestations = fireStationService.getFirestationsByStation(station);

		for (FireStationModel fireStation : firestations) {
			List<PersonModel> persons = personService.getPersonsByAddress(fireStation.getAddress());
			for (PersonModel person : persons) {
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

		List<FireStationModel> firestations = fireStationService.getFirestationsByStation(station);

		for (FireStationModel fireStation : firestations) {
			List<PersonModel> persons = personService.getPersonsByAddress(fireStation.getAddress());
			for (PersonModel person : persons) {
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