package com.safetynets.alerts.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynets.alerts.api.model.PersonModel;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long> {
	
}