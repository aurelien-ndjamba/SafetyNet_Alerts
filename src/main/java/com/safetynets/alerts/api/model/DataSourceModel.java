package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DataSourceModel {

	ArrayList<PersonModel> persons;
	ArrayList<FireStationModel> fireStations;
	ArrayList<MedicalRecordModel> medicalRecords;
	
}
