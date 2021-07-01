package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.repository.FireStationRepository;
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
	private FireStationRepository fireStationRepository;
	
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
	 * Filtre des personnes dans la base de donnée à partir du nom de famille "lastName" en
	 * paramètre
	 * 
	 * @Param String lastName
	 * @return List<PersonModel>
	 * 
	 */
	public List<PersonModel> findByLastName(String lastName) {
		return personRepository.findByLastName(lastName);
	}

//	/**
//	 * Filtre une personne dans la base de donnée à partir du nom et prenom en
//	 * paramètres
//	 * 
//	 * @Param String firstName
//	 * @Param String lastName
//	 * @return PersonModel
//	 * @see FireStationService.getFirestationsByStation(station)
//	 * 
//	 */


	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir une personne par prénom et nom
	// ----------------------------------------------------------------------------------------
	public ArrayList<PersonModel> getPersonsByStation(long station) {

		ArrayList<PersonModel> personsByStation = new ArrayList<PersonModel>();
		List<FireStationModel> fireStations = fireStationRepository.findByStation(station);//findby
		// requete jointure

		for (FireStationModel fireStation : fireStations) {
			for (PersonModel person : findByAddress(fireStation.getAddress())) {
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
		person = personRepository.findByFirstNameAndLastName(firstName, lastName);
		// Obtenir des personnes du même nom
		List<PersonModel> PersonsByLastName = findByLastName(lastName); //utiliser directement repositoru
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
//		person.setId(null); 
		return personRepository.save(person);
	}

	/**
	 * Modifie les informations d'une personne dans la base de donnée.
	 * Le prénom et le nom ne peuvent pas être modifiable
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
	 * Supprime les informations d'une personne dans la base de donnée à partir d'un
	 * id représentant son prénom et son nom (exemple : firstnamelastName => EmmanuelMacron)
	 * 
	 * @Param String id
	 * @return PersonModel
	 * 
	 */
	public PersonModel deletePersonByLastNameFirstname(String id) {

		PersonModel personDelete = new PersonModel();
		long i = 0;
		long j = 0;
		long countEntities = personRepository.count();

		do {
			PersonModel person = new PersonModel();
			if (personRepository.existsById(i)) {
				j++;
				person = personRepository.findById(i);
				if (id.startsWith(person.getFirstName()) && id.endsWith(person.getLastName())) {
					personRepository.deleteByFirstNameAndLastName(person.getFirstName(), person.getLastName());
					personDelete = person;
					break;
				}
			}
			i++;
		} while (j != countEntities);
		return personDelete;
	}

}
