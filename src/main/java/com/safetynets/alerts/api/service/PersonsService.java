package com.safetynets.alerts.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.model.SpecificInfoPersonsModel;
import com.safetynets.alerts.api.repository.PersonRepository;

@Service
public class PersonsService {

	@Autowired
	private PersonModel person;
	@Autowired
	private PersonsService personsService;
	@Autowired
	PersonRepository personsRepository;
	@Autowired
	private FireStationService fireStationsService;

	// ----------------------------------------------------------------------------------------
	// CREATE "listIdsEntitiesPerson" FROM PersonsRepository
	// ----------------------------------------------------------------------------------------

	public List<Long> getlistIdsEntitiesPerson() {

		List<PersonModel> listEntitiesPerson = personsRepository.findAll();
		long CountIds = personsRepository.count();
		int id = 0;
		List<Long> listIdsEntitiesPerson = new ArrayList<Long>();
		while (id != CountIds) {
			person = listEntitiesPerson.get(id);
			listIdsEntitiesPerson.add(person.getId());
			++id;
		}
		return listIdsEntitiesPerson;
	}

	// ----------------------------------------------------------------------------------------
	// GET
	// ----------------------------------------------------------------------------------------
	public List<PersonModel> getAllPersons() {
		return personsRepository.findAll();
	}

	public PersonModel getPersonById(Long id) {
		return personsRepository.getById(id);
	}

	// ----------------------------------------------------------------------------------------
	// POST
	// ----------------------------------------------------------------------------------------
	public PersonModel createPersons(PersonModel personsModel) {
		System.out.println("Nouvelle personne enregistrée dans la base de donnée avec succès !");
		return personsRepository.save(personsModel);
	}

	// ----------------------------------------------------------------------------------------
	// PUT
	// ----------------------------------------------------------------------------------------
	public boolean updatePersonInfo(PersonModel newPerson) throws IllegalArgumentException {

		boolean result = false;
		List<Long> listIdsEntitiesPerson = getlistIdsEntitiesPerson();

		for (long i : listIdsEntitiesPerson) {
			person = personsRepository.getById(i);
			if (person.getFirstName().equals(newPerson.getFirstName())
					&& person.getLastName().equals(newPerson.getLastName())) {

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
		List<Long> listIdsEntitiesPerson = getlistIdsEntitiesPerson();

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

	// ----------------------------------------------------------------------------------------
	// methode pour obtenir la liste des personnes impactée quand numéro station
	// donnée
	// ----------------------------------------------------------------------------------------
	public ArrayList<SpecificInfoPersonsModel> getListSpecificPersonImpacted(long stationNumber) {

		ArrayList<String> listAdressImpacted = fireStationsService.getListAdressImpactedByStationNumber(stationNumber);
		ArrayList<SpecificInfoPersonsModel> listSpecificPersonImpacted = new ArrayList<SpecificInfoPersonsModel>();
		List<Long> listIdsEntitiesPerson = personsService.getlistIdsEntitiesPerson();

		for (Long i : listIdsEntitiesPerson) {

			person = personsRepository.getById(i);

			if (listAdressImpacted.contains(person.getAddress())) {

				SpecificInfoPersonsModel specificInfoPersons = new SpecificInfoPersonsModel();

				specificInfoPersons.setFirstName(person.getFirstName());
				specificInfoPersons.setLastName(person.getLastName());
				specificInfoPersons.setAddress(person.getAddress());
				specificInfoPersons.setPhone(person.getPhone());

				listSpecificPersonImpacted.add(specificInfoPersons);

			}
		}
		return listSpecificPersonImpacted;
	}
}