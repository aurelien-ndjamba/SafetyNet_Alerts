package com.safetynets.alerts.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.safetynets.alerts.api.model.PersonImpactedByStationNumber;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.model.PersonDataBaseModel;

@Repository
public interface PersonRepository extends JpaRepository<PersonDataBaseModel, Long> {
	
	List<PersonDataBaseModel> findAll();
	Optional<PersonDataBaseModel> findById(long id);
	List<PersonDataBaseModel> findByAddress(String address);
	List<PersonDataBaseModel> findByLastName(String lastName);
	PersonDataBaseModel findByFirstNameAndLastName(String firstName, String lastName);
	
	void deleteById(long id);
	void delete(PersonDataBaseModel person);
	@Transactional
	void deleteByLastName(String lastName);
	@Transactional
	void deleteByFirstNameAndLastName(String firstName, String lastName);
}