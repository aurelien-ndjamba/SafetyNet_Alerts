package com.safetynets.alerts.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynets.alerts.api.model.MedicalRecordsModel;
import com.safetynets.alerts.api.repository.MedicalrecordsRepository;

@Component
public class MedicationsHistoryBylastNameService {

	@Autowired
	private MedicalRecordsModel medicalRecords;
	@Autowired
	private MedicalRecordsService medicalRecordsService;
	@Autowired
	private MedicalrecordsRepository medicalRecordsRepository;

	public ArrayList<ArrayList<String>> getMedicalHistory(String lastName) {

		ArrayList<ArrayList<String>> listMedicationsHistory = new ArrayList<ArrayList<String>>();
		ArrayList<String> medications = new ArrayList<String>();

		List<Long> listIdsEntitiesMedicalRecords = new ArrayList<Long>();
		listIdsEntitiesMedicalRecords = medicalRecordsService.getlistIdsEntitiesMedicalRecords();

		for (Long i : listIdsEntitiesMedicalRecords) {
			
			medicalRecords = medicalRecordsRepository.getById(i);
			
			if (medicalRecords.getLastName().equals(lastName)) {
				medications = medicalRecords.getMedications(); //
				listMedicationsHistory.add(medications);
			}
		}
		return listMedicationsHistory;
	}
}