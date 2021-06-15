package com.safetynets.alerts.api.model;

import java.util.HashSet;

import lombok.Data;

@Data
public class PersonWithMedicalHistoryModel {
	
    private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private Long stationNumber;
	private int age;
	private String phone;
	private HashSet<String> medications;
	private HashSet<String> allergies;
}
