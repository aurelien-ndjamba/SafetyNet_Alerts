package com.safetynets.alerts.api.controller;

import java.text.ParseException;
import java.util.List;

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

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.PersonImpactedByStationNumberModel;
import com.safetynets.alerts.api.repository.FireStationRepository;
import com.safetynets.alerts.api.service.FireStationService;
import com.safetynets.alerts.api.service.PersonService;

@RestController
public class FireStationController {

	@Autowired
	FireStationService fireStationService;
	@Autowired PersonService personService;
	@Autowired FireStationRepository fireStationRepository; //ajout à chck

	// ----------------------------------------------------------------------------------------
	// GET	http://localhost:8080/firestation
	// ----------------------------------------------------------------------------------------
	@GetMapping("/firestation")
	public List<FireStationModel> getAllFireStation() {
//		return fireStationService.getAllFireStation();
		return fireStationRepository.findAll();
	}
	
	@RequestMapping(value = "/firestation", method = RequestMethod.GET, params = { "stationNumber" })
	public PersonImpactedByStationNumberModel getSpecificInfoPersonsImpacted(
			@RequestParam("stationNumber") long stationNumber) throws ParseException {
		return personService.getSpecificInfoPersonsImpacted(stationNumber);
	}

	// ----------------------------------------------------------------------------------------
	// POST	http://localhost:8080/firestation
	// ----------------------------------------------------------------------------------------
	@PostMapping("/firestation")
	@ResponseStatus(HttpStatus.CREATED)
	public FireStationModel createFireStation(@RequestBody FireStationModel newFireStation) {
		return fireStationService.createFireStation(newFireStation);
	}

	// ----------------------------------------------------------------------------------------
	// PUT	http://localhost:8080/firestation 
	// ----------------------------------------------------------------------------------------
	@PutMapping("/firestation")
	public boolean updateStationNumber(@RequestBody FireStationModel updateFireStation) {
		return fireStationService.updateStationNumber(updateFireStation);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE	http://localhost:8080/firestation?address=951 LoneTree Rd
	// ----------------------------------------------------------------------------------------
	@RequestMapping(value = "/firestation", method = RequestMethod.DELETE, params = { "address" })
	public boolean deleteFireStationByAddress(
			@RequestParam("address") String address) throws ParseException {
		return fireStationService.deleteFireStationByAddress(address);
	}
	
}