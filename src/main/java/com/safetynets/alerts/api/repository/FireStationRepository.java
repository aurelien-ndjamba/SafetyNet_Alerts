package com.safetynets.alerts.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.safetynets.alerts.api.model.FireStationModel;

/**
 * Interface définissant les contrats utilisables les classes. elle permet
 * d'effectuer des opérations CRUD sur le repository "FireStation" grace à
 * JpaRepository
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@Repository
public interface FireStationRepository extends JpaRepository<FireStationModel, Long> {

	List<FireStationModel> findByStation(long station);

	List<FireStationModel> findByAddress(String address);

	@Transactional
	void deleteByAddress(String address);

	@Transactional
	void deleteByStation(long station);

}