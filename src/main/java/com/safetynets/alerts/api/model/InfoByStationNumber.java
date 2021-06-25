package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class InfoByStationNumber {
	
	ArrayList<PersonModel> PersonsByStation;
	long Station;
	int CountAdult;
	int CountChildren;
}
