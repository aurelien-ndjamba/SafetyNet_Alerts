package com.safetynets.alerts.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.repository.FireStationRepository;

/**
 * Classe définissant les méthodes de Service "FireStation"
 * 
 * @author aurelien.ndjamba
 * @version 1.0
 */
@Service
public class FireStationService {

	@Autowired
	private FireStationRepository fireStationRepository;

	/**
	 * Liste toutes les casernes présentes dans la base de donnée
	 * 
	 * @return List<FireStationModel>
	 * 
	 */
	public List<FireStationModel> getAllFireStation() {
		return fireStationRepository.findAll();
	}

	/**
	 * Filtre les casernes présentes dans la base de donnée à partir du numéro de
	 * station en parametre
	 * 
	 * @param long station
	 * @return List<FireStationModel>
	 * 
	 */
	public List<FireStationModel> getFirestationsByStation(long station) {
		return fireStationRepository.findByStation(station);
	}

	/**
	 * Filtre les casernes présentes dans la base de donnée à partir de l'adresse en
	 * parametre
	 * 
	 * @param String address
	 * @return List<FireStationModel>
	 * 
	 */
	public List<FireStationModel> getFirestationsByAddress(String address) {
		return fireStationRepository.findByAddress(address);
	}

	/**
	 * Donne le numéro de station de la caserne dont l'adresse est le paramètre
	 * 
	 * @param String address
	 * @return long
	 * @see getFirestationsByAddress()
	 */
	public long getStationNumberByAddress(String address) {
		long result = 0;
		for (FireStationModel fireStation : getFirestationsByAddress(address)) {
			if (fireStation.getAddress().equals(address))
				result = fireStation.getStation();
		}
		return result;
	}

	/**
	 * Liste les casernes de pompier dans la base de donnée dont les numéros de
	 * caserne sont en paramètres.
	 * 
	 * @Param List<Long> stations
	 * @return List<FireStationModel> 
	 * @see getFirestationsByStation()
	 */
	public List<FireStationModel> getFirestationsByManyStation(List<Long> stations) {
		List<FireStationModel> firestationsByManyStation = new ArrayList<FireStationModel>();
		for (long station : stations) {
			firestationsByManyStation.addAll(getFirestationsByStation(station));
		}
		return firestationsByManyStation;
	}

	/**
	 * Ajoute une caserne de pompier dans la base de donnée
	 * 
	 * @param FireStationModel fireStation
	 * @return FireStationModel -> la caserne ajoutée avec son id dans la base de
	 *         donnée
	 * 
	 */
	public FireStationModel postFireStation(FireStationModel fireStation) {
		fireStation.setId(null);
		return fireStationRepository.save(fireStation);
	}

	/**
	 * Met à jour une caserne de pompier dans la base de donnée
	 * 
	 * @param FireStationModel fireStation
	 * @return boolean -> true=OK & false=NOK
	 * 
	 */
	public boolean updateFireStation(FireStationModel fireStation) throws IllegalArgumentException {
		boolean result = false;
		long i = 0;
		long j = 0;
		long countEntities = fireStationRepository.count();

		do {
			if ((fireStationRepository.existsById(i))
					&& fireStation.getAddress().equals(fireStationRepository.findById(i).get().getAddress())) {
				j++;
				fireStation.setId(i);
				fireStationRepository.saveAndFlush(fireStation);
				result = true;
				break;
			}
			i++;
			if (i == 5000)
				break;
		} while (j != countEntities);

		return result;
	}

	/**
	 * Supprime une caserne de pompier par l'adresse en parametre
	 * 
	 * @Param String address
	 * @return void
	 * 
	 */
	public void deleteFireStationByAddress(String address) throws IllegalArgumentException {
		fireStationRepository.deleteByAddress(address);
	}

}