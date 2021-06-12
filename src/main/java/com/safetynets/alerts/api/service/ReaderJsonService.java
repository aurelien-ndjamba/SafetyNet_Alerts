package com.safetynets.alerts.api.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.safetynets.alerts.api.model.FireStationsModel;
import com.safetynets.alerts.api.model.DataSourceModel;
import com.safetynets.alerts.api.model.MedicalRecordsModel;
import com.safetynets.alerts.api.model.PersonsModel;
import com.safetynets.alerts.api.repository.FirestationsRepository;
import com.safetynets.alerts.api.repository.MedicalrecordsRepository;
import com.safetynets.alerts.api.repository.PersonsRepository;

import lombok.Data;

@Data
@Component
public class ReaderJsonService {

	public static List<PersonsModel> getListPersons(String link)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(link), DataSourceModel.class);
		return dataSource.getPersons();
	}

	public static List<FireStationsModel> getListFireStations(String link)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(link), DataSourceModel.class);
		return dataSource.getFireStations();
	}

	public static List<MedicalRecordsModel> getListMedicalRecords(String link)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(link), DataSourceModel.class);
		return dataSource.getMedicalRecords();
	}

	@Autowired
	public PersonsRepository personsRepository;
	@Autowired
	public FirestationsRepository firestationsRepository;
	@Autowired
	public MedicalrecordsRepository medicalrecordsRepository;

	public void SavingJsonInDataBase(String path) throws JsonParseException, JsonMappingException, IOException {

		// Creation d'un objet java à partir de la lecture du JSON
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(path), DataSourceModel.class);
		
		// Sauvegarde dans la base de données des entités "Persons"
		List<PersonsModel> listPersons = dataSource.getPersons();
		personsRepository.saveAll(listPersons);

		// Sauvegarde dans la base de données des entités "FireStations"
		List<FireStationsModel> listFireStations = dataSource.getFireStations();
		firestationsRepository.saveAll(listFireStations);

		// Sauvegarde dans la base de données des entités "MedicalRecords"
		List<MedicalRecordsModel> listMedicalrecords = dataSource.getMedicalRecords();
		medicalrecordsRepository.saveAll(listMedicalrecords);

		System.out.println(listPersons);
		System.out.println(listFireStations);
		System.out.println(listMedicalrecords);

	}

}