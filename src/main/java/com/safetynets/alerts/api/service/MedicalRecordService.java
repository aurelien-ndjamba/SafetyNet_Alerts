package com.safetynets.alerts.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordModel medicalRecords;
	@Autowired
	private MedicalRecordRepository medicalRecordsRepository;

	// ----------------------------------------------------------------------------------------
	// CREATE "listIdsEntitiesFireStation" FROM MedicalrecordsRepository
	// ----------------------------------------------------------------------------------------
	public List<Long> getlistIdsEntitiesMedicalRecords() {

		List<MedicalRecordModel> listEntitiesMedicalRecords = medicalRecordsRepository.findAll();
		long CountIds = medicalRecordsRepository.count();
		int id = 0;
		List<Long> listIdsEntitiesMedicalRecords = new ArrayList<Long>();
		while (id != CountIds) {
			medicalRecords = listEntitiesMedicalRecords.get(id);
			listIdsEntitiesMedicalRecords.add(medicalRecords.getId());
			++id;
		}
		return listIdsEntitiesMedicalRecords;
	}

	// ----------------------------------------------------------------------------------------
	// GET AllMedicalRecords
	// ----------------------------------------------------------------------------------------
	public List<MedicalRecordModel> getAllMedicalRecords() {
		return medicalRecordsRepository.findAll();
	}

	// ----------------------------------------------------------------------------------------
	// POST OK Vérifier qu'il n'effectue pas de mise à jour
	// ----------------------------------------------------------------------------------------
	public MedicalRecordModel createMedicalRecords(MedicalRecordModel newMedicalRecords) {
		System.out.println("Nouveau dossier medical enregistrée dans la base de donnée avec succès !");
		return medicalRecordsRepository.save(newMedicalRecords);
	}

	// ----------------------------------------------------------------------------------------
	// PUT
	// ----------------------------------------------------------------------------------------

	public boolean updateMedicalRecords(MedicalRecordModel updateMedicalRecords) throws IllegalArgumentException {

		boolean result = false;
		List<Long> listIdsEntitiesMedicalRecords = new ArrayList<Long>();
		listIdsEntitiesMedicalRecords = getlistIdsEntitiesMedicalRecords();

		// Mise à jour un dossier medical existant
		for (long i : listIdsEntitiesMedicalRecords) {
			medicalRecords = medicalRecordsRepository.getById(i);
			if (medicalRecords.getFirstName().equals(updateMedicalRecords.getFirstName())
					&& medicalRecords.getLastName().equals(updateMedicalRecords.getLastName())) {

				medicalRecords.setBirthdate(updateMedicalRecords.getBirthdate());
				medicalRecords.setMedications(updateMedicalRecords.getMedications());
				medicalRecords.setAllergies(updateMedicalRecords.getAllergies());

				medicalRecordsRepository.saveAndFlush(medicalRecords);
				System.out.println("Mise à jour effectuée dans la base de donnée avec succès !");
				result = true;
				break;
			}
		}
		return result;
	}

	// ----------------------------------------------------------------------------------------
	// DELETE
	// ----------------------------------------------------------------------------------------

	public boolean deleteMedicalRecords(String firstname, String lastname) throws IllegalArgumentException {

		boolean result = false;
		List<Long> listIdsEntitiesMedicalRecords = new ArrayList<Long>();
		listIdsEntitiesMedicalRecords = getlistIdsEntitiesMedicalRecords();

		// Suppression d'un dossier medical identifié dans la BDD par le nom et prénom
		for (Long i : listIdsEntitiesMedicalRecords) {
			medicalRecords = medicalRecordsRepository.getById(i);

			if (medicalRecords.getFirstName().equals(firstname) && medicalRecords.getLastName().equals(lastname)) {
				medicalRecordsRepository.delete(medicalRecords);
				listIdsEntitiesMedicalRecords.remove(i);
				result = true;
				System.out.println("l'ID du dossier medical supprimé est le suivant: " + i);
				break;
			}
		}
		return result;
	}
}