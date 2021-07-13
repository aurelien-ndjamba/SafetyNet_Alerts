package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.Calendar;
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
	private PersonService personService;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private MedicalRecordService medicalRecordService;

	/**
	 * Setter de personService
	 * 
	 * @return void
	 * 
	 */
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	/**
	 * Setter de fireStationService
	 * 
	 * @return void
	 * 
	 */
	public void setFireStationService(FireStationService fireStationService) {
		this.fireStationService = fireStationService;
	}

	/**
	 * Setter de medicalRecordService
	 * 
	 * @return void
	 * 
	 */
	public void setMedicalRecordService(MedicalRecordService medicalRecordService) {
		this.medicalRecordService = medicalRecordService;
	}

	/**
	 * Donne l'age d'une personne à partir de son nom et prénom en paramètres
	 * 
	 * @param firstName (String)
	 * @param lastName  (String)
	 * @return age (int)
	 * 
	 */
	public int findAge(String birthdayInString) throws ParseException {
		int age = 0;
		int currentYear;
		int birthdayYear;
		
		birthdayInString.substring(6);

		// Année de naissance 
		birthdayYear = Integer.parseInt(birthdayInString.substring(6));
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
	public int findCountAdult(long station) throws ParseException {

		int countAdult = 0;

		List<FireStationModel> firestations = fireStationService.findByStation(station);

		for (FireStationModel fireStation : firestations) {
			List<PersonModel> persons = personService.findByAddress(fireStation.getAddress());
			for (PersonModel person : persons) {
				if (fireStation.getAddress().equals(person.getAddress())) {
					MedicalRecordModel medicalRecordModel = medicalRecordService
							.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
					int agePerson = findAge(medicalRecordModel.getBirthdate());
					if (agePerson > 18)
						countAdult++;
				}
			}
		}
		return countAdult;
	}

	/**
	 * Donne le nombre d'enfants (<=18 ans) vivants à une adresse désservie par une
	 * caserne dont le numéro est en paramètre
	 * 
	 * @param station (long)
	 * @return CountChildren (int)
	 * 
	 */
	public int findCountChildren(long station) throws ParseException {

		int countChildren = 0;

		List<FireStationModel> firestations = fireStationService.findByStation(station);

		for (FireStationModel fireStation : firestations) {
			List<PersonModel> persons = personService.findByAddress(fireStation.getAddress());
			for (PersonModel person : persons) {
				if (fireStation.getAddress().equals(person.getAddress())) {
					MedicalRecordModel medicalRecordModel = medicalRecordService
							.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
					int agePerson = findAge(medicalRecordModel.getBirthdate());
					if (agePerson <= 18)
						countChildren++;
				}
			}
		}
		return countChildren;
	}

}