package com.safetynets.alerts.api.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PersonImpactedByStationNumber {
	
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	
}
