package com.safetynets.alerts.api.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.safetynets.alerts.api.model.FireStationDataBaseModel;
import com.safetynets.alerts.api.model.DataSourceModel;
import com.safetynets.alerts.api.model.MedicalRecordDataBaseModel;
import com.safetynets.alerts.api.model.PersonDataBaseModel;
import com.safetynets.alerts.api.repository.FireStationRepository;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;
import com.safetynets.alerts.api.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class ReaderJsonService {
	
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private FireStationRepository firestationRepository;
	@Autowired
	private MedicalRecordRepository medicalrecordRepository;

	public static List<PersonDataBaseModel> getListPersons(String link)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(link), DataSourceModel.class);
		return dataSource.getPersons();
	}

	public static List<FireStationDataBaseModel> getListFireStations(String link)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(link), DataSourceModel.class);
		return dataSource.getFireStations();
	}

	public static List<MedicalRecordDataBaseModel> getListMedicalRecords(String link)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(link), DataSourceModel.class);
		return dataSource.getMedicalRecords();
	}

	public void SavingJsonInDataBase(String path) throws JsonParseException, JsonMappingException, IOException {

		// Creation d'un objet java à partir de la lecture du JSON
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(path), DataSourceModel.class);
		
		// Sauvegarde dans la base de données des entités "Persons"
		List<PersonDataBaseModel> listPersons = dataSource.getPersons();
		personRepository.saveAll(listPersons);

		// Sauvegarde dans la base de données des entités "FireStations"
		List<FireStationDataBaseModel> listFireStations = dataSource.getFireStations();
		firestationRepository.saveAll(listFireStations);

		// Sauvegarde dans la base de données des entités "MedicalRecords"
		List<MedicalRecordDataBaseModel> listMedicalrecords = dataSource.getMedicalRecords();
		medicalrecordRepository.saveAll(listMedicalrecords);

	}

}