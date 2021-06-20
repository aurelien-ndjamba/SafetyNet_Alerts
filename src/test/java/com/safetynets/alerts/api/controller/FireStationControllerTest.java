package com.safetynets.alerts.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


//@WebMvcTest(controllers = FireStationController.class)

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test // OK
	public void getAllFireStationTest() throws Exception {
		long result = 2;
		mockMvc.perform(get("/firestation")).andExpect(status().isOk())
				.andExpect(jsonPath("$[12].station", is(result)));
	}

	@Test
	public void createFireStationTest() throws Exception {
		mockMvc.perform(post("/firestation")).andExpect(status().isCreated());
	}

	@Test
	public void updateStationNumberTest() throws Exception {
		mockMvc.perform(put("/firestation")).andExpect(status().isOk());
	}

	@Test
	public void deleteFireStationTest() throws Exception {
//		int result;
		mockMvc.perform(delete("/firestation?address=951 LoneTree Rd&station=2"));
		mockMvc.perform(get("/firestation/")).andExpect(status().isOk()).andExpect(jsonPath("$[12].station", is(null)));
	}

}
