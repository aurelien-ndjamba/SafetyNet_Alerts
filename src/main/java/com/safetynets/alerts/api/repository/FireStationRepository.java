package com.safetynets.alerts.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.safetynets.alerts.api.model.FireStationModel;

@Repository
public interface FireStationRepository extends JpaRepository<FireStationModel, Long> {

	List<FireStationModel> findByStation(long station);	
	List<FireStationModel> findByAddress(String address);
	
	@Transactional
	void deleteByAddress(String address);
	@Transactional
	void deleteByStation(long station);
	

}