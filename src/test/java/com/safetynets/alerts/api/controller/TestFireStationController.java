package com.safetynets.alerts.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.service.FireStationService;
import com.safetynets.alerts.api.service.ReaderJsonService;

@WebMvcTest(controllers = FireStationController.class)
public class TestFireStationController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReaderJsonService readerJsonService;

	@MockBean
	private FireStationService fireStationService;

	@Test
	public void testGetAllFireStation() throws Exception {

		// GIVEN
		FireStationModel fireStation = new FireStationModel();
		fireStation.setAddress("Tokyo");
		fireStation.setStation((long) 99);
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		fireStations.add(fireStation);

		// WHEN
		when(fireStationService.getAllFireStation()).thenReturn(fireStations);

		// THEN
		mockMvc.perform(get("/firestation")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].address", is("Tokyo")));
	}

	@Test
	public void testGetFirestationsByStation() throws Exception {

		// GIVEN
		FireStationModel fireStation = new FireStationModel();
		fireStation.setAddress("London");
		fireStation.setStation((long) 101);
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		fireStations.add(fireStation);

		// WHEN
		when(fireStationService.getFirestationsByStation((long) 101)).thenReturn(fireStations);

		// THEN
		mockMvc.perform(get("/firestation?station=101")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].address", is(("London"))));
	}

	@Test
	public void testPostFireStation() throws Exception {

		// GIVEN
		FireStationModel fireStationInput = new FireStationModel();
		fireStationInput.setAddress("Tokyo");
		fireStationInput.setStation((long) 101);
		FireStationModel fireStationOutput = new FireStationModel();
		fireStationOutput.setAddress("Dubai");
		fireStationOutput.setStation((long) 22);

		// WHEN
		when(fireStationService.postFireStation(fireStationInput)).thenReturn(fireStationOutput);

		// THEN
		String newFirestation = "{ \"address\":\"Tokyo\", \"station\":\"101\"}";
		MockHttpServletRequestBuilder req = post("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(newFirestation);
		mockMvc.perform(req).andExpect(status().isCreated()).andExpect(jsonPath("$.address", is("Dubai")))
				.andExpect(jsonPath("$.station", is(22)));
	}
	
	@Test
	public void testPutFireStation() throws Exception {
		String newFirestation = "{ \"address\":\"Tokyo\", \"station\":\"101\"}";
		MockHttpServletRequestBuilder req = put("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(newFirestation);
		mockMvc.perform(req).andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteFireStation() throws Exception {
		mockMvc.perform(get("/firestation?address=Paris")).andExpect(status().isOk());
	}

}