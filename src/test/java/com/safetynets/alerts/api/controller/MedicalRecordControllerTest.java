package com.safetynets.alerts.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynets.alerts.api.model.MedicalRecordModel;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

	@Autowired
	public MockMvc mockMvc;
	@Autowired
	public MedicalRecordModel medicalRecord;

	@Test
	public void testUpdateAndGetMethod() throws Exception {
		// GIVEN
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Boyd");
		medicalRecord.setBirthdate("11/11/1111");
		// WHEN
		ObjectMapper objectMapper = new ObjectMapper();
		String newFireStationInJson = objectMapper.writeValueAsString(medicalRecord);
		// THEN
		mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(newFireStationInJson))
				.andExpect(status().isOk());
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].birthdate", is("11/11/1111")));
	}

	@Test
	public void testPutAndDeleteMethod() throws Exception {
		// GIVEN
		ArrayList<String> medications = new ArrayList<String>();
		medications.add("paludisme");
		ArrayList<String> allergies = new ArrayList<String>();
		allergies.add("covid19");

		medicalRecord.setFirstName("Aurelien");
		medicalRecord.setLastName("NDJAMBA");
		medicalRecord.setBirthdate("02/02/2002");
		medicalRecord.setMedications(medications);
		medicalRecord.setAllergies(allergies);

		// WHEN
		ObjectMapper objectMapper = new ObjectMapper();
		String newFireStationInJson = objectMapper.writeValueAsString(medicalRecord);
		// THEN
		// Post
		mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(newFireStationInJson))
				.andExpect(status().isCreated());
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk()).andExpect(jsonPath("$[23].id", is(24)));
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk()).andExpect(jsonPath("$[23].birthdate", is("02/02/2002")));
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk()).andExpect(jsonPath("$[23].firstName", is("Aurelien")));
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk()).andExpect(jsonPath("$[23].allergies", is(allergies)));

		// Delete
		mockMvc.perform(delete("/medicalRecord?firstNamelastName=JacobBoyd").contentType(MediaType.APPLICATION_JSON).content(newFireStationInJson))
				.andExpect(status().isOk());
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk()).andExpect(jsonPath("$[22].id", is(24)));
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk()).andExpect(jsonPath("$[22].birthdate", is("02/02/2002")));
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk()).andExpect(jsonPath("$[22].firstName", is("Aurelien")));
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk()).andExpect(jsonPath("$[22].allergies", is(allergies)));
	}

}