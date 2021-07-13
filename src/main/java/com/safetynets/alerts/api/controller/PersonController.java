package com.safetynets.alerts.api.controller;

import java.util.List;

import org.jboss.logging.Logger;
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

	private Logger logger = Logger.getLogger(this.getClass());

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
	public List<PersonModel> findAll() {
		logger.info("Appel de l'api GET sur '/person' sans parametre");
		return personService.findAll();
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
	public PersonModel findById(@RequestParam long id) {
		logger.info("Appel de l'api GET sur '/person' avec le parametre id :" + id);
		return personService.findById(id);
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
	public PersonModel save(@RequestBody PersonModel person) {
		logger.info("Appel de l'api POST sur '/person' avec pour parametre Body 'PersonModel' :" + person);
		return personService.save(person);
	}

	/**
	 * PUT http://localhost:8080/person
	 * 
	 * Modifie les informations d'une personne dans la base de donnée. Le prénom et
	 * le nom ne peuvent pas être modifiable
	 * 
	 * @Param PersonModel person
	 * @return PersonModel
	 * 
	 */
	@PutMapping("/person")
	public PersonModel update(@RequestBody PersonModel person) {
		logger.info("Appel de l'api PUT sur '/person' avec pour parametre Body 'PersonModel' :" + person);
		return personService.update(person);
	}

	/**
	 * DELETE http://localhost:8080/person?firstNamelastName=<firstNamelastName>
	 * 
	 * Supprime les informations d'une personne dans la base de donnée à partir de
	 * son prénom et son nom 
	 * 
	 * @Param String firstname
	 * @Param String lastname
	 * @return void
	 * 
	 */
	@RequestMapping(value = "/person", method = RequestMethod.DELETE, params = { "firstName", "lastName" })
	public void deletePersonByLastNameFirstname(@RequestParam String firstName, @RequestParam String lastName)
			throws IllegalArgumentException {
		logger.info("Appel de l'api DELETE sur '/person' avec pour parametre 'firstName' :" + firstName
				+ " et lastName: " + lastName);
		personService.delete(firstName, lastName);
	}

}
