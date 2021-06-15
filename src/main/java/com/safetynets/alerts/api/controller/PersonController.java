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
	PersonService personsService;

	// GET
	@GetMapping("/person")
	public List<PersonModel> getAllPersons() {
		return personsService.getAllPersons();
	}

	// POST
	@PostMapping("/person")
	@ResponseStatus(HttpStatus.CREATED) // CHECK
	public PersonModel createPersons(@RequestBody PersonModel newPersons) {
		return personsService.createPersons(newPersons);
	}

	// PUT @PutMapping("/person")
//	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
//	public PersonsModel updatePersonInfo(@PathVariable Long id, @RequestBody PersonsModel updatePersons) {
	@PutMapping("/person")
	public boolean updatePersonInfo(@RequestBody PersonModel newPerson) {
		return personsService.updatePersonInfo(newPerson);
	}

	// DELETE
	@DeleteMapping("/person")
	public boolean deletePersonById(@RequestParam String firstName, @RequestParam String lastName) throws IllegalArgumentException {
		return personsService.deletePersonById(firstName, lastName);
	}
}