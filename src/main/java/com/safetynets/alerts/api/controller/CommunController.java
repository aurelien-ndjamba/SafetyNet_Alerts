package com.safetynets.alerts.api.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynets.alerts.api.model.ChildInfoModel;
import com.safetynets.alerts.api.model.PersonsByAddressModel;
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
	
	private Logger logger = Logger.getLogger(this.getClass());

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
		logger.info("Appel de l'api GET sur '/childAlert' avec pour parametre 'address' :" + address);
		return communService.findChildInfos(address);
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
	public HashSet<String> getPhoneAlert(@RequestParam Long firestation) {
		logger.info("Appel de l'api GET sur '/phoneAlert' avec pour parametre 'firestation' :" + firestation);
		return communService.findPhoneAlert(firestation);
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
		logger.info("Appel de l'api GET sur '/fire' avec pour parametre 'address' :" + address);
		return communService.findPersonsInfoAdvanced(address);
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
	public List<PersonsByAddressModel> getPersonsByStations(@RequestParam List<Long> stations) throws ParseException {
		logger.info("Appel de l'api GET sur '/flood/stations' avec pour parametre 'stations' :" + stations);
		return communService.findPersonsByStations(stations);
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
		logger.info("Appel de l'api GET sur '/personInfo' avec pour parametres 'firstName' : " + firstName + " et lastName :" + lastName);
		return communService.findPersonInfoGlobal(firstName, lastName);
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
		logger.info("Appel de l'api GET sur '/communityEmail' avec pour parametre 'city' :" + city);
		return communService.findCommunityEmail(city);
	}

}