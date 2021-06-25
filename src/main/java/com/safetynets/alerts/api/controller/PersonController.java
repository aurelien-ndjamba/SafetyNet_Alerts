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

import com.safetynets.alerts.api.model.PersonDataBaseModel;
import com.safetynets.alerts.api.service.PersonService;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;

	/** GET http://localhost:8080/person?id=<id> */
	@GetMapping("/person")
	public List<PersonDataBaseModel> getAllPerson() {
		return personService.getAllPerson(); 
	}
	
	/** */
	@RequestMapping(value = "/person", method = RequestMethod.GET, params = { "id" })
	public Optional<PersonDataBaseModel> getPersonById(@RequestParam long id) {
		return personService.getPersonById(id); 
	}
	
	/** */
	@RequestMapping(value = "/person", method = RequestMethod.GET, params = { "address" })
	public List<PersonDataBaseModel> getPersonsByAddress(@RequestParam String address) {
		return personService.getPersonsByAddress(address); 
	}
	
	/** */
	@RequestMapping(value = "/person", method = RequestMethod.GET, params = { "lastName" })
	public List<PersonDataBaseModel> getPersonsByLastName(@RequestParam String lastName) {
		return personService.getPersonsByLastName(lastName); 
	}
	
	/** */
	@RequestMapping(value = "/person", method = RequestMethod.GET, params = { "firstName", "lastName" })
	public PersonDataBaseModel getPersonsByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
		return personService.getPersonByFirstNameAndLastName(firstName, lastName); 
	}

	/** POST http://localhost:8080/person */
	@PostMapping("/person")
	@ResponseStatus(HttpStatus.CREATED)
	public PersonDataBaseModel postPerson(@RequestBody PersonDataBaseModel person) {
		return personService.postPerson(person);
	}

	/** PUT http://localhost:8080/person */
	@PutMapping("/person")
	public boolean updatePerson(@RequestBody PersonDataBaseModel person) {
		return personService.updatePerson(person);
	}

	/** DELETE http://localhost:8080/person?firstNamelastName=CliveFerguson */
	public void deletePersonById(long id) throws IllegalArgumentException {
		personService.deletePersonById(id);
	}
	
	/** DELETE http://localhost:8080/person */
	public void deletePersonByEntity(@RequestBody PersonDataBaseModel person) throws IllegalArgumentException {
		personService.deletePersonByEntity(person);
	}
	
	/** DELETE http://localhost:8080/person?firstName&lastName=CliveFerguson */
	public void deleteByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) throws IllegalArgumentException {
		personService.deleteByFirstNameAndLastName(firstName, lastName);
	}
	
	/** DELETE http://localhost:8080/person?lastName=Ferguson */
	public void deleteByLastName(@RequestParam String lastName) throws IllegalArgumentException {
		personService.deleteByLastName(lastName);
	}
	
	/** DELETE http://localhost:8080/person?firstNamelastName=CliveFerguson */
	@RequestMapping(value = "/person", method = RequestMethod.DELETE, params = { "id" })
	public boolean deletePersonByLastNameFirstname(@RequestParam String id) throws IllegalArgumentException {
		return personService.deletePersonByLastNameFirstname(id);
	}
}
