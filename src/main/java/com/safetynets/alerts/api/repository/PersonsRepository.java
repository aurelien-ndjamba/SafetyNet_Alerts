package com.safetynets.alerts.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.safetynets.alerts.api.model.PersonsModel;

@Repository
public interface PersonsRepository extends JpaRepository<PersonsModel, Long> {
	
	@Query(value = "SELECT email AS communityEmail FROM PERSONS WHERE city=?", nativeQuery = true)
	List<String> findcommunityEmail(String city);
	
}