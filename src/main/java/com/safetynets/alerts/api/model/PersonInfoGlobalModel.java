package com.safetynets.alerts.api.model;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PersonInfoGlobalModel {
    private String firstName;
	private String lastName;
	private String address;
	private int age;
	private String email;
	private HashSet<String> medications;
	private HashSet<String> allergies;
}