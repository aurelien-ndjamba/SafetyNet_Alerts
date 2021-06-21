//package com.safetynets.alerts.api.controller;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.safetynets.alerts.api.model.FireStationModel;
//import com.safetynets.alerts.api.repository.FireStationRepository;
//
//class PutMethodFireStationTest {
//	
//	@Autowired
//	public MockMvc mockMvc;
//	@Autowired
//	public FireStationModel newFireStation;
//	
//	@Test
//	public void testPutMethod() throws Exception {
//		// GIVEN
//		newFireStation.setAddress("1509 Culver St");
//		newFireStation.setStation((long) 99);
//		// WHEN
//		ObjectMapper objectMapper = new ObjectMapper();
//		String newFireStationInJson = objectMapper.writeValueAsString(newFireStation);
//		// THEN
//		mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON).content(newFireStationInJson)).andExpect(status().isOk());
//		mockMvc.perform(get("/firestation")).andExpect(status().isOk()).andExpect(jsonPath("$[0].address", is("1509 Culver St")));
//		mockMvc.perform(get("/firestation")).andExpect(status().isOk()).andExpect(jsonPath("$[0].station", is(99)));
//	}
//
//}
