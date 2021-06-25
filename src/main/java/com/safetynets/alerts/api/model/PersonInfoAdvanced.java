package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PersonInfoAdvanced {
	
    private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private Long stationNumber;
	private int age;
	private String phone;
	private ArrayList<String> medications;
	private ArrayList<String> allergies;
}
