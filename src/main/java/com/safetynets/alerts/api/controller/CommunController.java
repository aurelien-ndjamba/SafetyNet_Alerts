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
import com.safetynets.alerts.api.model.PersonImpactedByStationNumberModel;
import com.safetynets.alerts.api.model.PersonInfoModel;
import com.safetynets.alerts.api.model.PersonWithMedicalHistoryModel;
import com.safetynets.alerts.api.service.CommunService;

@RestController
public class CommunController {

	@Autowired
	CommunService communService;

	@GetMapping("/")
	@ResponseBody
	public String home() {
		return "<h1> SAFETYNETS ALERTS vous souhaite la bienvenue! </h1>";
	}

	/**
	 * Read - Get all employees
	 * 
	 * @return - This url should return a list of people covered by the
	 *         corresponding fire station. So if the station number = 1, it should
	 *         return the inhabitants covered by station number 1. The list should
	 *         include the following specific information: first name, last name,
	 *         address, phone number. In addition, it must provide a count of the
	 *         number of adults and the number of children (any individual aged 18
	 *         or under) in the service area.
	 *         
	 * @throws ParseException
	 */
	@GetMapping("/firestation")
	public PersonImpactedByStationNumberModel getSpecificInfoPersonsImpacted(long stationNumber) throws ParseException {
		return communService.getSpecificInfoPersonsImpacted(stationNumber);
	}

	/**
	 * Read - This url should return a list of children (anyone aged 18 or under)
	 * living at this address. The list should include each child's first and last
	 * name, age, and a list of other household members. If there is no child, this
	 * url may return an empty string
	 * 
	 * @return - An Iterable object of Employee full filled
	 * @throws ParseException
	 */
	@GetMapping("/childAlert")
	public ArrayList<ChildInfoModel> getChildInfo(@RequestParam String address) throws ParseException {
		return communService.getChildInfo(address);
	}

	/**
	 * Read - This url should return a list of the telephone numbers of the
	 * residents served by the fire station. We will use it to send emergency text
	 * messages to specific households.
	 * 
	 * @return - An Iterable object of Employee full filled
	 */
	@GetMapping("/phoneAlert")
	public HashSet<String> phoneAlert(@RequestParam Long firestation) {
		return communService.phoneAlert(firestation);
	}

	/**
	 * Read - This url must return the list of inhabitants living at the given
	 * address as well as the number of the fire station serving it. The list should
	 * include the name, phone number, age, and medical history (medications,
	 * dosage, and allergies) of each person
	 * 
	 * @return - An Iterable object of Employee full filled
	 * @throws ParseException
	 */
	@GetMapping("/fire")
	public ArrayList<PersonWithMedicalHistoryModel> getResidentListAndFirestationWhenAddressGiven(@RequestParam String address)
			throws ParseException {
		return communService.getResidentListAndFirestationWhenAddressGiven(address);
	}

	/**
	 * Read - This url should return a list of all homes served by the barracks.
	 * This list should group people by address. It should also include the name,
	 * phone number and age of residents, and include their medical history
	 * (medications, dosage, and allergies) next to each name.
	 * 
	 * @return - An Iterable object of Employee full filled
	 */
	@GetMapping("/flood/stations")
	public Iterable<String> getFlood(int station_number) {
		return communService.getResidentListAndStationNumberWhenAddressGiven(station_number);
	}

	/**
	 * Read - This url must return the name, address, age, email address and medical
	 * history (drugs, dosage, allergies) of each inhabitant. If more than one
	 * person has the same name, they should all appear.
	 * 
	 * @return - An Iterable object of Employee full filled
	 * @throws ParseException
	 */
	@GetMapping("/personInfo")
	public PersonInfoModel getPersonInfo(@RequestParam String firstName, @RequestParam String lastName)
			throws ParseException {
		return communService.getPersonInfo(firstName, lastName);
	}

	/**
	 * Read - This url must return the email addresses of all the inhabitants of the city
	 * 
	 * @return - An Iterable object of Employee full filled
	 */
	@GetMapping("/communityEmail")
	public List<String> getCommunityEmail(@RequestParam String city) {
		return communService.getCommunityEmail(city);
	}

}