package com.safetynets.alerts.api.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PersonsByStationNumberModel {
	
	private String address;
	private List<PersonDataBaseModel> PersonsByAddress;
	
}
