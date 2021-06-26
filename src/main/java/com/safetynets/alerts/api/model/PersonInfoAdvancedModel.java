package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import lombok.Data;

/** 
 * Classe modélisant un objet de type "PersonInfoAdvancedModel"
 * 
 */
@Data
public class PersonInfoAdvancedModel {
	
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
