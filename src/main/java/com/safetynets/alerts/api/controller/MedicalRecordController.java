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

import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

	@Autowired
	MedicalRecordService medicalRecordsService;

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/medicalRecord
	// ----------------------------------------------------------------------------------------
	@GetMapping("/medicalRecord")
	public List<MedicalRecordModel> getAllMedicalRecords() {
		return medicalRecordsService.getAllMedicalRecords();
	}

	// ----------------------------------------------------------------------------------------
	// POST http://localhost:8080/medicalRecord
	// ----------------------------------------------------------------------------------------
	@PostMapping("/medicalRecord")
	@ResponseStatus(HttpStatus.CREATED)
	public MedicalRecordModel createMedicalRecords(@RequestBody MedicalRecordModel newMedicalRecords) {
		return medicalRecordsService.createMedicalRecords(newMedicalRecords);
	}

	// ----------------------------------------------------------------------------------------
	// PUT http://localhost:8080/medicalRecord
	// ----------------------------------------------------------------------------------------
	@PutMapping("/medicalRecord")
	public boolean updateMedicalRecords(@RequestBody MedicalRecordModel updateMedicalRecords) {
		return medicalRecordsService.updateMedicalRecords(updateMedicalRecords);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE http://localhost:8080/medicalRecord?firstNamelastName=JohnBoyd
	// ---------------------------------------------------------------------------------------- 
	@DeleteMapping("/medicalRecord")
	public boolean deleteMedicalRecords(@RequestParam String firstNamelastName) throws IllegalArgumentException {
		return medicalRecordsService.deleteMedicalRecords(firstNamelastName);
	}
}