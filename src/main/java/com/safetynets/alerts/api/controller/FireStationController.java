package com.safetynets.alerts.api.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.service.FireStationService;

/** 
 * Classe qui s'occupe du Controller "FireStation" l'API
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@RestController
public class FireStationController {

	@Autowired private FireStationService fireStationService;

	/** 
	 * GET	http://localhost:8080/firestation
	 * 
	 * liste toutes les casernes de pompiers
	 * 
	 * @return	List<FireStationModel>
	 * 
	 */
	@GetMapping("/firestation")
	public List<FireStationModel> getAllFireStation() {
		return fireStationService.getAllFireStation();
	}
	
	/** 
	 * GET	http://localhost:8080/firestation?stationNumber=<stationNumber>
	 * 
	 * liste les casernes de pompiers ayant pour numéro de station le paramètre de la methode
	 * 
	 * @return	List<FireStationModel>
	 * 
	 */
	@RequestMapping(value = "/firestation", method = RequestMethod.GET, params = { "station" })
	public List<FireStationModel> getFirestationsByStation(long station) throws ParseException {
		return fireStationService.getFirestationsByStation(station);
	}

	/** 
	 * POST	http://localhost:8080/firestation
	 * 
	 * Ajoute une caserne de pompier dans la base de donnée
	 * 
	 * @return	FireStationModel -> la caserne ajoutée avec son id dans la base de donnée
	 * 
	 */
	@PostMapping("/firestation")
	@ResponseStatus(HttpStatus.CREATED)
	public FireStationModel postFireStation(@RequestBody FireStationModel fireStation) {
		return fireStationService.postFireStation(fireStation);
	}

	/** 
	 * PUT	http://localhost:8080/firestation
	 * 
	 * Met à jour le numéro de station d'une caserne de pompier. 
	 * L'adresse ne peut pas être modifiée.
	 * 
	 * @return	boolean
	 * 
	 */
	@PutMapping("/firestation")
	public boolean updateFireStation(@RequestBody FireStationModel firestation) {
		return fireStationService.updateFireStation(firestation);
	}

	/** 
	 * DELETE	http://localhost:8080/firestation?address=951 LoneTree Rd
	 * 
	 * Supprime une caserne de pompier dans la base de donnée à partir de l'adresse en parametre
	 * 
	 * @return	void
	 * 
	 */
	@RequestMapping(value = "/firestation", method = RequestMethod.DELETE, params = { "address" })
	public void deleteFireStationByAddress(
			@RequestParam("address") String address) throws ParseException {
		fireStationService.deleteFireStationByAddress(address);
	}
	
}