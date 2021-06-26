package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.Data;

/** 
 * Classe modélisant un objet de type "DataSourceModel"
 * 
 */
@Data
@Component
public class DataSourceModel {

	ArrayList<PersonModel> persons;
	ArrayList<FireStationModel> fireStations;
	ArrayList<MedicalRecordModel> medicalRecords;
	
}
