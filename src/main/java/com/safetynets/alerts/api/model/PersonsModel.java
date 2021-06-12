package com.safetynets.alerts.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Entity
@Table(name = "persons")
@Component
public class PersonsModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
//	@NotNull(message="First name cannot be null")
//	@Size(min=2, message="First name must be less than 2 caracters")
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private long zip;
	private String phone;
	private String email;
}
