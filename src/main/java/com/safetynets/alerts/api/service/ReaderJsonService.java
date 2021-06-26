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

/**
 * Classe définissant les méthodes permettant de:
 * 
 * - Obtenir des listes d'objets désérialisés depuis un fichier JSON.
 * - Sauvegarde des entités dans une base de donnée depuis des listes d'objet.
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@Data
@Service
public class ReaderJsonService {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private FireStationRepository firestationRepository;
	@Autowired
	private MedicalRecordRepository medicalrecordRepository;

	/**
	 * Donne la liste d'objets "PersonModel" désérialisés depuis un fichier JSON.
	 * 
	 * @param firstName (String path)
	 * @return List<PersonModel>
	 * 
	 */
	public static List<PersonModel> getListPersons(String path)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(path), DataSourceModel.class);
		return dataSource.getPersons();
	}

	/**
	 * Donne la liste d'objets "FireStationModel" désérialisés depuis un fichier JSON.
	 * 
	 * @param firstName (String path)
	 * @return List<FireStationModel>
	 * 
	 */
	public static List<FireStationModel> getListFireStations(String path)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(path), DataSourceModel.class);
		return dataSource.getFireStations();
	}

	/**
	 * Donne la liste d'objets "MedicalRecordModel" désérialisés depuis un fichier JSON.
	 * 
	 * @param firstName (String path)
	 * @return List<MedicalRecordModel>
	 * 
	 */
	public static List<MedicalRecordModel> getListMedicalRecords(String path)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(path), DataSourceModel.class);
		return dataSource.getMedicalRecords();
	}

	/**
	 * Sauvegarde des entités dans une base de donnée depuis des listes d'objet.
	 * 
	 * @param firstName (String path)
	 * @return void
	 * 
	 */
	public void SavingJsonInDataBase(String path) throws JsonParseException, JsonMappingException, IOException {

		// Creation d'un objet java à partir de la lecture du JSON
		ObjectMapper objectMapper = new ObjectMapper();
		DataSourceModel dataSource = objectMapper.readValue(new File(path), DataSourceModel.class);

		// Sauvegarde dans la base de données des entités "Persons"
		List<PersonModel> listPersons = dataSource.getPersons();
		personRepository.saveAll(listPersons);

		// Sauvegarde dans la base de données des entités "FireStations"
		List<FireStationModel> listFireStations = dataSource.getFireStations();
		firestationRepository.saveAll(listFireStations);

		// Sauvegarde dans la base de données des entités "MedicalRecords"
		List<MedicalRecordModel> listMedicalrecords = dataSource.getMedicalRecords();
		medicalrecordRepository.saveAll(listMedicalrecords);

	}

}