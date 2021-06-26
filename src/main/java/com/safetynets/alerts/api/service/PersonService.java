package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.repository.PersonRepository;

/** 
 * Classe définissant les méthodes de Service "Person"
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private FireStationService fireStationService;

	/**
	 * Liste toutes les personnes présentes dans la base de donnée
	 * 
	 * @return List<PersonModel>
	 * 
	 */
	public List<PersonModel> getAllPerson() {
		return personRepository.findAll();
	}

	/**
	 * Filtre une personne dans la base de donnée à partir de son id
	 * 
	 * @Param long id
	 * @return Optional<PersonModel>
	 * 
	 */
	public Optional<PersonModel> getPersonById(long id) {
		return personRepository.findById(id);
	}

	/**
	 * Ajoute une personne dans la base de donnée
	 * 
	 * @Param String address
	 * @return List<PersonModel>
	 * 
	 */
	public List<PersonModel> getPersonsByAddress(String address) {
		return personRepository.findByAddress(address);
	}

	/**
	 * Filtre des personnes dans la base de donnée à partir de la ville "city" en
	 * paramètre
	 * 
	 * @Param String city
	 * @return List<PersonModel>
	 * 
	 */
	public List<PersonModel> getPersonsByCity(String city) {
		return personRepository.findByCity(city);
	}

	/**
	 * Filtre des personnes dans la base de donnée à partir du nom de famille "lastName" en
	 * paramètre
	 * 
	 * @Param String lastName
	 * @return List<PersonModel>
	 * 
	 */
	public List<PersonModel> getPersonsByLastName(String lastName) {
		return personRepository.findByLastName(lastName);
	}

	/**
	 * Filtre une personne dans la base de donnée à partir du nom et prenom en
	 * paramètres
	 * 
	 * @Param String firstName
	 * @Param String lastName
	 * @return PersonModel
	 * @see FireStationService.getFirestationsByStation(station)
	 * 
	 */
	public PersonModel getPersonByFirstNameAndLastName(String firstName, String lastName) {
		return personRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir une personne par prénom et nom
	// ----------------------------------------------------------------------------------------
	public ArrayList<PersonModel> getPersonsByStation(long station) {

		ArrayList<PersonModel> personsByStation = new ArrayList<PersonModel>();
		List<FireStationModel> fireStations = fireStationService.getFirestationsByStation(station);

		for (FireStationModel fireStation : fireStations) {
			for (PersonModel person : getPersonsByAddress(fireStation.getAddress())) {
				personsByStation.add(person);
			}
		}
		return personsByStation;
	}


	// ----------------------------------------------------------------------------------------
	// Methode pour obtenir les parentés d'une personne en fonction du nom et de
	// l'adresse
	// ----------------------------------------------------------------------------------------
	public HashSet<PersonModel> getRelationship(String address, String firstName, String lastName)
			throws ParseException {

		// Liste du foyer ayant nom et adresse similaire avec la personne dont le prénom
		// est en argument
		HashSet<PersonModel> Relationship = new HashSet<PersonModel>();
		// Obtenir une personne par son nom et prénom
		PersonModel person = new PersonModel();
		person = getPersonByFirstNameAndLastName(firstName, lastName);
		// Obtenir des personnes du même nom
		List<PersonModel> PersonsByLastName = getPersonsByLastName(lastName);
		// Obtenir des personnes du même nom sans une personne (celle ayant le prénom en
		// argument)
		PersonsByLastName.remove(person);

		for (PersonModel pM : PersonsByLastName) {

			if (pM.getAddress().equals(address))
				Relationship.add(pM);
		}

		return Relationship;
	}

	/**
	 * Ajoute une personne dans la base de donnée
	 * 
	 * @Param PersonModel person
	 * @return PersonModel -> la personne ajoutée avec son id dans la base de donnée
	 * 
	 */
	public PersonModel postPerson(PersonModel person) {

		person.setId(null); // Pour éviter que le post face l'update avec un id déjà existant
		return personRepository.save(person);
	}

	/**
	 * Modifie les informations d'une personne dans la base de donnée.
	 * Le prénom et le nom ne peuvent pas être modifiable
	 * 
	 * @Param PersonModel person
	 * @return boolean
	 * 
	 */
	public boolean updatePerson(PersonModel person) throws IllegalArgumentException {
		boolean result = false;
		long i = 0;
		long j = 0;
		long countEntities = personRepository.count();

		do {
			if ((personRepository.existsById(i))
					&& person.getFirstName().equals(personRepository.findById(i).get().getFirstName())
					&& person.getLastName().equals(personRepository.findById(i).get().getLastName())) {
				j++;
				person.setId(i);
				personRepository.saveAndFlush(person);
				result = true;
				break;
			}
			i++;
			if (i == 5000)
				break;
		} while (j != countEntities);
		return result;

	}

	/**
	 * Supprime les informations d'une personne dans la base de donnée à partir d'un
	 * id représentant son prénom et son nom (exemple : firstnamelastName => EmmanuelMacron)
	 * 
	 * @Param String id
	 * @return boolean
	 * 
	 */
	public boolean deletePersonByLastNameFirstname(String id) {

		boolean result = false;
		long i = 0;
		long j = 0;
		long countEntities = personRepository.count();

		do {
			PersonModel person = new PersonModel();
			if (personRepository.existsById(i)) {
				j++;
				person = personRepository.findById(i).get();
				if (id.startsWith(person.getFirstName()) && id.endsWith(person.getLastName())) {
					personRepository.deleteByFirstNameAndLastName(person.getFirstName(), person.getLastName());
					result = true;
					break;
				}
			}
			i++;
		} while (j != countEntities);
		return result;
	}

}
