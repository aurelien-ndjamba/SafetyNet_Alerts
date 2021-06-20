package com.safetynets.alerts.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynets.alerts.api.model.FireStationModel;

@Repository
public interface FireStationRepository extends JpaRepository<FireStationModel, Long> {

	List<FireStationModel> findAll();
	long count();
}