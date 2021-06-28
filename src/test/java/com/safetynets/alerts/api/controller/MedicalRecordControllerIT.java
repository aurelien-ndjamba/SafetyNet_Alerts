package com.safetynets.alerts.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetAllMedicalRecord() throws Exception {
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[2].firstName", is("Tenley")));
	}

	@Test
	public void testGetMedicalRecordsById() throws Exception {
		mockMvc.perform(get("/medicalRecord?id=2")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName", is("Jacob")));
	}

	@Test
	public void testPostMedicalRecord() throws Exception {
		String newMedicalRecord = "{ \"firstName\":\"Emmanuel\", \"lastName\":\"Macron\", \"birthdate\":\"21/12/1977\", \"medications\":[\"pfizer:350mg\"], \"allergies\":[\"covid\"] }";

		MockHttpServletRequestBuilder req = post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(newMedicalRecord);
		mockMvc.perform(req).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is("Emmanuel")))
				.andExpect(jsonPath("$.lastName", is("Macron")));
	}

	@Test
	public void testUpdateMedicalRecord() throws Exception {

		String updateFirestation = "{ \"firstName\":\"Roger\", \"lastName\":\"Boyd\", \"birthdate\":\"21/12/2021\", \"medications\":[\"pfizer:350mg\"], \"allergies\":[\"gluten\"] }";
		MockHttpServletRequestBuilder reqPut = put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(updateFirestation);

		mockMvc.perform(reqPut).andExpect(status().isOk());

		mockMvc.perform(get("/medicalRecord?id=4")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.allergies.[0]", is("gluten")))
				.andExpect(jsonPath("$.birthdate", is("21/12/2021")));
	}

	@Test
	public void testDeleteMedicalRecordById() throws Exception {

		String newFirestation = "{ \"firstName\":\"Emmanuel\", \"lastName\":\"Macron\", \"birthdate\":\"21/12/1977\", \"medications\":[\"pfizer:350mg\"], \"allergies\":[\"covid\"] }";
		MockHttpServletRequestBuilder reqPost = post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(newFirestation);

		mockMvc.perform(reqPost).andExpect(status().isCreated());

		mockMvc.perform(delete("/medicalRecord?id=EmmanuelMacron")).andExpect(status().isOk());

		mockMvc.perform(get("//medicalRecord?id=EmmanuelMacron")).andExpect(status().is4xxClientError());

	}

	@Test
	public void testDeleteByIdWhenTodoIsNotFound() throws Exception {
		mockMvc.perform(delete("/UriFalse?id=JohnBoyd")).andExpect(status().isNotFound())
				.andExpect(status().is4xxClientError());
	}

}