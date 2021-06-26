package com.safetynets.alerts.api.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynets.alerts.api.model.ChildInfoModel;
import com.safetynets.alerts.api.model.PersonsByAddress;
import com.safetynets.alerts.api.model.PersonInfoGlobalModel;
import com.safetynets.alerts.api.model.PersonInfoAdvancedModel;
import com.safetynets.alerts.api.service.CommunService;

/** 
 * Classe qui s'occupe du Controller "Commun" de l'API
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@RestController
public class CommunController {

	@Autowired private CommunService communService;

	/** 
	 * GET http://localhost:8080/childAlert?address=<address>
	 * 
	 * liste les enfants vivants à l'addresse en parametre sous la forme:
	 * 
	 * -    String firstName
	 * -	String lastName
	 * -	int age
	 * -	HashSet<PersonModel> familyRelationShip -> Liste des autres membres du foyer (majeur et mineur compris)
	 * 
	 * @param	address(String)
	 * @return	List<ChildInfoModel>
	 * 
	 */
	@GetMapping("/childAlert")
	public List<ChildInfoModel> getChildInfos(@RequestParam String address) throws ParseException {
		return communService.getChildInfos(address);
	}
	
	/** 
	 * GET http://localhost:8080/phoneAlert?firestation=<firestation>
	 * 
	 * Liste les numéros de téléphones des résidents désservis par la caserne de pompier
	 * Les doublons ne sont pas pris en compte.
	 * 
	 * @param	address(String)
	 * @return	personsInfoAdvanced(HashSet<String>)
	 * 
	 */
	@GetMapping("/phoneAlert")
	public HashSet<String> phoneAlert(@RequestParam Long firestation) {
		return communService.getPhoneAlert(firestation);
	}

	/** 
	 * GET http://localhost:8080/fire?address=<address>
	 * 
	 * Liste les informations avancées sur des personnes vivant à l'adresse en paramètre sous la forme:
	 * 
	 * -    String firstName
	 * -	String lastName
	 * -	String address
	 * -	Long stationNumber
	 * -	int age
	 * - 	String phone;
	 * -	HashSet<String> medications  -> Liste uniquement les medicaments de cette personne
	 * - 	HashSet<String> allergies -> Liste uniquement les allergies de cette personne
	 * 
	 * @param	address(String)
	 * @return	personsInfoAdvanced
	 * 
	 */
	@GetMapping("/fire")
	public ArrayList<PersonInfoAdvancedModel> getPersonsInfoAdvanced(
			@RequestParam String address) throws ParseException {
		return communService.getPersonsInfoAdvanced(address);
	}

	/** 
	 * GET http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	 * 
	 * Liste des personnes par adresse à partir des numéros de station en paramètre sous la forme:
	 * 
	 * -	String address
	 * -	List<PersonModel> PersonsByAddress
	 * 
	 * 
	 * @param	address(String)
	 * @return	personsInfoAdvanced
	 * @see PersonService.personsByAddress
	 */
	@GetMapping("/flood/stations")
	public List<PersonsByAddress> getPersonsByStations(@RequestParam List<Long> stations) throws ParseException {
		return communService.getPersonsByStations(stations);
	}

	/** 
	 * GET http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	 * 
	 * Liste les informations globales d'une personne sous la forme:
	 * 
	 * -    String firstName
	 * -	String lastName
	 * -	String address
	 * -	int age
	 * -	String email
	 * -	HashSet<String> medications  -> Liste toutes les medicaments de la famille
	 * - 	HashSet<String> allergies -> Liste toutes les allergies de la famille
	 * 
	 * @param	firstName(String)
	 * @param	lastName(String)
	 * @return	personInfoGlobal(PersonInfoGlobalModel)
	 * 
	 */
	@GetMapping("/personInfo")
	public PersonInfoGlobalModel getPersonInfoGlobal(@RequestParam String firstName, @RequestParam String lastName)
			throws ParseException {
		return communService.getPersonInfoGlobal(firstName, lastName);
	}

	/** 
	 * GET http://localhost:8080/communityEmail?city=<city> 
	 * 
	 * Liste les adresses emails des habitants de la ville en paramètre.
	 * Les doublons sont pris en compte
	 * 
	 * @param	city(String)
	 * @return	List<String>
	 * 
	 */
	@GetMapping("/communityEmail")
	public List<String> getCommunityEmail(@RequestParam String city) {
		return communService.getCommunityEmail(city);
	}

}