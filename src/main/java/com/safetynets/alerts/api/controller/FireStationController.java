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

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.service.FireStationService;

@RestController
public class FireStationController {

	@Autowired
	FireStationService fireStationService;

	// GET
//	@GetMapping("/firestation")
//	public List<FireStationsModel> getAllFireStation() {
//		return fireStationService.getAllFireStation();
//	}

	// POST
	@PostMapping("/firestation")
	@ResponseStatus(HttpStatus.CREATED) // Vérifier qu'il n'effectue pas de mise à jour
	public FireStationModel createFireStation(@RequestBody FireStationModel newFireStation) {
		return fireStationService.createFireStation(newFireStation);
	}

	// PUT
	@PutMapping("/firestation")
	public boolean updateStationNumber(@RequestBody FireStationModel updateFireStation) {
		return fireStationService.updateStationNumber(updateFireStation);
	}

	// DELETE 
	@DeleteMapping("/firestation")
	public boolean deleteFireStation(@RequestParam String addressToDelete) throws IllegalArgumentException {
		return fireStationService.deleteFireStation(addressToDelete);
	}
}