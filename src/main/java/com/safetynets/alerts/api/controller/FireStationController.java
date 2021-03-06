package com.safetynets.alerts.api.controller;

import java.text.ParseException;
import java.util.List;

import org.jboss.logging.Logger;
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
	
	private Logger logger = Logger.getLogger(this.getClass());

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
	public List<FireStationModel> findAll() {
		logger.info("Appel de l'api GET sur '/firestation' sans parametre");
		return fireStationService.findAll();
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
	public List<FireStationModel> findByStation(long station) throws ParseException {
		logger.info("Appel de l'api GET sur '/firestation' avec le parametre station :" + station);
		return fireStationService.findByStation(station);
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
	public FireStationModel save(@RequestBody FireStationModel fireStation) {
		logger.info("Appel de l'api POST sur '/firestation' avec pour parametre Body 'FireStationModel' :" + fireStation);
		return fireStationService.save(fireStation);
	}

	/** 
	 * PUT	http://localhost:8080/firestation
	 * 
	 * Met à jour le numéro de station d'une caserne de pompier. 
	 * L'adresse ne peut pas être modifiée.
	 * 
	 * @return	FireStationModel -> la caserne mis à jour avec son id dans la base de donnée
	 * 
	 */
	@PutMapping("/firestation")
	public FireStationModel update(@RequestBody FireStationModel firestation) {
		logger.info("Appel de l'api PUT sur '/firestation' avec pour parametre Body 'FireStationModel' :" + firestation);
		return fireStationService.update(firestation);
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
	public void deleteByAddress(
			@RequestParam("address") String address) throws ParseException {
		logger.info("Appel de l'api DELETE sur '/firestation' avec pour parametre 'address' :" + address);
		fireStationService.deleteByAddress(address);
	}
	
}