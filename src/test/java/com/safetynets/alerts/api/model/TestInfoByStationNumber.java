package com.safetynets.alerts.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestInfoByStationNumber {
	
	InfoByStationNumber infoByStationNumber = new InfoByStationNumber();
	
	@BeforeEach
	public void setUp() {
		
		PersonModel person = new PersonModel();
		ArrayList<PersonModel> personsModel = new ArrayList<PersonModel>();
		personsModel.add(person);
		
		infoByStationNumber.setStation((long)9);
		infoByStationNumber.setCountAdult(7);
		infoByStationNumber.setCountChildren(14);
		infoByStationNumber.setPersonsByStation(personsModel);
	}

	@Disabled
	@Test
	public void testSetId() {

		// WHEN
		infoByStationNumber.setCountChildren(15);
		// THEN
//		assertThat(infoByStationNumber.getCountChildren()).thenReturn(15);
	}
	
}
