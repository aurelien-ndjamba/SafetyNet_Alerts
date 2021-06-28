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
		public void testPostMedicalRecord() throws Exception {
			String newPerson = "{ \"firstName\":\"Emmanuel\", \"lastName\":\"Macron\", \"address\":\"Palais Elysée\", \"city\":\"Paris\", \"zip\":\"75000\", \"phone\":\"999-777-6512\", \"email\":\"macron@email.com\" }";

			MockHttpServletRequestBuilder req = post("/person").contentType(MediaType.APPLICATION_JSON)
					.content(newPerson);
			mockMvc.perform(req).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is("Emmanuel")))
					.andExpect(jsonPath("$.lastName", is("Macron")));
		}

		@Test
		public void testUpdateMedicalRecord() throws Exception {

			String updatePerson = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Paris\", \"zip\":\"75000\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";
			MockHttpServletRequestBuilder reqPut = put("/person").contentType(MediaType.APPLICATION_JSON)
					.content(updatePerson);

			mockMvc.perform(reqPut).andExpect(status().isOk());

			mockMvc.perform(get("/person?id=1")).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.city", is("Paris")))
					.andExpect(jsonPath("$.zip", is(75000)));
		}

		@Test
		public void testDeleteMedicalRecordById() throws Exception {

			String newPerson = "{ \"firstName\":\"Emmanuel\", \"lastName\":\"Macron\", \"address\":\"Palais Elysée\", \"city\":\"Paris\", \"zip\":\"75000\", \"phone\":\"999-777-6512\", \"email\":\"macron@email.com\" }";

			MockHttpServletRequestBuilder req = post("/person").contentType(MediaType.APPLICATION_JSON)
					.content(newPerson);

			mockMvc.perform(req).andExpect(status().isCreated());
			
			mockMvc.perform(delete("/medicalRecord?id=EmmanuelMacron")).andExpect(status().isOk());

			mockMvc.perform(get("//medicalRecord?id=EmmanuelMacron")).andExpect(status().is4xxClientError());

		}

		@Test
		public void testDeleteByIdWhenTodoIsNotFound() throws Exception {
			mockMvc.perform(delete("/UriFalse?id=JohnBoyd")).andExpect(status().isNotFound())
					.andExpect(status().is4xxClientError());
		}
	
}
