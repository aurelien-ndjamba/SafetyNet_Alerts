package com.safetynets.alerts.api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynets.alerts.api.model.MedicalRecordDataBaseModel;
import com.safetynets.alerts.api.model.PersonDataBaseModel;
import com.safetynets.alerts.api.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

	@Autowired
	MedicalRecordService medicalRecordService;

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/medicalRecord
	// ----------------------------------------------------------------------------------------
	@GetMapping("/medicalRecord")
	public List<MedicalRecordDataBaseModel> getAllMedicalRecord() {
		return medicalRecordService.getAllMedicalRecord();
	}

	// ----------------------------------------------------------------------------------------
	// POST http://localhost:8080/medicalRecord
	// ----------------------------------------------------------------------------------------
	@PostMapping("/medicalRecord")
	@ResponseStatus(HttpStatus.CREATED)
	public MedicalRecordDataBaseModel postMedicalRecord(@RequestBody MedicalRecordDataBaseModel medicalRecord) {
		return medicalRecordService.postMedicalRecord(medicalRecord);
	}

	// ----------------------------------------------------------------------------------------
	// PUT http://localhost:8080/medicalRecord
	// ----------------------------------------------------------------------------------------
	@PutMapping("/medicalRecord")
	public boolean updateMedicalRecord(@RequestBody MedicalRecordDataBaseModel medicalRecord) {
		return medicalRecordService.updateMedicalRecord(medicalRecord);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE http://localhost:8080/medicalRecord?firstNamelastName=JohnBoyd
	// ---------------------------------------------------------------------------------------- 
//	@DeleteMapping("/medicalRecord")
	public void deleteMedicalRecord(@RequestParam long id) throws IllegalArgumentException {
		medicalRecordService.deleteMedicalRecordById(id);
	}
	
	// ----------------------------------------------------------------------------------------
	// DELETE http://localhost:8080/person?firstNamelastName=CliveFerguson
	// ----------------------------------------------------------------------------------------
	@RequestMapping(value = "/medicalRecord", method = RequestMethod.DELETE, params = { "id" })
	public boolean deleteMedicalRecordByLastNameFirstname(@RequestParam String id) throws IllegalArgumentException {
		return medicalRecordService.deleteMedicalRecordByLastNameFirstname(id);
	}
	
}