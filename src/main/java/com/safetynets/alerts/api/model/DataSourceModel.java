package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class DataSourceModel {

	ArrayList<PersonsModel> persons;
	ArrayList<FireStationsModel> fireStations;
	ArrayList<MedicalRecordsModel> medicalRecords;
	
}
