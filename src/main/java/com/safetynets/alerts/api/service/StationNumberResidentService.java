package com.safetynets.alerts.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.repository.FireStationRepository;


@Service
public class StationNumberResidentService {
	
	@Autowired
	private FireStationModel fireStation;
	@Autowired
	private FireStationsService fireStationsService;
	@Autowired
	private FireStationRepository firestationsRepository;

	public Long getStationNumber(String address) {
		
		List<Long> listIdsEntitiesFireStations = fireStationsService.getlistIdsEntitiesFireStation();
		Long stationNumberFromAdresse = null;
		
		for (Long i : listIdsEntitiesFireStations) {
			
			fireStation = firestationsRepository.getById(i);
			
			if (fireStation.getAddress().equals(address)) {
				stationNumberFromAdresse = fireStation.getStation();	
				break;
			}
		}
		
		return stationNumberFromAdresse;
	}

}
