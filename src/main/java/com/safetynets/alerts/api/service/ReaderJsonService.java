package com.safetynets.alerts.api.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.DataSourceModel;
import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.repository.FireStationRepository;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;
import com.safetynets.alerts.api.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class ReaderJsonService {

	public static List<PersonModel> getListPersons(String link)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(link), DataSourceModel.class);
		return dataSource.getPersons();
	}

	public static List<FireStationModel> getListFireStations(String link)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(link), DataSourceModel.class);
		return dataSource.getFireStations();
	}

	public static List<MedicalRecordModel> getListMedicalRecords(String link)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(link), DataSourceModel.class);
		return dataSource.getMedicalRecords();
	}

	@Autowired
	public PersonRepository personsRepository;
	@Autowired
	public FireStationRepository firestationsRepository;
	@Autowired
	public MedicalRecordRepository medicalrecordsRepository;

	public void SavingJsonInDataBase(String path) throws JsonParseException, JsonMappingException, IOException {

		// Creation d'un objet java à partir de la lecture du JSON
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(path), DataSourceModel.class);
		
		// Sauvegarde dans la base de données des entités "Persons"
		List<PersonModel> listPersons = dataSource.getPersons();
		personsRepository.saveAll(listPersons);

		// Sauvegarde dans la base de données des entités "FireStations"
		List<FireStationModel> listFireStations = dataSource.getFireStations();
		firestationsRepository.saveAll(listFireStations);

		// Sauvegarde dans la base de données des entités "MedicalRecords"
		List<MedicalRecordModel> listMedicalrecords = dataSource.getMedicalRecords();
		medicalrecordsRepository.saveAll(listMedicalrecords);

	}

}