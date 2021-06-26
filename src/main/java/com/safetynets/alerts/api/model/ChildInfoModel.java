package com.safetynets.alerts.api.model;

import java.util.HashSet;

import lombok.Data;

/** 
 * Classe modélisant un objet de type "ChildInfoModel"
 * 
 */
@Data
public class ChildInfoModel {
    private Long id;
    private String firstName;
	private String lastName;
	private int age;
	private HashSet<PersonModel> familyRelationShip;
}