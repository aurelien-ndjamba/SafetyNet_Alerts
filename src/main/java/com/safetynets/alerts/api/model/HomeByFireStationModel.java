package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class HomeByFireStationModel {
	
	private String address;
	private ArrayList<PersonForStationModel> listPersonsByAddress;
	
}
