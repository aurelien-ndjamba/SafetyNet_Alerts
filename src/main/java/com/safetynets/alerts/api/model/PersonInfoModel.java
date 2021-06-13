package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PersonInfoModel {
	
    private Long id;
    
    private String firstName;
	private String lastName;
	
	private String address;
	
	private int age;

	private String email;
	
	private ArrayList<ArrayList<String>> medications;
	private ArrayList<ArrayList<String>> allergies;
}