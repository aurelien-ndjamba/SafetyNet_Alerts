package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class InfoByStationNumber {
	
	ArrayList<PersonDataBaseModel> PersonsByStation;
	long Station;
	int CountAdult;
	int CountChildren;
}
