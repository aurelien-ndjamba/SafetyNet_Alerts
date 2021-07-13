package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
	 * Setter de personRepository
	 * 
	 * @return void
	 * 
	 */
	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
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
	 * Liste toutes les personnes présentes dans la base de donnée
	 * 
	 * @return List<PersonModel>
	 * 
	 */
	public List<PersonModel> findAll() {
		return personRepository.findAll();
	}

	/**
	 * Filtre une personne dans la base de donnée à partir de son id
	 * 
	 * @Param long id
	 * @return Optional<PersonModel>
	 * 
	 */
	public PersonModel findById(long id) {
		return personRepository.findById(id);
	}

	/**
	 * Ajoute une personne dans la base de donnée
	 * 
	 * @Param String address
	 * @return List<PersonModel>
	 * 
	 */
	public List<PersonModel> findByAddress(String address) {
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
	public List<PersonModel> findByCity(String city) {
		return personRepository.findByCity(city);
	}

	/**
	 * Filtre des personnes dans la base de donnée à partir du nom de famille
	 * "lastName" en paramètre
	 * 
	 * @Param String lastName
	 * @return List<PersonModel>
	 * 
	 */
	public List<PersonModel> findByLastName(String lastName) {
		return personRepository.findByLastName(lastName);
	}

	/**
	 * Donne une liste de personne couverte par un numéro de caserne de pompier.
	 * 
	 * @Param long station
	 * @return ArrayList<PersonModel>
	 * @see FireStationService.findByStation(station)
	 * 
	 */
	public ArrayList<PersonModel> findPersonsByStation(long station) {

		ArrayList<PersonModel> personsByStation = new ArrayList<PersonModel>();
		List<FireStationModel> fireStations = fireStationService.findByStation(station);
		// requete jointure
		for (FireStationModel fireStation : fireStations) {
			for (PersonModel person : findByAddress(fireStation.getAddress())) {
				personsByStation.add(person);
			}
		}
		return personsByStation;
	}

	/**
	 * Donne une liste de personne de la famille d'une personne ayant le même nom et
	 * la même adresse qu'une personne
	 * 
	 * @Param String address
	 * @Param String firstName
	 * @Param String lastName
	 * @return HashSet<PersonModel>
	 * @see FireStationService.findByStation(station)
	 * 
	 */
	public HashSet<PersonModel> findRelationship(String address, String firstName, String lastName)
			throws ParseException {

		// Liste du foyer ayant nom et adresse similaire avec la personne dont le prénom
		// est en argument
		HashSet<PersonModel> Relationship = new HashSet<PersonModel>();
		// Obtenir une personne par son nom et prénom
		PersonModel person = new PersonModel();
		person = personRepository.findByFirstNameAndLastName(firstName, lastName);
		// Obtenir des personnes du même nom
		List<PersonModel> PersonsByLastName = personRepository.findByLastName(lastName);
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
	public PersonModel save(PersonModel person) {
		person.setId(null);
		return personRepository.save(person);
	}

	/**
	 * Modifie les informations d'une personne dans la base de donnée. Le prénom et
	 * le nom ne peuvent pas être modifiable
	 * 
	 * @Param PersonModel person
	 * @return PersonModel
	 * 
	 */
	public PersonModel update(PersonModel person) throws IllegalArgumentException {
		PersonModel personInDb = new PersonModel();
		personInDb = personRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
		person.setId(personInDb.getId());
		return personRepository.save(person);

	}

	/**
	 * Supprime les informations d'une personne dans la base de donnée à partir de
	 * son prénom et son nom
	 * 
	 * @Param String firstname
	 * @Param String lastname
	 * @return void
	 * 
	 */
	public void delete(String firstname, String lastname) {
			personRepository.deleteByFirstNameAndLastName(firstname, lastname);
	}

}
