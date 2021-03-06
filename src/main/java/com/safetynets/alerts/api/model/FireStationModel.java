package com.safetynets.alerts.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

/** 
 * Classe modélisant un objet entité de type "FireStationModel"
 * 
 */
@Data
@Entity
@Table(name = "firestations")
@Component
public class FireStationModel {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String address;
	public long station;

}