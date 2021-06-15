package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.repository.PersonRepository;

@Component
public class FamilyRelationshipService {
	
	@Autowired
	private PersonModel person;
	@Autowired
	private PersonsService personsService;
	@Autowired
	private PersonRepository personsRepository;

	public HashSet<PersonModel> getBySameAddressAndName(String address,String firstName, String lastName) throws ParseException {
	
		List<Long> listIdsEntitiesPerson = personsService.getlistIdsEntitiesPerson();
		HashSet<PersonModel> listFamilyRelationship = new HashSet<PersonModel>(listIdsEntitiesPerson.size());

		for (Long i : listIdsEntitiesPerson) {

			person = personsRepository.getById(i);

			if  (person.getAddress().equals(address) && !person.getFirstName().equals(firstName) && person.getLastName().equals(lastName) ) {
				listFamilyRelationship.add(person);
			}
		}
		return listFamilyRelationship;
	}
}
