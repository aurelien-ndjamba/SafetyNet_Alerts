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

import com.safetynets.alerts.api.model.FireStationsModel;
import com.safetynets.alerts.api.service.FireStationsService;

@RestController
public class FirestationsController {

	@Autowired
	FireStationsService firestationService;

	// GET
	@GetMapping("/firestation")
	public List<FireStationsModel> getAllFireStation() {
		return firestationService.getAllFireStation();
	}

	// POST
	@PostMapping("/firestation")
	@ResponseStatus(HttpStatus.CREATED) // Vérifier qu'il n'effectue pas de mise à jour
	public FireStationsModel createFireStation(@RequestBody FireStationsModel newFireStation) {
		return firestationService.createFireStation(newFireStation);
	}

	// PUT
	@PutMapping("/firestation")
	public boolean updateStationNumber(@RequestBody FireStationsModel updateFireStation) {
		return firestationService.updateStationNumber(updateFireStation);
	}

	// DELETE 
	@DeleteMapping("/firestation")
	public boolean deleteFireStation(@RequestParam String addressToDelete) throws IllegalArgumentException {
		return firestationService.deleteFireStation(addressToDelete);
	}
}