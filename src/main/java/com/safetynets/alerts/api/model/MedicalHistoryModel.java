package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import javax.persistence.Entity;


import lombok.Data;

@Data
@Entity
public class MedicalHistoryModel {
	private Long id;
	private ArrayList<String> medications;
	private ArrayList<String> allergies;
}