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

/** 
 * Classe qui s'occupe du Controller "FireStation" de l'API
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(get("/firestation")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[2].station", is(3)));
	}

	@Test
	public void testFindByStation() throws Exception {
		mockMvc.perform(get("/firestation?station=2")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].address", is("29 15th St")));
	}

	@Test
	public void testSave() throws Exception {
		String newFirestation = "{ \"address\":\"PARIS\", \"station\":\"75\"}";
		MockHttpServletRequestBuilder req = post("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(newFirestation);
		mockMvc.perform(req).andExpect(status().isCreated()).andExpect(jsonPath("$.address", is("PARIS")))
				.andExpect(jsonPath("$.station", is(75)));
	}

	@Test
	public void testUpdate() throws Exception {
		String updateFirestation = "{ \"address\":\"1509 Culver St\", \"station\":\"99\"}";
		MockHttpServletRequestBuilder req = put("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(updateFirestation);
		mockMvc.perform(req).andExpect(status().isOk());
		mockMvc.perform(get("/firestation")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].station", is(99)));
	}

	@Test
	public void testDeleteByAddress() throws Exception {
		
		String newFirestation = "{ \"address\":\"PARIS\", \"station\":\"75\"}";
		MockHttpServletRequestBuilder req = put("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(newFirestation);
		mockMvc.perform(req).andExpect(status().isOk());
		
		mockMvc.perform(delete("/firestation?address=PARIS")).andExpect(status().isOk());
		
		mockMvc.perform(get("/firestation?station=75")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))	
		.andExpect(content().string("[]"));
		
	}

	@Test
	public void testDeleteByAddressWhenTodoIsNotFound() throws Exception {
		mockMvc.perform(delete("/UriFalse?address=London")).andExpect(status().isNotFound()).andExpect(status().is4xxClientError());
	}

}