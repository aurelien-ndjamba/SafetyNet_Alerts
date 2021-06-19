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
import com.safetynets.alerts.api.model.PersonInfoModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.model.PersonWithMedicalHistoryModel;
import com.safetynets.alerts.api.repository.FireStationRepository;
import com.safetynets.alerts.api.repository.PersonRepository;

@Service
public class CommunService {

	@Autowired
	private PersonModel person;
	@Autowired
	private PersonService personService;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private FireStationModel fireStation;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private FireStationRepository fireStationRepository;
	@Autowired
	private PersonInfoModel personInfo;
	@Autowired
	private MedicalHistoryService medicalHistory;
	@Autowired
	private FamilyRelationshipService familyRelationshipService;
	@Autowired
	private CountService countService;

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/childAlert?address=<address> OK
	// ----------------------------------------------------------------------------------------
	public ArrayList<ChildInfoModel> getChildInfo(String address) throws ParseException {

		List<Long> listIdsEntitiesPerson = personService.getlistIdsEntitiesPerson();
		ArrayList<ChildInfoModel> listChildInfo = new ArrayList<ChildInfoModel>(listIdsEntitiesPerson.size());
		int ageOfMajority = 18;
		int age;

		for (Long i : listIdsEntitiesPerson) {

			person = personRepository.getById(i);
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
	public HashSet<String> phoneAlert(Long firestation) {

		List<Long> listIdsEntitiesPerson = personService.getlistIdsEntitiesPerson();
		List<Long> listIdsEntitiesFireStations = fireStationService.getlistIdsEntitiesFireStation();
		List<String> listAddressPhoneAlert = new ArrayList<String>();
		HashSet<String> listPhoneAlert = new HashSet<String>();

		for (Long i : listIdsEntitiesFireStations) {
			fireStation = fireStationRepository.getById(i);
			if (fireStation.getStation() == firestation) {
				listAddressPhoneAlert.add(fireStation.getAddress());
			}
		}
		for (Long j : listIdsEntitiesPerson) {
			person = personRepository.getById(j);
			if (listAddressPhoneAlert.contains(person.getAddress())) {
				listPhoneAlert.add(person.getPhone());
			}
		}
		return listPhoneAlert;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/fire?address=<address> OK
	// ----------------------------------------------------------------------------------------
	public ArrayList<PersonWithMedicalHistoryModel> getResidentListAndFirestationWhenAddressGiven(String address)
			throws ParseException {

		List<Long> listIdsEntitiesPerson = personService.getlistIdsEntitiesPerson();
		ArrayList<PersonWithMedicalHistoryModel> listResident = new ArrayList<PersonWithMedicalHistoryModel>(
				listIdsEntitiesPerson.size());

		for (Long i : listIdsEntitiesPerson) {

			person = personRepository.getById(i);
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
	public List<HomeByFireStationModel> getPersonsByStation(List<Long> stations) throws ParseException {

		List<FireStationModel> listEntitiesFireStation = fireStationService.getAllFireStation();
		List<HomeByFireStationModel> listHomeByFireStation = new ArrayList<HomeByFireStationModel>();

		ArrayList<String> listAddress = new ArrayList<String>();

		for (int i = 0; i < listEntitiesFireStation.size(); i++) {

			if (stations.contains(listEntitiesFireStation.get(i).getStation())) {

				listAddress.add(listEntitiesFireStation.get(i).getAddress());
			}
		}

		for (int j = 0; j < listAddress.size(); j++) {
			HomeByFireStationModel homeByFireStationModel = new HomeByFireStationModel();
			homeByFireStationModel.setAddress(listAddress.get(j));
			homeByFireStationModel.setListPersonsByAddress(personService.getListPersonImpacted(listAddress.get(j)));
			listHomeByFireStation.add(homeByFireStationModel);
		}

		return listHomeByFireStation;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName> OK
	// ----------------------------------------------------------------------------------------
	public PersonInfoModel getPersonInfo(String firstName, String lastName) throws ParseException {

		HashSet<String> listMedicationsHistory = new HashSet<String>();
		HashSet<String> listAllergiesHistory = new HashSet<String>();
		List<Long> listIdsEntitiesPerson = personService.getlistIdsEntitiesPerson();

		for (Long i : listIdsEntitiesPerson) {

			person = personRepository.getById(i);

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
	public List<String> getCommunityEmail(String city) {

		List<Long> listIdsEntitiesPerson = personService.getlistIdsEntitiesPerson();
		List<String> listCommunityEmail = new ArrayList<String>();

		for (Long i : listIdsEntitiesPerson) {
			person = personRepository.getById(i);
			if (person.getCity().equals(city)) {
				listCommunityEmail.add(person.getEmail());
			}
		}

		return listCommunityEmail;
	}

}
