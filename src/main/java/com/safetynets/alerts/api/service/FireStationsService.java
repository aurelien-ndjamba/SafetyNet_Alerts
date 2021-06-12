package com.safetynets.alerts.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.FireStationsModel;
import com.safetynets.alerts.api.repository.FirestationsRepository;

@Service
public class FireStationsService {

	@Autowired(required = false)
	FireStationsModel firestation;
	@Autowired
	FirestationsRepository firestationsRepository;

	// ----------------------------------------------------------------------------------------
	// CREATE "listIdsEntitiesFireStation" FROM FirestationsRepository
	// ----------------------------------------------------------------------------------------
	public List<Long> getlistIdsEntitiesFireStation() {
		
		List<FireStationsModel> listEntitiesFireStation = firestationsRepository.findAll();
		long CountIds = firestationsRepository.count();
		int id = 0;
		List<Long> listIdsEntitiesFireStation = new ArrayList<Long>();
		while (id != CountIds) {
			firestation = listEntitiesFireStation.get(id);
			listIdsEntitiesFireStation.add(firestation.getId());
			++id;
		}
		System.out.println(listIdsEntitiesFireStation);
		return listIdsEntitiesFireStation;
	}

	// ----------------------------------------------------------------------------------------
	// GET
	// ----------------------------------------------------------------------------------------
	public List<FireStationsModel> getAllFireStation() {
		return firestationsRepository.findAll();
	}

	// ----------------------------------------------------------------------------------------
	// POST OK Vérifier qu'il n'effectue pas de mise à jour
	// ----------------------------------------------------------------------------------------
	public FireStationsModel createFireStation(FireStationsModel newFirestation) {
		return firestationsRepository.save(newFirestation);
	}

	// ----------------------------------------------------------------------------------------
	// PUT OK // pourquoi renvoi une erreur quand je cherche en renoyer l'entité per
	// ----------------------------------------------------------------------------------------
	public boolean updateStationNumber(FireStationsModel firestationToUpdateStationNumber)
			throws IllegalArgumentException {
		
		boolean result = false;
		List<Long> listIdsEntitiesFireStation = new ArrayList<Long>();
		listIdsEntitiesFireStation = getlistIdsEntitiesFireStation();
		
		// Mise à jour du numéro de station en fonction de l'adresse de la caserne
		for (Long i : listIdsEntitiesFireStation) {
			firestation = firestationsRepository.getById(i);
			final long StationNumber = firestation.getStation();
			long newStationNumber = firestationToUpdateStationNumber.getStation();
			if (firestation.getAddress().equals(firestationToUpdateStationNumber.getAddress())) {
				firestation.setStation(newStationNumber);
				firestationsRepository.saveAndFlush(firestation); // à bien comprendre
				System.out.println("Numéro de la caserne ayant l'ID : " + i + " situé à l'adresse '"
						+ firestation.getAddress() + "' a été mis à jour avec succès ! -> l'ancien numéro de caserne '"
						+ StationNumber + "' a été remplacé par le nouveau numéro de caserne :" + newStationNumber);
				result = true;
				break;
			}
		}
		return result;
	}

	// ----------------------------------------------------------------------------------------
	// DELETE
	// ----------------------------------------------------------------------------------------

	public boolean deleteFireStation(String addressToDelete) throws IllegalArgumentException {
		
		boolean result = false;
		List<Long> listIdsEntitiesFireStation = new ArrayList<Long>();
		listIdsEntitiesFireStation = getlistIdsEntitiesFireStation();

		// Suppression caserne(s) identifiée(s) par numéro dans la BDD
		for (Long i : listIdsEntitiesFireStation) {
			firestation = firestationsRepository.getById(i);
			if (firestation.getAddress().equals(addressToDelete)) {
				firestationsRepository.delete(firestation);
				System.out.println("La caserne ayant pour adresse '" + addressToDelete
						+ "' a été supprimée de la base de donnée avec succès !");
				result = true;
				listIdsEntitiesFireStation.remove(i);
				break;
			}
		}
		return result;
	}
}