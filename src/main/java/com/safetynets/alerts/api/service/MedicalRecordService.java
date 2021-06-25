package com.safetynets.alerts.api.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.MedicalRecordDataBaseModel;
import com.safetynets.alerts.api.model.PersonDataBaseModel;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	// ###############################################################################################
	// ----------------------------------------------------------------------------------------
	// GET ALL: Methode pour obtenir la liste des personnes dans une BDD
	// ----------------------------------------------------------------------------------------
	public List<MedicalRecordDataBaseModel> getAllMedicalRecord() {
		return medicalRecordRepository.findAll();
	}

	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir ungetPersonsByAddresse personne par id
	// ----------------------------------------------------------------------------------------
	public Optional<MedicalRecordDataBaseModel> getMedicalRecordById(long id) {
		return medicalRecordRepository.findById(id);
	}

	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir des personnes par nom
	// ----------------------------------------------------------------------------------------
	public List<MedicalRecordDataBaseModel> getMedicalRecordsByLastName(String lastName) {
		return medicalRecordRepository.findByLastName(lastName);
	}

	// ----------------------------------------------------------------------------------------
	// GET BY: Methode pour obtenir une personne par prénom et nom
	// ----------------------------------------------------------------------------------------
	public MedicalRecordDataBaseModel getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
		return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	// ----------------------------------------------------------------------------------------
	// POST: Methode pour ajouter une personne dans la BDD
	// ----------------------------------------------------------------------------------------
	public MedicalRecordDataBaseModel postMedicalRecord(MedicalRecordDataBaseModel medicalRecord) {

		System.out.println("Nouvelle personne enregistrée dans la base de donnée avec succès !");

		medicalRecord.setId(null); // Pour éviter que le post face l'update avec un id déjà existant
		return medicalRecordRepository.save(medicalRecord);
	}

	// ----------------------------------------------------------------------------------------
	// PUT: Methode pour mettre à jour les infos d'une personne dans la BDD
	// ----------------------------------------------------------------------------------------
	public boolean updateMedicalRecord(MedicalRecordDataBaseModel medicalRecord) throws IllegalArgumentException {
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
		} while (j != countEntities);
		return result;

	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne par id
	// ----------------------------------------------------------------------------------------
	public void deleteMedicalRecordById(long id) throws IllegalArgumentException {
		medicalRecordRepository.deleteById(id);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne à partir d'une entité
	// ----------------------------------------------------------------------------------------
	public void deleteMedicalRecordByEntity(MedicalRecordDataBaseModel medicalRecord) throws IllegalArgumentException {
		medicalRecordRepository.delete(medicalRecord);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne à partir son nom et prenom dans
	// la BDD
	// ----------------------------------------------------------------------------------------
	public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) throws IllegalArgumentException {
		medicalRecordRepository.deleteByFirstNameAndLastName(firstName, lastName);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne à partir d'un
	// id=firstNamelastName
	// ------------------------------------------------------------------------------------
	public boolean deleteMedicalRecordByLastNameFirstname(String id) {

		boolean result = false;
		long i = 0;
		long j = 0;
		long countEntities = medicalRecordRepository.count();

		do {
			MedicalRecordDataBaseModel medicalRecord = new MedicalRecordDataBaseModel();
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

	public long getMedicalRecordCount() {
		return medicalRecordRepository.count();
	}

	public boolean existsById(long id) {
		return medicalRecordRepository.existsById(id);
	}
	
	// ###############################################################################################



//	// ----------------------------------------------------------------------------------------
//	// GET: Methode pour obtenir la liste de 'medicalRecord' dans une BDD
//	// ----------------------------------------------------------------------------------------
//	public List<MedicalRecordDataBaseModel> getAllMedicalRecord() {
//		return medicalRecordRepository.findAll();
//	}
//
//	// ----------------------------------------------------------------------------------------
//	// GET: Methode pour obtenir un 'medicalRecord' à partir d'un nom
//	// ----------------------------------------------------------------------------------------
//	public HashSet<MedicalRecordDataBaseModel> getMedicalRecordByLastName(String lastName) {
//		return medicalRecordRepository.findByLastName(lastName);
//	}
//
//	// ----------------------------------------------------------------------------------------
//	// GET: Methode pour obtenir un 'medicalRecord' à partir d'un 
//	// ----------------------------------------------------------------------------------------
//	public MedicalRecordDataBaseModel getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
//		return medicalRecordRepository.findByFirstNameAndLastName(lastName);
//	}
//	
//	// ----------------------------------------------------------------------------------------
//	// GET: Méthode pour obtenir l'historique des allergies à partir du nom
//	// ----------------------------------------------------------------------------------------
//	public HashSet<String> getAllergiesHistoryByLastName(String lastName) {
//		return medicalRecordRepository.findAllergiesByLastName(lastName);
//	}
//
//	// ----------------------------------------------------------------------------------------
//	// GET: Méthode pour obtenir l'historique des allergies à partir du prénom et du
//	// nom
//	// ----------------------------------------------------------------------------------------
//	public HashSet<String> getAllergiesHistoryByFisrtNameAndLastName(String firstName, String lastName) {
//		return medicalRecordRepository.findAllergiesByFisrtNameAndLastName(firstName, lastName);
//	}
//
//	// ----------------------------------------------------------------------------------------
//	// GET: Méthode pour obtenir l'historique des traitements à partir du nom
//	// ----------------------------------------------------------------------------------------
//	public HashSet<String> getMedicationsHistoryByLastName(String lastName) {
//		return medicalRecordRepository.findMedicationsByLastName(lastName);
//	}
//
//	// ----------------------------------------------------------------------------------------
//	// GET: Méthode pour obtenir l'historique des traitements à partir du prénom et
//	// du nom
//	// ----------------------------------------------------------------------------------------
//	public HashSet<String> getMedicationsHistoryByFisrtNameAndLastName(String firstName, String lastName) {
//		return medicalRecordRepository.findMedicationsByFisrtNameAndLastName(firstName, lastName);
//	}
//
//	// ----------------------------------------------------------------------------------------
//	// POST: Methode pour ajouter un 'medicalRecord' dans la BDD
//	// ----------------------------------------------------------------------------------------
//	public MedicalRecordDataBaseModel postMedicalRecord(MedicalRecordDataBaseModel medicalRecord) {
//		System.out.println("Nouveau dossier medical enregistrée dans la base de donnée avec succès !");
//		return medicalRecordRepository.save(medicalRecord);
//	}
//
//	// ----------------------------------------------------------------------------------------
//	// PUT: Methode pour mettre à jour les infos d'une 'medicalRecord' dans la BDD
//	// ----------------------------------------------------------------------------------------
//	public MedicalRecordDataBaseModel updateMedicalRecord(MedicalRecordDataBaseModel medicalRecord) throws IllegalArgumentException {
//		return medicalRecordRepository.saveAndFlush(medicalRecord);
//	}
//
//	// ----------------------------------------------------------------------------------------
//	// DELETE: Methode pour Supprimer un 'medicalRecord'dans la BDD
//	// ----------------------------------------------------------------------------------------
//	public void deleteMedicalRecord(MedicalRecordDataBaseModel medicalRecord) throws IllegalArgumentException {
//		medicalRecordRepository.delete(medicalRecord);
//	}
//	
//	// ----------------------------------------------------------------------------------------
//	// DELETE: Methode pour obtenir un 'medicalRecord' à partir d'un id représentant
//	// lastNameFirstName
//	// ----------------------------------------------------------------------------------------
//	public void deleteMedicalRecordById(String id) {
//		// String id = "lastName" + "firstName"
//		medicalRecordRepository.deleteByLastNameFirstName();
////		medicalRecordRepository.deleteByLastNameAndFirstName(lastName, firstName);
//	}
}