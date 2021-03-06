package com.safetynets.alerts.api;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynets.alerts.api.service.ReaderJsonService;

import lombok.Data;

@Data
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired ReaderJsonService readerJsonService;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws JsonParseException, JsonMappingException, IOException, Exception {

		ResourceBundle dataJson = ResourceBundle.getBundle("application");
		readerJsonService.SavingJsonInDataBase(dataJson.getString("path"));
		}
	}