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

/**
 * Classe définissant les méthodes de Service "Count"
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@Service
public class CountService {

	@Autowired
	PersonService personService;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private MedicalRecordService medicalRecordService;

	/**
	 * Donne l'age d'une personne à partir de son nom et prénom en paramètres
	 * 
	 * @param firstName (String)
	 * @param lastName (String)
	 * @return age (int)
	 * 
	 */
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

	/**
	 * Donne le nombre d'adulte vivants à une adresse désservie par une caserne dont
	 * le numéro est en paramètre
	 * 
	 * @param station (long)
	 * @return countAdult (int)
	 * 
	 */
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

	/**
	 * Donne le nombre d'enfants (<=18 ans) vivants à une adresse désservie par une caserne dont
	 * le numéro est en paramètre
	 * 
	 * @param station (long)
	 * @return CountChildren (int)
	 * 
	 */
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