package com.safetynets.alerts.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.safetynets.alerts.api.model.PersonModel;

/**
 * Interface définissant les contrats utilisables les classes. elle permet
 * d'effectuer des opérations CRUD sur le repository "Person" grace à
 * JpaRepository
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long> {
	
	List<PersonModel> findAll();
	Optional<PersonModel> findById(long id);
	List<PersonModel> findByAddress(String address);
	List<PersonModel> findByCity(String city);
	List<PersonModel> findByLastName(String lastName);
	PersonModel findByFirstNameAndLastName(String firstName, String lastName);
	
	void deleteById(long id);
	void delete(PersonModel person);
	@Transactional
	void deleteByLastName(String lastName);
	@Transactional
	void deleteByFirstNameAndLastName(String firstName, String lastName);
}