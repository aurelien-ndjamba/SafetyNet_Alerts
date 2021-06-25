package com.safetynets.alerts.api.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PersonImpactedByStationNumberModel {
	
    private long stationNumber;
    private ArrayList<PersonImpactedByStationNumber> listSpecificInfoPersons;
	private int countAdult;
	private int countChildren;
	
}