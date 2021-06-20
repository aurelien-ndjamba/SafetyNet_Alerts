package com.safetynets.alerts.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynets.alerts.api.model.PersonModel;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long> {
	
	List<PersonModel> findAll();
//	List<Person> findByLastname(String lastname);
	
}