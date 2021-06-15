package com.safetynets.alerts.api.model;

import java.util.HashSet;

import lombok.Data;

@Data
public class PersonForStationModel {
	
    private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private int age;
	private HashSet<String> medications;
	private HashSet<String> allergies;
}
