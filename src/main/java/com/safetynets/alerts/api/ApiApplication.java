package com.safetynets.alerts.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.databind.JsonMappingException;

import com.safetynets.alerts.api.repository.PersonRepository;
import com.safetynets.alerts.api.service.MedicalRecordService;
import com.safetynets.alerts.api.service.PersonsService;
import com.safetynets.alerts.api.service.ReaderJsonService;
import com.safetynets.alerts.api.controller.PersonController;
import com.safetynets.alerts.api.model.PersonModel;
//import com.safetynets.alerts.api.controller.Controller;//  Ajout pour test
import com.safetynets.alerts.api.repository.FireStationRepository;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;

import lombok.Data;

@Data
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired PersonsService personsService;
	@Autowired PersonRepository personsRepository;
	@Autowired PersonController personsController;
	@Autowired PersonModel personsModel;
	@Autowired MedicalRecordService fireStationsService;
	@Autowired FireStationRepository fireStationsRepository;
	@Autowired MedicalRecordRepository medicalrecordsRepository;
	@Autowired ReaderJsonService readerJsonService;
	
//	@Autowired Controller controller;// Ajout pour test
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws JsonParseException, JsonMappingException, IOException, Exception {

		String path = "src/main/resources/data.json";
		readerJsonService.SavingJsonInDataBase(path);
		}
	}