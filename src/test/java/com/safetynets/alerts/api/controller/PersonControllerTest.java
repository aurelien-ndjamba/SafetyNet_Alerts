package com.safetynets.alerts.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynets.alerts.api.service.PersonService;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PersonService personsService;

	@Test
	public void getAllPersonsTest() throws Exception {
		mockMvc.perform(get("/person")).andExpect(status().isOk());
	}
	
	@Test
	public void createPersonTest() throws Exception {
		mockMvc.perform(post("/person")).andExpect(status().isOk());
	}

	@Test
	public void updatePersonInfoTest() throws Exception {
		mockMvc.perform(put("/person")).andExpect(status().isOk());
	}

	@Test
	public void deletePersonByIdTest() throws Exception {
		mockMvc.perform(delete("/person")).andExpect(status().isOk());
	}

}
