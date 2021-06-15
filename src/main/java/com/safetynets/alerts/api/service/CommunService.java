package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.ChildInfoModel;
import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.HomeByFireStationModel;
import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.model.PersonImpactedByStationNumberModel;
import com.safetynets.alerts.api.model.PersonInfoModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.model.PersonWithMedicalHistoryModel;
import com.safetynets.alerts.api.repository.FireStationRepository;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;
import com.safetynets.alerts.api.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class CommunService {

	@Autowired
	private PersonModel person;
	@Autowired
	private PersonsService personsService;
	@Autowired
	private PersonRepository personsRepository;
	@Autowired
	private FireStationModel fireStation;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private FireStationRepository firestationRepository;
	@Autowired
	private MedicalRecordModel medicalRecord; // NO USE
	@Autowired
	private MedicalRecordService medicalRecordService; // NO USE
	@Autowired
	private MedicalRecordRepository medicalRecordRepository; // NO USE
	@Autowired
	private PersonInfoModel personInfo;
	@Autowired
	private MedicalHistoryService medicalHistory;
	@Autowired
	private FamilyRelationshipService familyRelationshipService;
	@Autowired
	private CountService countService;
	@Autowired
	private PersonImpactedByStationNumberModel personImpacted;

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/firestation?stationNumber=<station_number> OK
	// ----------------------------------------------------------------------------------------
	/**
	 * Read - Get all CommunityEmail
	 * 
	 * @return - This url should return a list of people covered by the
	 *         corresponding fire station. So if the station number = 1, it should
	 *         return the inhabitants covered by station number 1. The list should
	 *         include the following specific information: first name, last name,
	 *         address, phone number. In addition, it must provide a count of the
	 *         number of adults and the number of children (any individual aged 18
	 *         or under) in the service area.
	 * 
	 * @throws ParseException
	 */
	public PersonImpactedByStationNumberModel getSpecificInfoPersonsImpacted(long stationNumber) throws ParseException {

		personImpacted.setStationNumber(stationNumber);
		personImpacted.setListSpecificInfoPersons(personsService.getListSpecificPersonImpacted(stationNumber));
		personImpacted.setCountAdult(countService.getCountAdult(stationNumber));
		personImpacted.setCountChildren(countService.getCountChildren(stationNumber));

		return personImpacted;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/childAlert?address=<address> OK
	// ----------------------------------------------------------------------------------------
	/**
	 * Read - Get all CommunityEmail
	 * 
	 * @return - This url should return a list of people covered by the
	 *         corresponding fire station. So if the station number = 1, it should
	 *         return the inhabitants covered by station number 1. The list should
	 *         include the following specific information: first name, last name,
	 *         address, phone number. In addition, it must provide a count of the
	 *         number of adults and the number of children (any individual aged 18
	 *         or under) in the service area.
	 * 
	 * @throws ParseException
	 */
	public ArrayList<ChildInfoModel> getChildInfo(String address) throws ParseException {

		List<Long> listIdsEntitiesPerson = personsService.getlistIdsEntitiesPerson();
		ArrayList<ChildInfoModel> listChildInfo = new ArrayList<ChildInfoModel>(listIdsEntitiesPerson.size());
		int ageOfMajority = 18;
		int age;

		for (Long i : listIdsEntitiesPerson) {

			person = personsRepository.getById(i);
			age = countService.getAge(person.getFirstName(), person.getLastName());

			if (person.getAddress().equals(address) && age <= ageOfMajority) {

				ChildInfoModel childInfo = new ChildInfoModel();

				childInfo.setId(person.getId());
				childInfo.setFirstName(person.getFirstName());
				childInfo.setLastName(person.getLastName());
				childInfo.setAge(age);
				childInfo.setFamilyRelationShip(familyRelationshipService.getBySameAddressAndName(address,
						person.getFirstName(), person.getLastName()));

				listChildInfo.add(childInfo);
			}
		}
		return listChildInfo;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/phoneAlert?firestation=<firestation_number> OK
	// ----------------------------------------------------------------------------------------
	/**
	 * Read - Get all CommunityEmail
	 * 
	 * @return - This url should return a list of people covered by the
	 *         corresponding fire station. So if the station number = 1, it should
	 *         return the inhabitants covered by station number 1. The list should
	 *         include the following specific information: first name, last name,
	 *         address, phone number. In addition, it must provide a count of the
	 *         number of adults and the number of children (any individual aged 18
	 *         or under) in the service area.
	 * 
	 * @throws ParseException
	 */
	public HashSet<String> phoneAlert(Long firestation) {

		List<Long> listIdsEntitiesPerson = personsService.getlistIdsEntitiesPerson();
		List<Long> listIdsEntitiesFireStations = fireStationService.getlistIdsEntitiesFireStation();
		List<String> listAddressPhoneAlert = new ArrayList<String>();
		HashSet<String> listPhoneAlert = new HashSet<String>();

		for (Long i : listIdsEntitiesFireStations) {
			fireStation = firestationRepository.getById(i);
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
	// http://localhost:8080/fire?address=<address> OK
	// ----------------------------------------------------------------------------------------
	/**
	 * Read - Get all CommunityEmail
	 * 
	 * @return - This url should return a list of people covered by the
	 *         corresponding fire station. So if the station number = 1, it should
	 *         return the inhabitants covered by station number 1. The list should
	 *         include the following specific information: first name, last name,
	 *         address, phone number. In addition, it must provide a count of the
	 *         number of adults and the number of children (any individual aged 18
	 *         or under) in the service area.
	 * 
	 * @throws ParseException
	 */
	public ArrayList<PersonWithMedicalHistoryModel> getResidentListAndFirestationWhenAddressGiven(String address)
			throws ParseException {

		List<Long> listIdsEntitiesPerson = personsService.getlistIdsEntitiesPerson();
		ArrayList<PersonWithMedicalHistoryModel> listResident = new ArrayList<PersonWithMedicalHistoryModel>(
				listIdsEntitiesPerson.size());

		for (Long i : listIdsEntitiesPerson) {

			person = personsRepository.getById(i);
			if (person.getAddress().equals(address)) {

				PersonWithMedicalHistoryModel resident = new PersonWithMedicalHistoryModel();

				resident.setId(person.getId());
				resident.setFirstName(person.getFirstName());
				resident.setLastName(person.getLastName());
				resident.setAddress(person.getAddress());
				resident.setStationNumber(fireStationService.getStationNumber(address));
				resident.setAge(countService.getAge(person.getFirstName(), person.getLastName()));
				resident.setPhone(person.getPhone());
				resident.setMedications(
						medicalHistory.getMedicationsHistory(person.getFirstName(), person.getLastName()));
				resident.setAllergies(medicalHistory.getAllergiesHistory(person.getFirstName(), person.getLastName()));
				listResident.add(resident);

			}
		}

		return listResident;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// ----------------------------------------------------------------------------------------
	public HomeByFireStationModel getPersonsByStation(List<Long> stations) {
		
		int NumberOfStations = stations.size();
		ArrayList<FireStation> listFireStation; 
		ArrayList<String> listAddress = new ArrayList<String>();
		
		for (int i = 0 ; i < NumberOfStations; i++) {
			for (int j = 0 ; j < sizeListFireStation; j++) {
			if ( listFireStation.get(j).getStation() = stations.get(i) ) {
				
			}
			}
			listFireStation
			
		}
		
		
		
		
		
		return null;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName> OK
	// ----------------------------------------------------------------------------------------
	/**
	 * Read - Get all CommunityEmail
	 * 
	 * @return - This url should return a list of people covered by the
	 *         corresponding fire station. So if the station number = 1, it should
	 *         return the inhabitants covered by station number 1. The list should
	 *         include the following specific information: first name, last name,
	 *         address, phone number. In addition, it must provide a count of the
	 *         number of adults and the number of children (any individual aged 18
	 *         or under) in the service area.
	 * 
	 * @throws ParseException
	 */
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
				int age = countService.getAge(firstName, lastName);
				personInfo.setAge(age);
				personInfo.setEmail(person.getEmail());
				listMedicationsHistory = medicalHistory.getMedicationsHistory(lastName);
				personInfo.setMedications(listMedicationsHistory);
				listAllergiesHistory = medicalHistory.getAllergiesHistory(lastName);
				personInfo.setAllergies(listAllergiesHistory);

			}
		}

		return personInfo;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/communityEmail?city=<city> OK
	// ----------------------------------------------------------------------------------------
	/**
	 * Read - Get all CommunityEmail
	 * 
	 * @return - This url should return a list of people covered by the
	 *         corresponding fire station. So if the station number = 1, it should
	 *         return the inhabitants covered by station number 1. The list should
	 *         include the following specific information: first name, last name,
	 *         address, phone number. In addition, it must provide a count of the
	 *         number of adults and the number of children (any individual aged 18
	 *         or under) in the service area.
	 * 
	 * @throws ParseException
	 */
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
