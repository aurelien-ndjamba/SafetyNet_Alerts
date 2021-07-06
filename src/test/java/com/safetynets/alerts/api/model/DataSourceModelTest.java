package com.safetynets.alerts.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DataSourceModelTest {
	
	DataSourceModel dataSourceModel = new DataSourceModel();
	DataSourceModel newdataSourceModel = new DataSourceModel();

	@Test
	public void testEquals() {
		assertThat(dataSourceModel.equals(newdataSourceModel)).isEqualTo(true);
	}
	
	@Test
	public void testToString() {
		String expected = "DataSourceModel(persons=null, fireStations=null, medicalRecords=null)";
		assertThat(dataSourceModel.toString()).isEqualTo(expected);
	}
	
	@Test
	public void testCanEqual() {
		assertThat(dataSourceModel.canEqual(newdataSourceModel)).isEqualTo(true);
	}

}
