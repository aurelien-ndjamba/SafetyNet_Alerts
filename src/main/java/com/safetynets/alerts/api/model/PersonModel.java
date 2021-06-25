package com.safetynets.alerts.api.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PersonModel {
	
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private long zip;
	private String phone;
	private String email;
}
