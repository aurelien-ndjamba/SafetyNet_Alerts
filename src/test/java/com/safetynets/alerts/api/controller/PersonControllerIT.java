package com.safetynets.alerts.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetAllMedicalRecord() throws Exception {
		mockMvc.perform(get("/person")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[4].firstName", is("Felicia")));
	}

	@Test
	public void testGetMedicalRecordsById() throws Exception {
		mockMvc.perform(get("/person?id=8")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName", is("Peter")));
	}

	@Test
	public void testGetMedicalRecordsByIdWhenUriIsNotFound() throws Exception {
		mockMvc.perform(get("/UriFalse?id=8")).andExpect(status().is4xxClientError());
	}

	@Test
	public void testGetMedicalRecordsByIdWhenIdIsNotFound() throws Exception {
		mockMvc.perform(get("/person?id=12000")).andExpect(status().isOk()).andExpect(status().isOk())
				.andExpect(flash().attributeCount(0));
	}

	@Test
	public void testPostMedicalRecord() throws Exception {
		String newPerson = "{ \"firstName\":\"Emmanuel\", \"lastName\":\"Macron\", \"address\":\"Palais Elysée\", \"city\":\"Paris\", \"zip\":\"75000\", \"phone\":\"999-777-6512\", \"email\":\"macron@email.com\" }";

		MockHttpServletRequestBuilder req = post("/person").contentType(MediaType.APPLICATION_JSON).content(newPerson);
		mockMvc.perform(req).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is("Emmanuel")))
				.andExpect(jsonPath("$.lastName", is("Macron")));
	}

	@Test
	public void testPostMedicalRecordWhenUriIsNotFound() throws Exception {
		String newPerson = "{ \"firstName\":\"Emmanuel\", \"lastName\":\"Macron\", \"address\":\"Palais Elysée\", \"city\":\"Paris\", \"zip\":\"75000\", \"phone\":\"999-777-6512\", \"email\":\"macron@email.com\" }";

		MockHttpServletRequestBuilder req = post("/UriFalse").contentType(MediaType.APPLICATION_JSON)
				.content(newPerson);
		mockMvc.perform(req).andExpect(status().is4xxClientError());
	}

	@Test
	public void testPostMedicalRecordWhenRequestBodyNotExist() throws Exception {
		String newPerson = "{}";
		MockHttpServletRequestBuilder req = post("/person").contentType(MediaType.APPLICATION_JSON).content(newPerson);
		mockMvc.perform(req).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.lastName", is(nullValue())));
	}

	@Test
	public void testUpdateMedicalRecordWhenUriIsNotFound() throws Exception {
		mockMvc.perform(put("/UriFalse?id=JohnBoyd")).andExpect(status().is4xxClientError());
	}

	@Test
	public void testUpdateMedicalRecordWhenRequestBodyNotExist() throws Exception {
		mockMvc.perform(put("/person")).andExpect(status().is4xxClientError());
	}

	@Disabled
	@Test // TODO
	public void testUpdateMedicalRecordWhenRequestBodyIsFalse() throws Exception {
		String updatePerson = "{\"prenom\":\"Jacob\"}";
		MockHttpServletRequestBuilder reqPut = put("/person").contentType(MediaType.APPLICATION_JSON)
				.content(updatePerson);
		mockMvc.perform(reqPut).andExpect(status().is5xxServerError());
	}

	@Test
	public void testUpdateMedicalRecord() throws Exception {

		String updatePerson = "{ \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Paris\", \"zip\":\"75000\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";
		MockHttpServletRequestBuilder reqPut = put("/person").contentType(MediaType.APPLICATION_JSON)
				.content(updatePerson);

		mockMvc.perform(reqPut).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.city", is("Paris"))).andExpect(jsonPath("$.zip", is(75000)));
	}

	@Test
	public void testDeleteMedicalRecordById() throws Exception {
		mockMvc.perform(delete("/person?id=JohnBoyd")).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstName", is("John")));
	}

	@Test
	public void testDeleteByIdWhenUriIsNotFound() throws Exception {
		mockMvc.perform(delete("/UriFalse?id=JohnBoyd")).andExpect(status().is4xxClientError());
	}

	@Disabled
	@Test
	public void testDeleteByIdWhenIdIsNotFound() throws Exception {
		mockMvc.perform(delete("/person?id=PersonNotInDataBase"))
//		.andExpect(status().is5xxServerError());
				.andExpect(jsonPath("$.id", is(nullValue()))).andExpect(jsonPath("$.lastName", is(nullValue())));
	}

}
