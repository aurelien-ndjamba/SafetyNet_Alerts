package com.safetynets.alerts.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynets.alerts.api.service.CommunService;


@RestController
public class CommunController {

	@Autowired
	CommunService communService;

	/**
	 * Read - Get all employees
	 * 
	 * @return - Cette url doit retourner une liste des personnes couvertes par la
	 *         caserne de pompiers correspondante. Donc, si le numéro de station =
	 *         1, elle doit renvoyer les habitants couverts par la station numéro 1.
	 *         La liste doit inclure les informations spécifiques suivantes :
	 *         prénom, nom, adresse, numéro de téléphone. De plus, elle doit fournir
	 *         un décompte du nombre d'adultes et du nombre d'enfants (tout individu
	 *         âgé de 18 ans ou moins) dans la zone desservie
	 */
	@GetMapping("/firestation?stationNumber=<station_number>")
	public Iterable<String> getFirestation(int station_number) {
		return communService.getFireStationWhenStationNumberGiven(station_number);
	}

	/**
	 * Read - Cette url doit retourner une liste d'enfants (tout individu âgé de 18
	 * ans ou moins) habitant à cette adresse. La liste doit comprendre le prénom et
	 * le nom de famille de chaque enfant, son âge et une liste des autres membres
	 * du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide
	 * 
	 * @return - An Iterable object of Employee full filled
	 */
	@GetMapping("/childAlert?address=<address>")
	public Iterable<String> getChildAlert(String address) {
		return communService.getChildrenListWithAddressGiven(address);
	}

	/**
	 * Read - Cette url doit retourner une liste des numéros de téléphone des
	 * résidents desservis par la caserne de pompiers. Nous l'utiliserons pour
	 * envoyer des messages texte d'urgence à des foyers spécifiques.
	 * 
	 * @return - An Iterable object of Employee full filled
	 */
	@GetMapping("/phoneAlert?firestation=<firestation_number>")
	public Iterable<String> getPhoneAlert(int station_number) {
		return communService.getPhoneListWhenStationNumberGiven(station_number);
	}

	/**
	 * Read - Cette url doit retourner la liste des habitants vivant à l’adresse
	 * donnée ainsi que le numéro de la caserne de pompiers la desservant. La liste
	 * doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
	 * médicaux (médicaments, posologie et allergies) de chaque personne
	 * 
	 * @return - An Iterable object of Employee full filled
	 */
	@GetMapping("/fire?address=<address>")
	public Iterable<String> getFire(String address) {
		return communService.getPeopleListAndFirestationWhenAddressGiven(address);
	}

	/**
	 * Read - Cette url doit retourner une liste de tous les foyers desservis par la
	 * caserne. Cette liste doit regrouper les personnes par adresse. Elle doit
	 * aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et faire
	 * figurer leurs antécédents médicaux (médicaments, posologie et allergies) à
	 * côté de chaque nom.
	 * 
	 * @return - An Iterable object of Employee full filled
	 */
	@GetMapping("/flood/stations?stations=<a list of station_numbers>")
	public Iterable<String> getFlood(int station_number) {
		return communService.getResidentListAndStationNumberWhenAddressGiven(station_number);
	}

	/**
	 * Read - Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et
	 * les antécédents médicaux (médicaments, posologie, allergies) de chaque
	 * habitant. Si plusieurs personnes portent le même nom, elles doivent toutes
	 * apparaître.
	 * 
	 * @return - An Iterable object of Employee full filled
	 */
	@GetMapping("/personInfo?firstName=<firstName>&lastName=<lastName>")
	public Iterable<String> getPersonInfo(String firstName, String lastName) {
		return communService.getPersonInfoWhenNameGiven(firstName, lastName);
	}

	/**
	 * Read - Cette url doit retourner les adresses mail de tous les habitants de la
	 * ville
	 * 
	 * @return - An Iterable object of Employee full filled
	 */
	@GetMapping("/communityEmail")
	public List<String> getCommunityEmail(@RequestParam String city) {
		return communService.getCommunityEmail(city);
	}

	@GetMapping("/")
	@ResponseBody
	public String home() {
		return "<h1> SAFETYNETS ALERTS vous souhaite la bienvenue! </h1>";
	}

}