package com.safetynets.alerts.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class FireStationModelTest {

	@Disabled
	@Test
	public void testSetId() {

		// GIVEN
		FireStationModel fireStation = new FireStationModel();
		// WHEN
		fireStation.setId((long) 7);
		// THEN
		long expected = 7;
//		assertThat(fireStation.getId()).thenReturn(expected);
	}

}
