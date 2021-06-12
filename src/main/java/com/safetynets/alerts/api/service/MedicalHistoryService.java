package com.safetynets.alerts.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynets.alerts.api.model.MedicalHistoryModel;
import com.safetynets.alerts.api.model.MedicalRecordsModel;
import com.safetynets.alerts.api.repository.MedicalrecordsRepository;

public class MedicalHistoryService {

	@Autowired
	MedicalHistoryModel medicalHistory;
	@Autowired
	MedicalRecordsModel medicalRecords;
	@Autowired
	MedicalRecordsService medicalRecordsService;
	@Autowired
	MedicalrecordsRepository medicalRecordsRepository;

	public List<MedicalHistoryModel> getMedicalHistoryBylastName(String lastName) {

		ArrayList<MedicalHistoryModel> listMedicalHistory = new ArrayList<MedicalHistoryModel>();
		ArrayList<String> medications = new ArrayList<String>();
		ArrayList<String> allergies = new ArrayList<String>();

		List<Long> listIdsEntitiesMedicalRecords = new ArrayList<Long>();
		listIdsEntitiesMedicalRecords = medicalRecordsService.getlistIdsEntitiesMedicalRecords();

		for (Long i : listIdsEntitiesMedicalRecords) {
			medicalRecords = medicalRecordsRepository.getById(i);

			if (medicalRecords.getLastName().equals(lastName)) {
				medications = medicalRecords.getMedications();
				allergies = medicalRecords.getAllergies();

				medicalHistory.setId(i);
				medicalHistory.setMedications(medications);
				medicalHistory.setAllergies(allergies);

				listMedicalHistory.add(medicalHistory);
			}
		}

		return listMedicalHistory;
	}
}