//package com.safetynets.alerts.api.controller;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CommunControllerTest {
//
//	@Autowired
//	public MockMvc mockMvc;
//
//	@Test
//	public void testGetChildInfo() throws Exception {
//		mockMvc.perform(get("/childAlert?address=1509 Culver St")).andExpect(status().isOk())
//				.andExpect(jsonPath("$[1].age", is(4)));
//	}
//	
//	@Test
//	public void testPhoneAlert() throws Exception {
//		mockMvc.perform(get("/phoneAlert?firestation=2")).andExpect(status().isOk())
//				.andExpect(jsonPath("$[3]", is("841-874-7458")));
//	}
//
//	@Test
//	public void testGetResidentListAndFirestationWhenAddressGiven() throws Exception {
//		mockMvc.perform(get("/fire?address=1509 Culver St")).andExpect(status().isOk())
//				.andExpect(jsonPath("$[3].phone", is("841-874-6512")));
//	}
//	
//	@Test
//	public void testGetPersonsByStation() throws Exception {
//		mockMvc.perform(get("/flood/stations?stations=1,2,3")).andExpect(status().isOk())
//				.andExpect(jsonPath("$[0].address", is("1509 Culver St")));
//	}
//	
//	@Test
//	public void testGetPersonInfo() throws Exception {
//		mockMvc.perform(get("/personInfo?firstName=Jacob&lastName=Boyd")).andExpect(status().isOk())
//				.andExpect(jsonPath("age", is(32)));
//	}
//	
//	@Test
//	public void testGetCommunityEmail() throws Exception {
//		mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk())
//				.andExpect(jsonPath("$[0]", is("jaboyd@email.com")));
//	}
//	
//
//}