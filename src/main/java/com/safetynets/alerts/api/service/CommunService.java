package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.FireStationsModel;
import com.safetynets.alerts.api.model.MedicalRecordsModel;
import com.safetynets.alerts.api.model.PersonInfoModel;
import com.safetynets.alerts.api.model.PersonsModel;
import com.safetynets.alerts.api.repository.FirestationsRepository;
import com.safetynets.alerts.api.repository.MedicalrecordsRepository;
import com.safetynets.alerts.api.repository.PersonsRepository;

import lombok.Data;

@Data
@Service
public class CommunService {

	@Autowired
	private PersonsModel person;
	@Autowired
	private PersonsService personsService;
	@Autowired
	private PersonsRepository personsRepository;
	@Autowired
	private FireStationsModel fireStation;
	@Autowired
	private FireStationsService fireStationsService;
	@Autowired
	private FirestationsRepository firestationsRepository;
	@Autowired
	private MedicalRecordsModel medicalRecord;
	@Autowired
	private MedicalRecordsService medicalRecordsService;
	@Autowired
	private MedicalrecordsRepository medicalrecordsRepository;
	@Autowired
	private PersonInfoModel personInfo;
	@Autowired
	private AgeService ageService;
	@Autowired
	private MedicationsHistoryBylastNameService medicalHistoryMedicationsService;
	@Autowired
	private AllergiesHistoryBylastNameService allergiesHistoryBylastNameService;

	public Iterable<String> getFireStationWhenStationNumberGiven(int station_number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getChildrenListWithAddressGiven(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/phoneAlert?firestation=<firestation_number>  OK
	// ----------------------------------------------------------------------------------------
	public HashSet<String> phoneAlert(Long firestation) {

		List<Long> listIdsEntitiesPerson = personsService.getlistIdsEntitiesPerson();
		List<Long> listIdsEntitiesFireStations = fireStationsService.getlistIdsEntitiesFireStation();
		List<String> listAddressPhoneAlert = new ArrayList<String>();
		HashSet<String> listPhoneAlert = new HashSet<String>();

		for (Long i : listIdsEntitiesFireStations) {
			fireStation = firestationsRepository.getById(i);
			if (fireStation.getStation() == firestation) {
				listAddressPhoneAlert.add(fireStation.getAddress());
			}
		}
		for (Long j : listIdsEntitiesPerson) {
			person = personsRepository.getById(j);
			if (listAddressPhoneAlert.contains(person.getAddress())) {
				listPhoneAlert.add(person.getPhone());
			}
		}
		return listPhoneAlert;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/fire?address=<address>
	// ----------------------------------------------------------------------------------------
	public Iterable<String> getPeopleListAndFirestationWhenAddressGiven(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// ----------------------------------------------------------------------------------------
	public Iterable<String> getResidentListAndStationNumberWhenAddressGiven(int station_number) {
		// TODO Auto-generated method stub
		return null;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName> OK
	// ----------------------------------------------------------------------------------------

	public PersonInfoModel getPersonInfo(String firstName, String lastName) throws ParseException {

		HashSet<String> listMedicationsHistory = new HashSet<String>();
		HashSet<String> listAllergiesHistory = new HashSet<String>();
		List<Long> listIdsEntitiesPerson = personsService.getlistIdsEntitiesPerson();

		for (Long i : listIdsEntitiesPerson) {

			person = personsRepository.getById(i);

			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {

				personInfo.setId(i);
				personInfo.setFirstName(firstName);
				personInfo.setLastName(lastName);
				personInfo.setAddress(person.getAddress());
				int age = ageService.getAge(firstName, lastName);
				personInfo.setAge(age);
				personInfo.setEmail(person.getEmail());
				listMedicationsHistory = medicalHistoryMedicationsService.getMedicalHistory(lastName);
				personInfo.setMedications(listMedicationsHistory);
				listAllergiesHistory = allergiesHistoryBylastNameService.getMedicalHistory(lastName);
				personInfo.setAllergies(listAllergiesHistory);
			}
		}

		return personInfo;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/communityEmail?city=<city> OK
	// ----------------------------------------------------------------------------------------

	public List<String> getCommunityEmail(String city) {

		List<Long> listIdsEntitiesPerson = personsService.getlistIdsEntitiesPerson();
		List<String> listCommunityEmail = new ArrayList<String>();

		for (Long i : listIdsEntitiesPerson) {
			person = personsRepository.getById(i);
			if (person.getCity().equals(city)) {
				listCommunityEmail.add(person.getEmail());
			}
		}

		return listCommunityEmail;
	}

}
