package com.safetynets.alerts.api.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

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

import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.service.MedicalRecordService;

/** 
 * Classe qui s'occupe du Controller "FireStation" l'API
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@RestController
public class MedicalRecordController {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private MedicalRecordService medicalRecordService;

	/** 
	 * GET http://localhost:8080/medicalRecord
	 * 
	 * liste tous les enregistrements medicaux de la base de donnée
	 * 
	 * @return	List<MedicalRecord>
	 * 
	 */
	@GetMapping("/medicalRecord")
	public List<MedicalRecordModel> getMedicalRecords() {
		logger.info("Appel de l'api GET sur '/medicalRecord' sans parametre");
		return medicalRecordService.getMedicalRecords();
	}
	
	/** 
	 * GET	http://localhost:8080/firestation?id=<id>
	 * 
	 * filtre l'enregistrement medical d'une personne par son id dans la base de donnée
	 * 
	 * @return	MedicalRecord
	 * 
	 */
	@RequestMapping(value = "/medicalRecord", method = RequestMethod.GET, params = { "id" })
	public Optional<MedicalRecordModel> getMedicalRecordById(long id) throws ParseException {
		logger.info("Appel de l'api GET sur '/medicalRecord' avec le parametre id :" + id);
		return medicalRecordService.getMedicalRecordById(id);
	}

	/** 
	 * POST http://localhost:8080/medicalRecord
	 * 
	 * Ajoute un enregistrement medical dans la base de donnée
	 * 
	 * @return	MedicalRecord -> le nouvel enregistrement avec son id dans la base de donnée
	 * 
	 */
	@PostMapping("/medicalRecord")
	@ResponseStatus(HttpStatus.CREATED)
	public MedicalRecordModel postMedicalRecord(@RequestBody MedicalRecordModel medicalRecord) {
		logger.info("Appel de l'api POST sur '/medicalRecord' avec pour parametre Body 'MedicalRecordModel' :" + medicalRecord);
		return medicalRecordService.postMedicalRecord(medicalRecord);
	}

	/** 
	 * PUT http://localhost:8080/medicalRecord
	 * 
	 * Met à jour un enregistrement medical dans la base de donnée 
	 * Le prénom et le nom ne peuvent pas être modifiable
	 * 
	 * @return	boolean
	 * 
	 */
	@PutMapping("/medicalRecord")
	public boolean updateMedicalRecord(@RequestBody MedicalRecordModel medicalRecord) {
		logger.info("Appel de l'api PUT sur '/medicalRecord' avec pour parametre Body 'MedicalRecordModel' :" + medicalRecord);
		return medicalRecordService.updateMedicalRecord(medicalRecord);
	}
	
	/**
	 * DELETE http://localhost:8080/medicalRecord?firstNamelastName=<firstNamelastName>
	 * 
	 * Supprime un enregistrement medical dans la base de donnée à partir d'un
	 * id représentant son prénom et son nom (exemple : firstnamelastName => EmmanuelMacron)
	 * 
	 * @return boolean
	 * 
	 */
	@RequestMapping(value = "/medicalRecord", method = RequestMethod.DELETE, params = { "id" })
	public boolean deleteMedicalRecordByLastNameFirstname(@RequestParam String id) throws IllegalArgumentException {
		logger.info("Appel de l'api DELETE sur '/medicalRecord' avec pour parametre 'id' :" + id);
		return medicalRecordService.deleteMedicalRecordByLastNameFirstname(id);
	}
	
}