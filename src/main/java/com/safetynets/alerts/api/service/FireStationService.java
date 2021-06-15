package com.safetynets.alerts.api.service;

import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.repository.FireStationRepository;

@Service
public class FireStationService {

	@Autowired
	private FireStationModel fireStation;
	@Autowired
	private FireStationService fireStationsService;
	@Autowired
	private FireStationRepository fireStationRepository;

	
	// ----------------------------------------------------------------------------------------
	// CREATE "listIdsEntitiesFireStation" FROM FirestationsRepository
	// ----------------------------------------------------------------------------------------
	public List<Long> getlistIdsEntitiesFireStation() {

		List<FireStationModel> listEntitiesFireStation = fireStationRepository.findAll();
		long CountIds = fireStationRepository.count();
		int id = 0;
		List<Long> listIdsEntitiesFireStation = new ArrayList<Long>();
		while (id != CountIds) {
			fireStation = listEntitiesFireStation.get(id);
			listIdsEntitiesFireStation.add(fireStation.getId());
			++id;
		}
		return listIdsEntitiesFireStation;
	}

	// ----------------------------------------------------------------------------------------
	// GET
	// ----------------------------------------------------------------------------------------
	public List<FireStationModel> getAllFireStation() {
		return fireStationRepository.findAll();
	}
	
//	public Optional<FireStationModel> getFireStationById(Long id) {
//		return fireStationRepository.findById(id);
//	}

	// ----------------------------------------------------------------------------------------
	// POST OK Vérifier qu'il n'effectue pas de mise à jour
	// ----------------------------------------------------------------------------------------
	public FireStationModel createFireStation(FireStationModel newFirestation) {
		return fireStationRepository.save(newFirestation);
	}

	// ----------------------------------------------------------------------------------------
	// PUT OK // pourquoi renvoi une erreur quand je cherche en renoyer l'entité per
	// ----------------------------------------------------------------------------------------
	public boolean updateStationNumber(FireStationModel firestationToUpdateStationNumber)
			throws IllegalArgumentException {

		boolean result = false;
		List<Long> listIdsEntitiesFireStation = new ArrayList<Long>();
		listIdsEntitiesFireStation = getlistIdsEntitiesFireStation();

		// Mise à jour du numéro de station en fonction de l'adresse de la caserne
		for (Long i : listIdsEntitiesFireStation) {
			fireStation = fireStationRepository.getById(i);
			final long StationNumber = fireStation.getStation();
			long newStationNumber = firestationToUpdateStationNumber.getStation();
			if (fireStation.getAddress().equals(firestationToUpdateStationNumber.getAddress())) {
				fireStation.setStation(newStationNumber);
				fireStationRepository.saveAndFlush(fireStation); // à bien comprendre
				System.out.println("Numéro de la caserne ayant l'ID : " + i + " situé à l'adresse '"
						+ fireStation.getAddress() + "' a été mis à jour avec succès ! -> l'ancien numéro de caserne '"
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
			fireStation = fireStationRepository.getById(i);
			if (fireStation.getAddress().equals(addressToDelete)) {
				fireStationRepository.delete(fireStation);
				System.out.println("La caserne ayant pour adresse '" + addressToDelete
						+ "' a été supprimée de la base de donnée avec succès !");
				result = true;
				listIdsEntitiesFireStation.remove(i);
				break;
			}
		}
		return result;
	}

	// ----------------------------------------------------------------------------------------
	// Methode pour obtenir la liste des adresses concernées par un numéro de
	// caserne donné
	// ----------------------------------------------------------------------------------------
	public ArrayList<String> getListAdressImpactedByStationNumber(long stationNumber) {

		List<Long> listIdsEntitiesFireStation = new ArrayList<Long>();
		listIdsEntitiesFireStation = getlistIdsEntitiesFireStation();
		ArrayList<String> listAdressImpacted = new ArrayList<String>();

		// Suppression caserne(s) identifiée(s) par numéro dans la BDD
		for (Long i : listIdsEntitiesFireStation) {
			fireStation = fireStationRepository.getById(i);
			if (fireStation.getStation() == stationNumber) {
				listAdressImpacted.add(fireStation.getAddress());
			}
		}

		return listAdressImpacted;
	}

	// ----------------------------------------------------------------------------------------
	// Methode pour obtenir la liste des adresses concernées par un numéro de
	// caserne donné
	// ----------------------------------------------------------------------------------------
	public Long getStationNumber(String address) {

		List<Long> listIdsEntitiesFireStations = fireStationsService.getlistIdsEntitiesFireStation();
		Long stationNumberFromAdresse = null;

		for (Long i : listIdsEntitiesFireStations) {

			fireStation = fireStationRepository.getById(i);

			if (fireStation.getAddress().equals(address)) {
				stationNumberFromAdresse = fireStation.getStation();
				break;
			}
		}

		return stationNumberFromAdresse;
	}
}