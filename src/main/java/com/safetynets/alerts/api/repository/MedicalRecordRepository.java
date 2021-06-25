package com.safetynets.alerts.api.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.safetynets.alerts.api.model.MedicalRecordDataBaseModel;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecordDataBaseModel, Long> {
	

	List<MedicalRecordDataBaseModel> findAll();
	Optional<MedicalRecordDataBaseModel> findById(long id);
	List<MedicalRecordDataBaseModel> findByLastName(String lastName);
	MedicalRecordDataBaseModel findByFirstNameAndLastName(String firstName, String lastName);
	
	void deleteById(long id);
	void delete(MedicalRecordDataBaseModel medicalRecord);
	@Transactional
	void deleteByLastName(String lastName);
	@Transactional
	void deleteByFirstNameAndLastName(String firstName, String lastName);
	HashSet<String> findAllergiesByLastName(String lastName);
}