package com.safetynets.alerts.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.service.PersonService;
import com.safetynets.alerts.api.service.ReaderJsonService;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReaderJsonService readerJsonService;

	@MockBean
	private PersonService personService;

	@Test
	public void testFindAll() throws Exception {
		// GIVEN
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person = new PersonModel();
		person.setFirstName("Charles");
		person.setLastName("Boyd");
		persons.add(person);

		// WHEN
		when(personService.findAll()).thenReturn(persons);

		// THEN
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(jsonPath("$[0].firstName", is("Charles")));
	}

	@Test
	public void findById() throws Exception {
		// GIVEN
		PersonModel person = new PersonModel();
		person.setFirstName("Charles");
		person.setLastName("Rocco");

		// WHEN
		when(personService.findById((long) 101)).thenReturn(person);

		// THEN
		mockMvc.perform(get("/person?id=101")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Charles"))).andExpect(jsonPath("$.lastName", is("Rocco")));
	}

	@Test
	public void testSave() throws Exception {

		// GIVEN
		PersonModel personInput = new PersonModel();
		personInput.setFirstName("Charles");
		personInput.setLastName("Rocco");
		PersonModel personOutput = new PersonModel();
		personOutput.setFirstName("Kevin");
		personOutput.setLastName("Rocco");

		// WHEN
		when(personService.save(personInput)).thenReturn(personOutput);

		// THEN
		String newPerson = "{ \"firstName\":\"Charles\", \"lastName\":\"Rocco\"}";
		MockHttpServletRequestBuilder req = post("/person").contentType(MediaType.APPLICATION_JSON)
				.content(newPerson);
		mockMvc.perform(req).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is("Kevin")));
	}

	@Test
	public void testUpdate() throws Exception {

		// GIVEN
		PersonModel personInput = new PersonModel();
		personInput.setFirstName("Charles");
		personInput.setLastName("Rocco");
		personInput.setAddress("Paris");
		PersonModel personOutput = new PersonModel();
		personOutput.setFirstName("Kevin");
		personOutput.setLastName("Rocco");
		personOutput.setAddress("Dublin");

		// WHEN
		when(personService.update(personInput)).thenReturn(personOutput);

		// THEN
		String person = "{ \"firstName\":\"Charles\", \"lastName\":\"Rocco\", \"address\":\"Paris\"}";
		MockHttpServletRequestBuilder req = put("/person").contentType(MediaType.APPLICATION_JSON)
				.content(person);
		mockMvc.perform(req).andExpect(status().isOk()).andExpect(jsonPath("$.firstName", is("Kevin")))
		.andExpect(jsonPath("$.address", is("Dublin")));
	}

	@Test
	public void testDelete() throws Exception {

		// GIVEN
		PersonModel person = new PersonModel();
		person.setFirstName("thomas");
		person.setLastName("Jupiter");

		// WHEN
		when(personService.delete("KevinRocco")).thenReturn(person);

		// THEN
		mockMvc.perform(delete("/person?id=KevinRocco")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("thomas"))).andExpect(jsonPath("$.lastName", is("Jupiter")));
	}

}