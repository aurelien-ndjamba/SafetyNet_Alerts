package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.PersonInfoAdvancedModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.model.FireStationDataBaseModel;
import com.safetynets.alerts.api.model.PersonDataBaseModel;
import com.safetynets.alerts.api.model.PersonImpactedByStationNumber;
import com.safetynets.alerts.api.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	@Autowired private FireStationService fireStationService;
//	@Autowired private MedicalRecordService medicalRecordService;
//	@Autowired private CountService countService;

	// ----------------------------------------------------------------------------------------
	// GET ALL: Methode pour obtenir la liste des personnes dans une BDD
	// ----------------------------------------------------------------------------------------
	public List<PersonDataBaseModel> getAllPerson() {
		return personRepository.findAll();
	}

	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir ungetPersonsByAddresse personne par id
	// ----------------------------------------------------------------------------------------
	public Optional<PersonDataBaseModel> getPersonById(long id) {
		return personRepository.findById(id);
	}

	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir des personnes à partir d'une adresse
	// ----------------------------------------------------------------------------------------
	public  List<PersonDataBaseModel> getPersonsByAddress(String address) {
		return personRepository.findByAddress(address);
	}
	
	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir des personnes à partir d'une adresse
	// ----------------------------------------------------------------------------------------
	public  List<PersonDataBaseModel> getPersonsByCity(String city) {
		return personRepository.findByCity(city);
	}

	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir des personnes par nom
	// ----------------------------------------------------------------------------------------
	public List<PersonDataBaseModel> getPersonsByLastName(String lastName) {
		return personRepository.findByLastName(lastName);
	}

	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir une personne par prénom et nom
	// ----------------------------------------------------------------------------------------
	public PersonDataBaseModel getPersonByFirstNameAndLastName(String firstName, String lastName) {
		return personRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir une personne par prénom et nom
	// ----------------------------------------------------------------------------------------
	public ArrayList<PersonDataBaseModel> getPersonsByStation(long station) {
		
		ArrayList<PersonDataBaseModel> personsByStation = new ArrayList<PersonDataBaseModel>();
		List<FireStationDataBaseModel> fireStations = fireStationService.getFirestationsByStation(station);
		
		for (FireStationDataBaseModel fireStation : fireStations) {
			for ( PersonDataBaseModel person : getPersonsByAddress(fireStation.getAddress()) ){
				personsByStation.add(person);
			}
		}
		return personsByStation;
	}
		
//	// ----------------------------------------------------------------------------------------
//		// Methode pour obtenir la liste des personnes impactées par un numéro de
//		// caserne
//		// ----------------------------------------------------------------------------------------
//		public ArrayList<PersonImpactedByStationNumber> getPersonsByStationNumber(long stationNumber) {
//			// Liste d'adresses par numéro de caserne
//			List<String> listAddressByStationNumber = fireStationService.getAddressByStationNumber(stationNumber);
//			 
//			personRepository.findAllByAddress(listAddressByStationNumber);
//			 
//			 return null;
//		}
//
//		// ----------------------------------------------------------------------------------------
//		// Methode pour obtenir des informations avancées sur une personne par son 
//		// ----------------------------------------------------------------------------------------
//		public PersonInfoAdvancedModel getPersonInfoAdvancedModel(PersonModel person) throws ParseException {
//
//				PersonInfoAdvancedModel personInfoAdvanced = new PersonInfoAdvancedModel();
//
//				personInfoAdvanced.setId(person.getId());
//				personInfoAdvanced.setFirstName(person.getFirstName());
//				personInfoAdvanced.setLastName(person.getLastName());
//				personInfoAdvanced.setPhone(person.getPhone());
//				personInfoAdvanced.setAge(countService.getAge(person.getFirstName(), person.getLastName()));
//				personInfoAdvanced.setMedications(
//						medicalRecordService.getAllergiesHistoryByFisrtNameAndLastName(person.getFirstName(), person.getLastName()));
//				personInfoAdvanced.setAllergies(
//						medicalRecordService.getMedicationsHistoryByFisrtNameAndLastName(person.getFirstName(), person.getLastName()));
//			
//			return personInfoAdvanced;
//		}

	// ----------------------------------------------------------------------------------------
	// Methode pour obtenir les parentés d'une personne en fonction du nom et de
	// l'adresse
	// ----------------------------------------------------------------------------------------
		public HashSet<PersonDataBaseModel> getRelationship(String address, String firstName, String lastName)
				throws ParseException {

			// Liste du foyer ayant nom et adresse similaire avec la personne dont le prénom
			// est en argument
			HashSet<PersonDataBaseModel> Relationship = new HashSet<PersonDataBaseModel>();
			// Obtenir une personne par son nom et prénom
			PersonDataBaseModel person = new PersonDataBaseModel();
			person = getPersonByFirstNameAndLastName(firstName, lastName);
			// Obtenir des personnes du même nom
			List<PersonDataBaseModel> PersonsByLastName = getPersonsByLastName(lastName);
			// Obtenir des personnes du même nom sans une personne (celle ayant le prénom en
			// argument)
			PersonsByLastName.remove(person);

			for (PersonDataBaseModel pM : PersonsByLastName) {

				if (pM.getAddress().equals(address))
					Relationship.add(pM);
			}

			return Relationship;
		}

	// ----------------------------------------------------------------------------------------
	// POST: Methode pour ajouter une personne dans la BDD
	// ----------------------------------------------------------------------------------------
	public PersonDataBaseModel postPerson(PersonDataBaseModel person) {

		System.out.println("Nouvelle personne enregistrée dans la base de donnée avec succès !");

		person.setId(null); // Pour éviter que le post face l'update avec un id déjà existant
		return personRepository.save(person);
	}

	// ----------------------------------------------------------------------------------------
	// PUT: Methode pour mettre à jour les infos d'une personne dans la BDD
	// ----------------------------------------------------------------------------------------
	public boolean updatePerson(PersonDataBaseModel person) throws IllegalArgumentException {
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
			if (i == 10000)
				break;
		} while (j != countEntities);
		return result;

	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne par id
	// ----------------------------------------------------------------------------------------
	public void deletePersonById(long id) throws IllegalArgumentException {
		personRepository.deleteById(id);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne à partir d'une entité
	// ----------------------------------------------------------------------------------------
	public void deletePersonByEntity(PersonDataBaseModel person) throws IllegalArgumentException {
		personRepository.delete(person);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne à partir son nom et prenom dans
	// la BDD
	// ----------------------------------------------------------------------------------------
	public void deleteByFirstNameAndLastName(String firstName, String lastName) throws IllegalArgumentException {
		personRepository.deleteByFirstNameAndLastName(firstName, lastName);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer des personnes à partir du nom
	// ----------------------------------------------------------------------------------------
	public void deleteByLastName(String lastName) throws IllegalArgumentException {
		personRepository.deleteByLastName(lastName);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne à partir d'un
	// id=firstNamelastName
	// ------------------------------------------------------------------------------------
	public boolean deletePersonByLastNameFirstname(String id) {

		boolean result = false;
		long i = 0;
		long j = 0;
		long countEntities = personRepository.count();

		do {
			PersonDataBaseModel person = new PersonDataBaseModel();
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
