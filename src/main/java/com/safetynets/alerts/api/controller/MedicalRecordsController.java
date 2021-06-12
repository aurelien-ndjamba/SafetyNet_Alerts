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

import com.safetynets.alerts.api.model.MedicalRecordsModel;
import com.safetynets.alerts.api.service.MedicalRecordsService;

@RestController
public class MedicalRecordsController {

	@Autowired
	MedicalRecordsService medicalRecordsService;

	// GET
	@GetMapping("/medicalRecord")
	public List<MedicalRecordsModel> getAllMedicalRecords() {
		return medicalRecordsService.getAllMedicalRecords();
	}

	// POST
	@PostMapping("/medicalRecord")
	@ResponseStatus(HttpStatus.CREATED) // Vérifier qu'il n'effectue pas de mise à jour
	public MedicalRecordsModel createMedicalRecords(@RequestBody MedicalRecordsModel newMedicalRecords) {
		return medicalRecordsService.createMedicalRecords(newMedicalRecords);
	}

	// PUT
	@PutMapping("/medicalRecord")
	public boolean updateMedicalRecords(@RequestBody MedicalRecordsModel updateMedicalRecords) {
		return medicalRecordsService.updateMedicalRecords(updateMedicalRecords);
	}

	// DELETE 
	@DeleteMapping("/medicalRecord")
	public boolean deleteMedicalRecords(@RequestParam String firstName, @RequestParam String lastName) throws IllegalArgumentException {
		return medicalRecordsService.deleteMedicalRecords(firstName, lastName);
	}
}