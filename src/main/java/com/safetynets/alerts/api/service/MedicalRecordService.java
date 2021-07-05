package com.safetynets.alerts.api.service;

import java.util.List;

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
	
	/**
	 * Setter de medicalRecordRepository
	 * 
	 * @return void
	 * 
	 */
	public void setMedicalRecordRepository(MedicalRecordRepository medicalRecordRepository) {
		this.medicalRecordRepository = medicalRecordRepository;
	}
	
	/**
	 * Liste tous les enregistrements médicaux présents dans la base de donnée
	 * 
	 * @return List<FireStationModel>
	 * 
	 */
	public List<MedicalRecordModel> findAll() {
		return medicalRecordRepository.findAll();
	}

	/** 
	 * Filtre un enregistrement médical dans la base de donnée ayant l'id en paramètre
	 * 
	 * @Param long id
	 * @return	Optional<MedicalRecordModel>
	 * 
	 */
	public MedicalRecordModel findById(long id) {
		return medicalRecordRepository.findById(id);
	}

	/** 
	 * Filtre des enregistrements médicaux dans la base de donnée ayant le même nom en paramètre
	 * 
	 * @Param String lastName
	 * @return	List<MedicalRecordModel>
	 * 
	 */
	public List<MedicalRecordModel> findByLastName(String lastName) {
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
	public MedicalRecordModel findByFirstNameAndLastName(String firstName, String lastName) {
		return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	/** 
	 * Ajoute un enregistrement medical dans la base de donnée
	 * 
	 * @Param MedicalRecordModel medicalRecord
	 * @return	MedicalRecord -> le nouvel enregistrement avec son id dans la base de donnée
	 * 
	 */
	public MedicalRecordModel save(MedicalRecordModel medicalRecord) {

		medicalRecord.setId(null);
		return medicalRecordRepository.save(medicalRecord);
	}

	/** 
	 * Met à jour un enregistrement medical dans la base de donnée 
	 * Le prénom et le nom ne peuvent pas être modifiable
	 * 
	 * @param MedicalRecordModel medicalRecord
	 * @return	MedicalRecordModel
	 * 
	 */
	public MedicalRecordModel update(MedicalRecordModel medicalRecord) {
		MedicalRecordModel MedicalRecordInDb = new MedicalRecordModel();
		MedicalRecordInDb = medicalRecordRepository.findByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
		medicalRecord.setId(MedicalRecordInDb.getId());
		return medicalRecordRepository.save(medicalRecord);
	}

	/**
	 * Supprime un enregistrement medical dans la base de donnée à partir d'un
	 * id représentant son prénom et son nom (exemple : firstnamelastName => EmmanuelMacron)
	 * 
	 * @Param String id
	 * @return MedicalRecordModel
	 * 
	 */
	public MedicalRecordModel delete(String id) { //

		MedicalRecordModel medicalRecordDelete = new MedicalRecordModel();
		long i = 0;
		long j = 0;
		long countEntities = medicalRecordRepository.count();

		do {
			MedicalRecordModel medicalRecord = new MedicalRecordModel();
			if (medicalRecordRepository.existsById(i)) {
				j++;
				medicalRecord = medicalRecordRepository.findById(i);
				if (id.startsWith(medicalRecord.getFirstName()) && id.endsWith(medicalRecord.getLastName())) {
					medicalRecordRepository.deleteByFirstNameAndLastName(medicalRecord.getFirstName(),
							medicalRecord.getLastName());
					medicalRecordDelete = medicalRecord;
					break;
				}
			}
			i++;
		} while (j != countEntities);
		return medicalRecordDelete;
	}

}