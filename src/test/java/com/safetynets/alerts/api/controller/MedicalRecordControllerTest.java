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

import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.service.MedicalRecordService;
import com.safetynets.alerts.api.service.ReaderJsonService;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ReaderJsonService readerJsonService;
	
	@MockBean
	private MedicalRecordService medicalRecordService;

	@Test
	public void testFindAll() throws Exception {
		// GIVEN
		List<MedicalRecordModel> medicalRecords = new ArrayList<MedicalRecordModel>();
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Boyd");
		medicalRecord.setBirthdate("11/11/1111");
		medicalRecords.add(medicalRecord);

		// WHEN
		when(medicalRecordService.findAll()).thenReturn(medicalRecords);

		// THEN
		mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].birthdate", is("11/11/1111")));
	}
	
	@Test
	public void findById() throws Exception {
		// GIVEN
		MedicalRecordModel medicalRecord = new MedicalRecordModel();
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Boyd");

		// WHEN
		when(medicalRecordService.findById((long)101)).thenReturn(medicalRecord);

		// THEN
		mockMvc.perform(get("/medicalRecord?id=101")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John")))
				.andExpect(jsonPath("$.lastName", is("Boyd")));
	}

	@Test
	public void testSave() throws Exception {

		// GIVEN
		MedicalRecordModel medicalRecordInput = new MedicalRecordModel();
		medicalRecordInput.setFirstName("Kevin");
		medicalRecordInput.setLastName("Mars");
		MedicalRecordModel medicalRecordOutput = new MedicalRecordModel();
		medicalRecordOutput.setFirstName("Jean");
		medicalRecordOutput.setLastName("Durant");

		// WHEN
		when(medicalRecordService.save(medicalRecordInput)).thenReturn(medicalRecordOutput);

		// THEN
		String newMedicalRecord = "{ \"firstName\":\"Kevin\", \"lastName\":\"Mars\"}";
		MockHttpServletRequestBuilder req = post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(newMedicalRecord);
		mockMvc.perform(req).andExpect(status().isCreated()).andExpect(jsonPath("$.lastName", is("Durant")));
	}
	
	@Test
	public void testUpdate() throws Exception {
		
		// GIVEN
		MedicalRecordModel medicalRecordInput = new MedicalRecordModel();
		medicalRecordInput.setFirstName("Kevin");
		medicalRecordInput.setLastName("Mars");
		MedicalRecordModel medicalRecordOutput = new MedicalRecordModel();
		medicalRecordOutput.setFirstName("Jean");
		medicalRecordOutput.setLastName("Durant");
		
		// WHEN
		when(medicalRecordService.update(medicalRecordInput)).thenReturn(medicalRecordOutput);
		
		// THEN
		String newMedicalRecord = "{ \"firstName\":\"Kevin\", \"lastName\":\"Mars\"}";
		MockHttpServletRequestBuilder req = put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(newMedicalRecord);
		mockMvc.perform(req).andExpect(status().isOk()).andExpect(jsonPath("$.lastName", is("Durant")));
	}

	@Test
	public void testDelete() throws Exception {
		
		// GIVEN
		MedicalRecordModel medicalRecordOutput = new MedicalRecordModel();
		medicalRecordOutput.setFirstName("Jean");
		medicalRecordOutput.setLastName("Durant");
		
		// WHEN
		when(medicalRecordService.deleteMedicalRecordByLastNameFirstname("KevinMars")).thenReturn(medicalRecordOutput);
		
		// THEN
		mockMvc.perform(delete("/medicalRecord?id=KevinMars")).andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName", is("Jean")))
		.andExpect(jsonPath("$.lastName", is("Durant")));
	}
}