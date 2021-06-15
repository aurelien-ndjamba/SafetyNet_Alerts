package com.safetynets.alerts.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynets.alerts.api.service.FireStationService;

@SpringBootTest
@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private FireStationService fireStationsService;

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
		mockMvc.perform(delete("/firestation")).andExpect(status().isOk());
	}

}
