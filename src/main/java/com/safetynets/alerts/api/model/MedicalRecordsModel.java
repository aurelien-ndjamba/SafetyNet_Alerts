package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "medicalrecords")
public class MedicalRecordsModel {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	private String firstName;
	private String lastName;
	private String birthdate;
	private ArrayList<String> medications;
	private ArrayList<String> allergies;
}