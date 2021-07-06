package com.safetynets.alerts.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ChildInfoModelTest {

	ChildInfoModel childInfo = new ChildInfoModel();
	ChildInfoModel newChildInfo = new ChildInfoModel();

	@Test
	public void testEquals() {
		assertThat(childInfo.equals(newChildInfo)).isEqualTo(true);
	}
	
	@Test
	public void testToString() {
		String expected = "ChildInfoModel(id=null, firstName=null, lastName=null, age=0, familyRelationShip=null)";
		assertThat(childInfo.toString()).isEqualTo(expected);
	}
	
	@Test
	public void testCanEqual() {
		assertThat(childInfo.canEqual(newChildInfo)).isEqualTo(true);
	}

	@Test
	public void testHashCode() {
		assertThat(childInfo.hashCode()).isEqualTo(723907859);
	}
}
