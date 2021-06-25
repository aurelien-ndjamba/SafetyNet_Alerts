package com.safetynets.alerts.api.model;

import java.util.HashSet;

import lombok.Data;

@Data
public class PersonInfoAdvancedModel {
	
	long id;
	String firstName;
	String lastName;
	String phone;
	int age;
	HashSet<String> medications;
	HashSet<String> allergies;

}
