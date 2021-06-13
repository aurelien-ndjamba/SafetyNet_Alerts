package com.safetynets.alerts.api.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynets.alerts.api.model.MedicalRecordsModel;
import com.safetynets.alerts.api.repository.MedicalrecordsRepository;

@Component
public class AllergiesHistoryBylastNameService {

	@Autowired
	private MedicalRecordsModel medicalRecords;
	@Autowired
	private MedicalRecordsService medicalRecordsService;
	@Autowired
	private MedicalrecordsRepository medicalRecordsRepository;

	public HashSet<String> getMedicalHistory(String lastName) {

		HashSet<String> listAllergiesHistory = new HashSet<String>();
		ArrayList<String> allergies = new ArrayList<String>();

		List<Long> listIdsEntitiesMedicalRecords = new ArrayList<Long>();
		listIdsEntitiesMedicalRecords = medicalRecordsService.getlistIdsEntitiesMedicalRecords();

		for (Long i : listIdsEntitiesMedicalRecords) {

			medicalRecords = medicalRecordsRepository.getById(i);

			if (medicalRecords.getLastName().equals(lastName)) {

				allergies = medicalRecords.getAllergies();
				int allergiesNumber = allergies.size();

				for (int j = 0; j < allergiesNumber; j++) {
					listAllergiesHistory.add(allergies.get(j));
				}
			}
		}
		return listAllergiesHistory;
	}
}