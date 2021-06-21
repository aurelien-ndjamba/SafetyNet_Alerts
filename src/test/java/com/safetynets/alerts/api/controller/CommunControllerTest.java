//package com.safetynets.alerts.api.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.safetynets.alerts.api.service.CommunService;
//
//@WebMvcTest(controllers = CommunController.class)
//public class CommunControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//	@MockBean
//	private CommunService communService;
//
//	@Test
//	public void getSpecificInfoPersonsImpactedTest() throws Exception {
//		mockMvc.perform(get("/firestation")).andExpect(status().isOk());
//	}
//
//	@Test
//	public void getChildInfoTest() throws Exception {
//		mockMvc.perform(get("/childAlert")).andExpect(status().isOk());
//	}
//
//	@Test
//	public void phoneAlertTest() throws Exception {
//		mockMvc.perform(get("/phoneAlert")).andExpect(status().isOk());
//	}
//
//	@Test
//	public void getResidentListAndFirestationWhenAddressGivenTest() throws Exception {
//		mockMvc.perform(get("/fire")).andExpect(status().isOk());
//	}
//
//	@Test
//	public void getPersonsByStationTest() throws Exception {
//		mockMvc.perform(get("/flood/stations")).andExpect(status().isOk());
//	}
//
//	@Test
//	public void getPersonInfoTest() throws Exception {
//		mockMvc.perform(get("/personInfo")).andExpect(status().isOk());
//	}
//
//	@Test
//	public void getCommunityEmailTest() throws Exception {
//		mockMvc.perform(get("/communityEmail")).andExpect(status().isOk());
//	}
//
//}
