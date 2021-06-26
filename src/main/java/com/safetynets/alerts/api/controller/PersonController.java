package com.safetynets.alerts.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.service.PersonService;

/** 
 * Classe qui s'occupe du Controller "Person" l'API
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@RestController
public class PersonController {

	@Autowired
	private PersonService personService;

	/**
	 * GET http://localhost:8080/person
	 * 
	 * Liste toutes les personnes présentes dans la base de donnée
	 * 
	 * @return List<PersonModel>
	 * 
	 */
	@GetMapping("/person")
	public List<PersonModel> getAllPerson() {
		return personService.getAllPerson();
	}

	/**
	 * GET http://localhost:8080/firestation?id=<id>
	 * 
	 * Filtre une personne dans la base de donnée à partir de son id
	 * 
	 * @Param long id
	 * @return Optional<PersonModel>
	 * 
	 */
	@RequestMapping(value = "/person", method = RequestMethod.GET, params = { "id" })
	public Optional<PersonModel> getPersonById(@RequestParam long id) {
		return personService.getPersonById(id);
	}

	/**
	 * POST http://localhost:8080/person
	 * 
	 * Ajoute une personne dans la base de donnée
	 * 
	 * @Param PersonModel person
	 * @return PersonModel -> la personne ajoutée avec son id dans la base de donnée
	 * 
	 */
	@PostMapping("/person")
	@ResponseStatus(HttpStatus.CREATED)
	public PersonModel postPerson(@RequestBody PersonModel person) {
		return personService.postPerson(person);
	}

	/**
	 * PUT http://localhost:8080/person
	 * 
	 * Modifie les informations d'une personne dans la base de donnée.
	 * Le prénom et le nom ne peuvent pas être modifiable
	 * 
	 * @Param PersonModel person
	 * @return boolean
	 * 
	 */
	@PutMapping("/person")
	public boolean updatePerson(@RequestBody PersonModel person) {
		return personService.updatePerson(person);
	}

	/**
	 * DELETE http://localhost:8080/person?firstNamelastName=<firstNamelastName>
	 * 
	 * Supprime les informations d'une personne dans la base de donnée à partir d'un
	 * id représentant son prénom et son nom (exemple : firstnamelastName => EmmanuelMacron)
	 * 
	 * @Param String id
	 * @return boolean
	 * 
	 */
	@RequestMapping(value = "/person", method = RequestMethod.DELETE, params = { "id" })
	public boolean deletePersonByLastNameFirstname(@RequestParam String id) throws IllegalArgumentException {
		return personService.deletePersonByLastNameFirstname(id);
	}
}
