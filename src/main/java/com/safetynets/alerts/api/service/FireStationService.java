package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.FireStationDataBaseModel;
import com.safetynets.alerts.api.model.InfoByStationNumber;
import com.safetynets.alerts.api.model.MedicalRecordDataBaseModel;
import com.safetynets.alerts.api.service.CountService;
import com.safetynets.alerts.api.repository.FireStationRepository;

@Service
public class FireStationService {

	@Autowired
	private PersonService personService;
	@Autowired
	private FireStationRepository fireStationRepository;
	@Autowired
	private CountService countService;

	// ----------------------------------------------------------------------------------------
	// GET ALL: Methode pour obtenir la liste de 'firestation' dans une BDD
	// ----------------------------------------------------------------------------------------
	public List<FireStationDataBaseModel> getAllFireStation() {
		return fireStationRepository.findAll();
	}

	// ----------------------------------------------------------------------------------------
	// Methode pour filtrer des 'firestations' à partir d'un numéro de station
	// ----------------------------------------------------------------------------------------
	public List<FireStationDataBaseModel> getFirestationsByStation(long station) {
		return fireStationRepository.findByStation(station);
	}
	
	// ----------------------------------------------------------------------------------------
	// Methode pour filtrer des 'firestations' à partir d'un numéro de station
	// ----------------------------------------------------------------------------------------
//	public Iterable<FireStationDataBaseModel> getFirestationsByManyStation(List<Long> stations) {
//		return fireStationRepository.findAllByStation(stations);
//	}
	
	// ----------------------------------------------------------------------------------------
	// Methode pour obtenir des adresses de station liées à un
	// numéro de caserne
	// ----------------------------------------------------------------------------------------
	public List<FireStationDataBaseModel> getFirestationsByAddress(String address) {
		return fireStationRepository.findByAddress(address);
	}
	
	// ----------------------------------------------------------------------------------------
	// Methode pour obtenir des adresses de station liées à un
	// numéro de caserne
	// ----------------------------------------------------------------------------------------
	public long getStationNumberByAddress(String address) {
		long result = 0;
		for (FireStationDataBaseModel fireStation : getFirestationsByAddress(address)) {
			if (fireStation.getAddress().equals(address))
				result = fireStation.getStation();
		}
		return result;
	}

	// ----------------------------------------------------------------------------------------
	// POST: Methode pour ajouter une 'firestation' dans la BDD
	// ----------------------------------------------------------------------------------------
	public FireStationDataBaseModel postFireStation(FireStationDataBaseModel fireStation) {
		fireStation.setId(null);
		return fireStationRepository.save(fireStation);
	}

	// ----------------------------------------------------------------------------------------
	// PUT: Methode pour mettre à jour les infos d'une 'firestation' dans la BDD
	// ----------------------------------------------------------------------------------------
	public boolean updateFireStation(FireStationDataBaseModel fireStation) throws IllegalArgumentException {
		boolean result = false;
		long i = 0;
		long j = 0;
		long countEntities = fireStationRepository.count();

		do {
			if ((fireStationRepository.existsById(i))
					&& fireStation.getAddress().equals(fireStationRepository.findById(i).get().getAddress())) {
				System.out.println("la valeur de i est: " + i);
				System.out.println("la valeur de i est: " + j);
				j++;
				fireStation.setId(i);
				fireStationRepository.saveAndFlush(fireStation);
				result = true;
				break;
			}
			System.out.println("la valeur de i est: " + i);
			System.out.println("la valeur de i est: " + j);
			i++;
			if (i == 5000)
				;
			break;
		} while (j != countEntities);

		return result;
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne par id
	// ----------------------------------------------------------------------------------------
	public void deleteFireStationById(long id) throws IllegalArgumentException {
		fireStationRepository.deleteById(id);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne à partir d'une entité
	// ----------------------------------------------------------------------------------------
	public void deleteFireStationByEntity(FireStationDataBaseModel fireStation) throws IllegalArgumentException {
		fireStationRepository.delete(fireStation);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne à partir son nom et prenom dans
	// la BDD
	// ----------------------------------------------------------------------------------------
	public void deleteFireStationByAddress(String address) throws IllegalArgumentException {
		fireStationRepository.deleteByAddress(address);
	}

	// ----------------------------------------------------------------------------------------
	// DELETE: Methode pour supprimer une personne à partir son nom et prenom dans
	// la BDD
	// ----------------------------------------------------------------------------------------
	public void deleteFireStationByStation(long station) throws IllegalArgumentException {
		fireStationRepository.deleteByStation(station);
	}

	// ----------------------------------------------------------------------------------------
	// Methode pour obtenir des informations spécifiques d'une station liées à un
	// numéro de caserne
	// ----------------------------------------------------------------------------------------
	public InfoByStationNumber getInfoByStationNumber(long station) throws ParseException {

		InfoByStationNumber infoByStationNumber = new InfoByStationNumber();

		infoByStationNumber.setStation(station);
		infoByStationNumber.setPersonsByStation(personService.getPersonsByStation(station));
		int countAdult = countService.getCountAdult(station);
		int countChildren = countService.getCountChildren(station);
		infoByStationNumber.setCountAdult(countAdult);
		infoByStationNumber.setCountChildren(countChildren);

		return infoByStationNumber;
	}

	// ----------------------------------------------------------------------------------------
	// Methode pour obtenir des informations spécifiques d'une station liées à un
	// numéro de caserne
	// ----------------------------------------------------------------------------------------
	public boolean existsById(long id) {
		return fireStationRepository.existsById(id);
	}

}