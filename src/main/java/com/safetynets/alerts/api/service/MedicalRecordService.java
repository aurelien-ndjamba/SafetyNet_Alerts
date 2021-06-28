package com.safetynets.alerts.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;

/** 
 * Classe définissant les méthodes de Service "MedicalRecord"
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	
	private long dataBaseScoredEstimated = 5000;

	/**
	 * Liste tous les enregistrements médicaux présents dans la base de donnée
	 * 
	 * @return List<FireStationModel>
	 * 
	 */
	public List<MedicalRecordModel> getMedicalRecords() {
		return medicalRecordRepository.findAll();
	}

	/** 
	 * Filtre un enregistrement médical dans la base de donnée ayant l'id en paramètre
	 * 
	 * @Param long id
	 * @return	Optional<MedicalRecordModel>
	 * 
	 */
	public Optional<MedicalRecordModel> getMedicalRecordById(long id) {
		return medicalRecordRepository.findById(id);
	}

	/** 
	 * Filtre des enregistrements médicaux dans la base de donnée ayant le même nom en paramètre
	 * 
	 * @Param String lastName
	 * @return	List<MedicalRecordModel>
	 * 
	 */
	public List<MedicalRecordModel> getMedicalRecordsByLastName(String lastName) {
		return medicalRecordRepository.findByLastName(lastName);
	}

	/** 
	 * Filtre un enregistrement médical dans la base de donnée à partir de son prénom et nom en paramètre
	 * 
	 * @Param String firstName
	 * @Param String lastName
	 * @return	MedicalRecordModel
	 * 
	 */
	public MedicalRecordModel getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
		return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	/** 
	 * Ajoute un enregistrement medical dans la base de donnée
	 * 
	 * @Param MedicalRecordModel medicalRecord
	 * @return	MedicalRecord -> le nouvel enregistrement avec son id dans la base de donnée
	 * 
	 */
	public MedicalRecordModel postMedicalRecord(MedicalRecordModel medicalRecord) {

		medicalRecord.setId(null); // Pour éviter que le post face l'update avec un id déjà existant
		return medicalRecordRepository.save(medicalRecord);
	}

	/** 
	 * Met à jour un enregistrement medical dans la base de donnée 
	 * Le prénom et le nom ne peuvent pas être modifiable
	 * 
	 * @param MedicalRecordModel medicalRecord
	 * @return	boolean
	 * 
	 */
	public boolean updateMedicalRecord(MedicalRecordModel medicalRecord) throws IllegalArgumentException {
		boolean result = false;
		long i = 0;
		long j = 0;
		long countEntities = medicalRecordRepository.count();

		do {
			if ((medicalRecordRepository.existsById(i))
					&& medicalRecord.getFirstName().equals(medicalRecordRepository.findById(i).get().getFirstName())
					&& medicalRecord.getLastName().equals(medicalRecordRepository.findById(i).get().getLastName())) {
				j++;
				medicalRecord.setId(i);
				medicalRecordRepository.saveAndFlush(medicalRecord);
				result = true;
				break;
			}
			i++;
			if (i == dataBaseScoredEstimated)
				break;
		} while (j != countEntities);
		return result;

	}

	/**
	 * Supprime un enregistrement medical dans la base de donnée à partir d'un
	 * id représentant son prénom et son nom (exemple : firstnamelastName => EmmanuelMacron)
	 * 
	 * @Param String id
	 * @return boolean
	 * 
	 */
	public boolean deleteMedicalRecordByLastNameFirstname(String id) {

		boolean result = false;
		long i = 0;
		long j = 0;
		long countEntities = medicalRecordRepository.count();

		do {
			MedicalRecordModel medicalRecord = new MedicalRecordModel();
			if (medicalRecordRepository.existsById(i)) {
				j++;
				medicalRecord = medicalRecordRepository.findById(i).get();
				if (id.startsWith(medicalRecord.getFirstName()) && id.endsWith(medicalRecord.getLastName())) {
					medicalRecordRepository.deleteByFirstNameAndLastName(medicalRecord.getFirstName(),
							medicalRecord.getLastName());
					result = true;
					break;
				}
			}
			i++;
		} while (j != countEntities);
		return result;
	}

}