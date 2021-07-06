package com.safetynets.alerts.api.model;

import java.util.List;

import lombok.Data;

/** 
 * Classe modélisant un objet de type "PersonsByAddress"
 * 
 */
@Data
public class PersonsByAddressModel {
	
	private String address;
	private List<PersonModel> PersonsByAddress;
	
}
