package com.safetynets.alerts.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.databind.JsonMappingException;

import com.safetynets.alerts.api.repository.PersonsRepository;
import com.safetynets.alerts.api.service.MedicalRecordsService;
import com.safetynets.alerts.api.service.PersonsService;
import com.safetynets.alerts.api.service.ReaderJsonService;
import com.safetynets.alerts.api.controller.PersonsController;
import com.safetynets.alerts.api.model.PersonsModel;
//import com.safetynets.alerts.api.controller.Controller;//  Ajout pour test
import com.safetynets.alerts.api.repository.FirestationsRepository;
import com.safetynets.alerts.api.repository.MedicalrecordsRepository;

import lombok.Data;

@Data
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired PersonsService personsService;
	@Autowired PersonsRepository personsRepository;
	@Autowired PersonsController personsController;
	@Autowired PersonsModel personsModel;
	@Autowired MedicalRecordsService fireStationsService;
	@Autowired FirestationsRepository fireStationsRepository;
	@Autowired MedicalrecordsRepository medicalrecordsRepository;
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