package com.safetynets.alerts.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynets.alerts.api.service.MedicalRecordService;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	MedicalRecordService medicalRecordsService;

	@Test
	public void getAllMedicalRecordsTest() throws Exception {
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk());
	}
	
	@Test
	public void createMedicalRecordsTest() throws Exception {
		mockMvc.perform(post("/medicalRecord")).andExpect(status().isOk());
	}

	@Test
	public void updateMedicalRecordsTest() throws Exception {
		mockMvc.perform(put("/medicalRecord")).andExpect(status().isOk());
	}

	@Test
	public void deleteMedicalRecordsTest() throws Exception {
		mockMvc.perform(delete("/medicalRecord")).andExpect(status().isOk());
	}
	
}
