package com.safetynets.alerts.api.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;

@Service
public class AllergiesHistoryService {

	@Autowired
	private MedicalRecordModel medicalRecords;
	@Autowired
	private MedicalRecordService medicalRecordsService;
	@Autowired
	private MedicalRecordRepository medicalRecordsRepository;

	public HashSet<String> getAllergiesHistory(String lastName) {

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
	
	public HashSet<String>getAllergiesHistory(String firstName, String lastName) {

		HashSet<String> listAllergiesHistory = new HashSet<String>();
		ArrayList<String> allergies = new ArrayList<String>();

		List<Long> listIdsEntitiesMedicalRecords = new ArrayList<Long>();
		listIdsEntitiesMedicalRecords = medicalRecordsService.getlistIdsEntitiesMedicalRecords();

		for (Long i : listIdsEntitiesMedicalRecords) {

			medicalRecords = medicalRecordsRepository.getById(i);

			if (medicalRecords.getFirstName().equals(firstName) && medicalRecords.getLastName().equals(lastName)) {

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