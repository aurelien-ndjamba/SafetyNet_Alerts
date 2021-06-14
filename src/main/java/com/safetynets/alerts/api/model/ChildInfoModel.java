package com.safetynets.alerts.api.model;

import java.util.HashSet;

import lombok.Data;

@Data
public class ChildInfoModel {
    private Long id;
    private String firstName;
	private String lastName;
	private int age;
	private HashSet<PersonsModel> familyRelationship;
}