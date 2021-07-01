package com.safetynets.alerts.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynets.alerts.api.model.ChildInfoModel;
import com.safetynets.alerts.api.model.PersonInfoAdvancedModel;
import com.safetynets.alerts.api.model.PersonInfoGlobalModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.model.PersonsByAddress;
import com.safetynets.alerts.api.service.CommunService;
import com.safetynets.alerts.api.service.ReaderJsonService;

@WebMvcTest(controllers = CommunController.class)
public class CommunControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReaderJsonService readerJsonService;

	@MockBean
	private CommunService communService;

	@Test
	public void testGetChildInfos() throws Exception {

		// GIVEN
		PersonModel personModel = new PersonModel();
		personModel.setId((long) 5);
		personModel.setFirstName("Felicia");
		personModel.setLastName("Boyd");
		personModel.setAddress("1509 Culver St");
		personModel.setCity("Culver");
		personModel.setZip((long) 97451);
		personModel.setPhone("841-874-6544");
		personModel.setEmail("jaboyd@email.com");
		HashSet<PersonModel> familyRelationShip = new HashSet<PersonModel>();
		familyRelationShip.add(personModel);

		ChildInfoModel childInfoModel = new ChildInfoModel();
		childInfoModel.setId((long) 3);
		childInfoModel.setFirstName("Tenley");
		childInfoModel.setLastName("Boyd");
		childInfoModel.setAge(9);
		childInfoModel.setFamilyRelationShip(familyRelationShip);
		List<ChildInfoModel> childInfos = new ArrayList<ChildInfoModel>();
		childInfos.add(childInfoModel);

		// WHEN
		when(communService.getChildInfos("1509 Culver St")).thenReturn(childInfos);

		// THEN
		mockMvc.perform(get("/childAlert?address=1509 Culver St")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].age", is(9)));
	}

	@Test
	public void testGetPhoneAlert() throws Exception {

		// GIVEN
		HashSet<String> phoneAlert = new HashSet<String>();
		phoneAlert.add("841-874-7512");
		phoneAlert.add("841-874-7878");
		phoneAlert.add("841-874-6513");

		// WHEN
		when(communService.getPhoneAlert((long) 7)).thenReturn(phoneAlert);

		// THEN
		mockMvc.perform(get("/phoneAlert?firestation=7")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[2]", is("841-874-6513")));
	}

	@Test
	public void testGetPersonsInfoAdvanced() throws Exception {

		// GIVEN
		ArrayList<String> medications = new ArrayList<String>();
		ArrayList<String> allergies = new ArrayList<String>();
		medications.add("pfizer");
		allergies.add("pollen");

		PersonInfoAdvancedModel personInfoAdvanced = new PersonInfoAdvancedModel();
		personInfoAdvanced.setId((long) 77);
		personInfoAdvanced.setFirstName("Emmanuel");
		personInfoAdvanced.setLastName("Macron");
		personInfoAdvanced.setAddress("Elysée");
		personInfoAdvanced.setStationNumber((long) 75);
		personInfoAdvanced.setAge(46);
		personInfoAdvanced.setPhone("7878787878");
		personInfoAdvanced.setMedications(medications);
		personInfoAdvanced.setAllergies(allergies);

		ArrayList<PersonInfoAdvancedModel> personsInfoAdvanced = new ArrayList<PersonInfoAdvancedModel>();
		personsInfoAdvanced.add(personInfoAdvanced);

		// WHEN
		when(communService.getPersonsInfoAdvanced("PARIS")).thenReturn(personsInfoAdvanced);

		// THEN
		mockMvc.perform(get("/fire?address=PARIS")).andExpect(status().isOk()).andExpect(jsonPath("$[0].age", is(46)));
	}

	@Test
	public void testGetPersonsByStations() throws Exception {

		// GIVEN
		PersonModel personModel = new PersonModel();
		personModel.setId((long) 5);
		personModel.setFirstName("Nicolas");
		personModel.setLastName("Sarkozy");
		personModel.setAddress("Elysee");
		personModel.setCity("Paris");
		personModel.setZip((long) 97451);
		personModel.setPhone("841-874-6544");
		personModel.setEmail("jaboyd@email.com");

		List<PersonModel> personsByAddress = new ArrayList<PersonModel>();
		personsByAddress.add(personModel);

		PersonsByAddress personByAddress = new PersonsByAddress();
		personByAddress.setAddress("Elysee");
		personByAddress.setPersonsByAddress(personsByAddress);

		List<PersonsByAddress> personsByStationNumber = new ArrayList<PersonsByAddress>();
		personsByStationNumber.add(personByAddress);

		// WHEN
		List<Long> stations = new ArrayList<Long>();
		stations.add((long) 7);
		stations.add((long) 12);
		stations.add((long) 25);
		when(communService.getPersonsByStations(stations)).thenReturn(personsByStationNumber);

		// THEN
		mockMvc.perform(get("/flood/stations?stations=7,12,25")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].address", is("Elysee")));
	}

	@Test
	public void testGetPersonInfoGlobal() throws Exception {

		// GIVEN
		HashSet<String> medications = new HashSet<String>();
		HashSet<String> allergies = new HashSet<String>();
		medications.add("pfizer");
		allergies.add("pollen");

		PersonInfoGlobalModel personInfoGlobal = new PersonInfoGlobalModel();
		personInfoGlobal.setFirstName("Georges");
		personInfoGlobal.setLastName("Pompidou");
		personInfoGlobal.setAddress("13 Rue Belchamps");
		personInfoGlobal.setEmail("g.pomidou@gmail.com");
		personInfoGlobal.setAge(95);
		personInfoGlobal.setMedications(medications);
		personInfoGlobal.setAllergies(allergies);

		// WHEN
		when(communService.getPersonInfoGlobal("Georges", "Pompidou")).thenReturn(personInfoGlobal);

		// THEN
		mockMvc.perform(get("/personInfo?firstName=Georges&lastName=Pompidou")).andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is("g.pomidou@gmail.com")));
	}

	@Test
	public void testGetCommunityEmail() throws Exception {

		// GIVEN
		List<String> communityEmail = new ArrayList<String>();
		communityEmail.add("emmanuel.macron@gmail.com");
		communityEmail.add("francois.hollande@gmail.com");
		communityEmail.add("nicolas.sarkozy@gmail.com");

		// WHEN
		when(communService.getCommunityEmail("Elysee")).thenReturn(communityEmail);

		// THEN
		mockMvc.perform(get("/communityEmail?city=Elysee")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[1]", is("francois.hollande@gmail.com")));
	}

}