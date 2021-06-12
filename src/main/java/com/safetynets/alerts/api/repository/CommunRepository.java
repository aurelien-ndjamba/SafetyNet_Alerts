package com.safetynets.alerts.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetynets.alerts.api.model.PersonsModel;

public interface CommunRepository extends JpaRepository<PersonsModel, Long>{

}
