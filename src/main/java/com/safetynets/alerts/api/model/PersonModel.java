package com.safetynets.alerts.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

/** 
 * Classe modélisant un objet entité de type "PersonModel"
 * 
 */
@Data
@Entity
@Table(name = "persons")
@Component
public class PersonModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private long zip;
	private String phone;
	private String email;
}
