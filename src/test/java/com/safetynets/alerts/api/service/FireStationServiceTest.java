package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.repository.FireStationRepository;

@SpringBootTest
public class FireStationServiceTest {

	@Autowired
	private FireStationService fireStationService;
	@Mock
	private FireStationRepository fireStationRepositoryMock;

	@Test
	public void testFindAll() throws ParseException {
		// GIVEN
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setId((long) 1111);
		fireStation.setAddress("Paris");
		fireStation.setStation((long) 75);
		fireStations.add(fireStation);
		// WHEN
		when(fireStationRepositoryMock.findAll()).thenReturn(fireStations);
		fireStationService.setFireStationRepository(fireStationRepositoryMock);
		// THEN
		assertThat(fireStationService.findAll().size()).isEqualTo(1);
	}

	@Test
	public void testFindByStation() throws ParseException {
		// GIVEN
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setId((long) 1111);
		fireStation.setAddress("Paris");
		fireStation.setStation((long) 75);
		fireStations.add(fireStation);
		// WHEN
		when(fireStationRepositoryMock.findByStation(12)).thenReturn(fireStations);
		fireStationService.setFireStationRepository(fireStationRepositoryMock);
		// THEN
		assertThat(fireStationService.findByStation(12).size()).isEqualTo(1);
	}

	@Test
	public void testFindStationByAddress() throws ParseException {
		// GIVEN
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setId((long) 1111);
		fireStation.setAddress("Paris");
		fireStation.setStation((long) 75);
		fireStations.add(fireStation);
		// WHEN
		when(fireStationRepositoryMock.findByAddress("Paris")).thenReturn(fireStations);
		fireStationService.setFireStationRepository(fireStationRepositoryMock);
		// THEN
		assertThat(fireStationService.findStationByAddress("Paris")).isEqualTo(75);
	}

	@Test
	public void testFindFirestationsByManyStation() throws ParseException {
		// GIVEN
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setId((long) 1111);
		fireStation.setAddress("Paris");
		fireStation.setStation((long) 75);
		fireStations.add(fireStation);
		// WHEN
		when(fireStationRepositoryMock.findByStation(7)).thenReturn(fireStations);
		when(fireStationRepositoryMock.findByStation(8)).thenReturn(fireStations);
		when(fireStationRepositoryMock.findByStation(9)).thenReturn(fireStations);
		fireStationService.setFireStationRepository(fireStationRepositoryMock);
		// THEN
		List<Long> stations = new ArrayList<Long>();
		stations.add((long) 7);
		stations.add((long) 8);
		stations.add((long) 9);
		assertThat(fireStationService.findFirestationsByManyStation(stations).size()).isEqualTo(3);
	}

	@Test
	public void testSave() throws ParseException {
		// GIVEN
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setId((long) 1111);
		fireStation.setAddress("Paris");
		fireStation.setStation((long) 75);
		fireStations.add(fireStation);
		// WHEN
		when(fireStationRepositoryMock.save(fireStation)).thenReturn(fireStation);
		fireStationService.setFireStationRepository(fireStationRepositoryMock);
		// THEN
		assertThat(fireStationService.save(fireStation).getId()).isEqualTo(null);
	}

	@Test
	public void testUpdate() throws ParseException {
		// GIVEN
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		FireStationModel fireStation = new FireStationModel();
		fireStation.setId((long) 11);
		fireStation.setAddress("Paris");
		fireStation.setStation((long) 7);
		fireStations.add(fireStation);
		List<FireStationModel> fireStationsInDb = new ArrayList<FireStationModel>();
		FireStationModel fireStationInDb = new FireStationModel();
		fireStationInDb.setId((long) 1111);
		fireStationInDb.setAddress("Paris");
		fireStationInDb.setStation((long) 75);
		fireStationsInDb.add(fireStationInDb);
		// WHEN
		when(fireStationRepositoryMock.findByAddress("Paris"))
		.thenReturn(fireStationsInDb);
		when(fireStationRepositoryMock.save(fireStation))
		.thenReturn(fireStation);
		fireStationService.setFireStationRepository(fireStationRepositoryMock);
		// THEN
		assertThat(fireStationService.update(fireStation).getId()).isEqualTo(1111);
	}

}
