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
import com.safetynets.alerts.api.model.PersonModel;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

	@Autowired
	public MockMvc mockMvc;
	@Autowired
	public PersonModel person;

	@Test
	public void testUpdateAndGetMethod() throws Exception {
		// GIVEN
		person.setFirstName("John");
		person.setLastName("Boyd");
		person.setAddress("13 Rue Belchamps");
		person.setCity("Paris");
		person.setZip(75000);
		person.setPhone("841-874-6512");
		person.setEmail("jaboyd@email.com");
		
		// WHEN
		ObjectMapper objectMapper = new ObjectMapper();
		String newPersonInJson = objectMapper.writeValueAsString(person);
		mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(newPersonInJson))
		.andExpect(status().isOk());
		
		// THEN
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is(1)));
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[0].address", is("13 Rue Belchamps")));
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[0].city", is("Paris")));
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[0].phone", is("841-874-6512")));
	}

	@Test
	public void testPutAndDeleteMethod() throws Exception {
		// GIVEN
		person.setFirstName("Aurelien");
		person.setLastName("Ndjamba");
		person.setAddress("2 Rue villars");
		person.setCity("Metz");
		person.setZip(57000);
		person.setPhone("841-874-6512");
		person.setEmail("jaboyd@email.com");
		
		// WHEN
		ObjectMapper objectMapper = new ObjectMapper();
		String newPersonInJson = objectMapper.writeValueAsString(person);
		
		// THEN
		// Post
		mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(newPersonInJson))
				.andExpect(status().isCreated());
		
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[23].id", is(24)));
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[23].firstName", is("Aurelien")));
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[23].city", is("Metz")));

		// Delete
		mockMvc.perform(delete("/person").queryParam("id", "JacobBoyd"));
		
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[22].id", is(24)));
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[22].lastName", is("Ndjamba")));
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[22].zip", is(57000)));
	}
	
}