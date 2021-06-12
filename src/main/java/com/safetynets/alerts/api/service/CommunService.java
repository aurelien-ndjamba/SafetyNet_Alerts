package com.safetynets.alerts.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.PersonsModel;
import com.safetynets.alerts.api.repository.FirestationsRepository;
import com.safetynets.alerts.api.repository.MedicalrecordsRepository;
import com.safetynets.alerts.api.repository.PersonsRepository;

import lombok.Data;

@Data
@Service
public class CommunService {

	@Autowired
	private PersonsRepository personsRepository;
	@Autowired
	private FirestationsRepository firestationsRepository;
	@Autowired
	private MedicalrecordsRepository medicalrecordsRepository;
	@Autowired
	private PersonsModel personsModel;

	public Iterable<String> getFireStationWhenStationNumberGiven(int station_number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getChildrenListWithAddressGiven(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getPhoneListWhenStationNumberGiven(int station_number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getPeopleListAndFirestationWhenAddressGiven(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getResidentListAndStationNumberWhenAddressGiven(int station_number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getPersonInfoWhenNameGiven(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getCommunityEmailWhenCityGiven(String city) {
		return personsRepository.findcommunityEmail(city);
				//personsRepository.findByCity(city);
		//findBy
	}
	
}
