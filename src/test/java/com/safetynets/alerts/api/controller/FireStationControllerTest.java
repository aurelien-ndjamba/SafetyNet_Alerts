package com.safetynets.alerts.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynets.alerts.api.model.FireStationModel;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {

	@Autowired
	public MockMvc mockMvc;
	@Autowired
	public FireStationModel fireStation;

	@Test
	public void testGetMethodWithParam() throws Exception {
		mockMvc.perform(get("/firestation?stationNumber=1")).andExpect(status().isOk())
		.andExpect(jsonPath("countAdult", is(5)));
	}
	
	@Test
	public void testUpdateAndGetMethodWithOutParam() throws Exception {
		// GIVEN
		fireStation.setAddress("1509 Culver St");
		fireStation.setStation((long) 99);
		
		// WHEN
		ObjectMapper objectMapper = new ObjectMapper();
		String newFireStationInJson = objectMapper.writeValueAsString(fireStation);
		
		// THEN
		mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON).content(newFireStationInJson))
				.andExpect(status().isOk());
		mockMvc.perform(get("/firestation")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].address", is("1509 Culver St")));
		mockMvc.perform(get("/firestation")).andExpect(status().isOk()).andExpect(jsonPath("$[0].station", is(99)));
	}

	@Test
	public void testPutAndDeleteMethod() throws Exception {
		// GIVEN
		fireStation.setAddress("Paris");
		fireStation.setStation((long) 77);
		
		// WHEN
		ObjectMapper objectMapper = new ObjectMapper();
		String newFireStationInJson = objectMapper.writeValueAsString(fireStation);
		
		// THEN
		// Post
		mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON).content(newFireStationInJson))
				.andExpect(status().isCreated());
		
		mockMvc.perform(get("/firestation")).andExpect(status().isOk()).andExpect(jsonPath("$[13].id", is(14)));
		mockMvc.perform(get("/firestation")).andExpect(status().isOk())
				.andExpect(jsonPath("$[13].address", is("Paris")));
		mockMvc.perform(get("/firestation")).andExpect(status().isOk()).andExpect(jsonPath("$[13].station", is(77)));
		// Delete
		mockMvc.perform(delete("/firestation").queryParam("address", "29 15th St"));
		
		mockMvc.perform(get("/firestation")).andExpect(status().isOk()).andExpect(jsonPath("$[12].id", is(14)));
		mockMvc.perform(get("/firestation")).andExpect(status().isOk())
				.andExpect(jsonPath("$[12].address", is("Paris")));
		mockMvc.perform(get("/firestation")).andExpect(status().isOk()).andExpect(jsonPath("$[12].station", is(77)));
	}

}