package com.safetynets.alerts.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.PersonsModel;
import com.safetynets.alerts.api.repository.PersonsRepository;

@Service
public class PersonsService {

	@Autowired
	PersonsModel person;
	@Autowired
	PersonsRepository personsRepository;

	// ----------------------------------------------------------------------------------------
	// CREATE "listIdsEntitiesPerson" FROM PersonRepository
	// ----------------------------------------------------------------------------------------

	public List<Long> getlistIdsEntitiesPerson() {

		List<PersonsModel> listEntitiesPerson = personsRepository.findAll();
		long CountIds = personsRepository.count();
		int id = 0;
		List<Long> listIdsEntitiesPerson = new ArrayList<Long>();
		while (id != CountIds) {
			person = listEntitiesPerson.get(id);
			listIdsEntitiesPerson.add(person.getId());
			++id;
		}
		System.out.println(listIdsEntitiesPerson);
		return listIdsEntitiesPerson;
	}

	// ----------------------------------------------------------------------------------------
	// GET
	// ----------------------------------------------------------------------------------------
	public List<PersonsModel> getAllPersons() {
		return personsRepository.findAll();
	}

	public PersonsModel getPersonById(Long id) {
		return personsRepository.getById(id);
	}

	// ----------------------------------------------------------------------------------------
	// POST
	// ----------------------------------------------------------------------------------------
	public PersonsModel createPersons(PersonsModel personsModel) {
		System.out.println("Nouveau personne enregistrée dans la base de donnée avec succès !");
		return personsRepository.save(personsModel);
	}

	// ----------------------------------------------------------------------------------------
	// PUT
	// ----------------------------------------------------------------------------------------
	public boolean updatePersonInfo(PersonsModel newPerson) throws IllegalArgumentException {
		
		boolean result = false;
		List<Long> listIdsEntitiesPerson = new ArrayList<Long>();
		listIdsEntitiesPerson = getlistIdsEntitiesPerson();
		
		for (long i : listIdsEntitiesPerson) {
			person = personsRepository.getById(i);
			if (person.getFirstName().equals(newPerson.getFirstName())  && person.getLastName().equals(newPerson.getLastName()) ) {
				
				person.setAddress(newPerson.getAddress());
				person.setCity(newPerson.getCity());
				person.setZip(newPerson.getZip());
				person.setPhone(newPerson.getPhone());
				person.setEmail(newPerson.getEmail());
				
				personsRepository.saveAndFlush(person);
				System.out.println("Mise à jour effectuée dans la base de donnée avec succès !");
				result = true;
				break;
			}
		} 
		return result;
	}

	// ----------------------------------------------------------------------------------------
	// DELETE
	// ----------------------------------------------------------------------------------------
	public boolean deletePersonById(String firstname, String lastname) throws IllegalArgumentException {

		boolean result = false;
		List<Long> listIdsEntitiesPerson = new ArrayList<Long>();
		listIdsEntitiesPerson = getlistIdsEntitiesPerson();

		// Suppression d'une personne identifiée dans la BDD par le nom et le prénom
		for (Long i : listIdsEntitiesPerson) {
			person = personsRepository.getById(i);

			if (person.getLastName().equals(lastname) && person.getFirstName().equals(firstname)) {
				personsRepository.delete(person);
				listIdsEntitiesPerson.remove(i);
				result = true;
				System.out.println("l'ID de la personne supprimée est le suivant: " + i);
				break;
			}
		}
		return result;
	}
}