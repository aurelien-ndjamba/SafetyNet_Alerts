package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynets.alerts.api.model.FireStationModel;

@SpringBootTest
public class FireStationServiceIT {

	@Autowired
	private FireStationService fireStationService;

	@Test
	public void testFindAll() throws ParseException {
		assertThat(fireStationService.findAll().size()).isEqualTo(12);
	}

	@Test
	public void testFindByStation() throws ParseException {
		assertThat(fireStationService.findByStation(1).size()).isEqualTo(3);
	}

	@Test
	public void testFindStationByAddress() throws ParseException {
		assertThat(fireStationService.findStationByAddress("748 Townings Dr")).isEqualTo(3);
	}

	@Test
	public void testFindFirestationsByManyStation() throws ParseException {
		List<Long> stations = new ArrayList<Long>();
		stations.add((long) 1);
		stations.add((long) 2);
		stations.add((long) 3);
		assertThat(fireStationService.findFirestationsByManyStation(stations).size()).isEqualTo(11);
	}

	@Test
	public void testSave() throws ParseException {
		FireStationModel fireStation = new FireStationModel();
		fireStation.setId((long) 57000);
		fireStation.setAddress("Metz");
		fireStation.setStation(57);
		assertThat(fireStationService.save(fireStation).getId()).isEqualTo(13);
	}

	@Test
	public void testUpdate() throws ParseException {
		FireStationModel fireStation = new FireStationModel();
		fireStation.setId((long) 88000);
		fireStation.setAddress("834 Binoc Ave");
		fireStation.setStation(88);
		assertThat(fireStationService.update(fireStation).getId()).isEqualTo(3);
		assertThat(fireStationService.update(fireStation).getStation()).isEqualTo(88);
	}

}
