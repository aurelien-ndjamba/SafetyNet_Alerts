package com.safetynets.alerts.api.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynets.alerts.api.model.ChildInfoModel;
import com.safetynets.alerts.api.model.HomeByFireStationModel;
import com.safetynets.alerts.api.model.PersonInfoModel;
import com.safetynets.alerts.api.model.PersonWithMedicalHistoryModel;
import com.safetynets.alerts.api.service.CommunService;

@RestController
public class CommunController {

	@Autowired
	CommunService communService;

	// ----------------------------------------------------------------------------------------
	// WELCOME http://localhost:8080/
	// ----------------------------------------------------------------------------------------
	@GetMapping("/")
	@ResponseBody
	public String home() {
		return "<h1> SAFETYNETS ALERTS vous souhaite la bienvenue! </h1>";
	}

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/childAlert?address=1509 Culver St
	// ----------------------------------------------------------------------------------------
	@GetMapping("/childAlert")
	public ArrayList<ChildInfoModel> getChildInfo(@RequestParam String address) throws ParseException {
		return communService.getChildInfo(address);
	}

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/phoneAlert?firestation=2
	// ----------------------------------------------------------------------------------------
	@GetMapping("/phoneAlert")
	public HashSet<String> phoneAlert(@RequestParam Long firestation) {
		return communService.phoneAlert(firestation);
	}

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/fire?address=951 LoneTree Rd
	// ----------------------------------------------------------------------------------------
	@GetMapping("/fire")
	public ArrayList<PersonWithMedicalHistoryModel> getResidentListAndFirestationWhenAddressGiven(
			@RequestParam String address) throws ParseException {
		return communService.getResidentListAndFirestationWhenAddressGiven(address);
	}

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/flood/stations?stations=1,2,3
	// ----------------------------------------------------------------------------------------
	@GetMapping("/flood/stations")
	public List<HomeByFireStationModel> getPersonsByStation(@RequestParam List<Long> stations) throws ParseException {
		return communService.getPersonsByStation(stations);
	}

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/personInfo?firstName=Jacob&lastName=Boyd
	// ----------------------------------------------------------------------------------------
	@GetMapping("/personInfo")
	public PersonInfoModel getPersonInfo(@RequestParam String firstName, @RequestParam String lastName)
			throws ParseException {
		return communService.getPersonInfo(firstName, lastName);
	}

	// ----------------------------------------------------------------------------------------
	// GET http://localhost:8080/communityEmail?city=Culver
	// ----------------------------------------------------------------------------------------
	@GetMapping("/communityEmail")
	public List<String> getCommunityEmail(@RequestParam String city) {
		return communService.getCommunityEmail(city);
	}

}