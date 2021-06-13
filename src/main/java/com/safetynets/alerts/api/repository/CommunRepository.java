package com.safetynets.alerts.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynets.alerts.api.model.PersonsModel;

@Repository
public interface CommunRepository extends JpaRepository<PersonsModel, Long>{

}
