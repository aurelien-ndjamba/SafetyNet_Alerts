package com.safetynets.alerts.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.safetynets.alerts.api.model.FireStationDataBaseModel;

@Repository
public interface FireStationRepository extends JpaRepository<FireStationDataBaseModel, Long> {

	List<FireStationDataBaseModel> findByStation(long station);	
	List<FireStationDataBaseModel> findByAddress(String address);
//	Iterable<FireStationDataBaseModel> findAllByStation(List<Long> stations);
	
	@Transactional
	void deleteByAddress(String address);
	@Transactional
	void deleteByStation(long station);
	

}