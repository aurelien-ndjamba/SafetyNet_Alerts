package com.safetynets.alerts.api.repository;

import java.util.HashSet;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.safetynets.alerts.api.model.MedicalRecordModel;

/**
 * Interface définissant les contrats utilisables les classes. elle permet
 * d'effectuer des opérations CRUD sur le repository "MedicalRecord" grace à
 * JpaRepository
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecordModel, Long> {
	

	List<MedicalRecordModel> findAll();
	MedicalRecordModel findById(long id);
	List<MedicalRecordModel> findByLastName(String lastName);
	MedicalRecordModel findByFirstNameAndLastName(String firstName, String lastName);
	HashSet<String> findAllergiesByLastName(String lastName);
	
	void deleteById(long id);
	void delete(MedicalRecordModel medicalRecord);
	@Transactional
	void deleteByLastName(String lastName);
	@Transactional
	void deleteByFirstNameAndLastName(String firstName, String lastName);
}