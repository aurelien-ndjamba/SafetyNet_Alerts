package com.safetynets.alerts.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.service.PersonService;

@RestController
public class PersonController {

	@Autowired
	PersonService personService;

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/person
	// ----------------------------------------------------------------------------------------
	@GetMapping("/person")
	public List<PersonModel> getAllPersons() {
		return personService.getAllPersons();
	}

	// ----------------------------------------------------------------------------------------
	// POST	http://localhost:8080/person
	// ----------------------------------------------------------------------------------------
	@PostMapping("/person")
	@ResponseStatus(HttpStatus.CREATED) // CHECK
	public PersonModel createPerson(@RequestBody PersonModel newPerson) {
		return personService.createPersons(newPerson);
	}

	// ----------------------------------------------------------------------------------------
	// PUT	http://localhost:8080/person
	// ----------------------------------------------------------------------------------------
	@PutMapping("/person")
	public boolean updatePersonInfo(@RequestBody PersonModel updatePerson) {
		return personService.updatePersonInfo(updatePerson);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE http://localhost:8080/person?firstNamelastName=CliveFerguson
	// ----------------------------------------------------------------------------------------
	@DeleteMapping("/person")
	public boolean deletePersonById(@RequestParam String id) throws IllegalArgumentException {
		//id combinaison de firstName et lastName sans séparateur.
		return personService.deletePersonById(id);
	}
}
