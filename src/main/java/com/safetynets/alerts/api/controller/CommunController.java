package com.safetynets.alerts.api.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynets.alerts.api.model.ChildInfoModel;
import com.safetynets.alerts.api.model.PersonsByStationNumberModel;
import com.safetynets.alerts.api.model.MedicalRecordDataBaseModel;
import com.safetynets.alerts.api.model.PersonDataBaseModel;
import com.safetynets.alerts.api.model.PersonInfoModel;
import com.safetynets.alerts.api.model.PersonInfoAdvanced;
import com.safetynets.alerts.api.service.CommunService;
import com.safetynets.alerts.api.service.CountService;
import com.safetynets.alerts.api.service.MedicalRecordService;

@RestController
public class CommunController {

	@Autowired CommunService communService;
	@Autowired CountService countService;
	@Autowired MedicalRecordService medicalRecordService;

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/childAlert?address=1509 Culver St
	// ----------------------------------------------------------------------------------------
	@GetMapping("/childAlert")
	public List<ChildInfoModel> getChildInfo(@RequestParam String address) throws ParseException {
		return communService.getChildInfos(address);
	}
	
//	à supprimer pour test valider person
//	@RequestMapping(value = "/toto", method = RequestMethod.GET, params = { "firstName", "lastName" })
//	public MedicalRecordDataBaseModel getAge(@RequestParam String firstName, @RequestParam String lastName) throws ParseException {
//		return medicalRecordService.getMedicalRecordByFirstNameAndLastName(firstName, lastName); 
//	}

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/phoneAlert?firestation=2
	// ----------------------------------------------------------------------------------------
	@GetMapping("/phoneAlert")
	public HashSet<String> phoneAlert(@RequestParam Long firestation) {
		return communService.getPhoneAlert(firestation);
	}

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/fire?address=951 LoneTree Rd
	// ----------------------------------------------------------------------------------------
	@GetMapping("/fire")
	public ArrayList<PersonInfoAdvanced> getPersonsInfoAdvanced(
			@RequestParam String address) throws ParseException {
		return communService.getPersonsInfoAdvanced(address);
	}

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/flood/stations?stations=1,2,3
	// ----------------------------------------------------------------------------------------
//	@GetMapping("/flood/stations")
//	public List<PersonsByStationNumberModel> getPersonsByStation(@RequestParam List<Long> stations) throws ParseException {
//		return communService.getPersonsByManyStations(stations);
//	}

//	// ----------------------------------------------------------------------------------------
//	// GET http://localhost:8080/personInfo?firstName=Jacob&lastName=Boyd
//	// ----------------------------------------------------------------------------------------
//	@GetMapping("/personInfo")
//	public PersonInfoModel getPersonInfo(@RequestParam String firstName, @RequestParam String lastName)
//			throws ParseException {
//		return communService.getPersonInfo(firstName, lastName);
//	}
//
//	// ----------------------------------------------------------------------------------------
//	// GET http://localhost:8080/communityEmail?city=Culver
//	// ----------------------------------------------------------------------------------------
//	@GetMapping("/communityEmail")
//	public List<String> getCommunityEmail(@RequestParam String city) {
//		return communService.getCommunityEmail(city);
//	}

}